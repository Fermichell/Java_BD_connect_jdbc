package com.example;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.MapMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DatabaseConnection {
    private static Jdbi jdbi;

    public static Jdbi getJdbi() {
        if (jdbi == null) {
            // Підключення до SQL Server
            jdbi = Jdbi.create(
                "jdbc:sqlserver://DESKTOP-KDE8QBG:1433;databaseName=BD26;integratedSecurity=true;encrypt=false;trustServerCertificate=true"
            );

            jdbi.installPlugin(new SqlObjectPlugin()); // Підключаємо підтримку DAO
            jdbi.registerRowMapper(new MapMapper());
        }
        return jdbi;
    }
}
