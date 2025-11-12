package com.food.services.impl;

import com.food.model.entities.Category;
import com.food.repositories.CategoriesRepository;
import com.food.services.ICategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService implements ICategoriesService {
    private final CategoriesRepository categoriesRepository;

    @Override
    public List<Category> getCategories() {
        return categoriesRepository.findAll();
    }
}
