package org.park.repository;

import java.util.List;

import org.park.entity.Location;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {

    List<Location> findByLocationWithin(Box box);
    
}