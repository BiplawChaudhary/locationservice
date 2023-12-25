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

//    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        // Convert latitude and longitude from degrees to radians
//        double phi1 = Math.toRadians(lat1);
//        double phi2 = Math.toRadians(lat2);
//        double lambda1 = Math.toRadians(lon1);
//        double lambda2 = Math.toRadians(lon2);
//
//        // Calculate differences in longitude and latitude
//        double deltaLambda = lambda2 - lambda1;
//
//        // Vincenty's formulae
//        double U1 = Math.atan((1 - FLATTENING) * Math.tan(phi1));
//        double U2 = Math.atan((1 - FLATTENING) * Math.tan(phi2));
//        double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
//        double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
//
//        double lambda = deltaLambda;
//        double lambdaP;
//        int iterLimit = 100; // Set a limit for iterations to avoid infinite loops
//
//        double cosSigma, sinSigma, sigma, cosSqAlpha, cos2SigmaM, sinLambda, cosLambda;
//        do {
//            sinLambda = Math.sin(lambda);
//            cosLambda = Math.cos(lambda);
//            double sinSqSigma = (cosU2 * sinLambda) * (cosU2 * sinLambda) +
//                    (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) *
//                            (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda);
//            sinSigma = Math.sqrt(sinSqSigma);
//            if (sinSigma == 0) {
//                return 0; // Coincident points
//            }
//            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
//            sigma = Math.atan2(sinSigma, cosSigma);
//            double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
//            cosSqAlpha = 1 - sinAlpha * sinAlpha;
//            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
//
//            double C = FLATTENING / 16 * cosSqAlpha * (4 + FLATTENING * (4 - 3 * cosSqAlpha));
//            lambdaP = lambda;
//            lambda = deltaLambda +
//                    (1 - C) * FLATTENING * sinAlpha *
//                            (sigma + C * sinSigma *
//                                    (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
//        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);
//
//        if (iterLimit == 0) {
//            throw new IllegalStateException("Vincenty's formulae failed to converge");
//        }
//
//        double uSq = cosSqAlpha * (SEMI_MAJOR_AXIS * SEMI_MAJOR_AXIS - SEMI_MINOR_AXIS * SEMI_MINOR_AXIS) / (SEMI_MINOR_AXIS * SEMI_MINOR_AXIS);
//        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
//        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
//        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 *
//                (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM *
//                        (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
//
//        double distance = SEMI_MINOR_AXIS * A * (sigma - deltaSigma);
//
//        return distance; // Distance in meters
//    }

}
