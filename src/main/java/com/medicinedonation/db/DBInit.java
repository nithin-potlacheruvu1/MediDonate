package com.medicinedonation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBInit {

	 private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // your DB URL
	    private static final String USER = "system"; // your DB username
	    private static final String PASS = "Nithin200505"; // your DB password

	    public static void dropAllTables() {
	        String[] tables = { "USERS", "REWARD_SYSTEM", "REQUESTS", "DONATIONS", "RECIPIENTS", "MEDICINES", "DONORS"};
	        
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	             Statement stmt = conn.createStatement()) {
	             
	            // Disable constraints temporarily
	            stmt.execute("BEGIN FOR c IN (SELECT constraint_name, table_name FROM user_constraints WHERE constraint_type='R') LOOP EXECUTE IMMEDIATE 'ALTER TABLE '||c.table_name||' DISABLE CONSTRAINT '||c.constraint_name; END LOOP; END;");

	            for (String table : tables) {
	                try {
	                    stmt.executeUpdate("DROP TABLE " + table + " CASCADE CONSTRAINTS");
	                    System.out.println("Dropped table: " + table);
	                } catch (Exception e) {
	                    System.out.println("Table " + table + " does not exist or could not be dropped.");
	                }
	            }
	            
	            System.out.println("All tables dropped successfully.");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	        dropAllTables();
	    }
}
