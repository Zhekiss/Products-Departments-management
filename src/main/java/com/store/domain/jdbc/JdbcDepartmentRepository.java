package com.store.domain.jdbc;

import com.store.domain.models.Department;
import com.store.domain.repositories.DepartmentRepository;
import com.store.sql.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDepartmentRepository implements DepartmentRepository {

    private final Connection connection;

    public JdbcDepartmentRepository(DBConnection dbConnection) {
        this.connection = dbConnection.getConnection();
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                departments.add(mapRowToDepartment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех отделов: " + e.getMessage(), e);
        }
        return departments;
    }

    @Override
    public Optional<Department> findById(Long id) {
        String sql = "SELECT * FROM department WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToDepartment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске отдела по ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Department save(Department department) {
        if (department.getDepartmentId() == null) {
            return insert(department);
        } else {
            return update(department);
        }
    }

    private Department insert(Department department) {
        String sql = "INSERT INTO department (name, working_hours) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getWorkingHours());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                department.setDepartmentId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении отдела: " + e.getMessage(), e);
        }
        return department;
    }

    private Department update(Department department) {
        String sql = "UPDATE department SET name = ?, working_hours = ? WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getWorkingHours());
            pstmt.setLong(3, department.getDepartmentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении отдела: " + e.getMessage(), e);
        }
        return department;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM department WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении отдела: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Department> findByName(String name) {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department WHERE name ILIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                departments.add(mapRowToDepartment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске отдела по имени: " + e.getMessage(), e);
        }
        return departments;
    }

    private Department mapRowToDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getLong("department_id"),
                rs.getString("name"),
                rs.getString("working_hours")
        );
    }
}