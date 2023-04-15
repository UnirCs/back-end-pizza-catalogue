package com.unir.pizzams.model.api;

import java.util.List;
import java.util.stream.Collectors;

import com.unir.pizzams.model.data.ElasticIngredient;
import com.unir.pizzams.model.data.ElasticPizza;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pizza {

	private String name;
	private Double price;
	private Boolean sale;
	private Boolean vegan;
	private String description;
	private List<String> ingredients;

	public Pizza(ElasticPizza elasticItem) {
		this.name = elasticItem.getName();
		this.price = elasticItem.getPrice();
		this.sale = elasticItem.getSale();
		this.vegan = elasticItem.getVegan();
		this.description = elasticItem.getDescription();
		this.ingredients = elasticItem.getIngredients().stream().map(ElasticIngredient::getInternalName)
				.collect(Collectors.toList());
	}

}
