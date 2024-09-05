package dev.anzala.bicyclelocator.Controllers;


import dev.anzala.bicyclelocator.Dtos.ResponseDto;
import dev.anzala.bicyclelocator.Service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
public class BiCycleLocatorController {

    private final Jedis jedis;
    private BicycleService bicycleService;

    @Autowired
    public BiCycleLocatorController(Jedis jedis, BicycleService bicycleService) {
        this.jedis = jedis;
        this.bicycleService = bicycleService;
    }


    @GetMapping("/nearestBicycle")
    public ResponseDto locate(@RequestParam double latitude, @RequestParam double longitude) {
        return bicycleService.getNearestBicycle(latitude,longitude);
    }
}