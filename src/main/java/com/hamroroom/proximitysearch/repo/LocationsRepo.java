package com.hamroroom.proximitysearch.repo;

import com.hamroroom.proximitysearch.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationsRepo extends JpaRepository<Locations, UUID> {
}
