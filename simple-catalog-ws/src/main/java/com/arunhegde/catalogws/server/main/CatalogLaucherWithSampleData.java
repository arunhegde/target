package com.arunhegde.catalogws.server.main;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.arunhegde.catalogws.beans.CatalogStore;
import com.arunhegde.catalogws.beans.Category;
import com.arunhegde.catalogws.beans.Discount;
import com.arunhegde.catalogws.beans.Item;

@SpringBootApplication(scanBasePackages = {"com.arunhegde.projects"})
public class CatalogLaucherWithSampleData {
	private static final String testDbFile = "/tmp/test-catalog-ws.db";

	public static void main(String[] args) {
		buildCatalogStore();
		SpringApplication.run(CatalogLaucherWithSampleData.class, args);
	}
	
	private static void buildCatalogStore() {
		//Delete old testDBFile
		File file = new File(testDbFile);
		if(file.exists()) {
			file.delete();
		}
		
		CatalogStore store = CatalogStore.getInstance(testDbFile);
		
		//Add super category baby
		Category baby = new Category("Baby", "Baby products");
		store.createCategory(baby);
		
		//Add clothing and shoes sub categories to baby
		Category clothing = new Category("Clothing", "Baby cloths");
		store.createCategory(clothing);
		
		Category shoes = new Category("Shoes", "Baby shoes");
		store.createCategory(shoes);
		
		baby.addSubCategory(clothing);
		baby.addSubCategory(shoes);
		store.updateCategory(baby);
		
		//Add toys category with 10% discount
		Category toys = new Category("Toys", "Baby toys", new Discount("10% Discount on Toys", 10));
		store.createCategory(toys);
		baby.addSubCategory(toys);
		store.updateCategory(baby);
		
		//Add boys and girls sub categories to toys
		Category boys = new Category("Boys","Baby Boy's toys", new Discount("5% Discount on Boys Items", 5));
		store.createCategory(boys);
		Category girls = new Category("Girls","Baby Girl's toys");
		store.createCategory(girls);
		
		toys.addSubCategory(boys, girls);
		store.updateCategory(toys);
		
		//Add new born, toddler and 5-10 sub categories to boys
		Category newborn = new Category("New Born","New Born toys");
		store.createCategory(newborn);
		
		Category toddler = new Category("Toddler","Toddler toys");
		store.createCategory(toddler);
		
		Category years5to10 = new Category("5 to 10 years","5 to 10 years toys");
		store.createCategory(years5to10);
		
		boys.addSubCategory(newborn, toddler, years5to10);
		store.updateCategory(toys);

		
		//Add a 3% special discount sub category to baby
		Category bestDeal =  new Category("Best Deal", "Additional discount", new Discount("3% additional discount", 3));
		store.createCategory(bestDeal);		
		
		Item item1 =  new Item("Soft Rubber Ball", 15);
		Item item2 =  new Item("Toy dumbell", 10);
		Item item3 = new Item("Battery toy car", 30);
		
		store.createItem(item1);
		store.createItem(item2);
		store.createItem(item3);
		
		bestDeal.addItem(item1);
		newborn.addItem(item1, item2);
		store.updateCategory(newborn);
		years5to10.addItem(item3);
		
		store.updateCategory(bestDeal);
		store.updateCategory(years5to10);
		store.updateItem(item1);
		store.updateItem(item2);
		store.updateItem(item3);

		/*PriceCalculator pc = new PriceCalculator();
		
		Item item = store.getItem("sku-soft-rubber-ball");
		PriceBreakup pb = pc.getPriceBreakup(item);
		
		System.out.println(item1.getCode() + " final price = " + pb.getTotalDiscount());*/
	}
}
