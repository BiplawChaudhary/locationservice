package com.hamroroom.proximitysearch.service;

import com.hamroroom.proximitysearch.entity.Locations;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface LocationsService {
    List<Locations> getAllLocationsInTable();

    Locations getLocationById(UUID id) throws NameNotFoundException;

    Set<Neighbour> getAllNeighboursById(Set<UUID> idSet);
}
