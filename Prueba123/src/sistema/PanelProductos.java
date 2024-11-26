package sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelProductos extends JPanel {
    /**
	 * 
	 */
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
        modeloTablaProductos = new DefaultTableModel(new String[]{"ID", "Código", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tableProductos = new JTable(modeloTablaProductos);
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
        btnGuardarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        btnActualizarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        btnEliminarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnNuevoProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    // Métodos para las acciones
    private void guardarProducto() {
        // Guardar producto en la tabla
        modeloTablaProductos.addRow(new Object[]{
            modeloTablaProductos.getRowCount() + 1,
            txtCodigoProducto.getText(),
            txtNombreProducto.getText(),
            txtCategoriaProducto.getText(),
            txtPrecioProducto.getText(),
            txtStockProducto.getText()
        });
        limpiarCampos();
        System.out.println("Producto guardado.");
    }

    private void actualizarProducto() {
        // Actualizar datos de un producto (implementación adicional requerida)
        System.out.println("Actualizar Producto.");
    }

    private void eliminarProducto() {
        // Eliminar el producto seleccionado
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow >= 0) {
            modeloTablaProductos.removeRow(selectedRow);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Seleccione un producto para eliminar.");
        }
    }

    private void limpiarCampos() {
        // Limpiar los campos de texto
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCategoriaProducto.setText("");
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
        System.out.println("Campos limpiados.");
    }
}
