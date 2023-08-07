package com.onlinebookstore.service;

import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public void addCategory(CategoryModel categoryModel) {
        Category category = new Category();
        category.setName(categoryModel.getName());

        categoryRepository.save(category);
    }

    public CategoryModel getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + categoryId + " not found"));
        return mapCategoryToCategoryModel(category);
    }

    public void updateCategory(CategoryModel categoryModel) {
        Optional<Category> oCategory = categoryRepository.findById(categoryModel.getId());
        if (oCategory.isPresent()) {
            Category category = oCategory.get();
            category.setName(categoryModel.getName());
            categoryRepository.save(category);

        } else {
            throw new EntityNotFoundException("Category with name " + categoryModel.getName() + " not found");
        }
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryModel mapCategoryToCategoryModel(Category category) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(category.getId());
        categoryModel.setName(category.getName());
        return categoryModel;
    }
}