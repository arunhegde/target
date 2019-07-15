package com.arunhegde.catalogws.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.arunhegde.catalogws.util.Utils;

/**
 * The main class to launch springboot application that a some sample data to
 * catalog
 * 
 * @author arunkumar
 *
 */
@SpringBootApplication(scanBasePackages = { "com.arunhegde.catalogws" })
public class CatalogLaucherApplication {
	
	public static void main(String[] args) {

		if (args.length > 0 && args[0].equalsIgnoreCase("testMode")) {
			Utils.buildTestCatalogStore();
		}

		SpringApplication.run(CatalogLaucherApplication.class, args);
	}
}
