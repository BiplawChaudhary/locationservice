package com.hamroroom.proximitysearch.config;



import com.hamroroom.proximitysearch.service.QuadTreeService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitialConfig {
    //Injecting the locations service.
    @Autowired
    private QuadTreeService quadTreeService;

    //Creating a quadtree Structure for the applications use


    //Fetch all the locations in the database on application start up and then
    //create the quadtree structure for the data.
    //Then on each insertion to the Db of new location,
    //another api is needed to be hit with the uuid of the inserted locaiton.

//    @PostConstruct
//    public void createQuadTreeFromLocationsInDB(){
//        if(quadTreeService.createInitialQuadTree()){
//            log.info("Initial Quad Tree Created.");
//        }
//        else {
//            log.info("Initial Quad Tree Creation Failed.");
//        }
//    }
}
