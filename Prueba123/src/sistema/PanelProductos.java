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
import java.awt.Color;
import java.awt.Font;

public class PanelProductos extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtNombreProducto, txtPrecioProducto, txtStockProducto;
    private JTable tableProductos;
    private DefaultTableModel modeloTablaProductos;
    private JButton btnGuardarProducto, btnEliminarProducto;
    private Connection conn;
    private JComboBox<String> comboCategoriaProducto; // Cambiar JTextField a JComboBox

    public PanelProductos() {
        conn = Conexion_bdd.getConnection(); // Conexión a la base de datos
        setLayout(null);
        initializeComponents();
        cargarProductos(); // Cargar datos iniciales en la tabla
    }

    private void initializeComponents() {

        JLabel lblNombreProducto = new JLabel("Nombre:");
        lblNombreProducto.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        lblNombreProducto.setBounds(40, 123, 80, 14);
        add(lblNombreProducto);

        JLabel lblCategoriaProducto = new JLabel("Categoría:");
        lblCategoriaProducto.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        lblCategoriaProducto.setBounds(40, 162, 80, 14);
        add(lblCategoriaProducto);

        JLabel lblPrecioProducto = new JLabel("Precio:");
        lblPrecioProducto.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        lblPrecioProducto.setBounds(40, 202, 80, 14);
        add(lblPrecioProducto);

        JLabel lblStockProducto = new JLabel("Stock:");
        lblStockProducto.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        lblStockProducto.setBounds(40, 242, 80, 14);
        add(lblStockProducto);

        txtNombreProducto = new JTextField();
        txtNombreProducto.setBounds(130, 120, 120, 30);
        add(txtNombreProducto);

        // JComboBox para Categoría
        comboCategoriaProducto = new JComboBox<>(new String[]{"Celulares", "Apple Watch", "Tablets", "Laptops"});
        comboCategoriaProducto.setBounds(130, 159, 120, 30);
        add(comboCategoriaProducto);

        txtPrecioProducto = new JTextField();
        txtPrecioProducto.setBounds(130, 199, 120, 30);
        add(txtPrecioProducto);

        txtStockProducto = new JTextField();
        txtStockProducto.setBounds(130, 239, 120, 30);
        add(txtStockProducto);

        // Tabla
        modeloTablaProductos = new DefaultTableModel(new String[]{"Código", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tableProductos = new JTable(modeloTablaProductos);
        JScrollPane scrollPaneProductos = new JScrollPane(tableProductos);
        scrollPaneProductos.setBounds(280, 83, 464, 270);
        add(scrollPaneProductos);

     // Botón de guardar
        btnGuardarProducto = new JButton("Guardar");
        btnGuardarProducto.setBounds(38, 293, 90, 23);
        btnGuardarProducto.setBackground(new Color(34, 139, 34)); // Fondo verde
        btnGuardarProducto.setForeground(Color.WHITE); // Texto blanco
        add(btnGuardarProducto);

        // Botón de eliminar
        btnEliminarProducto = new JButton("Eliminar");
        btnEliminarProducto.setBounds(138, 293, 90, 23);
        btnEliminarProducto.setBackground(new Color(255, 99, 71)); // Fondo rojo
        btnEliminarProducto.setForeground(Color.WHITE); // Texto blanco
        add(btnEliminarProducto);

        
        JLabel lblNewLabel = new JLabel("Agregar Nuevos Productos");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        lblNewLabel.setBounds(233, 33, 269, 23);
        add(lblNewLabel);

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
            String categoria = (String) comboCategoriaProducto.getSelectedItem(); // Obtener selección del ComboBox
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
                        int id = rs.getInt(1);  // Obtener el id generado
                        modeloTablaProductos.addRow(new Object[]{id, nombre, categoria, precio, stock});  // Usar 'id' en lugar de 'codigo'
                        JOptionPane.showMessageDialog(this, "Producto guardado correctamente con ID: " + id);
                        limpiarCampos();
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarCampos() {
        if (txtNombreProducto.getText().isEmpty() || txtPrecioProducto.getText().isEmpty() || txtStockProducto.getText().isEmpty()) {
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
        txtNombreProducto.setText("");
        comboCategoriaProducto.setSelectedIndex(0); // Reiniciar al primer elemento
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
    }

    private void cargarProductos() {
        String query = "SELECT * FROM productos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                modeloTablaProductos.addRow(new Object[]{
                        rs.getInt("id"),            // Cambiar 'codigo' por 'id'
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
            int idProducto = (int) modeloTablaProductos.getValueAt(selectedRow, 0);

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM productos WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, idProducto);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
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
