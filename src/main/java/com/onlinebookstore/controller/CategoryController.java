package com.onlinebookstore.controller;

import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @RequestMapping("/category/add")
    public String showAddCategoryForm(Model model) {
        CategoryModel categoryModel = new CategoryModel();
        model.addAttribute("categoryModel", categoryModel);
        return "add-category-form";
    }

    @GetMapping
    @RequestMapping("/category/add")
    public String addCategory(@ModelAttribute("categoryModel") CategoryModel categoryModel) {
        Category category = new Category();
        category.setName(categoryModel.getName());

        if (categoryModel.getParentId() != null) {
            Category parentCategory = categoryService.getCategoryById(categoryModel.getParentId());
            category.setParent(parentCategory);
        }

        categoryService.addCategory(category);

        return "redirect:/admin";
    }
}