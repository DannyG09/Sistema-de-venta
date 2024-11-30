package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;

public class PanelConfiguracion extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtRnc, txtNombreEmpresa, txtTelefono, txtDireccion;
    private Connection conn;

    public String getRnc() {
        return txtRnc.getText().trim();
    }

    public String getNombreEmpresa() {
        return txtNombreEmpresa.getText().trim();
    }

    public String getTelefono() {
        return txtTelefono.getText().trim();
    }

    public String getDireccion() {
        return txtDireccion.getText().trim();
    }


    public PanelConfiguracion() {
        conn = Conexion_bdd.getConnection(); // Conexión a la base de datos
        setLayout(null);
        initializeComponents();
        cargarDatosConfiguracion(); // Cargar los datos al iniciar
    }

    private void initializeComponents() {
        // Etiquetas y campos de texto
        JLabel lblRnc = new JLabel("RNC:");
        lblRnc.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        lblRnc.setBounds(38, 148, 80, 20);
        add(lblRnc);

        txtRnc = new JTextField();
        txtRnc.setBounds(111, 143, 393, 30);
        add(txtRnc);

        JLabel lblNombreEmpresa = new JLabel("Nombre Empresa:");
        lblNombreEmpresa.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        lblNombreEmpresa.setBounds(228, 202, 120, 20);
        add(lblNombreEmpresa);

        txtNombreEmpresa = new JTextField();
        txtNombreEmpresa.setBounds(360, 198, 393, 30);
        add(txtNombreEmpresa);

        JLabel lblTelefonoConfig = new JLabel("Teléfono:");
        lblTelefonoConfig.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        lblTelefonoConfig.setBounds(10, 250, 80, 20);
        add(lblTelefonoConfig);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(100, 250, 393, 30);
        add(txtTelefono);

        JLabel lblDireccionConfig = new JLabel("Dirección:");
        lblDireccionConfig.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        lblDireccionConfig.setBounds(262, 306, 80, 20);
        add(lblDireccionConfig);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(352, 306, 393, 30);
        add(txtDireccion);

        // Botones de acción
        JButton btnGuardarConfiguracion = new JButton("Guardar");
        btnGuardarConfiguracion.setBounds(228, 357, 100, 30);
        add(btnGuardarConfiguracion);

        JButton btnCancelarConfiguracion = new JButton("Cancelar");
        btnCancelarConfiguracion.setBounds(338, 357, 100, 30);
        add(btnCancelarConfiguracion);
        
        JLabel lblNewLabel = new JLabel("Datos de la Empresa");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
        lblNewLabel.setBounds(249, 49, 348, 45);
        add(lblNewLabel);

        // Acción para guardar los datos de configuración
        btnGuardarConfiguracion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarConfiguracion();
            }
        });

        // Acción para cancelar (sin limpiar los campos)
        btnCancelarConfiguracion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarConfiguracion();
            }
        });
    }

    // Método para guardar la configuración
    private void guardarConfiguracion() {
        if (validarCampos()) {
            String rnc = txtRnc.getText();
            String nombreEmpresa = txtNombreEmpresa.getText();
            String telefono = txtTelefono.getText();
            String direccion = txtDireccion.getText();

            String query = "INSERT INTO Configuracion (rnc, nombre_empresa, telefono, direccion) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, rnc);
                stmt.setString(2, nombreEmpresa);
                stmt.setString(3, telefono);
                stmt.setString(4, direccion);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Configuración guardada correctamente.");
                    System.out.println("Configuración Guardada:");
                    System.out.println("RNC: " + rnc);
                    System.out.println("Nombre Empresa: " + nombreEmpresa);
                    System.out.println("Teléfono: " + telefono);
                    System.out.println("Dirección: " + direccion);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar la configuración: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para validar los campos
    private boolean validarCampos() {
        if (txtRnc.getText().isEmpty() || txtNombreEmpresa.getText().isEmpty() ||
            txtTelefono.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Método para cargar los datos de la configuración desde la base de datos
    private void cargarDatosConfiguracion() {
        String query = "SELECT rnc, nombre_empresa, telefono, direccion FROM Configuracion LIMIT 2";
        
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                txtRnc.setText(rs.getString("rnc"));
                txtNombreEmpresa.setText(rs.getString("nombre_empresa"));
                txtTelefono.setText(rs.getString("telefono"));
                txtDireccion.setText(rs.getString("direccion"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la configuración: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cancelar la configuración (sin limpiar los campos)
    private void cancelarConfiguracion() {
        System.out.println("Configuración cancelada.");
    }
}
