package com.store.web.context;

import com.store.domain.jdbc.JdbcDepartmentRepository;
import com.store.domain.jdbc.JdbcProductRepository;
import com.store.domain.repositories.DepartmentRepository;
import com.store.domain.repositories.ProductRepository;
import com.store.domain.services.DepartmentService;
import com.store.domain.services.ProductService;
import com.store.sql.config.DBConnection;

import java.sql.SQLException;

public class AppContext implements AutoCloseable {
    private static AppContext INSTANCE;

    private final DepartmentService departmentService;
    private final ProductService productService;
    private final DBConnection dbConnection;

    private AppContext() {
        dbConnection = new DBConnection();

        DepartmentRepository departmentRepository = new JdbcDepartmentRepository(dbConnection);
        ProductRepository productRepository = new JdbcProductRepository(dbConnection);

        this.departmentService = new DepartmentService(departmentRepository, productRepository);
        this.productService = new ProductService(productRepository, departmentRepository);
    }

    public static synchronized AppContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppContext();
        }
        return INSTANCE;
    }

    @Override
    public void close() throws SQLException {
        if (dbConnection != null) {
            // Если в DBConnection есть метод close()
            // dbConnection.close();
        }
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public DBConnection getDbConnection() {
        return dbConnection;
    }
}