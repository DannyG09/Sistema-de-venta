package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

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
        } else {
            cargarVentas(); // Cargar las ventas al iniciar el panel
        }
    }

    private void initializeComponents() {
        // Modelo de la tabla para Venta
        modeloTablaVenta = new DefaultTableModel(new String[]{"ID Producto", "Cliente", "Cantidad", "Precio", "Total"}, 0);
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
        lblTotal.setBounds(453, 389, 89, 14);
        add(lblTotal);

        JLabel lblClienteId = new JLabel("ID Cliente:");
        lblClienteId.setBounds(79, 361, 67, 14);
        add(lblClienteId);

        textFieldIdProducto = new JTextField();
        textFieldIdProducto.setBounds(43, 37, 147, 30);
        textFieldIdProducto.addActionListener(e -> buscarProducto());
        add(textFieldIdProducto);

        textFieldProducto = new JTextField();
        textFieldProducto.setBounds(210, 37, 192, 30);
        textFieldProducto.setEditable(false);
        add(textFieldProducto);

        textFieldCantidad = new JTextField();
        textFieldCantidad.setBounds(415, 36, 67, 30);
        textFieldCantidad.addActionListener(e -> calcularTotal());
        add(textFieldCantidad);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(504, 37, 86, 30);
        textFieldPrecio.setEditable(false);
        add(textFieldPrecio);

        textFieldTotal = new JTextField();
        textFieldTotal.setBounds(530, 386, 181, 30);
        textFieldTotal.setEditable(false);
        add(textFieldTotal);

        textFieldClienteId = new JTextField();
        textFieldClienteId.setBounds(195, 361, 120, 30);
        textFieldClienteId.addActionListener(e -> cargarDatosCliente()); // Llamar al método cuando se ingrese un ID de cliente
        add(textFieldClienteId);

        textFieldStock = new JTextField();
        textFieldStock.setBounds(613, 37, 86, 30);
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

    private void cargarDatosCliente() {
        String clienteId = textFieldClienteId.getText(); // Obtener el ID del cliente ingresado
        if (clienteId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT nombre, email, telefono, direccion FROM Clientes WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(clienteId)); // Usamos el ID proporcionado para consultar el cliente
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si se encuentra el cliente, llenar los campos con los datos obtenidos
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");

                // Mostrar los datos del cliente
                JOptionPane.showMessageDialog(this, "Cliente encontrado:\nNombre: " + nombre + "\nEmail: " + email +
                        "\nTeléfono: " + telefono + "\nDirección: " + direccion, "Datos Cliente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Si no se encuentra el cliente, mostrar un mensaje
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarVenta() {
        try {
            // Validar campos básicos
            if (textFieldProducto.getText().isEmpty() || textFieldCantidad.getText().isEmpty() ||
                textFieldPrecio.getText().isEmpty() || textFieldTotal.getText().isEmpty() || textFieldClienteId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la cantidad sea mayor a 0
            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el ID del cliente y convertirlo a entero
            int idCliente = Integer.parseInt(textFieldClienteId.getText());

            // Obtener el precio y el total
            double precio = Double.parseDouble(textFieldPrecio.getText());
            double total = Double.parseDouble(textFieldTotal.getText());

            // Insertar la venta principal con todos los valores
            String queryVenta = "INSERT INTO Ventas (producto, cantidad, precio, total, cliente_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmtVenta = conn.prepareStatement(queryVenta, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenta.setString(1, textFieldProducto.getText()); // Producto
                stmtVenta.setInt(2, cantidad); // Cantidad
                stmtVenta.setDouble(3, precio); // Precio
                stmtVenta.setDouble(4, total); // Total
                stmtVenta.setInt(5, idCliente); // ID Cliente

                int filasAfectadas = stmtVenta.executeUpdate();
                if (filasAfectadas == 0) {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Agregar venta al modelo de la tabla
                modeloTablaVenta.addRow(new Object[]{
                    textFieldIdProducto.getText(), textFieldProducto.getText(), cantidad, precio, total
                });

                JOptionPane.showMessageDialog(this, "Venta registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            // Limpiar campos después de la venta
            limpiarCampos();
            cargarVentas(); // Recargar ventas en la tabla

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private void cargarVentas() {
        String query = "SELECT v.id, v.cliente_id, v.fecha, v.total, c.nombre FROM Ventas v " +
                       "JOIN Clientes c ON v.cliente_id = c.id";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Limpiar la tabla antes de agregar los nuevos registros
            modeloTablaVenta.setRowCount(0);

            // Recorrer los resultados y agregar las filas a la tabla
            while (rs.next()) {
                int idVenta = rs.getInt("id");
                int clienteId = rs.getInt("cliente_id");
                Date fecha = rs.getDate("fecha");
                double total = rs.getDouble("total");
                String nombreCliente = rs.getString("nombre");

                // Agregar los datos a la tabla
                modeloTablaVenta.addRow(new Object[]{idVenta, nombreCliente, clienteId, fecha, total});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}