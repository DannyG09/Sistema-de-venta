package sistema;

import Funcionalidad.Producto; // Importar la clase Producto
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PanelVenta extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField textFieldCodigo, textFieldProducto, textFieldCantidad, textFieldPrecio, textFieldTotal, textFieldStock, textFieldCliente;
    private DefaultTableModel modeloTablaVenta;
    private JTable tableVenta;
    private List<Producto> listaProductos;

    public PanelVenta() {
        setLayout(null);
        initializeComponents();
        cargarProductos(); // Cargar datos de ejemplo
    }

    private void initializeComponents() {
        // Modelo de la tabla para Venta
        modeloTablaVenta = new DefaultTableModel(new String[]{"ID", "Producto", "Cantidad", "Precio", "Total"}, 0);
        tableVenta = new JTable(modeloTablaVenta);
        JScrollPane scrollPaneVenta = new JScrollPane(tableVenta);
        scrollPaneVenta.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneVenta.setBounds(10, 85, 712, 260);
        add(scrollPaneVenta);

        // Etiquetas y campos de texto
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 11, 80, 14);
        add(lblCodigo);

        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(275, 11, 67, 14);
        add(lblProducto);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(415, 11, 67, 14);
        add(lblCantidad);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(530, 11, 67, 14);
        add(lblPrecio);

        JLabel lblTotal = new JLabel("Total a pagar:");
        lblTotal.setBounds(530, 389, 89, 14);
        add(lblTotal);

        textFieldCodigo = new JTextField();
        textFieldCodigo.setBounds(43, 37, 147, 20);
        textFieldCodigo.addActionListener(e -> buscarProducto());
        add(textFieldCodigo);

        textFieldProducto = new JTextField();
        textFieldProducto.setBounds(210, 37, 192, 20);
        textFieldProducto.setEditable(false);
        add(textFieldProducto);

        textFieldCantidad = new JTextField();
        textFieldCantidad.setBounds(415, 36, 67, 20);
        textFieldCantidad.addActionListener(e -> calcularTotal()); // Vincular al evento del campo
        add(textFieldCantidad);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(504, 37, 86, 20);
        textFieldPrecio.setEditable(false);
        add(textFieldPrecio);

        textFieldTotal = new JTextField();
        textFieldTotal.setBounds(195, 387, 120, 20);
        textFieldTotal.setEditable(false);
        add(textFieldTotal);

        // Botón para agregar la venta
        JButton btnAgregarVenta = new JButton("Generar Venta");
        btnAgregarVenta.setBounds(530, 356, 181, 23);
        btnAgregarVenta.addActionListener(e -> agregarVenta());
        add(btnAgregarVenta);

        textFieldStock = new JTextField();
        textFieldStock.setBounds(613, 37, 86, 20);
        textFieldStock.setEditable(false);
        add(textFieldStock);

        textFieldCliente = new JTextField();
        textFieldCliente.setBounds(195, 361, 120, 20);
        add(textFieldCliente);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(79, 361, 46, 14);
        add(lblCliente);

        JLabel lblNombreCliente = new JLabel("Nombre del cliente");
        lblNombreCliente.setBounds(43, 389, 103, 14);
        add(lblNombreCliente);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(629, 11, 46, 14);
        add(lblStock);
    }

    private void cargarProductos() {
        listaProductos = new ArrayList<>();
        // Agregar productos de ejemplo
    }

    private void buscarProducto() {
        String codigo = textFieldCodigo.getText();
        for (Producto producto : listaProductos) {
            if (producto.getId().equalsIgnoreCase(codigo)) {
                textFieldProducto.setText(producto.getNombre());
                textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
                textFieldStock.setText(String.valueOf(producto.getStock()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        limpiarCampos();
    }

    private void calcularTotal() {
        try {
            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            double precio = Double.parseDouble(textFieldPrecio.getText());
            double total = cantidad * precio;
            textFieldTotal.setText(String.format("%.2f", total)); // Mostrar con 2 decimales
        } catch (NumberFormatException e) {
            textFieldTotal.setText("");
        }
    }

    private void agregarVenta() {
        try {
            if (textFieldProducto.getText().isEmpty() || textFieldCantidad.getText().isEmpty() ||
                textFieldPrecio.getText().isEmpty() || textFieldStock.getText().isEmpty() ||
                textFieldCliente.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            int stock = Integer.parseInt(textFieldStock.getText());

            if (cantidad > stock) {
                JOptionPane.showMessageDialog(this, "La cantidad supera el stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String producto = textFieldProducto.getText();
            double precio = Double.parseDouble(textFieldPrecio.getText());
            double total = cantidad * precio;

            modeloTablaVenta.addRow(new Object[]{
                modeloTablaVenta.getRowCount() + 1,
                producto,
                cantidad,
                precio,
                total
            });

            JOptionPane.showMessageDialog(this, "Venta generada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        textFieldCodigo.setText("");
        textFieldProducto.setText("");
        textFieldCantidad.setText("");
        textFieldPrecio.setText("");
        textFieldTotal.setText("");
        textFieldStock.setText("");
    }
}