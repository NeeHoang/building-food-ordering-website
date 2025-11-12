package com.food.response;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ReviewListResponse {
    private List<ReviewResponse> reviews;
    private int totalPages;
}
