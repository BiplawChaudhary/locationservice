package com.hamroroom.proximitysearch.mapper;

import com.hamroroom.proximitysearch.entity.Locations;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.List;
import java.util.UUID;

@Mapper
public interface LocationsRepo {

    @Select("SELECT id, latitude, longitude, name FROM location")
//    @Results(
//        {
//           @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "latitude", column = "latitude"),
//            @Result(property = "longitude", column = "longitude")
//        }
//    )
    List<Locations> findAll();

    @Select("SELECT id, latitude, longitude, name FROM location WHERE id=#{id}::uuid")
    Locations findById(@Param("id") UUID id);
}
