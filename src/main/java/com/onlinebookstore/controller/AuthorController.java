package com.onlinebookstore.controller;

import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("authorModel", new AuthorModel());
        return "add-author-form";
    }

    @PostMapping("/save")
    public String addAuthor(@ModelAttribute AuthorModel authorModel) {
        authorService.addAuthor(authorModel);
        return "redirect:/authors/add";
    }
}
