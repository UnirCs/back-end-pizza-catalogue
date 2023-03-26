package com.unir.pizzams.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.unir.pizzams.model.data.ElasticIngredient;

public interface ElasticIngredientRepository extends ElasticsearchRepository<ElasticIngredient, String> {

	Optional<ElasticIngredient> findByName(String name);
	
	List<ElasticIngredient> findAll();

	ElasticIngredient save(ElasticIngredient ingredient);

}
