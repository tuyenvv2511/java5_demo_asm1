package org.example.demo_j5_asm1.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SampleImageService {
    
    private static final String UPLOAD_DIR = "uploads/images/";
    
    public String createSampleImage(String productName, String brandName, int width, int height) {
        try {
            // Tạo hình ảnh mẫu
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            
            // Màu nền gradient
            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRect(0, 0, width, height);
            
            // Vẽ border
            g2d.setColor(new Color(200, 200, 200));
            g2d.drawRect(0, 0, width - 1, height - 1);
            
            // Vẽ text
            g2d.setColor(new Color(50, 50, 50));
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            
            // Vẽ brand name ở trên
            String brandText = brandName.toUpperCase();
            int brandX = (width - g2d.getFontMetrics().stringWidth(brandText)) / 2;
            g2d.drawString(brandText, brandX, 30);
            
            // Vẽ product name ở giữa
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            String[] words = productName.split(" ");
            int y = height / 2;
            for (String word : words) {
                int wordX = (width - g2d.getFontMetrics().stringWidth(word)) / 2;
                g2d.drawString(word, wordX, y);
                y += 15;
            }
            
            // Vẽ "SAMPLE IMAGE" ở dưới
            g2d.setFont(new Font("Arial", Font.ITALIC, 10));
            g2d.setColor(new Color(150, 150, 150));
            String sampleText = "SAMPLE IMAGE";
            int sampleX = (width - g2d.getFontMetrics().stringWidth(sampleText)) / 2;
            g2d.drawString(sampleText, sampleX, height - 10);
            
            g2d.dispose();
            
            // Lưu file
            String filename = productName.toLowerCase().replaceAll("[^a-z0-9]", "_") + ".jpg";
            Path targetPath = Paths.get(UPLOAD_DIR + filename);
            ImageIO.write(image, "jpg", targetPath.toFile());
            
            log.info("Created sample image: {}", filename);
            return "/uploads/images/" + filename;
            
        } catch (IOException e) {
            log.error("Failed to create sample image for {}", productName, e);
            return null;
        }
    }
    
    public void createAllSampleImages() {
        // Tạo hình ảnh mẫu cho các sản phẩm
        createSampleImage("Nike Dunk Low Panda", "Nike", 300, 300);
        createSampleImage("Adidas Samba OG", "Adidas", 300, 300);
        createSampleImage("Panini Prizm Messi #15", "Panini", 300, 300);
        createSampleImage("Jordan 1 Retro High", "Jordan", 300, 300);
        createSampleImage("Converse Chuck Taylor", "Converse", 300, 300);
        
        // Tạo ảnh cho Figures & Toys
        createSampleImage("Funko Pop Batman #01", "Funko", 300, 300);
        createSampleImage("Bandai Gundam RX-78-2", "Bandai", 300, 300);
        createSampleImage("Hasbro Transformers Optimus Prime", "Hasbro", 300, 300);
        createSampleImage("Lego Star Wars AT-AT", "Lego", 300, 300);
        createSampleImage("Lego Star Wars Death Star", "Lego", 300, 300);
    }
}
