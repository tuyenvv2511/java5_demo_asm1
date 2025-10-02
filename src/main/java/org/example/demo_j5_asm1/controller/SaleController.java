package org.example.demo_j5_asm1.controller;

import org.example.demo_j5_asm1.entity.Sale;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.SaleRepository;
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
@RequestMapping("/sales")
public class SaleController {
    private final SaleRepository saleRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("sales", saleRepo.findAll());
        return "sales/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "sales/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("sale") Sale s, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("products", productRepo.findAll());
            model.addAttribute("users", userRepo.findAll());
            return "sales/form";
        }
        
        // Load product and buyer from IDs
        if (s.getProduct() != null && s.getProduct().getId() != null) {
            var product = productRepo.findById(s.getProduct().getId()).orElse(null);
            s.setProduct(product);
            // Set seller from product
            if (product != null) {
                s.setSeller(product.getSeller());
            }
        }
        
        if (s.getBuyer() != null && s.getBuyer().getId() != null) {
            var buyer = userRepo.findById(s.getBuyer().getId()).orElse(null);
            s.setBuyer(buyer);
        }
        
        saleRepo.save(s);
        return "redirect:/sales";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        var s = saleRepo.findById(id).orElseThrow();
        model.addAttribute("sale", s);
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "sales/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        saleRepo.deleteById(id);
        return "redirect:/sales";
    }
}