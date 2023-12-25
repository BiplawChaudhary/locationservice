package com.hamroroom.proximitysearch;

import com.hamroroom.proximitysearch.service.QuadTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProximitysearchApplication implements CommandLineRunner {
	@Autowired
	private QuadTreeService quadTreeService;



	public static void main(String[] args) {
		SpringApplication.run(ProximitysearchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		quadTreeService.createInitialQuadTree();
	}
}