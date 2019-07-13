package com.arunhegde.catalogws.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.arunhegde.projects"})
public class CatalogLaucher {
	public static void main(String[] args) {
		SpringApplication.run(CatalogLaucher.class, args);
	}
}
