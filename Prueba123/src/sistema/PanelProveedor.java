package sistema;

import Funcionalidad.Proveedor;
import Funcionalidad.ProveedorDAO;
import Funcionalidad.ProveedorDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProveedor extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtNombreProveedor, txtTelefonoProveedor, txtEmailProveedor;
    private JTable tableProveedores;
    private DefaultTableModel modeloTablaProveedores;
    private JButton btnGuardarProveedor, btnActualizarProveedor, btnEliminarProveedor, btnNuevoProveedor;

    private ProveedorDAO proveedorDAO;

    public PanelProveedor() {
        proveedorDAO = new ProveedorDAOImpl(); // Usar la implementación de ProveedorDAO
        setLayout(null);
        initializeComponents();
        cargarProveedores();
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

    private void guardarProveedor() {
        if (validarCampos()) {
            Proveedor proveedor = new Proveedor(
                    txtNombreProveedor.getText(),
                    txtTelefonoProveedor.getText(),
                    txtEmailProveedor.getText()
            );

            if (proveedorDAO.guardarProveedor(proveedor)) {
                modeloTablaProveedores.addRow(new Object[]{
                        proveedor.getId(),
                        proveedor.getNombre(),
                        proveedor.getTelefono(),
                        proveedor.getEmail()
                });
                JOptionPane.showMessageDialog(this, "Proveedor guardado correctamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarProveedor() {
        int selectedRow = tableProveedores.getSelectedRow();
        if (selectedRow >= 0) {
            Proveedor proveedor = new Proveedor(
                    (int) modeloTablaProveedores.getValueAt(selectedRow, 0),
                    txtNombreProveedor.getText(),
                    txtTelefonoProveedor.getText(),
                    txtEmailProveedor.getText()
            );

            if (proveedorDAO.actualizarProveedor(proveedor)) {
                cargarProveedores();
                JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para actualizar.");
        }
    }

    private void eliminarProveedor() {
        int selectedRow = tableProveedores.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) modeloTablaProveedores.getValueAt(selectedRow, 0);

            if (proveedorDAO.eliminarProveedor(id)) {
                modeloTablaProveedores.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar.");
        }
    }

    private void cargarProveedores() {
        modeloTablaProveedores.setRowCount(0);
        List<Proveedor> proveedores = proveedorDAO.obtenerTodosLosProveedores();
        for (Proveedor proveedor : proveedores) {
            modeloTablaProveedores.addRow(new Object[]{
                    proveedor.getId(),
                    proveedor.getNombre(),
                    proveedor.getTelefono(),
                    proveedor.getEmail()
            });
        }
    }

    private boolean validarCampos() {
        if (txtNombreProveedor.getText().isEmpty() ||
            txtTelefonoProveedor.getText().isEmpty() ||
            txtEmailProveedor.getText().isEmpty()) {
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
}
