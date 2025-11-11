package com.store.domain.inmemory;

import com.store.domain.models.Department;
import com.store.domain.repositories.DepartmentRepository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryDepartmentRepository implements DepartmentRepository {
    private final Map<Long, Department> departments = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InMemoryDepartmentRepository() {
        initializeTestData();
    }

    private void initializeTestData() {
        save(new Department(1L, "Электроника", "10:00-22:00"));
        save(new Department(2L, "Одежда", "9:00-21:00"));
        save(new Department(3L, "Продукты", "8:00-23:00"));
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments.values());
    }

    @Override
    public Optional<Department> findById(Long id) {
        return Optional.ofNullable(departments.get(id));
    }

    @Override
    public Department save(Department department) {
        if (department.getDepartmentId() == null) {
            department.setDepartmentId(idGenerator.getAndIncrement());
        }
        departments.put(department.getDepartmentId(), department);
        return department;
    }

    @Override
    public void delete(Long id) {
        departments.remove(id);
    }

    @Override
    public List<Department> findByName(String name) {
        return departments.values().stream()
                .filter(department -> department.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}