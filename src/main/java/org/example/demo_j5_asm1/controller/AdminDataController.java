package org.example.demo_j5_asm1.controller;

import java.util.Arrays;
import java.util.List;

import org.example.demo_j5_asm1.service.DataManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller để quản lý dữ liệu database
 * Chỉ dành cho ADMIN
 */
@Controller
@RequestMapping("/admin/data")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminDataController {
    
    private final DataManagementService dataManagementService;
    
    /**
     * Trang quản lý dữ liệu chính
     */
    @GetMapping
    public String dataManagement(Model model) {
        log.info("Admin accessing data management page");
        
        // Lấy thống kê dữ liệu hiện tại
        DataManagementService.DataCount currentCount = dataManagementService.getDataCount();
        
        model.addAttribute("currentCount", currentCount);
        model.addAttribute("availableDataTypes", getAvailableDataTypes());
        
        return "admin/data-management";
    }
    
    /**
     * Load tất cả dữ liệu mặc định
     */
    @PostMapping("/load-all")
    public String loadAllData(RedirectAttributes redirectAttributes) {
        log.info("Admin loading all default data");
        
        try {
            DataManagementService.DataLoadResult result = dataManagementService.loadAllDefaultData();
            
            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    String.format("✅ Đã load thành công %d statements. %s", 
                        result.getStatementsExecuted(), result.getMessage()));
                redirectAttributes.addFlashAttribute("result", result);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    String.format("❌ Lỗi khi load dữ liệu: %s", result.getErrorMessage()));
            }
            
        } catch (Exception e) {
            log.error("Error in load all data", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Lỗi hệ thống khi load dữ liệu: " + e.getMessage());
        }
        
        return "redirect:/admin/data";
    }
    
    /**
     * Load dữ liệu theo loại cụ thể
     */
    @PostMapping("/load/{dataType}")
    public String loadSpecificData(@PathVariable String dataType, RedirectAttributes redirectAttributes) {
        log.info("Admin loading specific data: {}", dataType);
        
        try {
            DataManagementService.DataLoadResult result = dataManagementService.loadSpecificData(dataType);
            
            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    String.format("✅ Đã load thành công dữ liệu %s: %d statements", 
                        dataType, result.getStatementsExecuted()));
                redirectAttributes.addFlashAttribute("result", result);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    String.format("❌ Lỗi khi load dữ liệu %s: %s", dataType, result.getErrorMessage()));
            }
            
        } catch (Exception e) {
            log.error("Error loading specific data: {}", dataType, e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                String.format("❌ Lỗi hệ thống khi load dữ liệu %s: %s", dataType, e.getMessage()));
        }
        
        return "redirect:/admin/data";
    }
    
    /**
     * Xóa tất cả dữ liệu (cẩn thận!)
     */
    @PostMapping("/clear-all")
    public String clearAllData(RedirectAttributes redirectAttributes) {
        log.warn("Admin clearing all data - DANGEROUS OPERATION!");
        
        try {
            DataManagementService.DataLoadResult result = dataManagementService.clearAllData();
            
            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("warningMessage", 
                    "⚠️ Đã xóa tất cả dữ liệu! Database hiện tại đã trống.");
                redirectAttributes.addFlashAttribute("result", result);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "❌ Lỗi khi xóa dữ liệu: " + result.getErrorMessage());
            }
            
        } catch (Exception e) {
            log.error("Error clearing data", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Lỗi hệ thống khi xóa dữ liệu: " + e.getMessage());
        }
        
        return "redirect:/admin/data";
    }
    
    /**
     * API endpoint để lấy thống kê dữ liệu (JSON)
     */
    @GetMapping("/stats")
    @ResponseBody
    public DataManagementService.DataCount getDataStats() {
        return dataManagementService.getDataCount();
    }
    
    /**
     * API endpoint để load dữ liệu qua AJAX
     */
    @PostMapping("/api/load-all")
    @ResponseBody
    public DataManagementService.DataLoadResult loadAllDataApi() {
        log.info("API call to load all default data");
        return dataManagementService.loadAllDefaultData();
    }
    
    /**
     * API endpoint để load dữ liệu cụ thể qua AJAX
     */
    @PostMapping("/api/load/{dataType}")
    @ResponseBody
    public DataManagementService.DataLoadResult loadSpecificDataApi(@PathVariable String dataType) {
        log.info("API call to load specific data: {}", dataType);
        return dataManagementService.loadSpecificData(dataType);
    }
    
    /**
     * API endpoint để xóa dữ liệu qua AJAX
     */
    @PostMapping("/api/clear-all")
    @ResponseBody
    public DataManagementService.DataLoadResult clearAllDataApi() {
        log.warn("API call to clear all data - DANGEROUS!");
        return dataManagementService.clearAllData();
    }
    
    /**
     * Lấy danh sách các loại dữ liệu có thể load
     */
    private List<String> getAvailableDataTypes() {
        return Arrays.asList("brands", "categories", "users", "products", "promotions", "sales");
    }
}
