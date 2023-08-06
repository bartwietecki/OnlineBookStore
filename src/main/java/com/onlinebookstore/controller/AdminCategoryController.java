package com.onlinebookstore.controller;

import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String getCategories(Model model) {
        List<CategoryModel> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin-category-list";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        CategoryModel categoryModel = new CategoryModel();
        model.addAttribute("categoryModel", categoryModel);
        return "add-category-form";
    }

    @PostMapping("/save")
    public String addCategory(@ModelAttribute("categoryModel") CategoryModel categoryModel) {
        categoryService.addCategory(categoryModel);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        CategoryModel categoryModel = categoryService.getCategoryById(id);
        model.addAttribute(
                "categoryModel", categoryModel);
        return "edit-category-form";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("categoryModel") CategoryModel categoryModel) {
        categoryService.updateCategory(categoryModel);
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}