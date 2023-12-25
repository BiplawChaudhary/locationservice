package com.hamroroom.proximitysearch.service;

import javax.naming.NameNotFoundException;
import java.util.Set;
import java.util.UUID;

public interface QuadTreeService {


    boolean createInitialQuadTree();

    boolean insertIntoQuadTree(UUID uuid) throws NameNotFoundException;
    boolean deleteFromQuadTree(UUID id);

    Set<Neighbour> searchForNeighbors(Double latitude, Double longitude, Double searchRadius);
    Set<UUID> searchForNeighborsId(Double latitude, Double longitude, Double searchRadius);

    void visualizeQuadTree();
}
