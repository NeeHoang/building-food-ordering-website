package com.food.services;
import java.util.List;

import com.food.model.entities.Category;
import org.springframework.stereotype.Service;

@Service
public interface ICategoriesService {
    List<Category> getCategories();

}
