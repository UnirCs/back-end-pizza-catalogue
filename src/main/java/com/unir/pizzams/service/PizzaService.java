package com.unir.pizzams.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.unir.pizzams.data.DataAccessRepository;
import com.unir.pizzams.model.api.Pizza;
import com.unir.pizzams.model.data.ElasticIngredient;
import com.unir.pizzams.model.data.ElasticPizza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service // Tambien vale @Component
@RequiredArgsConstructor
@Slf4j
public class PizzaService {

	private final DataAccessRepository repository;

	public List<Pizza> getPizzas() {

		List<ElasticPizza> pizzas = repository.getPizzas();
		// No exponemos toda la informacion de la pizza tal cual esta en db, unicamente
		// aquella informacion que el llamante necesita

		// En esta linea usamos el constructor custom que hemos definido en la clase
		// Pizza
		return pizzas.stream().map(Pizza::new).collect(Collectors.toList());

		// Otra forma de indicarlo, quiza mas obvia
		// return pizzas.stream().map(elasticPizza -> new
		// Pizza(elasticPizza)).collect(Collectors.toList());
	}

	public Pizza registerPizza(Pizza pizza) {

		// Chequeamos que los datos introducidos sean correctos
		if (StringUtils.isEmpty(pizza.getName()) || StringUtils.isEmpty(pizza.getDescription())
				|| StringUtils.isEmpty(pizza.getDescription()) || Objects.isNull(pizza.getVegan())
				|| Objects.isNull(pizza.getSale())) {

			log.warn("Pizza attributes can not be blank", pizza);
			return null;
		}

		// Chequeamos que los ingredientes sean correctos
		List<ElasticIngredient> validIngredients = validIngredients(pizza.getIngredients());
		if (validIngredients.size() == pizza.getIngredients().size()) {

			ElasticPizza elasticPizza = repository.getPizzaById(pizza.getName());
			if (elasticPizza == null) {

				// Dejamos el id a null, que lo incluya ES
				repository.saveOrUpdate(new ElasticPizza(null, pizza.getName(), pizza.getPrice(), pizza.getSale(),
						pizza.getVegan(), pizza.getDescription(), validIngredients));
				return pizza;
			} else {
				log.warn("Trying to create an existing pizza {}", pizza.getName());
				return null;
			}

		} else {
			log.warn("Invalid ingredients {}", pizza.getIngredients());
			return null;
		}
	}

	public Pizza getPizzaById(String idPizza) {
		ElasticPizza elasticPizza = repository.getPizzaById(idPizza);
		return elasticPizza == null ? null : new Pizza(elasticPizza);
	}

	public List<Pizza> getPizzaByFilters(Boolean sale, Boolean vegan, String name) {

		List<ElasticPizza> elasticPizzas = repository.getPizzasByFilters(sale, vegan, name);
		return elasticPizzas.stream().map(Pizza::new).collect(Collectors.toList());
	}

	public Pizza modifyIngredients(Pizza pizza, List<String> newIngredients) {

		// Chequeamos que los ingredientes sean correctos
		List<ElasticIngredient> validIngredients = validIngredients(newIngredients);
		if (validIngredients.size() == newIngredients.size()) {

			ElasticPizza elasticPizza = repository.getPizzaById(pizza.getName());
			elasticPizza.setIngredients(validIngredients);
			repository.saveOrUpdate(elasticPizza);

			pizza.setIngredients(newIngredients);
			return pizza;

		} else {
			log.warn("Invalid ingredients {}", newIngredients);
			return null;
		}
	}

	private List<ElasticIngredient> validIngredients(List<String> ingredients) {

		// Chequeamos que los ingredientes sean correctos
		List<ElasticIngredient> elasticIngredients = new LinkedList<>();
		for (String ingredient : ingredients) {
			ElasticIngredient elasticIngredient = repository.getIngredientById(ingredient);
			if (elasticIngredient != null) {
				elasticIngredients.add(elasticIngredient);
			}
		}
		return elasticIngredients;
	}

}
