package org.example.waste_manager_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Configuration
public class DatabaseInitialize implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitialize(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    private void init() {
        Resource resource = new ClassPathResource("import.sql");
        try {
            Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            String sql = "SELECT * FROM WASTE_MANAGER";

            if (jdbcTemplate.queryForList(sql).isEmpty()){
                System.out.println("**Established connection, insert data into WASTE_MANAGER_ADDRESS");
                ScriptUtils.executeSqlScript(connection, resource);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
