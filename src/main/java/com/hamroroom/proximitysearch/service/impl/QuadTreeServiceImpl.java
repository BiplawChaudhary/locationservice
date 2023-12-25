package com.hamroroom.proximitysearch.service.impl;

import com.hamroroom.proximitysearch.entity.Locations;
import com.hamroroom.proximitysearch.dsa.QuadTree;
import com.hamroroom.proximitysearch.service.LocationsService;
import com.hamroroom.proximitysearch.service.Neighbour;
import com.hamroroom.proximitysearch.service.QuadTreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class QuadTreeServiceImpl implements QuadTreeService {

    private final LocationsService locationsService;


    QuadTree quadtree = new QuadTree();

    @Override
    public boolean createInitialQuadTree() {
        List<Locations> allLocations = locationsService.getAllLocationsInTable();

        for(Locations each: allLocations){
            quadtree.addNeighbour(each.getId(), each.getLatitude(), each.getLongitude());
            log.info("Quad Tree Created Successfully for:" + each   );
        }
        return true;
    }

    @Override
    public boolean insertIntoQuadTree(UUID uuid) throws NameNotFoundException {

        Locations foundLocation = locationsService.getLocationById(uuid);

        quadtree.addNeighbour(foundLocation.getId(), foundLocation.getLatitude(), foundLocation.getLongitude());
        log.info("New node with UUID " + uuid +  " inserted into the quadtree successfully.");
        return true;
    }

    @Override
    public boolean deleteFromQuadTree(UUID id) {
        quadtree.removeNeighbour(id);
        log.info("New node with UUID " + id +  " deleted from quadtree successfully.");
        return true;
    }

    @Override
    public Set<Neighbour> searchForNeighbors(Double latitude, Double longitude, Double searchRadius) {
        return quadtree.findNeighbours(latitude, longitude, searchRadius);
    }

    @Override
    public Set<UUID> searchForNeighborsId(Double latitude, Double longitude, Double searchRadius) {
        return quadtree.findNeighboursIds(latitude, longitude, searchRadius);
    }

    @Override
    public void visualizeQuadTree() {
//        int depth = 100;
//
//        // Indent based on depth for better visualization
//        StringBuilder indent = new StringBuilder();
//        for (int i = 0; i < depth; i++) {
//            indent.append("  "); // Adjust spaces for indentation
//        }
//
//        // Display node information
//        System.out.println(indent.toString() + "Node: Latitude " + node.getLatitude() +
//                ", Longitude " + node.getLongitude() +
//                ", Width " + node.getWidth() +
//                ", Height " + node.getHeight());
//
//        // Visualize children nodes recursively
//        visualizeQuadTree(node.mTopLeftNode, depth + 1);
//        visualizeQuadTree(node.mTopRightNode, depth + 1);
//        visualizeQuadTree(node.mBottomLeftNode, depth + 1);
//        visualizeQuadTree(node.mBottomRightNode, depth + 1);
    }
}
