package com.arunhegde.catalogws.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type")
		@JsonSubTypes({ 
		  @Type(value = Item.class, name = "item"), 
		  @Type(value = Category.class, name = "category") 
		})
public interface CatalogComponent {
	public String getCode();
	public String getLabel();
	public String getDescription();
	public Discount getDiscount();
	public List<CatalogComponent> getChildren();
	
	public default String generateCode(String prefix, String label) {
		return prefix + "-" + label.replace(" ", "-").toLowerCase();
	}
	
	public default String generatePath(String code) {
		return "/" + code;
	}
}
