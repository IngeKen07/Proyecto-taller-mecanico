/
package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
 

    
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    
    
    private static final String USER = "system";
    private static final String PASSWORD = "Trac123!!";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: Driver JDBC de Oracle no encontrado.");
        }
    }
}
    
