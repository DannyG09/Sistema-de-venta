package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PanelProductos extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtCodigoProducto, txtNombreProducto, txtCategoriaProducto, txtPrecioProducto, txtStockProducto;
    private JTable tableProductos;
    private DefaultTableModel modeloTablaProductos;
    private JButton btnGuardarProducto;
    private Connection conn;

    public PanelProductos() {
        conn = Conexion_bdd.getConnection(); // Conexión a la base de datos
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
        JScrollPane scrollPaneProductos = new JScrollPane(tableProductos);
        scrollPaneProductos.setBounds(270, 60, 400, 270);
        add(scrollPaneProductos);

        // Botón de guardar
        btnGuardarProducto = new JButton("Guardar");
        btnGuardarProducto.setBounds(30, 270, 90, 23);
        add(btnGuardarProducto);

        // Acción del botón de guardar
        btnGuardarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }

    private void guardarProducto() {
        if (validarCampos()) {
            String nombre = txtNombreProducto.getText();
            String categoria = txtCategoriaProducto.getText();
            double precio = Double.parseDouble(txtPrecioProducto.getText());
            int stock = Integer.parseInt(txtStockProducto.getText());

            // Query de inserción sin el campo "codigo" (id será AUTO_INCREMENT)
            String query = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES ('" +
                    nombre + "', '" + categoria + "', " + precio + ", " + stock + ")";
            
            try (Statement stmt = conn.createStatement()) {
                // Ejecutar la consulta de inserción
                int rowsAffected = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

                // Obtener el código generado automáticamente
                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int codigo = rs.getInt(1);  // El primer campo es el código generado (id)
                        
                        // Mostrar el código generado junto con los demás datos en la tabla
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

    private void limpiarCampos() {
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCategoriaProducto.setText("");
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
    }
}