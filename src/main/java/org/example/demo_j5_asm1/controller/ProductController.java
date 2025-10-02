package org.example.demo_j5_asm1.controller;

import java.math.BigDecimal;

import org.example.demo_j5_asm1.entity.Condition;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.repository.BrandRepository;
import org.example.demo_j5_asm1.repository.CategoryRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.UserRepository;
import org.example.demo_j5_asm1.service.PricingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepo;
    private final BrandRepository brandRepo;
    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;
    private final PricingService pricingService;

    @GetMapping
    public String list(@RequestParam(required = false) String brand,
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false, name = "q") String keyword,
                       Model model) {
        var spec = ProductSpecs.byFilters(brand, category, keyword);
        model.addAttribute("products", productRepo.findAll(spec));
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("brand", brand);
        model.addAttribute("category", category);
        model.addAttribute("q", keyword);
        return "products/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("conditions", Condition.values());
        return "products/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("product") Product p,
                       BindingResult br, Model model,
                       @RequestParam(required = false, defaultValue = "false") boolean applySuggest) {
        if (br.hasErrors()) {
            model.addAttribute("brands", brandRepo.findAll());
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("users", userRepo.findAll());
            model.addAttribute("conditions", Condition.values());
            return "products/form";
        }
        if (applySuggest) {
            BigDecimal suggested = pricingService.suggestPrice(p);
            p.setPrice(suggested);
        }
        productRepo.save(p);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        var p = productRepo.findById(id).orElseThrow();
        model.addAttribute("product", p);
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("conditions", Condition.values());
        return "products/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/products";
    }
}