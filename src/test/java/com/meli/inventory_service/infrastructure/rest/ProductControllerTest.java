package com.meli.inventory_service.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.inventory_service.application.service.ProductService;
import com.meli.inventory_service.domain.model.Product;
import com.meli.inventory_service.infrastructure.rest.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void createProduct_Success() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Test Laptop");
        request.setSku("LAP-TEST-001");
        request.setPrice(999.99);
        request.setStock(10);

        Product created = new Product();
        created.setId("1");
        created.setName(request.getName());
        created.setSku(request.getSku());
        created.setPrice(request.getPrice());
        created.setStock(request.getStock());

        when(productService.create(any(Product.class))).thenReturn(created);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    void getAllProducts_Success() throws Exception {
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Laptop");

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Mouse");

        when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    void getProduct_Success() throws Exception {
        Product product = new Product();
        product.setId("1");
        product.setName("Laptop");

        when(productService.findById("1")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void getProduct_NotFound() throws Exception {
        when(productService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProduct_Success() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Updated Laptop");
        request.setSku("LAP-UPD-001");

        Product updated = new Product();
        updated.setId("1");
        updated.setName(request.getName());
        updated.setSku(request.getSku());

        when(productService.update(eq("1"), any(Product.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    void deleteProduct_Success() throws Exception {
        doNothing().when(productService).delete("1");

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).delete("1");
    }

    @Test
    void createProduct_ValidationFail() throws Exception {
        ProductRequest request = new ProductRequest();
        // Missing required fields

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
