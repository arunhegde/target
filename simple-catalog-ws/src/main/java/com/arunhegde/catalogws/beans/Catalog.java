package com.arunhegde.catalogws.beans;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

@Indices({
	@Index(value = "label", type = IndexType.Unique),
    @Index(value = "description", type = IndexType.Fulltext),
})
public class Catalog {
	@Id
	private String code;
	private String label;
	private String description;
	
	public Catalog() {
		
	}
	
	public Catalog(CatalogComponent comp) {
		super();
		this.label = comp.getLabel();
		this.description = comp.getDescription();
		this.code = comp.getCode();
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
