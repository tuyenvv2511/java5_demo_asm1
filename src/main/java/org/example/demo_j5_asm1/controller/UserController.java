package org.example.demo_j5_asm1.controller;

import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", User.Role.values());
        return "users/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("user") User u, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("roles", User.Role.values());
            return "users/form";
        }
        userRepo.save(u);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        var u = userRepo.findById(id).orElseThrow();
        model.addAttribute("user", u);
        model.addAttribute("roles", User.Role.values());
        return "users/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}