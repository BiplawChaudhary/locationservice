package com.hamroroom.proximitysearch.service;

import com.hamroroom.proximitysearch.dto.response.NearestRoomDetails;

import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;

public interface KNNService {

    Set<NearestRoomDetails> getKthNearestRooms(Set<Neighbour> quadTreeResult, Double searchLat, Double searchLong);
}
