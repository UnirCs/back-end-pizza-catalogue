package com.unir.pizzams.model.data;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName = "pizzas", createIndex = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticPizza {

  @Id
  private String id;

  @MultiField(mainField = @Field(type = FieldType.Keyword, name = "name"),
      otherFields = @InnerField(suffix = "search", type = FieldType.Search_As_You_Type))
  private String name;

  @Field(type = FieldType.Double, name = "price")
  private Double price;

  @Field(type = FieldType.Boolean, name = "sale")
  private Boolean sale;
  
  @Field(type = FieldType.Boolean, name = "vegan")
  private Boolean vegan;
  
  @Field(type = FieldType.Text, name = "description")
  private String description;

  @Field(type = FieldType.Object, name = "ingredients")
  private List<ElasticIngredient> ingredients;

}
