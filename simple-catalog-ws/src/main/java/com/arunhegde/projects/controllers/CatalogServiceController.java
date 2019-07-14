package com.arunhegde.projects.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arunhegde.catalogws.beans.Catalog;
import com.arunhegde.catalogws.beans.CatalogItemNotFoundException;
import com.arunhegde.catalogws.beans.CatalogStore;
import com.arunhegde.catalogws.beans.Category;
import com.arunhegde.catalogws.beans.Item;
import com.arunhegde.catalogws.beans.PriceBreakup;
import com.arunhegde.catalogws.beans.PriceCalculator;

@Controller
/**
 * Defines REST API endpoints
 * 
 * @author arunkumar
 *
 */
@RequestMapping(value="/catalogws")
public class CatalogServiceController {
	
	CatalogStore store = CatalogStore.getInstance();
	
	@RequestMapping(method = RequestMethod.GET, value="/catalog")
	@ResponseBody
	public List<Catalog> getAllCatalog() {
		return store.getAllCatalog();
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/category", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> addCatagory(@RequestBody Category category) throws URISyntaxException {
		//TODO: Validate inputs
		
		category = store.createCategory(category);
		return ResponseEntity
			    .created(new URI(category.getId()))
			    .body(category);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/category/{id}")
	@ResponseBody
	public Category getCategoryByCode(@PathVariable("id") String id) {
		return store.getCategory(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/category")
	@ResponseBody
	public List<Category> findCategory(
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "code", required = false) String code) {
		return store.findCategory(query, code);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/item", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> addItem(@RequestBody Item item) throws URISyntaxException {
		//TODO: Validate inputs
		
		item = store.createItem(item);
		return ResponseEntity
			    .created(new URI(item.getId()))
			    .body(item);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/item/{id}")
	@ResponseBody
	public Item getItemByCode(@PathVariable("id") String id) {
		return store.getItem(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/item")
	@ResponseBody
	public List<Item> findItem(
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "code", required = false) String code) {
		return store.findItem(query, code);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/item/{id}/price")
	@ResponseBody
	public PriceBreakup getItemPrice(@PathVariable("id") String itemCode) {
		PriceCalculator pc = new PriceCalculator();
		Item item = store.getItem(itemCode);
		if(item == null) {
			throw new CatalogItemNotFoundException("itemCode not found");
		}
		
		PriceBreakup pb = pc.getPriceBreakup(item);
		return pb;
	}
}
