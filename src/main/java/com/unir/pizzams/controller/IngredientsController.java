package com.unir.pizzams.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unir.pizzams.model.api.Ingredient;
import com.unir.pizzams.service.IngredientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class IngredientsController {
	
	private final IngredientService service;
	
	@GetMapping("/ingredients")
	/**
	 * Posibles respuestas 200, 500
	 * @return
	 */
	public ResponseEntity<List<Ingredient>> getIngredients() {

		try {
			return ResponseEntity.ok(service.getIngredients());
		} catch (Exception e) {
			log.error("Unexpected exception retrieving ingredients: {}", e.getMessage(), e);
			// Logamos error pero no lo devolvemos, para prevenir que un cliente sepa como
			// esta organizado nuestro codigo
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("/ingredients")
	/**
	 * Posibles respuestas 201, 400, 500
	 * @param ingredient - Body con los datos del ingrediente
	 * @return
	 */
	public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {

		try {

			Ingredient created = service.registerIngredient(ingredient);
			if (created == null) {
				return ResponseEntity.badRequest().build();
			} else {
				return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);
			}
		} catch (Exception e) {
			log.error("Unexpected exception creating ingredient: {}", e.getMessage(), e);
			// Logamos error pero no lo devolvemos, para prevenir que un cliente sepa como
			// esta organizado nuestro codigo
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/ingredients/{ingredientName}")
	/**
	 * Posibles respuestas 200, 404, 400, 500
	 * @param ingredientName - Ingrediente a modificar
	 * @param ingredient - nuevos datos del ingrediente
	 * @return
	 */
	public ResponseEntity<Ingredient> modifyIngredient(@PathVariable String ingredientName, @RequestBody Ingredient ingredient) {

		try {

			Ingredient prev = service.getIngredientById(ingredientName);
			if (prev == null) {
				return ResponseEntity.notFound().build(); //Tambien podria ser un bad request
			} else {
				Ingredient updated = service.updateIngredient(ingredient);
				return updated == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(ingredient);
			}
		} catch (Exception e) {
			log.error("Unexpected exception creating ingredient: {}", e.getMessage(), e);
			// Logamos error pero no lo devolvemos, para prevenir que un cliente sepa como
			// esta organizado nuestro codigo
			return ResponseEntity.internalServerError().build();
		}
	}

}
