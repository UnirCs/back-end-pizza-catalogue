package com.unir.pizzams.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.unir.pizzams.model.data.ElasticPizza;

public interface ElasticPizzaRepository extends ElasticsearchRepository<ElasticPizza, String> {
	
	List<ElasticPizza> findAll();
	
	Optional<ElasticPizza> findByName(String name);

	ElasticPizza save(ElasticPizza pizza);

}
