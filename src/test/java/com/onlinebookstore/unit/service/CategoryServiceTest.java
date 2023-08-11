package com.onlinebookstore.unit.service;

import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.repository.CategoryRepository;
import com.onlinebookstore.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void testGetAllCategories() {
        // Given
        Category category1 = new Category(1L, "Category 1");
        Category category2 = new Category(2L, "Category 2");

        List<Category> categoryList = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        // When
        List<CategoryModel> result = categoryService.getAllCategories();

        // Then
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("Category 2", result.get(1).getName());
    }

    @Test
    public void testAddCategory() {
        // Given
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName("New Category");

        // When
        categoryService.addCategory(categoryModel);

        // Then
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testGetCategoryById() {
        // Given
        Long categoryId = 1L;
        Category category = new Category(categoryId, "Category 1");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        CategoryModel result = categoryService.getCategoryById(categoryId);

        // Then
        assertEquals(category.getName(), result.getName());
    }

    @Test
    public void testUpdateCategory() {
        // Given
        Long categoryId = 1L;
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(categoryId);
        categoryModel.setName("Updated Category");

        Category existingCategory = new Category(categoryId, "Category 1");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        // When
        categoryService.updateCategory(categoryModel);

        // Then
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertEquals(categoryModel.getName(), existingCategory.getName());
    }

    @Test
    public void testDeleteCategory() {
        // Given
        Long categoryId = 1L;

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}