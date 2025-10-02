package org.example.demo_j5_asm1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo_j5_asm1.entity.Brand;
import org.example.demo_j5_asm1.repository.BrandRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandRepository brandRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("brands", brandRepo.findAll());
        return "brands/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("brand", new Brand());
        return "brands/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("brand") Brand b, BindingResult br) {
        if (br.hasErrors()) return "brands/form";
        brandRepo.save(b);
        return "redirect:/brands";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandRepo.findById(id).orElseThrow());
        return "brands/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        brandRepo.deleteById(id);
        return "redirect:/brands";
    }
}