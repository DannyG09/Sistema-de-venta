package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PanelClientes extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField textFieldNombre, textFieldEmail, textFieldTelefono, textFieldDireccion;
    private JTable table;
    private DefaultTableModel modeloTablaClientes;
    private Connection conn;

    public PanelClientes() {
        conn = Conexion_bdd.getConnection(); // Usar la misma conexión que en PanelProductos
        setLayout(null);
        initializeComponents();
        cargarClientes(); // Cargar datos iniciales en la tabla
    }

    private void initializeComponents() {
        // Modelo de tabla
        modeloTablaClientes = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Teléfono", "Dirección"}, 0);
        table = new JTable(modeloTablaClientes);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(257, 49, 466, 283);
        add(scrollPane);

        // Etiquetas
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(43, 52, 67, 14);
        add(lblNombre);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(42, 97, 46, 14);
        add(lblEmail);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(42, 144, 46, 14);
        add(lblTelefono);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(42, 193, 68, 14);
        add(lblDireccion);

        // Campos de texto
        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(117, 49, 120, 30);
        add(textFieldNombre);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(117, 94, 120, 30);
        add(textFieldEmail);

        textFieldTelefono = new JTextField();
        textFieldTelefono.setBounds(117, 141, 120, 30);
        add(textFieldTelefono);

        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(117, 190, 120, 30);
        add(textFieldDireccion);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(25, 276, 89, 23);
        add(btnGuardar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(137, 275, 89, 23);
        add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(25, 309, 89, 23);
        add(btnEliminar);

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(137, 309, 89, 23);
        add(btnNuevo);

        // Listeners de botones
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });

        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    // Método para guardar cliente
    private void guardarCliente() {
        String nombre = textFieldNombre.getText();
        String email = textFieldEmail.getText();
        String telefono = textFieldTelefono.getText();
        String direccion = textFieldDireccion.getText();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Consulta para insertar cliente en la base de datos
        String query = "INSERT INTO Clientes (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, telefono);
            stmt.setString(4, direccion);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idCliente = rs.getInt(1);
                    modeloTablaClientes.addRow(new Object[]{idCliente, nombre, email, telefono, direccion});
                    JOptionPane.showMessageDialog(this, "Cliente guardado correctamente con ID: " + idCliente);
                    limpiarCampos();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar un cliente
    private void actualizarCliente() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idCliente = (int) table.getValueAt(filaSeleccionada, 0);
        String nombre = textFieldNombre.getText();
        String email = textFieldEmail.getText();
        String telefono = textFieldTelefono.getText();
        String direccion = textFieldDireccion.getText();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "UPDATE Clientes SET nombre = ?, email = ?, telefono = ?, direccion = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, telefono);
            stmt.setString(4, direccion);
            stmt.setInt(5, idCliente);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                cargarClientes(); // Recargar la lista de clientes
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar un cliente
    private void eliminarCliente() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idCliente = (int) table.getValueAt(filaSeleccionada, 0);

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM Clientes WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idCliente);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                    cargarClientes(); // Recargar la lista de clientes
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para cargar los clientes en la tabla
    private void cargarClientes() {
        String query = "SELECT * FROM Clientes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            modeloTablaClientes.setRowCount(0); // Limpiar la tabla
            while (rs.next()) {
                modeloTablaClientes.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpiar campos
    private void limpiarCampos() {
        textFieldNombre.setText("");
        textFieldEmail.setText("");
        textFieldTelefono.setText("");
        textFieldDireccion.setText("");
    }
}
