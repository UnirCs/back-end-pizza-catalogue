package com.unir.pizzams.data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import com.unir.pizzams.model.data.ElasticIngredient;
import com.unir.pizzams.model.data.ElasticPizza;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataAccessRepository {

	// Esta clase (y bean) es la unica que usan directamente los servicios para
	// acceder a los datos.
	private final ElasticIngredientRepository ingredientRepository;
	private final ElasticPizzaRepository pizzaRepository;
	private final ElasticsearchOperations elasticClient;

	private final String[] nameSearchFields = { "name.search", "name.search._2gram", "name.search._3gram" };

	// Metodos para trabajar con pizzas con Spring Data Elasticsearch - JPA
	public List<ElasticPizza> getPizzas() {
		return pizzaRepository.findAll();
	}

	public ElasticPizza getPizzaById(String id) {
		return pizzaRepository.findByName(id).orElse(null);
	}

	public ElasticPizza saveOrUpdate(ElasticPizza pizza) {
		return pizzaRepository.save(pizza);
	}

	// Metodos para trabajar con ingredientes con Spring Data Elasticsearch - JPA
	public List<ElasticIngredient> getIngredients() {
		return ingredientRepository.findAll();
	}

	public ElasticIngredient getIngredientById(String id) {
		return ingredientRepository.findByInternalName(id).orElse(null);
	}

	public ElasticIngredient saveOrUpdate(ElasticIngredient ingredient) {
		return ingredientRepository.save(ingredient);
	}

	// Metodos para trabajar con pizzas con Spring Data Elasticsearch - Sin JPA
	public List<ElasticPizza> getPizzasByFilters(Boolean onSale, Boolean isVegan, String name) {

		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

		if (!StringUtils.isEmpty(name)) {
			boolQuery.must(QueryBuilders.multiMatchQuery(name, nameSearchFields)
					.type(MultiMatchQueryBuilder.Type.BOOL_PREFIX));
		}

		if (Objects.nonNull(onSale)) {
			boolQuery.must(QueryBuilders.termQuery("sale", onSale));
		}

		if (Objects.nonNull(isVegan)) {
			boolQuery.must(QueryBuilders.termQuery("vegan", isVegan));
		}

		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQuery);
		Query query = nativeSearchQueryBuilder.build();
		SearchHits<ElasticPizza> result = elasticClient.search(query, ElasticPizza.class);
		return result.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
	}

}