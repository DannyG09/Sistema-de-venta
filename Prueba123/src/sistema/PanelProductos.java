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
        scrollPaneProductos.setBounds(292, 83, 452, 270);
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
        btnActualizarStock.setBounds(10, 330, 120, 23);
        btnActualizarStock.setBackground(new Color(70, 130, 180));
        btnActualizarStock.setForeground(Color.WHITE);
        add(btnActualizarStock);

        btnActualizarProducto = new JButton("Actualizar Producto");
        btnActualizarProducto.setBounds(148, 330, 132, 23);
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
            int idProducto = (int) modeloTablaProductos.getValueAt(selectedRow, 0);
            String nombre = (String) modeloTablaProductos.getValueAt(selectedRow, 1);
            String categoria = (String) modeloTablaProductos.getValueAt(selectedRow, 2);
            double precio = (double) modeloTablaProductos.getValueAt(selectedRow, 3);
            int stock = (int) modeloTablaProductos.getValueAt(selectedRow, 4);

            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Editar Producto", true);
            dialog.getContentPane().setLayout(null);
            dialog.setSize(350, 300);
            dialog.setLocationRelativeTo(this);

            JLabel lblNombre = new JLabel("Nombre:");
            lblNombre.setBounds(20, 20, 100, 25);
            dialog.getContentPane().add(lblNombre);

            JTextField txtNombre = new JTextField(nombre);
            txtNombre.setBounds(130, 20, 180, 25);
            dialog.getContentPane().add(txtNombre);

            JLabel lblCategoria = new JLabel("Categoría:");
            lblCategoria.setBounds(20, 60, 100, 25);
            dialog.getContentPane().add(lblCategoria);

            JComboBox<String> comboCategoria = new JComboBox<>(new String[]{"Celulares", "Apple Watch", "Tablets", "Laptops"});
            comboCategoria.setSelectedItem(categoria);
            comboCategoria.setBounds(130, 60, 180, 25);
            dialog.getContentPane().add(comboCategoria);

            JLabel lblPrecio = new JLabel("Precio:");
            lblPrecio.setBounds(20, 100, 100, 25);
            dialog.getContentPane().add(lblPrecio);

            JTextField txtPrecio = new JTextField(String.valueOf(precio));
            txtPrecio.setBounds(130, 100, 180, 25);
            dialog.getContentPane().add(txtPrecio);

            JLabel lblStock = new JLabel("Stock:");
            lblStock.setBounds(20, 140, 100, 25);
            dialog.getContentPane().add(lblStock);

            JTextField txtStock = new JTextField(String.valueOf(stock));
            txtStock.setBounds(130, 140, 180, 25);
            dialog.getContentPane().add(txtStock);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBounds(120, 200, 100, 30);
            dialog.getContentPane().add(btnGuardar);

            btnGuardar.addActionListener(e -> {
                try {
                    String nuevoNombre = txtNombre.getText();
                    String nuevaCategoria = (String) comboCategoria.getSelectedItem();
                    double nuevoPrecio = Double.parseDouble(txtPrecio.getText());
                    int nuevoStock = Integer.parseInt(txtStock.getText());

                    Producto producto = new Producto(idProducto, nuevoNombre, nuevaCategoria, nuevoPrecio, nuevoStock);

                    if (productoDAO.actualizarProducto(producto)) {
                        JOptionPane.showMessageDialog(dialog, "Producto actualizado correctamente.");
                        cargarProductos();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Precio y Stock deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarStock() {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int idProducto = (int) modeloTablaProductos.getValueAt(selectedRow, 0);
                String input = JOptionPane.showInputDialog(this, "Nuevo Stock:");
                if (input != null && !input.isEmpty()) {
                    int nuevoStock = Integer.parseInt(input);

                    Producto producto = productoDAO.obtenerProductoPorId(idProducto);
                    if (producto != null) {
                        producto.setStock(nuevoStock);
                        if (productoDAO.actualizarProducto(producto)) {
                            JOptionPane.showMessageDialog(this, "Stock actualizado correctamente.");
                            cargarProductos();
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al actualizar el stock.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El stock debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "El precio y el stock deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNombreProducto.setText("");
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
        comboCategoriaProducto.setSelectedIndex(0);
    }
}
