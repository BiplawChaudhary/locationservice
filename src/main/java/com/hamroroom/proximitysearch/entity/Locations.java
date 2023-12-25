package com.hamroroom.proximitysearch.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "location")
@Data
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "Latitude")
    private Double latitude;


    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "name")
    private String name;
}
