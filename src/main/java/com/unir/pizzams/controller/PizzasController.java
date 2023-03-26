package com.unir.pizzams.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unir.pizzams.model.api.Pizza;
import com.unir.pizzams.service.PizzaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PizzasController {

	private final PizzaService service;

	@GetMapping("/pizzas/{idPizza}")
	/**
	 * Posibles respuestas 200, 404, 500
	 * @param idPizza - Id de la Pizza a buscar
	 * @return
	 */
	public ResponseEntity<Pizza> getPizzaById(@PathVariable String idPizza) {

		try {
			log.info("Retrieving pizza data for {}", idPizza);
			Pizza pizza = service.getPizzaById(idPizza);
			return pizza == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(service.getPizzaById(idPizza));
		} catch (Exception e) {
			log.error("Unexpected exception retrieving pizza: {}", e.getMessage(), e);
			// Logamos error pero no lo devolvemos, para prevenir que un cliente sepa como
			// esta organizado nuestro codigo
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/pizzas")
	/**
	 * Posibles respuestas 200, 500
	 * @param sale - para buscar ofertas
	 * @param vegan - para buscar veganas
	 * @param name - para buscar por nombre
	 * @return
	 */
	public ResponseEntity<List<Pizza>> getPizzaByFilters(@RequestParam(name = "sale", required = false) Boolean sale,
			@RequestParam(name = "vegan", required = false) Boolean vegan, @RequestParam(name = "name", required = false) String name) {

		try {

			if (Objects.isNull(sale) && Objects.isNull(vegan) && Objects.isNull(name)) {
				return ResponseEntity.ok(service.getPizzas());
			} else {
				return ResponseEntity.ok(service.getPizzaByFilters(sale, vegan, name));
			}

		} catch (Exception e) {
			log.error("Unexpected exception retrieving pizza by filters: {}", e.getMessage(), e);
			// Logamos error pero no lo devolvemos, para prevenir que un cliente sepa como
			// esta organizado nuestro codigo
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/pizzas")
	/**
	 * Posibles respuestas 201, 400, 500
	 * @param pizza - Body con los datos de la pizza
	 * @return
	 */
	public ResponseEntity<Pizza> addPiza(@RequestBody Pizza pizza) {

		try {

			Pizza created = service.registerPizza(pizza);
			if (created == null) {
				return ResponseEntity.badRequest().build();
			} else {
				return ResponseEntity.status(HttpStatus.CREATED).body(created);
			}
		} catch (Exception e) {
			log.error("Unexpected exception creating pizza: {}", e.getMessage(), e);
			// Logamos error pero no lo devolvemos, para prevenir que un cliente sepa como
			// esta organizado nuestro codigo
			return ResponseEntity.internalServerError().build();
		}
	}

	@PatchMapping("/pizzas/{idPizza}/ingredients")
	/**
	 * Posibles respuestas 200, 404, 400, 500
	 * @param idPizza - id de la pizza a modificar
	 * @param ingredients - nuevos ingredientes
	 * @return
	 */
	public ResponseEntity<Pizza> updatePizzaIngredients(@PathVariable String idPizza,
			@RequestBody List<String> ingredients) {

		try {

			Pizza prev = service.getPizzaById(idPizza);
			if (prev == null) {
				return ResponseEntity.notFound().build();
			} else {
				Pizza updated = service.modifyIngredients(prev, ingredients);
				return updated == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(updated);
			}
		} catch (Exception e) {
			log.error("Unexpected exception modifying pizza ingredients: {}", e.getMessage(), e);
			// Logamos error pero no lo devolvemos, para prevenir que un cliente sepa como
			// esta organizado nuestro codigo
			return ResponseEntity.internalServerError().build();
		}
	}

}
