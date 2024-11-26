package sistema;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class PanelVenta extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private DefaultTableModel modeloTablaVenta;

    public PanelVenta() {
        setLayout(null);

        // Modelo de la tabla para Venta
        modeloTablaVenta = new DefaultTableModel(new String[]{"ID", "Producto", "Cantidad", "Precio", "Total"}, 0);
        JTable table_Venta = new JTable(modeloTablaVenta);
        JScrollPane scrollPaneVenta = new JScrollPane(table_Venta);
        scrollPaneVenta.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneVenta.setBounds(257, 49, 394, 283);
        add(scrollPaneVenta);

        // Etiquetas para los campos de venta
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblProducto.setBounds(42, 52, 67, 14);
        add(lblProducto);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblCantidad.setBounds(42, 97, 67, 14);
        add(lblCantidad);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblPrecio.setBounds(42, 144, 46, 14);
        add(lblPrecio);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblTotal.setBounds(42, 193, 46, 14);
        add(lblTotal);

        // Campos de texto para los datos de la venta
        textField_1 = new JTextField();
        textField_1.setBounds(117, 49, 120, 20);
        add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(117, 94, 120, 20);
        add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(117, 141, 120, 20);
        add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(117, 190, 120, 20);
        add(textField_4);
        textField_4.setColumns(10);

        // Botón para agregar la venta
        JButton btnAgregarVenta = new JButton("Agregar");
        btnAgregarVenta.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnAgregarVenta.setBounds(137, 276, 89, 23);
        btnAgregarVenta.addActionListener(e -> agregarVenta());
        add(btnAgregarVenta);
    }

    // Método para agregar la venta a la tabla
    private void agregarVenta() {
        String producto = textField_1.getText();
        String cantidad = textField_2.getText();
        String precio = textField_3.getText();
        String total = textField_4.getText();

        // Lógica para agregar una nueva fila en la tabla con los datos de la venta
        modeloTablaVenta.addRow(new Object[]{modeloTablaVenta.getRowCount() + 1, producto, cantidad, precio, total});

        // Limpiar los campos de texto después de agregar la venta
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
        textField_4.setText("");
    }
}
