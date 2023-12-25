package com.hamroroom.proximitysearch.service.impl;


import com.hamroroom.proximitysearch.entity.Locations;
import com.hamroroom.proximitysearch.repo.LocationsRepo;
import com.hamroroom.proximitysearch.service.LocationsService;
import com.hamroroom.proximitysearch.service.Neighbour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LocationsServiceImpl implements LocationsService {

    private final LocationsRepo locationsRepo;


    @Override
    public List<Locations> getAllLocationsInTable() {
        return locationsRepo.findAll();
    }

    @Override
    public Locations getLocationById(UUID id) throws NameNotFoundException {
        Optional<Locations> foundLocation = locationsRepo.findById(id);
        return foundLocation.orElseThrow(NameNotFoundException::new);
    }

    @Override
    public Set<Neighbour> getAllNeighboursById(Set<UUID> idSet) {
        Set<Neighbour> foundData = new LinkedHashSet<>();

        for(var each: idSet){
            Locations foundLocations = locationsRepo.findById(each).get();
            foundData.add(new NeighbourImpl(foundLocations.getId(), foundLocations.getLatitude(), foundLocations.getLongitude()));
        }
        return foundData;
    }
}
