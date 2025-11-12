package com.food.response;

import com.food.model.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public static ReviewResponse fromReview(Review review) {
        ReviewResponse response = new ReviewResponse().builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
        return response;
    }
}
