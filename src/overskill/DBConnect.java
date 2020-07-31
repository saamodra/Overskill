/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

/**
 *
 * @author samod
 */
public class DBConnect {
    public Connection conn;
    public Statement stat;
    public ResultSet result;
    public PreparedStatement pstat;
    
    public DBConnect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Overskill;integratedSecurity=true";
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
        } catch(Exception e) {
            System.out.println("Error saat connect database: " + e);
        }
    }
    
    public DBConnect(String db) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=" + db + ";integratedSecurity=true";
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
        } catch(Exception e) {
            System.out.println("Error saat connect database: " + e);
        }
    }
    
}
