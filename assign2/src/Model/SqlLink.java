package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlLink {

    public static void main(String[] args) {

        final String DB_NAME = "CareHomeDB";

        //use try-with-resources Statement
        try (Connection con = getConnection(DB_NAME)) {

            System.out.println("Connection to database "
                    + DB_NAME + " created successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection(String dbName)
            throws SQLException, ClassNotFoundException {
        //Registering the HSQLDB JDBC driver
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        /* Database files will be created in the "database"
         * folder in the project. If no username or password is
         * specified, the default SA user and an empty password are used */
        Connection con = DriverManager.getConnection
                ("jdbc:hsqldb:file:F:/advanced programing/assignment-2-S3810082-1/assignment-2-Lucasschool/assign2/database/CareHomeDB");
        return con;
    }
}
