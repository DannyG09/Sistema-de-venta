package sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelProductos extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtCodigoProducto, txtNombreProducto, txtCategoriaProducto, txtPrecioProducto, txtStockProducto;
    private JTable tableProductos;
    private DefaultTableModel modeloTablaProductos;

    public PanelProductos() {
        setLayout(null);
        initializeComponents();
    }

    private void initializeComponents() {
        // Etiquetas y campos de texto
        JLabel lblCodigoProducto = new JLabel("Código:");
        lblCodigoProducto.setBounds(30, 60, 80, 14);
        add(lblCodigoProducto);

        JLabel lblNombreProducto = new JLabel("Nombre:");
        lblNombreProducto.setBounds(30, 100, 80, 14);
        add(lblNombreProducto);

        JLabel lblCategoriaProducto = new JLabel("Categoría:");
        lblCategoriaProducto.setBounds(30, 140, 80, 14);
        add(lblCategoriaProducto);

        JLabel lblPrecioProducto = new JLabel("Precio:");
        lblPrecioProducto.setBounds(30, 180, 80, 14);
        add(lblPrecioProducto);

        JLabel lblStockProducto = new JLabel("Stock:");
        lblStockProducto.setBounds(30, 220, 80, 14);
        add(lblStockProducto);

        txtCodigoProducto = new JTextField();
        txtCodigoProducto.setBounds(120, 57, 120, 20);
        add(txtCodigoProducto);

        txtNombreProducto = new JTextField();
        txtNombreProducto.setBounds(120, 97, 120, 20);
        add(txtNombreProducto);

        txtCategoriaProducto = new JTextField();
        txtCategoriaProducto.setBounds(120, 137, 120, 20);
        add(txtCategoriaProducto);

        txtPrecioProducto = new JTextField();
        txtPrecioProducto.setBounds(120, 177, 120, 20);
        add(txtPrecioProducto);

        txtStockProducto = new JTextField();
        txtStockProducto.setBounds(120, 217, 120, 20);
        add(txtStockProducto);

        // Tabla
        modeloTablaProductos = new DefaultTableModel(new String[]{"Código", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tableProductos = new JTable(modeloTablaProductos);
        tableProductos.getSelectionModel().addListSelectionListener(e -> llenarCamposDesdeTabla());
        JScrollPane scrollPaneProductos = new JScrollPane(tableProductos);
        scrollPaneProductos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneProductos.setBounds(270, 60, 400, 270);
        add(scrollPaneProductos);

        // Botones de acción
        JButton btnGuardarProducto = new JButton("Guardar");
        btnGuardarProducto.setBounds(30, 270, 90, 23);
        add(btnGuardarProducto);

        JButton btnActualizarProducto = new JButton("Actualizar");
        btnActualizarProducto.setBounds(130, 270, 100, 23);
        add(btnActualizarProducto);

        JButton btnEliminarProducto = new JButton("Eliminar");
        btnEliminarProducto.setBounds(30, 310, 90, 23);
        add(btnEliminarProducto);

        JButton btnNuevoProducto = new JButton("Nuevo");
        btnNuevoProducto.setBounds(130, 310, 100, 23);
        add(btnNuevoProducto);

        // Acciones de los botones
        btnGuardarProducto.addActionListener(e -> guardarProducto());
        btnActualizarProducto.addActionListener(e -> actualizarProducto());
        btnEliminarProducto.addActionListener(e -> eliminarProducto());
        btnNuevoProducto.addActionListener(e -> limpiarCampos());
    }

    private void guardarProducto() {
        if (validarCampos()) {
            modeloTablaProductos.addRow(new Object[]{
                txtCodigoProducto.getText(),
                txtNombreProducto.getText(),
                txtCategoriaProducto.getText(),
                Double.parseDouble(txtPrecioProducto.getText()),
                Integer.parseInt(txtStockProducto.getText())
            });
            limpiarCampos();
            System.out.println("Producto guardado.");
        }
    }

    private void actualizarProducto() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow >= 0 && validarCampos()) {
            modeloTablaProductos.setValueAt(txtCodigoProducto.getText(), selectedRow, 0);
            modeloTablaProductos.setValueAt(txtNombreProducto.getText(), selectedRow, 1);
            modeloTablaProductos.setValueAt(txtCategoriaProducto.getText(), selectedRow, 2);
            modeloTablaProductos.setValueAt(Double.parseDouble(txtPrecioProducto.getText()), selectedRow, 3);
            modeloTablaProductos.setValueAt(Integer.parseInt(txtStockProducto.getText()), selectedRow, 4);
            limpiarCampos();
            System.out.println("Producto actualizado.");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar.");
        }
    }

    private void eliminarProducto() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                modeloTablaProductos.removeRow(selectedRow);
                limpiarCampos();
                System.out.println("Producto eliminado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCategoriaProducto.setText("");
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
    }

    private boolean validarCampos() {
        if (txtCodigoProducto.getText().isEmpty() || txtNombreProducto.getText().isEmpty() ||
            txtCategoriaProducto.getText().isEmpty() || txtPrecioProducto.getText().isEmpty() ||
            txtStockProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtPrecioProducto.getText());
            Integer.parseInt(txtStockProducto.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y Stock deben ser numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void llenarCamposDesdeTabla() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow >= 0) {
            txtCodigoProducto.setText(modeloTablaProductos.getValueAt(selectedRow, 0).toString());
            txtNombreProducto.setText(modeloTablaProductos.getValueAt(selectedRow, 1).toString());
            txtCategoriaProducto.setText(modeloTablaProductos.getValueAt(selectedRow, 2).toString());
            txtPrecioProducto.setText(modeloTablaProductos.getValueAt(selectedRow, 3).toString());
            txtStockProducto.setText(modeloTablaProductos.getValueAt(selectedRow, 4).toString());
        }
    }
}
