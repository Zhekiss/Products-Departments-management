package com.store.domain.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Long departmentId;

    public Product() {}

    public Product(Long productId, String name, BigDecimal price, Long departmentId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.departmentId = departmentId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", departmentId=" + departmentId +
                '}';
    }
}