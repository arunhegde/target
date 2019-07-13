package com.arunhegde.catalogws.beans;

public class Discount implements Deal {
	private static final String PROMO_CODE = "PERCENTAGE_DISCOUNT";
	private String label;
	private double discountPercent;
	
	public Discount(String label, double discountPercent) {
		super();
		this.label = label;
		this.discountPercent = discountPercent;
	}

	@Override
	public String getLabel() {
		return label;
	}
	
	@Override
	public String getPromoCode() {
		return PROMO_CODE;
	}


	public void setLabel(String label) {
		this.label = label;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

}
