package com.onlinebookstore.service;

import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(CategoryModel categoryModel) {
        Category category = new Category();
        category.setName(categoryModel.getName());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }






    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + categoryId + " not found"));
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}