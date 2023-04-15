package com.unir.pizzams.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.unir.pizzams.data.DataAccessRepository;
import com.unir.pizzams.model.api.Ingredient;
import com.unir.pizzams.model.data.ElasticIngredient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service // Tambien vale @Component
@RequiredArgsConstructor
@Slf4j
public class IngredientService {

	private final DataAccessRepository repository;

	public List<Ingredient> getIngredients() {

		List<ElasticIngredient> ingredients = repository.getIngredients();
		// No exponemos toda la informacion del ingrediente tal cual esta en db,
		// unicamente
		// aquella informacion que el llamante necesita

		// En esta linea usamos el constructor custom que hemos definido en la clase
		// Ingredient
		return ingredients.stream().map(Ingredient::new).collect(Collectors.toList());

		// Otra forma de indicarlo, quiza mas obvia
		// return ingredients.stream().map(elasticIngredient -> new
		// Ingredient(elasticIngredient)).collect(Collectors.toList());
	}

	public Ingredient registerIngredient(Ingredient ingredient) {

		if (!StringUtils.isEmpty(ingredient.getDisplayName()) && !StringUtils.isEmpty(ingredient.getDescription())
				&& Objects.nonNull(ingredient.getVegan()) && !StringUtils.isEmpty(ingredient.getInternalName())
				&& !StringUtils.isEmpty(ingredient.getBase64Img())) {

			ElasticIngredient elasticIngredient = repository.getIngredientById(ingredient.getInternalName());
			if (elasticIngredient == null) {
				repository.saveOrUpdate(new ElasticIngredient(null, ingredient.getDisplayName(),
						ingredient.getInternalName(), ingredient.getDescription(), ingredient.getVegan(),
						ingredient.getBase64Img(), ingredient.getPrice(), ingredient.getSpicy()));
				return ingredient;
			} else {
				log.warn("Trying to create an existing ingredient {}", ingredient.getDisplayName());
				return null;
			}
		} else {
			return null;
		}
	}

	public Ingredient updateIngredient(Ingredient ingredient) {
		ElasticIngredient elasticIngredient = repository.getIngredientById(ingredient.getInternalName());
		elasticIngredient.setDescription(ingredient.getDescription());
		elasticIngredient.setDisplayName(ingredient.getDisplayName());
		elasticIngredient.setVegan(ingredient.getVegan());
		elasticIngredient.setBase64Img(ingredient.getBase64Img());
		elasticIngredient.setSpicy(ingredient.getSpicy());
		elasticIngredient.setPrice(ingredient.getPrice());
		repository.saveOrUpdate(elasticIngredient);
		return ingredient;

	}

	public Ingredient getIngredientById(String name) {
		ElasticIngredient elasticIngredient = repository.getIngredientById(name);
		return elasticIngredient == null ? null : new Ingredient(elasticIngredient);
	}

}
