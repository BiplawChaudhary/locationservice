package com.hamroroom.proximitysearch.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
public class Locations {

    private UUID id;

    private Double latitude;

    private Double longitude;

    private String name;
}
