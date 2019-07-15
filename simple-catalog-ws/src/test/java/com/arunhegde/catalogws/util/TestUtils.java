package com.arunhegde.catalogws.util;

import java.io.File;

import com.arunhegde.catalogws.beans.Category;
import com.arunhegde.catalogws.beans.Discount;
import com.arunhegde.catalogws.beans.Item;
import com.arunhegde.catalogws.db.CatalogDAO;

public class TestUtils {
	public static void cleanupTestCatalogStore(final String testDbFile) {
		// Delete old testDBFile
		File file = new File(testDbFile);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void buildTestCatalogStore() {
		cleanupTestCatalogStore(CatalogDAO.DB_FILE);
		
		CatalogDAO dao = CatalogDAO.getInstance();

		// Add super category baby
		Category baby = new Category("Baby", "Baby products", true);
		dao.createCategory(baby);

		// Add clothing and shoes sub categories to baby
		Category clothing = new Category("Clothing", "Baby cloths");
		dao.createCategory(clothing);

		Category shoes = new Category("Shoes", "Baby shoes");
		dao.createCategory(shoes);

		baby.addSubCategory(clothing);
		baby.addSubCategory(shoes);
		dao.updateCategory(baby);

		// Add toys category with 10% discount
		Category toys = new Category("Toys", "Baby toys", new Discount("10% Discount on Toys", 10));
		dao.createCategory(toys);
		baby.addSubCategory(toys);
		dao.updateCategory(baby);

		// Add boys and girls sub categories to toys
		Category boys = new Category("Boys", "Baby Boy's toys", new Discount("5% Discount on Boys Items", 5));
		dao.createCategory(boys);
		Category girls = new Category("Girls", "Baby Girl's toys");
		dao.createCategory(girls);

		toys.addSubCategory(boys, girls);
		dao.updateCategory(toys);

		// Add new born, toddler and 5-10 sub categories to boys
		Category newborn = new Category("New Born", "New Born toys");
		dao.createCategory(newborn);

		Category toddler = new Category("Toddler", "Toddler toys");
		dao.createCategory(toddler);

		Category years5to10 = new Category("5 to 10 years", "5 to 10 years toys");
		dao.createCategory(years5to10);

		boys.addSubCategory(newborn, toddler, years5to10);
		dao.updateCategory(toys);

		// Add a 3% special discount sub category to baby
		Category bestDeal = new Category("Best Deal", "Additional discount", new Discount("3% additional discount", 3));
		dao.createCategory(bestDeal);

		Item item1 = new Item("Soft Rubber Ball", "Soft rubber ball", 15);
		Item item2 = new Item("Toy dumbell", "dumbell toy", 10);
		Item item3 = new Item("Battery toy car", "baby toy car", 30);

		dao.createItem(item1);
		dao.createItem(item2);
		dao.createItem(item3);

		bestDeal.addItem(item1);
		newborn.addItem(item1, item2);
		dao.updateCategory(newborn);
		years5to10.addItem(item3);

		dao.updateCategory(bestDeal);
		dao.updateCategory(years5to10);
		dao.updateItem(item1);
		dao.updateItem(item2);
		dao.updateItem(item3);
		System.out.println("TestDB is built");
	}
}
