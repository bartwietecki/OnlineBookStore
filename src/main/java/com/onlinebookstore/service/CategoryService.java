package com.onlinebookstore.service;

import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryModel> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapCategoryToCategoryModel)
                .collect(Collectors.toList());
    }

    public CategoryModel addCategory(CategoryModel categoryModel) {
        Category category = new Category();
        category.setName(categoryModel.getName());

        Category savedCategory = categoryRepository.save(category);
        return mapCategoryToCategoryModel(savedCategory);
    }

    public CategoryModel getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        return mapCategoryToCategoryModel(category);
    }

    public CategoryModel getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + categoryId + " not found"));
        return mapCategoryToCategoryModel(category);
    }

    // zamiast id mamy name
    public CategoryModel updateCategory(CategoryModel categoryModel) {
        Category category = categoryRepository.findByName(categoryModel.getName());
        if (category == null) {
            throw new IllegalArgumentException("Category with name " + categoryModel.getName() + " not found");
        }

        category.setName(categoryModel.getName());
        Category updatedCategory = categoryRepository.save(category);
        return mapCategoryToCategoryModel(updatedCategory);
    }


    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private CategoryModel mapCategoryToCategoryModel(Category category) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(category.getName());
        return categoryModel;
    }
}