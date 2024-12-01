package ssf.day17.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ssf.day17.models.WeatherInfo;
import ssf.day17.repositories.WeatherRepository;

// Handles order logic/queries
@Service
public class WeatherService {
    @Autowired
    private WeatherRepository weatherRepo;

    public static final String GET_URL = "https://api.openweathermap.org/data/2.5/weather";
    public static final int CACHE_EXPIRY_MIN = 10;

    @Value("${openweathermap.api-key}")
    private String apiKey;

    public WeatherInfo getWeatherInfo(String city) {
        String json = null;

        // Lookup db cache
        json = weatherRepo.getWeatherInfo(city);

        // Do REST call if can't find in db
        if(json == null) {
            json = fetchWeatherInfoJSON(city);

            // return null if data not avail (most prob 404)
            if(json == null)
                return null;

            // Cache result if avail
            weatherRepo.cacheWeatherInfo(city, json, CACHE_EXPIRY_MIN);
            
            return WeatherInfo.jsonToWeatherInfo(json);
        }
        
        WeatherInfo wInfo = WeatherInfo.jsonToWeatherInfo(json);
        wInfo.setFromCache(true);

        return wInfo;
    }

    // REST call
    public String fetchWeatherInfoJSON(String city) {
        String url = UriComponentsBuilder.fromUriString(GET_URL)
                    .queryParam("q", city)
                    .queryParam("units", "metric")
                    .queryParam("appid", apiKey)
                    .toUriString();

        RequestEntity<Void> req = RequestEntity
                    .get(url)
                    .accept(MediaType.APPLICATION_JSON)     
                    .build();
                    
        // Create REST template
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try {
            // Make call
            resp = template.exchange(req, String.class);
            // Extract payload
            String payload = resp.getBody();

            return payload;
        } catch(HttpClientErrorException.NotFound ex) {
            //System.out.println(ex.getResponseBodyAsString());
            return null;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
