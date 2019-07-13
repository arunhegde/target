package com.arunhegde.catalogws.beans;

public interface DiscountHandler {
	public PriceBreakup processDiscount(PriceBreakup priceBreakup);
	void setNextDiscountHandler(DiscountHandler discountHandler);
}
