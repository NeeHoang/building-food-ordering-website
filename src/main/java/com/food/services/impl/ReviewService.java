package com.food.services.impl;

import com.food.model.entities.Review;
import com.food.repositories.ReviewRepository;
import com.food.response.ReviewResponse;
import com.food.services.IReviewService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    @Override
    public Page<ReviewResponse> getReviews(PageRequest pageRequest) {
        return reviewRepository.findAll(pageRequest).map(ReviewResponse::fromReview);
    }
}
