package org.example.demo_j5_asm1.controller;

import org.example.demo_j5_asm1.service.ScheduledTaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller để test ScheduledTaskService
 * Chỉ dành cho ADMIN
 */
@Controller
@RequestMapping("/admin/test")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminTestController {
    
    private final ScheduledTaskService scheduledTaskService;
    
    /**
     * Test promotion update
     */
    @PostMapping("/promotion-update")
    public String testPromotionUpdate(RedirectAttributes redirectAttributes) {
        log.info("Admin testing promotion update...");
        
        try {
            scheduledTaskService.triggerPromotionUpdate();
            redirectAttributes.addFlashAttribute("successMessage", 
                "✅ Successfully triggered promotion update. Check logs for details.");
        } catch (Exception e) {
            log.error("Error testing promotion update", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Error testing promotion update: " + e.getMessage());
        }
        
        return "redirect:/admin/data";
    }
    
    /**
     * Test daily maintenance
     */
    @PostMapping("/daily-maintenance")
    public String testDailyMaintenance(RedirectAttributes redirectAttributes) {
        log.info("Admin testing daily maintenance...");
        
        try {
            scheduledTaskService.triggerDailyMaintenance();
            redirectAttributes.addFlashAttribute("successMessage", 
                "✅ Successfully triggered daily maintenance. Check logs for details.");
        } catch (Exception e) {
            log.error("Error testing daily maintenance", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Error testing daily maintenance: " + e.getMessage());
        }
        
        return "redirect:/admin/data";
    }
}
