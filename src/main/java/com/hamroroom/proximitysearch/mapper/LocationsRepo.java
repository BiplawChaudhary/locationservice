package com.hamroroom.proximitysearch.mapper;

import com.hamroroom.proximitysearch.entity.Locations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.List;
import java.util.UUID;

@Mapper
public interface LocationsRepo {

    @Select("SELECT * from location")
    List<Locations> findAll();

    Locations findById(@Param("id")UUID id);
}
