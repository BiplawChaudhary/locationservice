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

    private Double latitude;

    private Double longitude;
    private String name;
}
