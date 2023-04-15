package com.unir.pizzams.model.data;

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

@Document(indexName = "ingredients", createIndex = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticIngredient {

	@Id
	private String id;

	@MultiField(mainField = @Field(type = FieldType.Keyword, name = "displayName"), otherFields = @InnerField(suffix = "search", type = FieldType.Search_As_You_Type))
	private String displayName;
	
	@Field(type = FieldType.Keyword, name = "internalName")
	private String internalName;

	@Field(type = FieldType.Text, name = "description")
	private String description;

	@Field(type = FieldType.Boolean, name = "vegan")
	private Boolean vegan;
	
	@Field(type = FieldType.Text, name = "base64")
	private String base64Img;
	
	@Field(type = FieldType.Double, name = "price")
	private Double price;
	
	@Field(type = FieldType.Boolean, name = "spicy")
	private Boolean spicy;
}
