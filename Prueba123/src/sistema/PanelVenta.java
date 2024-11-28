package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;

public class PanelVenta extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField textFieldIdProducto, textFieldProducto, textFieldCantidad, textFieldPrecio, textFieldTotal, textFieldStock, textFieldClienteId;
    private DefaultTableModel modeloTablaVenta;
    private JTable tableVenta;
    private Connection conn;

    public PanelVenta() {
        conn = Conexion_bdd.getConnection(); // Conexión a la base de datos
        setLayout(null);
        initializeComponents();

        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeComponents() {
        // Modelo de la tabla para Venta
        modeloTablaVenta = new DefaultTableModel(new String[]{"ID Producto", "Producto", "Cantidad", "Precio", "Total"}, 0);
        tableVenta = new JTable(modeloTablaVenta);
        JScrollPane scrollPaneVenta = new JScrollPane(tableVenta);
        scrollPaneVenta.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneVenta.setBounds(10, 85, 712, 260);
        add(scrollPaneVenta);

        // Etiquetas y campos de texto
        JLabel lblIdProducto = new JLabel("ID Producto:");
        lblIdProducto.setBounds(30, 11, 80, 14);
        add(lblIdProducto);

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

        JLabel lblClienteId = new JLabel("ID Cliente:");
        lblClienteId.setBounds(79, 361, 67, 14);
        add(lblClienteId);

        textFieldIdProducto = new JTextField();
        textFieldIdProducto.setBounds(43, 37, 147, 20);
        textFieldIdProducto.addActionListener(e -> buscarProducto());
        add(textFieldIdProducto);

        textFieldProducto = new JTextField();
        textFieldProducto.setBounds(210, 37, 192, 20);
        textFieldProducto.setEditable(false);
        add(textFieldProducto);

        textFieldCantidad = new JTextField();
        textFieldCantidad.setBounds(415, 36, 67, 20);
        textFieldCantidad.addActionListener(e -> calcularTotal());
        add(textFieldCantidad);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(504, 37, 86, 20);
        textFieldPrecio.setEditable(false);
        add(textFieldPrecio);

        textFieldTotal = new JTextField();
        textFieldTotal.setBounds(195, 387, 120, 20);
        textFieldTotal.setEditable(false);
        add(textFieldTotal);

        textFieldClienteId = new JTextField();
        textFieldClienteId.setBounds(195, 361, 120, 20);
        textFieldClienteId.addActionListener(e -> cargarDatosCliente()); // Llamar al método cuando se ingrese un ID de cliente
        add(textFieldClienteId);

        textFieldStock = new JTextField();
        textFieldStock.setBounds(613, 37, 86, 20);
        textFieldStock.setEditable(false);
        add(textFieldStock);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(629, 11, 46, 14);
        add(lblStock);

        // Botón para generar la venta
        JButton btnGenerarVenta = new JButton("Generar Venta");
        btnGenerarVenta.setBounds(530, 356, 181, 23);
        btnGenerarVenta.addActionListener(e -> registrarVenta()); // Acción que se llama cuando se presiona el botón
        add(btnGenerarVenta);
    }

    private void buscarProducto() {
        String idProducto = textFieldIdProducto.getText();
        String query = "SELECT nombre, precio, stock FROM productos WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                textFieldProducto.setText(rs.getString("nombre"));
                textFieldPrecio.setText(String.valueOf(rs.getDouble("precio")));
                textFieldStock.setText(String.valueOf(rs.getInt("stock")));
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                limpiarCampos();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTotal() {
        try {
            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            double precio = Double.parseDouble(textFieldPrecio.getText());
            double total = cantidad * precio;
            textFieldTotal.setText(String.format("%.2f", total));
        } catch (NumberFormatException e) {
            textFieldTotal.setText("");
        }
    }

    private void registrarVenta() {
        try {
            // Validar campos
            if (textFieldProducto.getText().isEmpty() || textFieldCantidad.getText().isEmpty() ||
                textFieldPrecio.getText().isEmpty() || textFieldStock.getText().isEmpty() ||
                textFieldClienteId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar ID Cliente
            int idCliente;
            try {
                idCliente = Integer.parseInt(textFieldClienteId.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID del cliente debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el cliente existe
            if (!clienteExiste(idCliente)) {
                JOptionPane.showMessageDialog(this, "El cliente no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar cantidad
            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            int stock = Integer.parseInt(textFieldStock.getText());

            if (cantidad <= 0 || cantidad > stock) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0 y no superar el stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insertar la venta
            String queryVenta = "INSERT INTO Ventas (id, fecha, total) VALUES (?, ?, ?)"; // Usamos cliente_id
            try (PreparedStatement stmtVenta = conn.prepareStatement(queryVenta, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenta.setInt(1, idCliente); // Usamos el ID Cliente validado
                stmtVenta.setDate(2, Date.valueOf(LocalDate.now()));
                stmtVenta.setDouble(3, Double.parseDouble(textFieldTotal.getText()));
                int filasAfectadas = stmtVenta.executeUpdate();

                if (filasAfectadas == 0) {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtener ID de la venta generada
                ResultSet rs = stmtVenta.getGeneratedKeys();
                if (rs.next()) {
                    int ventaId = rs.getInt(1);
                    JOptionPane.showMessageDialog(this, "Venta registrada con ID: " + ventaId, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }

                // Actualizar stock
                actualizarStock(Integer.parseInt(textFieldIdProducto.getText()), stock - cantidad);

                // Agregar venta al modelo de la tabla
                modeloTablaVenta.addRow(new Object[] {
                    textFieldIdProducto.getText(),
                    textFieldProducto.getText(),
                    cantidad,
                    textFieldPrecio.getText(),
                    textFieldTotal.getText()
                });

                // Mostrar mensaje de "Venta realizada"
                JOptionPane.showMessageDialog(this, "Venta realizada con éxito", "Venta Realizada", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar campos
                limpiarCampos();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean clienteExiste(int idCliente) {
        String query = "SELECT COUNT(*) FROM Clientes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al verificar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private void actualizarStock(int idProducto, int nuevoStock) throws SQLException {
        String queryStock = "UPDATE productos SET stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryStock)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        }
    }

    private void limpiarCampos() {
        textFieldIdProducto.setText("");
        textFieldProducto.setText("");
        textFieldCantidad.setText("");
        textFieldPrecio.setText("");
        textFieldTotal.setText("");
        textFieldStock.setText("");
        textFieldClienteId.setText("");
    }

    private void cargarDatosCliente() {
        try {
            int idCliente = Integer.parseInt(textFieldClienteId.getText());
            String query = "SELECT nombre FROM clientes WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idCliente);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String nombreCliente = rs.getString("nombre");
                    JOptionPane.showMessageDialog(this, "Cliente encontrado: " + nombreCliente, "Cliente", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
