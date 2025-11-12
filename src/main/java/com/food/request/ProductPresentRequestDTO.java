package com.food.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPresentRequestDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("type")
    private List<String> type;

    @JsonProperty("image_url")
    private String imageUrl;
}
