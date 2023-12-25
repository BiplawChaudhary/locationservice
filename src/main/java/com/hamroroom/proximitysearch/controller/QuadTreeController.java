package com.hamroroom.proximitysearch.controller;


import com.hamroroom.proximitysearch.service.KNNService;
import com.hamroroom.proximitysearch.service.LocationsService;
import com.hamroroom.proximitysearch.service.Neighbour;
import com.hamroroom.proximitysearch.service.QuadTreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameNotFoundException;
import javax.xml.stream.Location;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/spatial-data")
public class QuadTreeController {

    private final QuadTreeService quadTreeService;
    private final KNNService knnService;
    private final LocationsService locationsService;



    @GetMapping("/insert-into-quad-tree")
    public ResponseEntity<?> createQuadTree(@RequestParam("id")UUID id){
        try{
            log.info("Controller Insert INTO QUAD TREE -> UUID: " + id);
            return new ResponseEntity<>(quadTreeService.insertIntoQuadTree(id), HttpStatus.OK);
        }catch (NameNotFoundException ex){
            return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
        }
    }

    //The return result will the
    // Room UUID with the distance from the entered location in sorted order.
    //Then when this API is hit, django application will receive the data
    //Removing the need to rehit the same api, if user enters different radius.
    @GetMapping("/get-nearest-rooms")
    public ResponseEntity<?> searchForNearestRooms(@RequestParam("lat")Double latitude,
                                                   @RequestParam("long")Double longitude,
                                                   @RequestParam("searchRadiusInKm") Double searchRadius){

        //First Obtain the result from the quadtree for the given radius
        Set<UUID> proximityResultUUID = quadTreeService.searchForNeighborsId(latitude, longitude, searchRadius);

        Set<Neighbour> proximityResult = locationsService.getAllNeighboursById(proximityResultUUID);

        log.info("Result Set Callled: -> lat: " + latitude + ", long: " + longitude + ", searchRadius: " + searchRadius +"-> " + searchRadius);
        //Then send it to KNN to obtain the result of the proximity based result.
        return new ResponseEntity<>(knnService.getKthNearestRooms(proximityResult, latitude, longitude), HttpStatus.OK);
    }


    // ************ TEST API
    @GetMapping("test/get-nearest-rooms")
    public ResponseEntity<?> searchForNearestRoomsTest(@RequestParam("lat")Double latitude,
                                                   @RequestParam("long")Double longitude,
                                                   @RequestParam("searchRadiusInKm") Double searchRadius){


        //Then send it to KNN to obtain the result of the proximity based result.
        return new ResponseEntity<>(quadTreeService.searchForNeighborsId(latitude, longitude, searchRadius), HttpStatus.OK);
    }
}
