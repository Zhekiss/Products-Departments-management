package com.store.domain.services;

import com.store.domain.models.Product;
import com.store.domain.repositories.ProductRepository;
import com.store.domain.repositories.DepartmentRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;
    private final DepartmentRepository departmentRepository;

    public ProductService(ProductRepository productRepository, DepartmentRepository departmentRepository) {
        this.productRepository = productRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(String name, BigDecimal price, Long departmentId) {
        if (!departmentRepository.findById(departmentId).isPresent()) {
            throw new IllegalArgumentException("Отдел с ID " + departmentId + " не существует");
        }

        Product product = new Product(null, name, price, departmentId);
        return productRepository.save(product);
    }

    public boolean updateProduct(Long productId, String name, BigDecimal price, Long departmentId) {
        if (!departmentRepository.findById(departmentId).isPresent()) {
            return false;
        }

        return productRepository.findById(productId)
                .map(product -> {
                    product.setName(name);
                    product.setPrice(price);
                    product.setDepartmentId(departmentId);
                    productRepository.save(product);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteProduct(Long productId) {
        productRepository.delete(productId);
        return true;
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductsByDepartment(Long departmentId) {
        return productRepository.findByDepartmentId(departmentId);
    }

    public List<Product> searchProducts(String name, Long departmentId) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product ->
                        (name == null || product.getName().toLowerCase().contains(name.toLowerCase())) &&
                                (departmentId == null || product.getDepartmentId().equals(departmentId))
                )
                .collect(java.util.stream.Collectors.toList());
    }
}