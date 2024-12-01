package ssf.day17.models;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class WeatherInfo {
    
    // Using list here as there is a possiblity of multiple weather condition in city
    private List<WCondition> wConditionsList = new ArrayList<>();
    // WCondtion
    // - String condition;
    // - String description;
    // - String iconURL;

    private String city;
    private double temp;         // °C
    private double tempFeels;    // °C
    private double tempMin;      // °C
    private double tempMax;      // °C
    private double windSpeed;    // m/s
    private double windDir;      // deg
    private int pressure;     // hPa
    private int humidity;     // %
    private int visibility;   // m
    private int cloudiness;   // %

    private boolean isFromCache = false;

    private LocalDateTime timeMeasured;
    private LocalDateTime timeSunrise;
    private LocalDateTime timeSunset;

    private static final String ICON_URL_PREFIX = "https://openweathermap.org/img/wn/";
    private static final String ICON_URL_SUFFIX = "@2x.png";

    public static WeatherInfo jsonToWeatherInfo(String json) {
        if(json == null)
            return null;
            
        WeatherInfo wInfo = new WeatherInfo();

        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObj = reader.readObject();
        JsonArray weatherArr = jsonObj.getJsonArray("weather");
        List<WCondition> conditionsList = new ArrayList<>();

        for(int i = 0; i < weatherArr.size(); i++) {
            WCondition wCondition = new WCondition();
            JsonObject j = weatherArr.getJsonObject(i);

            wCondition.setCondition(j.getString("main"));
            wCondition.setDescription(j.getString("description"));            
            wCondition.setIconURL(ICON_URL_PREFIX + j.getString("icon") + ICON_URL_SUFFIX);

            conditionsList.add(wCondition);
        }

        wInfo.setwConditionsList(conditionsList);

        wInfo.setCity(jsonObj.getString("name"));

        JsonObject mainInfo = jsonObj.getJsonObject("main");
        wInfo.setTemp(mainInfo.getJsonNumber("temp").doubleValue());
        wInfo.setTempFeels(mainInfo.getJsonNumber("feels_like").doubleValue());
        wInfo.setTempMin(mainInfo.getJsonNumber("temp_min").doubleValue());
        wInfo.setTempMax(mainInfo.getJsonNumber("temp_max").doubleValue());
        wInfo.setPressure(mainInfo.getInt("pressure"));
        wInfo.setHumidity(mainInfo.getInt("humidity"));

        wInfo.setVisibility(jsonObj.getInt("visibility"));

        wInfo.setCloudiness(jsonObj.getJsonObject("clouds").getInt("all"));

        JsonObject windInfo = jsonObj.getJsonObject("wind");
        wInfo.setWindSpeed(windInfo.getJsonNumber("speed").doubleValue());
        wInfo.setWindDir(windInfo.getJsonNumber("deg").doubleValue());
        
        wInfo.setTimeMeasured(unixToLocalDT(jsonObj.getJsonNumber("dt").longValue()));

        JsonObject sysInfo = jsonObj.getJsonObject("sys");
        wInfo.setTimeSunrise(unixToLocalDT(sysInfo.getJsonNumber("sunrise").longValue()));
        wInfo.setTimeSunset(unixToLocalDT(sysInfo.getJsonNumber("sunset").longValue()));
        
        return wInfo;
    } 

    private static LocalDateTime unixToLocalDT(long unixTimestamp) {
        // Convert Unix timestamp to an Instant
        Instant instant = Instant.ofEpochSecond(unixTimestamp);

        // Convert Instant to LocalDateTime in the system's default time zone
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    
    @Override
    public String toString() {
        return "WeatherInfo [wConditionsList=" + wConditionsList + ", city=" + city + ", temp=" + temp + ", tempFeels="
                + tempFeels + ", tempMin=" + tempMin + ", tempMax=" + tempMax + ", pressure=" + pressure + ", humidity="
                + humidity + ", visibility=" + visibility + ", windSpeed=" + windSpeed + ", windDir=" + windDir
                + ", cloudiness=" + cloudiness + ", timeMeasured=" + timeMeasured + ", timeSunrise=" + timeSunrise
                + ", timeSunset=" + timeSunset + "]";
    }    

    public boolean isFromCache() {
        return isFromCache;
    }
    public void setFromCache(boolean isFromCache) {
        this.isFromCache = isFromCache;
    }

    public List<WCondition> getwConditionsList() {
        return wConditionsList;
    }
    public void setwConditionsList(List<WCondition> wConditionsList) {
        this.wConditionsList = wConditionsList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }    

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempFeels() {
        return tempFeels;
    }

    public void setTempFeels(double tempFeels) {
        this.tempFeels = tempFeels;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDir() {
        return windDir;
    }

    public void setWindDir(double windDir) {
        this.windDir = windDir;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    public LocalDateTime getTimeMeasured() {
        return timeMeasured;
    }

    public void setTimeMeasured(LocalDateTime timeMeasured) {
        this.timeMeasured = timeMeasured;
    }

    public LocalDateTime getTimeSunrise() {
        return timeSunrise;
    }

    public void setTimeSunrise(LocalDateTime timeSunrise) {
        this.timeSunrise = timeSunrise;
    }

    public LocalDateTime getTimeSunset() {
        return timeSunset;
    }

    public void setTimeSunset(LocalDateTime timeSunset) {
        this.timeSunset = timeSunset;
    }
}
