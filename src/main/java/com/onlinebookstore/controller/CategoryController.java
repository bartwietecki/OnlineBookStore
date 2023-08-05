package com.onlinebookstore.controller;

import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/add")
    public String showAddCategoryForm(Model model) {
        CategoryModel categoryModel = new CategoryModel();
        model.addAttribute("categoryModel", categoryModel);
        return "add-category-form";
    }

    @PostMapping("/category/save")
    public String addCategory(@ModelAttribute("categoryModel") CategoryModel categoryModel) {
        Category category = categoryService.addCategory(categoryModel);

        if (category != null) {
            return "redirect:/admin";
        } else {
            return "redirect:/admin/error";
        }
    }

//    @GetMapping
//    public String admin(Model model) {
//        List<Category> categories = categoryService.getAllCategories();
//        model.addAttribute("categories", categories);
//        return "admin";
//    }

}