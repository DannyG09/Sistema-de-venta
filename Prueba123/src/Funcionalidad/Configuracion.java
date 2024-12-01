package Funcionalidad;

import guiapp.Conexion_bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Configuracion implements IConfiguracion {
    private Connection conn;

    public Configuracion() {
        conn = Conexion_bdd.getConnection();
    }

    @Override
    public void cargarDatosConfiguracion() {
        String query = "SELECT rnc, nombre_empresa, telefono, direccion FROM Configuracion LIMIT 2";
        
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Asignar los valores a los campos de configuración (esto debería hacer algo en la UI)
                System.out.println("RNC: " + rs.getString("rnc"));
                System.out.println("Nombre Empresa: " + rs.getString("nombre_empresa"));
                System.out.println("Teléfono: " + rs.getString("telefono"));
                System.out.println("Dirección: " + rs.getString("direccion"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al cargar la configuración: " + ex.getMessage());
        }
    }

    @Override
    public void guardarConfiguracion(String rnc, String nombreEmpresa, String telefono, String direccion) {
        String query = "INSERT INTO Configuracion (rnc, nombre_empresa, telefono, direccion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, rnc);
            stmt.setString(2, nombreEmpresa);
            stmt.setString(3, telefono);
            stmt.setString(4, direccion);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Configuración guardada correctamente.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al guardar la configuración: " + ex.getMessage());
        }
    }

    @Override
    public boolean validarCampos(String rnc, String nombreEmpresa, String telefono, String direccion) {
        return !(rnc == null || rnc.isEmpty() ||
                 nombreEmpresa == null || nombreEmpresa.isEmpty() ||
                 telefono == null || telefono.isEmpty() ||
                 direccion == null || direccion.isEmpty());
    }
}
