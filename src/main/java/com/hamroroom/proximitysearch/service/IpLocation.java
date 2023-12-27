package com.hamroroom.proximitysearch.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

public interface IpLocation {
    Map<String , Object> getSpatialDataFromIp(String ipAddress) throws IOException, GeoIp2Exception;

}
