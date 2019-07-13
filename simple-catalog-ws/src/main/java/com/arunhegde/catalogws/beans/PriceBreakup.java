package com.arunhegde.catalogws.beans;

import java.util.HashSet;
import java.util.Set;

public class PriceBreakup {
	private String itemCode;
	private double mrp = 0;
	private Set<Discount> discounts;
	private double totalDiscount = 0;
	private double finalPrice = 0;
	
	public void addDiscount(final Discount discount) {
		if(discounts == null) {
			discounts = new HashSet<>();
		}
		
		discounts.add(discount);
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

}
