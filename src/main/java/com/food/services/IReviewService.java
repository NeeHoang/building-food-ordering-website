package com.food.services;
import java.util.List;

import com.food.model.entities.Review;
import com.food.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface IReviewService {
    Page<ReviewResponse> getReviews(PageRequest pageRequest);
}
