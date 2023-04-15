package com.unir.pizzams.model.api;

import com.unir.pizzams.model.data.ElasticIngredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

	private String displayName;
	private String internalName;
	private String description;
	private String base64Img;
	private Boolean vegan;
	private Boolean spicy;
	private Double price;

	public Ingredient(ElasticIngredient elasticItem) {
		this.displayName = elasticItem.getDisplayName();
		this.internalName = elasticItem.getInternalName();
		this.vegan = elasticItem.getVegan();
		this.description = elasticItem.getDescription();
		this.base64Img = elasticItem.getBase64Img();
		this.price = elasticItem.getPrice();
		this.spicy = elasticItem.getSpicy();
	}
}
