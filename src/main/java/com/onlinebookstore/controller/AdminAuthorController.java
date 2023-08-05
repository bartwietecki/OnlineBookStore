package com.onlinebookstore.controller;

import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {

    private final AuthorService authorService;

    public AdminAuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public String getAuthors(Model model) {
        List<AuthorModel> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "admin-author-list";
    }

    @GetMapping("/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("authorModel", new AuthorModel());
        return "add-author-form";
    }

    @PostMapping("/save")
    public String addAuthor(@ModelAttribute AuthorModel authorModel) {
        authorService.addAuthor(authorModel);
        return "redirect:/admin/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditAuthorForm(@PathVariable Long id, Model model) {
        Optional<AuthorModel> authorModel = authorService.getAuthorById(id);
        if (authorModel.isPresent()) {
            model.addAttribute("authorModel", authorModel.get());
            return "edit-author-form";
        } else {
            throw new EntityNotFoundException("Author with ID " + id + " not found");
        }
    }

    @PostMapping("/update")
    public String updateAuthor(@ModelAttribute AuthorModel authorModel) {
        authorService.updateAuthor(authorModel);
        return "redirect:/admin/authors";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return "redirect:/admin/authors";
    }
}
