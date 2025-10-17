package com.medicinedonation.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    // Create table from annotation
    public static void createTableIfNotExists(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) return;

        Table table = clazz.getAnnotation(Table.class);
        String tableName = table.name();

        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Column.class)) continue;

            Column col = field.getAnnotation(Column.class);
            sql.append(col.name()).append(" ").append(col.type());

            if (col.autoIncrement()) sql.append(" GENERATED ALWAYS AS IDENTITY");
            if (col.primaryKey()) sql.append(" PRIMARY KEY");
            if (col.notNull()) sql.append(" NOT NULL");
            if (col.unique()) sql.append(" UNIQUE");


            sql.append(", ");
        }

        sql.setLength(sql.length() - 2); // remove last comma
        sql.append(")");

        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql.toString());
            System.out.println("Table '" + tableName + "' created successfully.");
        }  catch (Exception e) {
        	if (e.getMessage().contains("ORA-00955")) { // table already exists
                System.out.println("Table '" + tableName + "' already exists, skipping creation.");
            } else {
                System.err.println("Error creating table " + tableName + ": " + e.getMessage());
            }
        }

    }


    // Setup database: create tables and drop empty ones
    public static void setupDatabase(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            createTableIfNotExists(clazz);
        }

       
    }
}
