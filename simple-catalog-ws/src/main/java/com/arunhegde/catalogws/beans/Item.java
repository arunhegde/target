package com.arunhegde.catalogws.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

@Indices({
    @Index(value = "label", type = IndexType.Unique),
    @Index(value = "description", type = IndexType.Fulltext),
})
public class Item implements CatalogComponent {
	@Id
	private String id;
	
	private String code;
	private String label;
	private String description;
	private double mrp;
	private Discount discount;
	private Set<String> paths;
	
	public Item() {
		this.id = UUID.randomUUID().toString();
	}

	public Item(String label, String description, double mrp) {
		super();
		this.label = label;
		this.description = description;
		this.mrp = mrp;
		this.code = generateCode("sku", label);
		this.id = UUID.randomUUID().toString();
	}

	public Item(String label, String description, double mrp, Discount discount) {
		super();
		this.label = label;
		this.description = description;
		this.mrp = mrp;
		this.discount = discount;
		this.code = generateCode("sku", label);
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
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

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public Set<String> getPaths() {
		return paths;
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
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
