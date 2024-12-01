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

    @Value("${openweathermap.api-key}")
    private String apiKey;

    // REST call
    public String getWeatherInfoJSON(String city) {
        String url = UriComponentsBuilder.fromUriString(GET_URL)
                    .queryParam("q", city)
                    .queryParam("units", "metrics")
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
            // Extract and return response body
            return ex.getResponseBodyAsString();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // db lookup
    public WeatherInfo getWeatherInfo(String city) {

        WeatherInfo wInfo = new WeatherInfo();

        return wInfo;
    }
}
