package com.arunhegde.catalogws.beans;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

public class PriceCalculator {
	CatalogStore store = CatalogStore.getInstance();

	public PriceBreakup getPriceBreakup(final Item item) {
		PriceBreakup priceBreakup = new PriceBreakup();
		priceBreakup.setMrp(item.getMrp());
		priceBreakup.setItemCode(item.getCode());

		Set<Discount> discounts = getAllDiscounts(item);
		priceBreakup.setDiscounts(discounts);

		double totalDiscount = discounts.stream().mapToDouble(d -> d.getDiscountPercent()).sum();
		discounts.forEach(d -> {
			System.out.println(d.getDiscountPercent());

		});

		priceBreakup.setTotalDiscount(totalDiscount);

		double finalPrice = item.getMrp() - (item.getMrp() * totalDiscount) / 100;
		priceBreakup.setFinalPrice(finalPrice);

		return priceBreakup;
	}

	public Set<Discount> getAllDiscounts(final Item item) {
		if (item == null) {
			return null;
		}

		Set<Discount> discounts = new HashSet<>();
		item.getPaths().forEach(path -> {
			discounts.addAll(getAllDiscountsFromPath(path));
		});

		return discounts;
	}

	private Set<Discount> getAllDiscountsFromPath(String path) {

		Set<Discount> discounts = new HashSet<Discount>();
		if (path != null) {
			List<String> codes = Arrays.asList(path.split("/"));

			for (String code : codes) {
				if (!StringUtils.isEmpty(code)) {
					Discount discount = getCatalogComponentByCode(code).getDiscount();
					if (discount != null) {
						discounts.add(discount);
					}
				}
			}
		}

		return discounts;
	}

	private CatalogComponent getCatalogComponentByCode(final String code) {
		if (code.startsWith("cat-")) {
			return store.getCategoryByCode(code);
		} else if (code.startsWith("sku-")) {
			return store.getItemByCode(code);
		} else {
			throw new CatalogServiceException(String.format("Invalid code %s", code));
		}
	}
}
