package com.food.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPresentResponse {
    private Long id;
    private String name;
    private String categoryName;
    private String type;
    private String imageUrl;
    private String description;
    private LocalDateTime updatedAt;
}
