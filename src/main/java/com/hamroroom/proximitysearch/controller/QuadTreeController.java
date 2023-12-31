package com.hamroroom.proximitysearch.controller;


import com.hamroroom.proximitysearch.service.*;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import javax.security.auth.Subject;
import javax.xml.stream.Location;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
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
    private final IpLocation ipLocation;



    @GetMapping("/insert-into-quad-tree")
    @Operation(summary = "Inserts the data into quadtree", description = "@QueryParams \n name = id, type = UUID \n\nReturn Type -> boolean")
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
    @Operation(summary = "Get the nearest room based on input", description = "@QueryParams \n (name = lat, type = Double, for=latitude),(name = long, type = Double, for=longitude) \n (name = searchRadiusInKm, type = Double, for=radius Based Search)")
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


    @PostMapping("/get-ip-recommendation")
    @Operation(summary = "Get the room data based on currnet IP location", description = "Send Ip address in request body as plain text and the search radius as query param")
    public ResponseEntity<?> getRecommendationBasedOnIpAddress(@RequestBody String ipString, @RequestParam("searchRadius") Double searchRadius){
        log.info("Got Ip address: " + ipString);

        try{
            Map<String, Object> receivedData = ipLocation.getSpatialDataFromIp(ipString);

            return new ResponseEntity<>(quadTreeService.searchForNeighborsId( (Double) receivedData.get("latitude"), (Double) receivedData.get("longitude"), searchRadius), HttpStatus.OK);
        }catch (IOException | GeoIp2Exception ex2){
            log.error(ex2.toString());
            return new ResponseEntity<>("Exception OCcurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
