package sistema;

import Funcionalidad.Producto;
import Funcionalidad.ProductoDAO;
import Funcionalidad.ProductoDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelProductos extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtNombreProducto, txtPrecioProducto, txtStockProducto;
    private JTable tableProductos;
    private DefaultTableModel modeloTablaProductos;
    private JButton btnGuardarProducto, btnEliminarProducto, btnActualizarStock, btnActualizarProducto;
    private JComboBox<String> comboCategoriaProducto;

    private ProductoDAO productoDAO;

    public PanelProductos() {
        productoDAO = new ProductoDAOImpl(); // Instancia de la implementación de ProductoDAO
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

        comboCategoriaProducto = new JComboBox<>(new String[]{"Celulares", "Apple Watch", "Tablets", "Laptops"});
        comboCategoriaProducto.setBounds(130, 159, 120, 30);
        add(comboCategoriaProducto);

        txtPrecioProducto = new JTextField();
        txtPrecioProducto.setBounds(130, 199, 120, 30);
        add(txtPrecioProducto);

        txtStockProducto = new JTextField();
        txtStockProducto.setBounds(130, 239, 120, 30);
        add(txtStockProducto);

        modeloTablaProductos = new DefaultTableModel(new String[]{"Código", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tableProductos = new JTable(modeloTablaProductos);
        JScrollPane scrollPaneProductos = new JScrollPane(tableProductos);
        scrollPaneProductos.setBounds(280, 83, 464, 270);
        add(scrollPaneProductos);

        btnGuardarProducto = new JButton("Guardar");
        btnGuardarProducto.setBounds(38, 293, 90, 23);
        btnGuardarProducto.setBackground(new Color(34, 139, 34));
        btnGuardarProducto.setForeground(Color.WHITE);
        add(btnGuardarProducto);

        btnEliminarProducto = new JButton("Eliminar");
        btnEliminarProducto.setBounds(138, 293, 90, 23);
        btnEliminarProducto.setBackground(new Color(255, 99, 71));
        btnEliminarProducto.setForeground(Color.WHITE);
        add(btnEliminarProducto);

        btnActualizarStock = new JButton("Actualizar Stock");
        btnActualizarStock.setBounds(38, 330, 140, 23);
        btnActualizarStock.setBackground(new Color(70, 130, 180));
        btnActualizarStock.setForeground(Color.WHITE);
        add(btnActualizarStock);

        btnActualizarProducto = new JButton("Actualizar Producto");
        btnActualizarProducto.setBounds(190, 330, 140, 23);
        btnActualizarProducto.setBackground(new Color(70, 130, 180));
        btnActualizarProducto.setForeground(Color.WHITE);
        add(btnActualizarProducto);

        JLabel lblNewLabel = new JLabel("Administrar Productos");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        lblNewLabel.setBounds(233, 33, 269, 23);
        add(lblNewLabel);

        btnGuardarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnActualizarStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarStock();
            }
        });

        btnActualizarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
    }

    private void guardarProducto() {
        if (validarCampos()) {
            Producto producto = new Producto(
                    txtNombreProducto.getText(),
                    (String) comboCategoriaProducto.getSelectedItem(),
                    Double.parseDouble(txtPrecioProducto.getText()),
                    Integer.parseInt(txtStockProducto.getText())
            );

            if (productoDAO.guardarProducto(producto)) {
                modeloTablaProductos.addRow(new Object[]{
                        producto.getId(),
                        producto.getNombre(),
                        producto.getCategoria(),
                        producto.getPrecio(),
                        producto.getStock()
                });
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarProducto() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow != -1) {
            int idProducto = (int) modeloTablaProductos.getValueAt(selectedRow, 0);

            if (productoDAO.eliminarProducto(idProducto)) {
                modeloTablaProductos.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarProducto() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow != -1) {
            Producto producto = new Producto(
                    (int) modeloTablaProductos.getValueAt(selectedRow, 0),
                    txtNombreProducto.getText(),
                    (String) comboCategoriaProducto.getSelectedItem(),
                    Double.parseDouble(txtPrecioProducto.getText()),
                    Integer.parseInt(txtStockProducto.getText())
            );

            if (productoDAO.actualizarProducto(producto)) {
                cargarProductos();
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarStock() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow != -1) {
            int idProducto = (int) modeloTablaProductos.getValueAt(selectedRow, 0);
            String nuevoStockStr = JOptionPane.showInputDialog(this, "Introduce el nuevo stock:", "Actualizar Stock", JOptionPane.PLAIN_MESSAGE);

            if (nuevoStockStr != null && !nuevoStockStr.isEmpty()) {
                try {
                    int nuevoStock = Integer.parseInt(nuevoStockStr);

                    Producto producto = new Producto(idProducto, "", "", 0, nuevoStock);
                    if (productoDAO.actualizarProducto(producto)) {
                        cargarProductos();
                        JOptionPane.showMessageDialog(this, "Stock actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al actualizar el stock.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El stock debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizar el stock.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarProductos() {
        modeloTablaProductos.setRowCount(0);
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        for (Producto producto : productos) {
            modeloTablaProductos.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getStock()
            });
        }
    }

    private void limpiarCampos() {
        txtNombreProducto.setText("");
        comboCategoriaProducto.setSelectedIndex(0);
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
    }

    private boolean validarCampos() {
        if (txtNombreProducto.getText().isEmpty() ||
                txtPrecioProducto.getText().isEmpty() ||
                txtStockProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtPrecioProducto.getText());
            Integer.parseInt(txtStockProducto.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y Stock deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
