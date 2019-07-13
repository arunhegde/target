package com.arunhegde.projects.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arunhegde.catalogws.beans.CatalogItemNotFoundException;
import com.arunhegde.catalogws.beans.CatalogServiceException;
import com.arunhegde.catalogws.beans.CatalogStore;
import com.arunhegde.catalogws.beans.Category;
import com.arunhegde.catalogws.beans.Item;
import com.arunhegde.catalogws.beans.PriceBreakup;
import com.arunhegde.catalogws.beans.PriceCalculator;

@Controller
public class CatalogServiceController {
	
	CatalogStore store = CatalogStore.getInstance();
	
	@RequestMapping(method = RequestMethod.GET, value="/catalog/category/{categoryCode}")
	@ResponseBody
	public Category getCategoryByCode(@PathVariable("categoryCode") String categoryCode) {
		return store.getCategory(categoryCode);
	}

	@RequestMapping(method = RequestMethod.GET, value="/catalog/item/{itemCode}")
	@ResponseBody
	public Item getItemByCode(@PathVariable("itemCode") String itemCode) {
		return store.getItem(itemCode);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/catalog/price/{itemCode}")
	@ResponseBody
	public PriceBreakup getItemPrice(@PathVariable("itemCode") String itemCode) {
		PriceCalculator pc = new PriceCalculator();
		Item item = store.getItem(itemCode);
		if(item == null) {
			throw new CatalogItemNotFoundException("itemCode not found");
		}
		
		PriceBreakup pb = pc.getPriceBreakup(item);
		return pb;
	}
}
