/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author keneth-gonzalez
 */
public class ConexionBD {
 

    // Usamos el formato //localhost:1521/XEPDB1 para Service Name
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    
    // El usuario y contraseña que creamos en el Paso 1
    private static final String USER = "TALLER_USER";
    private static final String PASSWORD = "Taller2026";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: Driver JDBC de Oracle no encontrado.");
        }
    }
}
    
