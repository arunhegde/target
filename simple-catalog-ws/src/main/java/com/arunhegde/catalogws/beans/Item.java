package com.arunhegde.catalogws.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dizitart.no2.objects.Id;

public class Item implements CatalogComponent {
	@Id
	private String code;

	private String label;
	private double mrp;
	private List<Category> categoryList;
	private Discount discount;
	private DiscountHandler nextHandler;
	private Set<String> paths;
	
	public Item() {
		
	}

	public Item(String label, double mrp) {
		super();
		this.label = label;
		this.mrp = mrp;
		this.code = generateCode("sku", label);
	}

	public Item(String label, double mrp, Discount discount) {
		super();
		this.label = label;
		this.mrp = mrp;
		this.discount = discount;
		this.code = generateCode("sku", label);
	}
	
	public void addPath(String path) {
		if(paths == null) {
			paths = new HashSet<>();
		}
		
		paths.add(path); 
	}
	
	public void removePath(String path) {
		if(paths != null) {
			paths.remove(path);
		}
	}

	public void addItemCategory(final Category category) {
		if (categoryList == null) {
			categoryList = new ArrayList<>();
		}

		categoryList.add(category);
	}

	public void removeItemFromCategory(final Category category) {
		categoryList.remove(category);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public PriceBreakup processDiscount(PriceBreakup priceBreakup) {
		if (priceBreakup == null) {
			priceBreakup = new PriceBreakup();
		}

		if(discount != null && discount.getDiscountPercent() > 0) {
			priceBreakup.addDiscount(discount);
		}
		
		
		return nextHandler.processDiscount(priceBreakup);
	}
	
	public Set<String> getPaths() {
		return paths;
	}

	@Override
	public void setNextDiscountHandler(DiscountHandler discountHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public Discount getDiscount() {
		return discount;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	@Override
	public List<CatalogComponent> getChildren() {
		return null;
	}
}
