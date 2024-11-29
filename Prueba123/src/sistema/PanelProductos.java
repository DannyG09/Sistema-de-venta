package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PanelProductos extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtCodigoProducto, txtNombreProducto, txtCategoriaProducto, txtPrecioProducto, txtStockProducto;
    private JTable tableProductos;
    private DefaultTableModel modeloTablaProductos;
    private JButton btnGuardarProducto, btnEliminarProducto;
    private Connection conn;

    public PanelProductos() {
        conn = Conexion_bdd.getConnection(); // Conexión a la base de datos
        setLayout(null);
        initializeComponents();
        cargarProductos(); // Cargar datos iniciales en la tabla
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
        txtCodigoProducto.setBounds(120, 57, 120, 30);
        txtCodigoProducto.setEditable(false); // Código generado automáticamente
        add(txtCodigoProducto);

        txtNombreProducto = new JTextField();
        txtNombreProducto.setBounds(120, 97, 120, 30);
        add(txtNombreProducto);

        txtCategoriaProducto = new JTextField();
        txtCategoriaProducto.setBounds(120, 137, 120, 30);
        add(txtCategoriaProducto);

        txtPrecioProducto = new JTextField();
        txtPrecioProducto.setBounds(120, 177, 120, 30);
        add(txtPrecioProducto);

        txtStockProducto = new JTextField();
        txtStockProducto.setBounds(120, 217, 120, 30);
        add(txtStockProducto);

        // Tabla
        modeloTablaProductos = new DefaultTableModel(new String[]{"Código", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tableProductos = new JTable(modeloTablaProductos);
        JScrollPane scrollPaneProductos = new JScrollPane(tableProductos);
        scrollPaneProductos.setBounds(270, 60, 400, 270);
        add(scrollPaneProductos);

        // Botón de guardar
        btnGuardarProducto = new JButton("Guardar");
        btnGuardarProducto.setBounds(30, 270, 90, 23);
        add(btnGuardarProducto);

        // Botón de eliminar
        btnEliminarProducto = new JButton("Eliminar");
        btnEliminarProducto.setBounds(130, 270, 90, 23);
        add(btnEliminarProducto);

        // Acción del botón de guardar
        btnGuardarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        // Acción del botón de eliminar
        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    private void guardarProducto() {
        if (validarCampos()) {
            String nombre = txtNombreProducto.getText();
            String categoria = txtCategoriaProducto.getText();
            double precio = Double.parseDouble(txtPrecioProducto.getText());
            int stock = Integer.parseInt(txtStockProducto.getText());

            String query = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombre);
                stmt.setString(2, categoria);
                stmt.setDouble(3, precio);
                stmt.setInt(4, stock);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int codigo = rs.getInt(1);
                        modeloTablaProductos.addRow(new Object[]{codigo, nombre, categoria, precio, stock});
                        JOptionPane.showMessageDialog(this, "Producto guardado correctamente con código: " + codigo);
                        limpiarCampos();
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarCampos() {
        if (txtNombreProducto.getText().isEmpty() || txtCategoriaProducto.getText().isEmpty() ||
            txtPrecioProducto.getText().isEmpty() || txtStockProducto.getText().isEmpty()) {
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

    private void limpiarCampos() {
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCategoriaProducto.setText("");
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
    }

    private void cargarProductos() {
        String query = "SELECT * FROM productos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                modeloTablaProductos.addRow(new Object[]{
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow != -1) {
            // Obtener el código del producto seleccionado
            int codigoProducto = (int) modeloTablaProductos.getValueAt(selectedRow, 0);

            // Confirmación antes de eliminar
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Eliminar producto de la base de datos
                String query = "DELETE FROM productos WHERE codigo = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, codigoProducto);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Eliminar fila de la tabla
                        modeloTablaProductos.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
