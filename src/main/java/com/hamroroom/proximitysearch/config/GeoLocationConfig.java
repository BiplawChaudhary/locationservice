package com.hamroroom.proximitysearch.config;


import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class GeoLocationConfig {

    private static DatabaseReader reader = null;
    private final ResourceLoader resourceLoader;


    @Bean
    public DatabaseReader databaseReader() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:geo/GeoLite2-City.mmdb");
        InputStream inputStream = resource.getInputStream();

        return new DatabaseReader.Builder(inputStream).fileMode(Reader.FileMode.MEMORY).build();
    }
}
