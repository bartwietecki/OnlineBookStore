package com.onlinebookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {

    @GetMapping("/contact-us")
    public String showContactUsPage() {
        return "contact-us";
    }
}