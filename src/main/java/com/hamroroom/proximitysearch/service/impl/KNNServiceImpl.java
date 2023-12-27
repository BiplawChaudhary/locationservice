package com.hamroroom.proximitysearch.service.impl;

import com.hamroroom.proximitysearch.dto.response.NearestRoomDetails;
import com.hamroroom.proximitysearch.service.KNNService;
import com.hamroroom.proximitysearch.service.Neighbour;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class KNNServiceImpl implements KNNService {

    private final double EARTH_RADIUS = 6371.009;

    // Constants defining the Earth's ellipsoid parameters (WGS-84)
    private static final double SEMI_MAJOR_AXIS = 6378137.0; // Earth's semi-major axis in meters
    private static final double SEMI_MINOR_AXIS = 6356752.314245; // Earth's semi-minor axis in meters
    private static final double FLATTENING = 1 / 298.257223563; // Earth's flattening

    @Override
    public Set<NearestRoomDetails> getKthNearestRooms(Set<Neighbour> quadTreeResult, Double searchLat, Double searchLong) {

        log.info("Received Param: " + quadTreeResult);

        //Select * FROM location l INNER JOIN room r ON r.id = l.room_id;

        return quadTreeResult.stream()
                .map(each -> new NearestRoomDetails(each.getId(), calculateDistance(searchLat, searchLong, each.getLatitude(), each.getLongitude())))
                .sorted(Comparator.comparingDouble(NearestRoomDetails::getDistance))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    //Havershine Formula for calculating the distance.
    private double calculateDistance(double lat1, double long1, double lat2, double long2){
        // Convert latitude and longitude from degrees to radians
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(long2 - long1);

        // Calculate the differences in latitude and longitude, and their respective halves
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        // Calculate the central angle using the haversine formula
        double centralAngle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance using the Earth's radius and the central angle
        double distance = EARTH_RADIUS * centralAngle;

        return distance; // Distance between the two points in kilometers
    }
}
