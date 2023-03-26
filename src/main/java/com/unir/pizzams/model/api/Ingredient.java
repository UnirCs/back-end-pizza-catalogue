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

	private String name;
	private String description;
	private Boolean vegan;

	public Ingredient(ElasticIngredient elasticItem) {
		this.name = elasticItem.getName();
		this.vegan = elasticItem.getVegan();
		this.description = elasticItem.getDescription();
	}
}
