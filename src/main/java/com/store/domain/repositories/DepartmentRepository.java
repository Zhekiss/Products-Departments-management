package com.store.domain.repositories;

import com.store.domain.models.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    List<Department> findAll();
    Optional<Department> findById(Long id);
    Department save(Department department);
    void delete(Long id);

    List<Department> findByName(String name);
}