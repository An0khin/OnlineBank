package com.home.controller;

import com.home.model.Account;
import com.home.model.repository.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.charset.StandardCharsets;

@Controller
public class FirstController {
    @GetMapping("/")
    public String firstPage() {
        return "first";
    }
}
