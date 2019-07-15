package com.arunhegde.catalogws.server.main;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.arunhegde.catalogws.controllers.CatalogServiceController;

@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * Tests application health
 * 
 * @author arunkumar
 *
 */
public class CatalogLaucherApplicationTest {
	@Autowired
    private CatalogServiceController controller;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
