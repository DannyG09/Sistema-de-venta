package sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelProveedor extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtNombreProveedor, txtTelefonoProveedor, txtEmailProveedor;
    private JTable tableProveedores;
    private DefaultTableModel modeloTablaProveedores;

    public PanelProveedor() {
        setLayout(null);
        initializeComponents();
    }

    private void initializeComponents() {
        // Etiquetas y campos de texto
        JLabel lblNombreProveedor = new JLabel("Nombre:");
        lblNombreProveedor.setBounds(30, 60, 80, 14);
        add(lblNombreProveedor);

        JLabel lblTelefonoProveedor = new JLabel("Teléfono:");
        lblTelefonoProveedor.setBounds(30, 100, 80, 14);
        add(lblTelefonoProveedor);

        JLabel lblEmailProveedor = new JLabel("Email:");
        lblEmailProveedor.setBounds(30, 140, 80, 14);
        add(lblEmailProveedor);

        txtNombreProveedor = new JTextField();
        txtNombreProveedor.setBounds(120, 57, 120, 20);
        add(txtNombreProveedor);

        txtTelefonoProveedor = new JTextField();
        txtTelefonoProveedor.setBounds(120, 97, 120, 20);
        add(txtTelefonoProveedor);

        txtEmailProveedor = new JTextField();
        txtEmailProveedor.setBounds(120, 137, 120, 20);
        add(txtEmailProveedor);

        // Tabla
        modeloTablaProveedores = new DefaultTableModel(new String[]{"Nombre", "Teléfono", "Email"}, 0);
        tableProveedores = new JTable(modeloTablaProveedores);
        JScrollPane scrollPaneProveedores = new JScrollPane(tableProveedores);
        scrollPaneProveedores.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneProveedores.setBounds(270, 60, 400, 270);
        add(scrollPaneProveedores);

        // Botones de acción
        JButton btnGuardarProveedor = new JButton("Guardar");
        btnGuardarProveedor.setBounds(30, 270, 90, 23);
        add(btnGuardarProveedor);

        JButton btnActualizarProveedor = new JButton("Actualizar");
        btnActualizarProveedor.setBounds(130, 270, 100, 23);
        add(btnActualizarProveedor);

        JButton btnEliminarProveedor = new JButton("Eliminar");
        btnEliminarProveedor.setBounds(30, 310, 90, 23);
        add(btnEliminarProveedor);

        JButton btnNuevoProveedor = new JButton("Nuevo");
        btnNuevoProveedor.setBounds(130, 310, 100, 23);
        add(btnNuevoProveedor);

        // Acciones de los botones
        btnGuardarProveedor.addActionListener(e -> guardarProveedor());
        btnActualizarProveedor.addActionListener(e -> actualizarProveedor());
        btnEliminarProveedor.addActionListener(e -> eliminarProveedor());
        btnNuevoProveedor.addActionListener(e -> limpiarCampos());
    }

    // Métodos para las acciones
    private void guardarProveedor() {
        modeloTablaProveedores.addRow(new Object[]{
            txtNombreProveedor.getText(),
            txtTelefonoProveedor.getText(),
            txtEmailProveedor.getText()
        });
        limpiarCampos();
        System.out.println("Proveedor guardado.");
    }

    private void actualizarProveedor() {
        int selectedRow = tableProveedores.getSelectedRow();
        if (selectedRow >= 0) {
            modeloTablaProveedores.setValueAt(txtNombreProveedor.getText(), selectedRow, 0);
            modeloTablaProveedores.setValueAt(txtTelefonoProveedor.getText(), selectedRow, 1);
            modeloTablaProveedores.setValueAt(txtEmailProveedor.getText(), selectedRow, 2);
            limpiarCampos();
            System.out.println("Proveedor actualizado.");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para actualizar.");
        }
    }

    private void eliminarProveedor() {
        int selectedRow = tableProveedores.getSelectedRow();
        if (selectedRow >= 0) {
            modeloTablaProveedores .removeRow(selectedRow);
            limpiarCampos();
            System.out.println("Proveedor eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtEmailProveedor.setText("");
    }
}