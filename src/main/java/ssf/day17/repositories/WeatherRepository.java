package ssf.day17.repositories;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

// Interfaces with db of key=CART:<cartID>
// K: <cartID>, V: 
// {
//    "name":"<name>"
//    "address":"<addr>"
//    "phone":"<phone>"
//    "del-date":<name>
// }
@Repository     
public class WeatherRepository {
    private final Logger logger = Logger.getLogger(WeatherRepository.class.getName());

    // DependencyInject (DI) the RedisTemplate into ContactRepository
    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    // GET <city>
    public String getWeatherInfo(String city) {
        ValueOperations<String, String> valueOps = template.opsForValue();

        return valueOps.get(city);
    }

    public void cacheWeatherInfo(String city, String json, int min) {
        ValueOperations<String, String> valueOps = template.opsForValue();

        valueOps.set(city, json, min, TimeUnit.MINUTES);
    }
}
