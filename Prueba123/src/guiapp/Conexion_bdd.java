package guiapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion_bdd {
    private String urlDB = "jdbc:mysql://localhost:3306/venta_db"; // Cambié el nombre de la base de datos aquí
    private String usuarioDB = "root"; // Cambia según tu configuración
    private String contrasenaDB = "Danny0905"; // Cambia según tu configuración

    // Método para conectar a la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(urlDB, usuarioDB, contrasenaDB);
    }

    // Método para agregar una venta
    public void agregarVenta(String cliente, String producto, int cantidad, double precio, double total) {
        String sql = "INSERT INTO ventas (cliente, producto, cantidad, precio, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, cliente);
            pstmt.setString(2, producto);
            pstmt.setInt(3, cantidad);
            pstmt.setDouble(4, precio);
            pstmt.setDouble(5, total);
            pstmt.executeUpdate();
            System.out.println("Venta agregada exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al agregar la venta: " + e.getMessage());
        }
    }

    // Método para inicializar la base de datos y las tablas
    public static void iniciarBaseDeDatos() {
        String urlDB = "jdbc:mysql://localhost:3306/"; // Base de datos predeterminada para la creación
        String usuarioDB = "root";
        String contrasenaDB = "Danny0905";

        try (Connection conexion = DriverManager.getConnection(urlDB, usuarioDB, contrasenaDB);
             Statement stmt = conexion.createStatement()) {

            // Crear la base de datos 'venta_db' si no existe
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS venta_db");
            stmt.executeUpdate("USE venta_db");

            // Deshabilitar claves foráneas temporalmente
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

            // Eliminar las tablas existentes si ya existen
            stmt.executeUpdate("DROP TABLE IF EXISTS detallesventa");
            stmt.executeUpdate("DROP TABLE IF EXISTS ventas");

            // Habilitar claves foráneas nuevamente
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

            // Crear la tabla 'ventas'
            String crearTablaVentas = "CREATE TABLE ventas (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "cliente VARCHAR(100) NOT NULL, " +
                    "producto VARCHAR(100) NOT NULL, " +
                    "cantidad INT NOT NULL, " +
                    "precio DECIMAL(10, 2) NOT NULL, " +
                    "total DECIMAL(10, 2) NOT NULL, " +
                    "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(crearTablaVentas);

            // Crear la tabla 'detallesventa'
            String crearTablaDetalles = "CREATE TABLE detallesventa (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "venta_id INT NOT NULL, " +
                    "detalle VARCHAR(255), " +
                    "FOREIGN KEY (venta_id) REFERENCES ventas(id) ON DELETE CASCADE)";
            stmt.executeUpdate(crearTablaDetalles);

            System.out.println("Base de datos y tablas inicializadas correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    // Método main para probar
    public static void main(String[] args) {
        // Inicializar la base de datos
        iniciarBaseDeDatos();

        // Agregar una venta de ejemplo
        Conexion_bdd db = new Conexion_bdd();
        db.agregarVenta("Juan Pérez", "Laptop", 1, 800.00, 800.00);
    }
}
