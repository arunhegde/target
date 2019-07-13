package com.arunhegde.catalogws.beans;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.util.StringUtils;

import com.arunhegde.catalogws.db.DbSession;

public class CatalogStore {
	private ObjectRepository<Category> categoryRepo = null;
	private ObjectRepository<Item> itemRepo = null; 
	private static final String dbFile = "/tmp/simple-catalog-ws.db";

	private static CatalogStore catalogStore = null;
	Nitrite db = null;

	private CatalogStore(String file) {
		if(file == null) {
			db = DbSession.getInstance(dbFile).getDb();
		} else {
			db = DbSession.getInstance(file).getDb();
		}
		categoryRepo = db.getRepository(Category.class);
		itemRepo = db.getRepository(Item.class);
	}

	public static CatalogStore getInstance() {
		return getInstance(null);
	}
	
	public static CatalogStore getInstance(String dbFile) {
		if (catalogStore == null) {
			catalogStore = new CatalogStore(dbFile);
			return catalogStore;
		} else {
			return catalogStore;
		}
	}

	public void createCategory(final Category category) {
		if (category == null) {
			throw new IllegalArgumentException("category can't be null");
		}

		categoryRepo.insert(category);
	}
	
	public void updateCategory(final Category category) {
		if (category == null) {
			throw new IllegalArgumentException("category can't be null");
		}

		categoryRepo.update(category);
	}
	
	public void createItem(final Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}

		itemRepo.insert(item);
	}
	
	public void updateItem(final Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}

		itemRepo.update(item);
	}

	public Category getCategory(final String categoryCode) {
		if (StringUtils.isEmpty(categoryCode)) {
			throw new IllegalArgumentException("categoryCode can't be null or empty");
		}

		return categoryRepo.find(eq("code", categoryCode))
                .firstOrDefault();
	}

	public Item getItem(final String itemCode) {
		if (StringUtils.isEmpty(itemCode)) {
			throw new IllegalArgumentException("itemCode can't be null or empty");
		}

		return itemRepo.find(eq("code", itemCode))
                .firstOrDefault();
	}
}
