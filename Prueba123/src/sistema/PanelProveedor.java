package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PanelProveedor extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtNombreProveedor, txtTelefonoProveedor, txtEmailProveedor;
    private JTable tableProveedores;
    private DefaultTableModel modeloTablaProveedores;
    private JButton btnGuardarProveedor, btnActualizarProveedor, btnEliminarProveedor, btnNuevoProveedor;
    private Connection conn; // Conexión a la base de datos

    public PanelProveedor() {
        conn = Conexion_bdd.getConnection(); // Establecer la conexión a la base de datos
        setLayout(null);
        initializeComponents();
        cargarProveedores(); // Cargar los proveedores al inicio
    }

    private void initializeComponents() {
        // Etiquetas y campos de texto
        JLabel lblNombreProveedor = new JLabel("Nombre:");
        lblNombreProveedor.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        lblNombreProveedor.setBounds(30, 122, 80, 14);
        add(lblNombreProveedor);

        JLabel lblTelefonoProveedor = new JLabel("Teléfono:");
        lblTelefonoProveedor.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        lblTelefonoProveedor.setBounds(30, 162, 80, 14);
        add(lblTelefonoProveedor);

        JLabel lblEmailProveedor = new JLabel("Email:");
        lblEmailProveedor.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        lblEmailProveedor.setBounds(30, 202, 80, 14);
        add(lblEmailProveedor);

        txtNombreProveedor = new JTextField();
        txtNombreProveedor.setBounds(120, 119, 120, 30);
        add(txtNombreProveedor);

        txtTelefonoProveedor = new JTextField();
        txtTelefonoProveedor.setBounds(120, 159, 120, 30);
        add(txtTelefonoProveedor);

        txtEmailProveedor = new JTextField();
        txtEmailProveedor.setBounds(120, 199, 120, 30);
        add(txtEmailProveedor);

        // Tabla
        modeloTablaProveedores = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Email"}, 0);
        tableProveedores = new JTable(modeloTablaProveedores);
        JScrollPane scrollPaneProveedores = new JScrollPane(tableProveedores);
        scrollPaneProveedores.setBounds(270, 99, 495, 270);
        add(scrollPaneProveedores);

        // Botones
        btnGuardarProveedor = new JButton("Guardar");
        btnGuardarProveedor.setBounds(30, 270, 90, 23);
        btnGuardarProveedor.setBackground(new Color(34, 139, 34)); // Fondo verde
        btnGuardarProveedor.setForeground(Color.WHITE); // Texto blanco
        add(btnGuardarProveedor);

        btnActualizarProveedor = new JButton("Actualizar");
        btnActualizarProveedor.setBounds(130, 270, 100, 23);
        btnActualizarProveedor.setBackground(new Color(30, 144, 255)); // Fondo azul
        btnActualizarProveedor.setForeground(Color.WHITE); // Texto blanco
        add(btnActualizarProveedor);

        btnEliminarProveedor = new JButton("Eliminar");
        btnEliminarProveedor.setBounds(30, 310, 90, 23);
        btnEliminarProveedor.setBackground(new Color(255, 99, 71)); // Fondo rojo
        btnEliminarProveedor.setForeground(Color.WHITE); // Texto blanco
        add(btnEliminarProveedor);

        btnNuevoProveedor = new JButton("Nuevo");
        btnNuevoProveedor.setBounds(130, 310, 100, 23);
        btnNuevoProveedor.setBackground(new Color(255, 165, 0)); // Fondo naranja
        btnNuevoProveedor.setForeground(Color.WHITE); // Texto blanco
        add(btnNuevoProveedor);

        JLabel lblNewLabel = new JLabel("Agregar Nuevos Proveedores");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        lblNewLabel.setBounds(229, 36, 366, 30);
        add(lblNewLabel);

        // Acciones de los botones
        btnGuardarProveedor.addActionListener(e -> guardarProveedor());
        btnActualizarProveedor.addActionListener(e -> actualizarProveedor());
        btnEliminarProveedor.addActionListener(e -> eliminarProveedor());
        btnNuevoProveedor.addActionListener(e -> limpiarCampos());
    }

    // Métodos para las acciones
    private void guardarProveedor() {
        if (validarCampos()) {
            String nombre = txtNombreProveedor.getText();
            String telefono = txtTelefonoProveedor.getText();
            String email = txtEmailProveedor.getText();

            String query = "INSERT INTO proveedores (nombre, telefono, email) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombre);
                stmt.setString(2, telefono);
                stmt.setString(3, email);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1); // Obtener el id generado
                        modeloTablaProveedores.addRow(new Object[]{id, nombre, telefono, email});
                        JOptionPane.showMessageDialog(this, "Proveedor guardado correctamente con ID: " + id);
                        limpiarCampos();
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarProveedor() {
        int selectedRow = tableProveedores.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (Integer) modeloTablaProveedores.getValueAt(selectedRow, 0);
            String nombre = txtNombreProveedor.getText();
            String telefono = txtTelefonoProveedor.getText();
            String email = txtEmailProveedor.getText();

            String query = "UPDATE proveedores SET nombre = ?, telefono = ?, email = ? WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nombre);
                stmt.setString(2, telefono);
                stmt.setString(3, email);
                stmt.setInt(4, id);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    modeloTablaProveedores.setValueAt(nombre, selectedRow, 1);
                    modeloTablaProveedores.setValueAt(telefono, selectedRow, 2);
                    modeloTablaProveedores.setValueAt(email, selectedRow, 3);
                    JOptionPane.showMessageDialog(this, "Proveedor actualizado.");
                    limpiarCampos();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para actualizar.");
        }
    }

    private void eliminarProveedor() {
        int selectedRow = tableProveedores.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (Integer) modeloTablaProveedores.getValueAt(selectedRow, 0);

            String query = "DELETE FROM proveedores WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    modeloTablaProveedores.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar.");
        }
    }

    private boolean validarCampos() {
        if (txtNombreProveedor.getText().isEmpty() || txtTelefonoProveedor.getText().isEmpty() || txtEmailProveedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtEmailProveedor.setText("");
    }

    private void cargarProveedores() {
        String query = "SELECT * FROM proveedores";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                modeloTablaProveedores.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los proveedores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
