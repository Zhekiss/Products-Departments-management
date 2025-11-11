package com.store.domain.services;

import com.store.domain.models.Department;
import com.store.domain.models.Product;
import com.store.domain.repositories.DepartmentRepository;
import com.store.domain.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;

public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ProductRepository productRepository;

    public DepartmentService(DepartmentRepository departmentRepository, ProductRepository productRepository) {
        this.departmentRepository = departmentRepository;
        this.productRepository = productRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(String name, String workingHours) {
        Department department = new Department(null, name, workingHours);
        return departmentRepository.save(department);
    }

    public boolean updateDepartment(Long departmentId, String name, String workingHours) {
        return departmentRepository.findById(departmentId)
                .map(department -> {
                    department.setName(name);
                    department.setWorkingHours(workingHours);
                    departmentRepository.save(department);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteDepartment(Long departmentId) {
        List<Product> productsInDepartment = productRepository.findByDepartmentId(departmentId);
        if (!productsInDepartment.isEmpty()) {
            return false;
        }

        departmentRepository.delete(departmentId);
        return true;
    }

    public List<Department> searchDepartmentsByName(String name) {
        return departmentRepository.findByName(name);
    }

    public List<Department> getDepartmentsWithoutProducts() {
        List<Department> allDepartments = departmentRepository.findAll();
        return allDepartments.stream()
                .filter(department -> {
                    List<Product> products = productRepository.findByDepartmentId(department.getDepartmentId());
                    return products.isEmpty();
                })
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Product> getProductsInDepartment(Long departmentId) {
        return productRepository.findByDepartmentId(departmentId);
    }
}