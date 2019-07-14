package com.arunhegde.catalogws.beans;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;


@Indices({
    	@Index(value = "label", type = IndexType.Unique),
        @Index(value = "description", type = IndexType.Fulltext),
        @Index(value = "superCategory", type = IndexType.NonUnique),
})
public class Category implements CatalogComponent {
	@Id
	private String id;
	
	private String code;
	private String categoryPath;
	private String label;
	private String description;
	private Discount discount;
	private List<CatalogComponent> children;
	private Category parent;
	private boolean superCategory = false;

	public Category() {
		this.id = UUID.randomUUID().toString();
	}

	public Category(String label, String description) {
		super();
		this.label = label;
		this.description = description;
		this.code = generateCode("cat", label);
		this.categoryPath = generatePath(code);
		this.id = UUID.randomUUID().toString();
	}
	
	public Category(String label, String description, boolean isSuperCategory) {
		super();
		this.label = label;
		this.description = description;
		this.code = generateCode("cat", label);
		this.categoryPath = generatePath(code);
		this.superCategory = isSuperCategory;
		this.id = UUID.randomUUID().toString();
	}

	public Category(String label, String description, Discount discount) {
		super();
		this.label = label;
		this.description = description;
		this.discount = discount;
		this.code = generateCode("cat", label);
		this.categoryPath = generatePath(code);
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public List<CatalogComponent> getChildren() {
		return children;
	}
	
	public boolean isSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(boolean superCategory) {
		this.superCategory = superCategory;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return categoryPath;
	}

	public void setPath(String path) {
		this.categoryPath = path;
	}

	public void addSubPath(String path) {
		this.categoryPath = categoryPath + "/" + path;
	}

	public void removeSubPath(String path) {
		this.categoryPath.replace("/" + path, "");
	}

	public void addItem(final Item... items) {
		if (items == null) {
			throw new IllegalArgumentException("items can't be null");
		}

		if (children == null) {
			children = new ArrayList<CatalogComponent>();
		}

		for (Item item :  items) {
			item.addPath(categoryPath + "/" + item.getCode());
			children.add(item);
		}
	}

	public void removeItem(final Item item) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}

		if (children == null) {
			children = new ArrayList<CatalogComponent>();
		}

		if (children != null) {
			item.removePath(categoryPath + "/" + item.getCode());
			children.remove(item);
		}
	}

	public void addSubCategory(final Category... subCategoryList) {
		if (subCategoryList == null) {
			throw new IllegalArgumentException("subCategoryList can't be null");
		}

		if (children == null) {
			children = new ArrayList<CatalogComponent>();
		}
		
		for (Category subCategory :  subCategoryList) {
			subCategory.setPath(this.getPath() + subCategory.getPath());
			children.add(subCategory);
		}
	}

	public void removeSubCategory(final Category subCategory) {
		if (subCategory == null) {
			throw new IllegalArgumentException("subCategory can't be null");
		}

		if (children != null) {
			subCategory.removeSubPath(subCategory.getCode());
			children.remove(subCategory);
		}
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setChildren(List<CatalogComponent> children) {
		this.children = children;
	}

	@Transient
	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
