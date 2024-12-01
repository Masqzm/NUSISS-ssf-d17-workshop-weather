package ssf.day17.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ssf.day17.models.WeatherInfo;
import ssf.day17.services.WeatherService;

@Controller
@RequestMapping
public class WeatherController {

    @Autowired
    WeatherService weatherSvc;

    @GetMapping("/search")
    public ModelAndView getWeather(@RequestParam String city) {
        ModelAndView mav = new ModelAndView();

        String weatherInfo = weatherSvc.getWeatherInfoJSON(city);

        WeatherInfo wInfo = WeatherInfo.jsonToWeatherInfo(weatherInfo);

        // 200
        mav.setViewName("weather-info");
        mav.addObject("weatherInfo", weatherInfo);
        mav.setStatus(HttpStatusCode.valueOf(200));
        
        return mav;
    }
}
