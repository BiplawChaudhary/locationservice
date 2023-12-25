package com.hamroroom.proximitysearch.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class NearestRoomDetails {
    private UUID uuid;
    private Double distance;

}
