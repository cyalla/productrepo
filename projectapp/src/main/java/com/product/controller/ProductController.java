
package com.product.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProductController.class);


    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
        	  double priceSave = product.getPrice();
              String priceStr = String.valueOf(priceSave);
              double taxSave = product.getTax();
              String taxStr = String.valueOf(taxSave);
              double finalPriceSave = product.getFinalPrice();
              String finalPriceStr = String.valueOf(finalPriceSave);
              productDTOs.add(new ProductDTO(product.getId(), product.getName(), priceStr, taxStr, finalPriceStr));
        }
        return productDTOs;
    }
    
    @PostMapping

public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
    try {
        if (productDTO == null || productDTO.getName() == null || productDTO.getPrice() == null || productDTO.getTax() == null) {
            throw new IllegalArgumentException("Invalid productDTO");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setTax(productDTO.getTax());
        product.setFinalPrice(calculateFinalPrice(productDTO));

        Product savedProduct = productService.save(product);

        return new ProductDTO(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), savedProduct.getTax(), savedProduct.getFinalPrice());
    } catch (IllegalArgumentException e) {
        logger.error("IllegalArgumentException: {}", e.getMessage());
        return new ErrorDTO("Invalid input");
    } catch (Exception e) {
        logger.error("An error occurred: {}", e.getMessage());
        return new ErrorDTO("Internal server error");
    }
}

private double calculateFinalPrice(ProductDTO productDTO) {
    double price = productDTO.getPrice();
    double tax = productDTO.getTax();

    if (price <= 0 || tax <= 0) {
        throw new IllegalArgumentException("Price and tax must be positive values");
    }

    double finalPrice = price * (1.0 + (tax / 100.0));
    return finalPrice;
}

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id)
                             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        double priceSave = product.getPrice();
        String priceStr = String.valueOf(priceSave);
        double taxSave = product.getTax();
        String taxStr = String.valueOf(taxSave);
        double finalPriceSave = product.getFinalPrice();
        String finalPriceStr = String.valueOf(finalPriceSave);
        // Conversion to ProductDTO and returning the response
        return ResponseEntity.ok(new ProductDTO(product.getId(), product.getName(), priceStr, taxStr, finalPriceStr));
    } 
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = productService.findById(id).orElse(null);
        if (product != null) {
            product.setName(productDTO.getName());
            
            String price = productDTO.getPrice();
            double priceDouble = Double.parseDouble(price);
            product.setPrice(priceDouble);
            String tax = productDTO.getTax();
            double taxtDouble = Double.parseDouble(tax);
            product.setTax(taxtDouble);
            double finalPrice = product.getPrice()* (1.0 - (product.getTax()/ 100.0));
            product.setFinalPrice(finalPrice);
            
 
            Product updatedProduct = productService.save(product);
            
            double priceSave = updatedProduct.getPrice();
            String priceStr = String.valueOf(priceSave);
            double taxSave = updatedProduct.getTax();
            String taxStr = String.valueOf(taxSave);
            double finalPriceSave = updatedProduct.getFinalPrice();
            String finalPriceStr = String.valueOf(finalPriceSave);
            
            return ResponseEntity.ok(new ProductDTO(updatedProduct.getId(), updatedProduct.getName(), priceStr, taxStr, finalPriceStr));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}