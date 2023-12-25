package com.hamroroom.proximitysearch.controller;


import com.hamroroom.proximitysearch.entity.Locations;
import com.hamroroom.proximitysearch.repo.LocationsRepo;
import com.hamroroom.proximitysearch.service.LocationsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationsController {

    private final LocationsRepo locationsRepo;


    @PostMapping("/save")
    @Operation(summary = "This is just test api. ", description = "Don't use this to persist the location data on DB.")
    public ResponseEntity<?> saveLocationData(@RequestParam("lat")Double latitude,
                                              @RequestParam("long")Double longitude,
                                              @RequestParam("name") String name){

        Locations newLocation  = new Locations();
        newLocation.setLatitude(latitude);
        newLocation.setLongitude(longitude);
        newLocation.setName(name);

        return new ResponseEntity<>(locationsRepo.save(newLocation), HttpStatus.OK);
    }
}
