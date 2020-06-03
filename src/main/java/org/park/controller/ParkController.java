package org.park.controller;

import java.util.List;

import org.park.entity.Location;
import org.park.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;

@RestController
@RequestMapping("/api/park")
public class ParkController {



    @Autowired
    private LocationRepository repo;
    
    @GetMapping("/hello")
    public String sayHello() {
        System.err.println("hello");
        return "hello";
    }

    @GetMapping("/")
    public List<Location> getAllParks(){
        return repo.findAll();
    }


    @GetMapping("/within")
    public List<Location> findParksWithin( @RequestParam("lat1") float lat1, 
                                            @RequestParam("lon1") float lon1,
                                            @RequestParam("lat2") float lat2, 
                                            @RequestParam("lon2") float lon2) {

        Box box = new Box(new Point(lon1, lat1 ), new Point(lon2, lat2));

        return repo.findByLocationWithin(box);
    }
    
}