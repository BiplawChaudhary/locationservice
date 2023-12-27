package com.hamroroom.proximitysearch.service.impl;

import com.hamroroom.proximitysearch.service.IpLocation;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Traits;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Database;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpLocationImpl implements IpLocation {

    private final DatabaseReader databaseReader;

    @Override
    public Map<String, Object> getSpatialDataFromIp(String stringIp) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(stringIp);

        CityResponse response = databaseReader.city(ipAddress);

        if(response!=null && response.getCity()!=null){
            String continent = response.getContinent().getName();
            String country = response.getCountry().getName();
            String city = response.getCity().getName();
            Double latitude = response.getLocation().getLatitude();
            Double longitude = response.getLocation().getLongitude();
            String timezone = response.getLocation().getTimeZone();
            Integer averageIncome = response.getLocation().getAverageIncome();
            Integer populationDensity = response.getLocation().getPopulationDensity();
            Integer metroCode = response.getLocation().getMetroCode();



            Traits traits = response.getTraits();
            log.info(traits.getIsp());
            log.info(traits.getOrganization());

            log.info(latitude+":"+longitude+":"+city+":"+country+":"+continent+":"+timezone);
            log.info(averageIncome+":");
            log.info(populationDensity+":");
            log.info(metroCode+":");

            //Prepare a data map for return
            Map<String, Object> returnData = new HashMap<>();
            returnData.put("continent", continent);
            returnData.put("country", country);
            returnData.put("city", city);
            returnData.put("latitude", latitude);
            returnData.put("longitude", longitude);
            returnData.put("timezone", timezone);
            returnData.put("averageIncome", averageIncome);
            returnData.put("populationDensity", populationDensity);
            returnData.put("metroCode", metroCode);

            return returnData;
        }

        return null;
    }

}
