package com.arunhegde.catalogws.server.main;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.arunhegde.catalogws.db.CatalogDAO;
import com.arunhegde.catalogws.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
/**
 * Tests webservice endpoints
 * 
 * @author arunkumar
 *
 */
public class WebMockTest {
	@Autowired
    private MockMvc mockMvc;
	
    @MockBean
    private CatalogDAO dao;
    
    @BeforeClass
    public static void setupDb() {
    	TestUtils.buildTestCatalogStore();
    }
    
    
    @AfterClass
    public static void tearDown() {
    	TestUtils.cleanupTestCatalogStore(CatalogDAO.DB_FILE);
    }

   @Test
    public void shouldReturnWelcomeMessage() throws Exception {
        this.mockMvc.perform(get("/catalogws/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to simple catalog service!")));
    }
    
    @Test
    public void shouldReturnCatalog() throws Exception {
        mockMvc.perform(get("/catalogws/catalog")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    
    @Test
    public void shouldReturnCategory() throws Exception {
        mockMvc.perform(get("/catalogws/category")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }
    
    @Test
    public void searchCategoryByCode() throws Exception {
        mockMvc.perform(get("/catalogws/category?code=cat-baby")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    
    @Test
    public void searchCategoryByQuery() throws Exception {
        mockMvc.perform(get("/catalogws/category?q=toys")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(6)));
    }
    
    @Test
    public void shouldReturnItem() throws Exception {
        this.mockMvc.perform(get("/catalogws/category")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("cat-baby")));
        
        mockMvc.perform(get("/catalogws/item")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }
    
    @Test
    public void searchItemByCode() throws Exception {
        mockMvc.perform(get("/catalogws/item?code=sku-soft-rubber-ball")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    
    @Test
    public void searchItemByQuery() throws Exception {
        mockMvc.perform(get("/catalogws/item?q=car")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    
   @Test
    public void searchPrice() throws Exception {
        mockMvc.perform(get("/catalogws/item-by-code/sku-soft-rubber-ball/price")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discounts", hasSize(3)))
                .andExpect(jsonPath("$.totalDiscount", equalTo(18.0)))
                .andExpect(jsonPath("$.finalPrice", equalTo(12.3)));
        
        mockMvc.perform(get("/catalogws/item-by-code/sku-toy-dumbell/price")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discounts", hasSize(2)))
                .andExpect(jsonPath("$.totalDiscount", equalTo(15.0)))
                .andExpect(jsonPath("$.finalPrice", equalTo(8.5)));
    }
}