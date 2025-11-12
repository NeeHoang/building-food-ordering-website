package com.food.controllers;
import java.util.List;
import com.food.model.entities.Category;
import com.food.services.impl.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoriesService categoriesService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductsByCategory(@Valid @PathVariable("id") Long id) {
        try{
            List<Category> categoryList = categoriesService.getCategories();
            return ResponseEntity.ok().body(categoryList);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
//    @PostMapping()
//    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category) {
//
//    }
}
