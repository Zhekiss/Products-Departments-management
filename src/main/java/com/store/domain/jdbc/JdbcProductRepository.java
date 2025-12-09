package com.store.domain.jdbc;

import com.store.domain.models.Product;
import com.store.domain.repositories.ProductRepository;
import com.store.sql.config.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductRepository implements ProductRepository {

    private final Connection connection;

    public JdbcProductRepository(DBConnection dbConnection) {
        this.connection = dbConnection.getConnection();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех товаров: " + e.getMessage(), e);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске товара по ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        if (product.getProductId() == null) {
            return insert(product);
        } else {
            return update(product);
        }
    }

    private Product insert(Product product) {
        String sql = "INSERT INTO product (name, price, department_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            pstmt.setLong(3, product.getDepartmentId());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setProductId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении товара: " + e.getMessage(), e);
        }
        return product;
    }

    private Product update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, department_id = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            pstmt.setLong(3, product.getDepartmentId());
            pstmt.setLong(4, product.getProductId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении товара: " + e.getMessage(), e);
        }
        return product;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM product WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении товара: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name ILIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске товара по имени: " + e.getMessage(), e);
        }
        return products;
    }

    @Override
    public List<Product> findByDepartmentId(Long departmentId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, departmentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске товара по отделу: " + e.getMessage(), e);
        }
        return products;
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getLong("department_id")
        );
    }
}