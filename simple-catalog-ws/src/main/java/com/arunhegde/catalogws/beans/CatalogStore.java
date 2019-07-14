package com.arunhegde.catalogws.beans;

import static org.dizitart.no2.objects.filters.ObjectFilters.*;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import java.util.List;
import java.util.UUID;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.util.StringUtils;

import com.arunhegde.catalogws.db.DbSession;

public class CatalogStore {
	private ObjectRepository<Category> categoryRepo = null;
	private ObjectRepository<Item> itemRepo = null;
	private ObjectRepository<Catalog> catalogRepo = null;
	private static final String dbFile = "/tmp/simple-catalog-ws.db";

	private static CatalogStore catalogStore = null;
	Nitrite db = null;

	private CatalogStore(String file) {
		if (file == null) {
			db = DbSession.getInstance(dbFile).getDb();
		} else {
			db = DbSession.getInstance(file).getDb();
		}
		categoryRepo = db.getRepository(Category.class);
		itemRepo = db.getRepository(Item.class);
		catalogRepo = db.getRepository(Catalog.class);
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

	public Category createCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("category can't be null");
		}

		if (category.getLabel() == null) {
			throw new IllegalArgumentException("category label can't be null");
		}
		
		if(category.getId() == null) {
			category.setId(UUID.randomUUID().toString());
		}
		
		if(category.getCode() == null) {
			category.setCode(generateCode("cat", category.getLabel()));
		}
		
		category.setPath(generatePath(category.getCode()));
		
		categoryRepo.insert(category);
		if (category.isSuperCategory()) {
			Catalog catalog = new Catalog(category);
			WriteResult result = catalogRepo.insert(catalog);
			System.out.println("Category created: " + result.toString());
		}

		return category;
	}

	public void updateCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("category can't be null");
		}

		categoryRepo.update(category);
	}

	public Item createItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}
		
		if(item.getId() == null) {
			item.setId(UUID.randomUUID().toString());
		}

		if(item.getCode() == null) {
			item.setCode(generateCode("sku", item.getLabel()));
		}
		
		item.addPath(generatePath(item.getCode()));
		
		WriteResult result = itemRepo.insert(item);
		System.out.println("item created: " + result.toString());
		return item;
	}

	public void updateItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}

		itemRepo.update(item);
	}

	public Category getCategory(final String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("id can't be null or empty");
		}

		return categoryRepo.find(eq("id", id)).firstOrDefault();
	}
	
	public Category getCategoryByCode(final String categoryCode) {
		if (StringUtils.isEmpty(categoryCode)) {
			throw new IllegalArgumentException("categoryCode can't be null or empty");
		}

		return categoryRepo.find(eq("code", categoryCode)).firstOrDefault();
	}

	public List<Category> findCategory(final String query, final String code) {
		if (StringUtils.isEmpty(query) && StringUtils.isEmpty(code)) {
			return categoryRepo.find().toList();
		}

		if (StringUtils.isEmpty(query) && !StringUtils.isEmpty(code)) {
			return categoryRepo.find(eq("code", code)).toList();
		}

		return categoryRepo.find(or(text("label", query), text("description", query))).toList();
	}

	public List<Item> findItem(final String query, final String code) {
		if (StringUtils.isEmpty(query) && StringUtils.isEmpty(code)) {
			return itemRepo.find().toList();
		}

		if (StringUtils.isEmpty(query) && !StringUtils.isEmpty(code)) {
			return itemRepo.find(eq("code", code)).toList();
		}

		return itemRepo.find(or(text("label", query), text("description", query))).toList();
	}

	public List<Catalog> getAllCatalog() {
		return catalogRepo.find().toList();
	}

	public Item getItem(final String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("id can't be null or empty");
		}

		return itemRepo.find(eq("id", id)).firstOrDefault();
	}
	
	public Item getItemByCode(final String itemCode) {
		if (StringUtils.isEmpty(itemCode)) {
			throw new IllegalArgumentException("itemCode can't be null or empty");
		}

		return itemRepo.find(eq("code", itemCode)).firstOrDefault();
	}
	
	private String generateCode(String prefix, String label) {
		return prefix + "-" + label.replace(" ", "-").toLowerCase();
	}
	
	private String generatePath(String code) {
		return "/" + code;
	}
}
