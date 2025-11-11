package com.store.domain.inmemory;

import com.store.domain.models.Product;
import com.store.domain.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InMemoryProductRepository() {
        initializeTestData();
    }

    private void initializeTestData() {
        save(new Product(1L, "iPhone 15", new BigDecimal("999.99"), 1L));
        save(new Product(2L, "MacBook Pro", new BigDecimal("1999.99"), 1L));
        save(new Product(3L, "Футболка", new BigDecimal("29.99"), 2L));
        save(new Product(4L, "Джинсы", new BigDecimal("79.99"), 2L));
        save(new Product(5L, "Хлеб", new BigDecimal("2.99"), 3L));
        save(new Product(6L, "Молоко", new BigDecimal("1.99"), 3L));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Product save(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(idGenerator.getAndIncrement());
        }
        products.put(product.getProductId(), product);
        return product;
    }

    @Override
    public void delete(Long id) {
        products.remove(id);
    }

    @Override
    public List<Product> findByName(String name) {
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByDepartmentId(Long departmentId) {
        return products.values().stream()
                .filter(product -> product.getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
    }
}