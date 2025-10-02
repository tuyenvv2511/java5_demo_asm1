package org.example.demo_j5_asm1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo_j5_asm1.entity.Category;
import org.example.demo_j5_asm1.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        return "categories/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("category") Category c, BindingResult br) {
        if (br.hasErrors()) return "categories/form";
        categoryRepo.save(c);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryRepo.findById(id).orElseThrow());
        return "categories/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return "redirect:/categories";
    }
}