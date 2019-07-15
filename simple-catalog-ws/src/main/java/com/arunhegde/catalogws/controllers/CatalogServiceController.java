package com.arunhegde.catalogws.controllers;

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
import com.arunhegde.catalogws.beans.Category;
import com.arunhegde.catalogws.beans.Item;
import com.arunhegde.catalogws.beans.PriceBreakup;
import com.arunhegde.catalogws.beans.PriceCalculator;
import com.arunhegde.catalogws.db.CatalogDAO;

@Controller
/**
 * Defines REST API end points
 * 
 * @author arunkumar
 *
 */
@RequestMapping(value="/catalogws")
public class CatalogServiceController {
	
	CatalogDAO dao = CatalogDAO.getInstance();
	
	@RequestMapping(method = RequestMethod.GET, value="/")
	@ResponseBody
	public String getWelcomeMessage() {
		return "Welcome to simple catalog service!";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/catalog")
	@ResponseBody
	public List<Catalog> getAllCatalog() {
		return dao.getAllCatalog();
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/category", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> addCatagory(@RequestBody Category category) throws URISyntaxException {
		//TODO: Validate inputs
		
		category = dao.createCategory(category);
		return ResponseEntity
			    .created(new URI(category.getId()))
			    .body(category);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/category/{id}")
	@ResponseBody
	public Category getCategoryByCode(@PathVariable("id") String id) {
		return dao.getCategory(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/category")
	@ResponseBody
	public List<Category> findCategory(
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "code", required = false) String code) {
		return dao.findCategory(query, code);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/item", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> addItem(@RequestBody Item item) throws URISyntaxException {
		//TODO: Validate inputs
		
		item = dao.createItem(item);
		return ResponseEntity
			    .created(new URI(item.getId()))
			    .body(item);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/item/{id}")
	@ResponseBody
	public Item getItemByCode(@PathVariable("id") String id) {
		return dao.getItem(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/item")
	@ResponseBody
	public List<Item> findItem(
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "code", required = false) String code) {
		return dao.findItem(query, code);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/item/{id}/price")
	@ResponseBody
	public PriceBreakup getItemPrice(@PathVariable("id") String id) {
		PriceCalculator pc = new PriceCalculator();
		Item item = dao.getItem(id);
		if(item == null) {
			throw new CatalogItemNotFoundException("itemCode not found");
		}
		
		PriceBreakup pb = pc.getPriceBreakup(item);
		return pb;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/item-by-code/{code}/price")
	@ResponseBody
	public PriceBreakup getItemPriceByItemCode(@PathVariable("code") String code) {
		PriceCalculator pc = new PriceCalculator();
		Item item = dao.getItemByCode(code);
		if(item == null) {
			throw new CatalogItemNotFoundException("code not found");
		}
		
		PriceBreakup pb = pc.getPriceBreakup(item);
		return pb;
	}
}
