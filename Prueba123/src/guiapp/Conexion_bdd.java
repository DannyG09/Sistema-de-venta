
package guiapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion_bdd {
    private static final String URL = "jdbc:mysql://localhost:3306/venta_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Danny0905";
    private static Connection conn;

    // Método para establecer conexión
    public static Connection getConnection() {
        if (conn == null) { // Usamos singleton para que haya una sola conexión
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos.");
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return conn;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
