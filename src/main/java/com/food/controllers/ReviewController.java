package com.food.controllers;
import java.util.List;

import com.food.response.ReviewListResponse;
import com.food.response.ReviewResponse;
import com.food.services.impl.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping("/{product_id}")
    public ResponseEntity<?> getReview(@Valid @PathVariable Long product_id,
                                       @RequestParam("page") int page,
                                       @RequestParam("limit") int limit) {
        PageRequest pageRequest = PageRequest.of(
                page,limit,
                Sort.by("createdAt").descending());
        Page<ReviewResponse> reviewPage = reviewService.getReviews(pageRequest);
        int totalPages = reviewPage.getTotalPages();
        List<ReviewResponse> reviews = reviewPage.getContent();
        return ResponseEntity.ok(ReviewListResponse.builder()
                .reviews(reviews)
                .totalPages(totalPages)
                .build());
    }
}
