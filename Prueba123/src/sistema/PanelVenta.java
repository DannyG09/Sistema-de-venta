package sistema;

import guiapp.Conexion_bdd;
import util.GeneradorPDFVenta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.sql.*;
import java.awt.Font;

public class PanelVenta extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField textFielNombredeprocto, textFieldProducto, textFieldCantidad, textFieldPrecio, textFieldTotal, textFieldStock,textFieldCategoria, textFieldClienteId;
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
    	modeloTablaVenta = new DefaultTableModel(new String[]{"Código Producto", "Producto", "Categoría", "Cliente", "Cantidad", "Precio", "Total"}, 0);
        tableVenta = new JTable(modeloTablaVenta);
        JScrollPane scrollPaneVenta = new JScrollPane(tableVenta);
        scrollPaneVenta.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneVenta.setBounds(10, 85, 763, 260);
        add(scrollPaneVenta);

        // Etiquetas y campos de texto
        JLabel lblCodigoProducto = new JLabel("Código Producto:");
        lblCodigoProducto.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        lblCodigoProducto.setBounds(26, 15, 147, 14);
        add(lblCodigoProducto);

        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        lblProducto.setBounds(210, 15, 67, 14);
        add(lblProducto);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        lblCantidad.setBounds(449, 15, 67, 14);
        add(lblCantidad);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        lblPrecio.setBounds(564, 15, 46, 14);
        add(lblPrecio);

        JLabel lblTotal = new JLabel("Total a pagar:");
        lblTotal.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        lblTotal.setBounds(337, 361, 114, 14);
        add(lblTotal);

        JLabel lblClienteId = new JLabel("ID Cliente:");
        lblClienteId.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        lblClienteId.setBounds(53, 361, 94, 14);
        add(lblClienteId);
        
     // Crear el JLabel para la categoría
        JLabel labelCategoria = new JLabel("Categoría:");
        labelCategoria.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        labelCategoria.setBounds(324, 11, 87, 23);  // Ajusta las coordenadas y tamaños según tu diseño
        add(labelCategoria);

        textFielNombredeprocto = new JTextField();
        textFielNombredeprocto.setBounds(10, 41, 147, 30);
        textFielNombredeprocto.addActionListener(e -> buscarProducto());
        add(textFielNombredeprocto);

        textFieldProducto = new JTextField();
        textFieldProducto.setBounds(167, 40, 147, 30);
        textFieldProducto.setEditable(false);
        add(textFieldProducto);

        textFieldCantidad = new JTextField();
        textFieldCantidad.setBounds(449, 40, 67, 30);
        textFieldCantidad.addActionListener(e -> calcularTotal());
        add(textFieldCantidad);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(538, 41, 86, 30);
        textFieldPrecio.setEditable(false);
        add(textFieldPrecio);

        textFieldTotal = new JTextField();
        textFieldTotal.setBounds(447, 356, 181, 23);
        textFieldTotal.setEditable(false);
        add(textFieldTotal);

        textFieldClienteId = new JTextField();
        textFieldClienteId.setBounds(139, 356, 140, 23);
        textFieldClienteId.addActionListener(e -> cargarDatosCliente()); // Llamar al método cuando se ingrese un ID de cliente
        add(textFieldClienteId);

        textFieldStock = new JTextField();
        textFieldStock.setBounds(647, 41, 86, 30);
        textFieldStock.setEditable(false);
        add(textFieldStock);
        
     // Crear el JTextField para mostrar la categoría
        textFieldCategoria = new JTextField();
        textFieldCategoria.setBounds(324, 40, 106, 31);  // Ajusta las coordenadas y tamaños según tu diseño
        textFieldCategoria.setEditable(false);  // No debe ser editable, solo mostrar la categoría
        add(textFieldCategoria);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        lblStock.setBounds(663, 15, 46, 14);
        add(lblStock);

     // Botón para generar la venta
        JButton btnGenerarVenta = new JButton("Generar Venta");
        btnGenerarVenta.setBounds(270, 409, 181, 23);
        btnGenerarVenta.setBackground(new Color(34, 139, 34));  // Color de fondo verde
        btnGenerarVenta.setForeground(Color.WHITE);  // Color del texto blanco
        btnGenerarVenta.addActionListener(e -> registrarVenta());
        add(btnGenerarVenta);

        // Botón para eliminar la venta
        JButton btnEliminarVenta = new JButton("Eliminar Venta");
        btnEliminarVenta.setBounds(79, 409, 181, 23);
        btnEliminarVenta.setBackground(new Color(255, 99, 71));  // Color de fondo rojo
        btnEliminarVenta.setForeground(Color.WHITE);  // Color del texto blanco
        btnEliminarVenta.addActionListener(e -> eliminarVenta());
        add(btnEliminarVenta);

        // Botón para generar el PDF
        JButton btnGenerarPDF = new JButton("Generar PDF");
        btnGenerarPDF.setBounds(461, 409, 181, 23);
        btnGenerarPDF.setBackground(new Color(30, 144, 255));  // Color de fondo azul
        btnGenerarPDF.setForeground(Color.WHITE);  // Color del texto blanco
        btnGenerarPDF.addActionListener(e -> {
            // Instanciar GeneradorPDFVenta con la JTable correspondiente
            GeneradorPDFVenta generadorPDF = new GeneradorPDFVenta(tableVenta);
            // Definir el título del PDF y la ruta donde guardarlo
            String tituloPDF = "Reporte de Ventas";
            String rutaPDF = System.getProperty("user.home") + "/ventas.pdf"; // Ruta para guardar el PDF
            // Llamar al método para generar el PDF
            generadorPDF.generarPDF(tituloPDF, rutaPDF);
        });
        add(btnGenerarPDF);
    }

    private void buscarProducto() {
        // Obtener el código del producto ingresado
        String codigoProducto = textFielNombredeprocto.getText();  // Asegúrate de que el nombre del campo esté correcto
        String query = "SELECT nombre, precio, stock, categoria FROM productos WHERE id = ?";  // Agregar 'categoria'

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codigoProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Asignar los valores a los campos correspondientes
                textFieldProducto.setText(rs.getString("nombre"));
                textFieldPrecio.setText(String.valueOf(rs.getDouble("precio")));
                textFieldStock.setText(String.valueOf(rs.getInt("stock")));
                
                // Agregar la categoría al JTextField correspondiente
                textFieldCategoria.setText(rs.getString("categoria"));  // Ahora hace referencia al campo de clase
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

            // Consultar el nombre del cliente para agregarlo en la venta
            String queryCliente = "SELECT nombre FROM Clientes WHERE id = ?";
            String nombreCliente = "";
            try (PreparedStatement stmtCliente = conn.prepareStatement(queryCliente)) {
                stmtCliente.setInt(1, idCliente);
                ResultSet rsCliente = stmtCliente.executeQuery();
                if (rsCliente.next()) {
                    nombreCliente = rsCliente.getString("nombre");
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Consultar el nombre del producto y la categoría
            String queryProducto = "SELECT nombre, categoria FROM Productos WHERE id = ?";
            String nombreProducto = "";
            String categoriaProducto = "";
            try (PreparedStatement stmtProducto = conn.prepareStatement(queryProducto)) {
                stmtProducto.setString(1, textFielNombredeprocto.getText());
                ResultSet rsProducto = stmtProducto.executeQuery();
                if (rsProducto.next()) {
                    nombreProducto = rsProducto.getString("nombre");
                    categoriaProducto = rsProducto.getString("categoria");
                } else {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Insertar la venta
            String queryVenta = "INSERT INTO Ventas (producto, cantidad, precio, total, cliente_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmtVenta = conn.prepareStatement(queryVenta, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenta.setString(1, textFielNombredeprocto.getText());  // Código del Producto
                stmtVenta.setInt(2, cantidad);                             // Cantidad
                stmtVenta.setDouble(3, precio);                            // Precio
                stmtVenta.setDouble(4, total);                             // Total
                stmtVenta.setInt(5, idCliente);                            // ID Cliente

                int filasAfectadas = stmtVenta.executeUpdate();
                if (filasAfectadas == 0) {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Agregar la venta al modelo de la tabla
                modeloTablaVenta.addRow(new Object[] {
                    textFielNombredeprocto.getText(),   // Código del Producto
                    nombreProducto,                     // Nombre del Producto
                    categoriaProducto,                  // Categoría del Producto
                    nombreCliente,                      // Nombre del Cliente
                    cantidad,                           // Cantidad
                    precio,                             // Precio
                    total                               // Total
                });

                // Limpiar campos
                limpiarCampos();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    


    private void eliminarVenta() {
        int selectedRow = tableVenta.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una venta para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String producto = (String) modeloTablaVenta.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar la venta de producto " + producto + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM Ventas WHERE producto = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, producto);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    modeloTablaVenta.removeRow(selectedRow); // Eliminar la fila de la tabla
                    JOptionPane.showMessageDialog(this, "Venta eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        textFielNombredeprocto.setText("");
        textFieldProducto.setText("");
        textFieldCantidad.setText("");
        textFieldPrecio.setText("");
        textFieldTotal.setText("");
        textFieldStock.setText("");
    }

    private void cargarVentas() {
        String query = "SELECT v.producto AS producto_id, p.nombre AS producto_nombre, p.categoria, v.cantidad, v.precio, v.total, c.nombre AS cliente_nombre " +
                       "FROM Ventas v " +
                       "JOIN Clientes c ON v.cliente_id = c.id " +  // Obtener el cliente
                       "JOIN Productos p ON v.producto = p.id";    // Obtener el nombre del producto

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                modeloTablaVenta.addRow(new Object[] {
                    rs.getString("producto_id"),        // Código del producto
                    rs.getString("producto_nombre"),    // Nombre del producto
                    rs.getString("categoria"),          // Categoría del producto
                    rs.getString("cliente_nombre"),     // Nombre del cliente
                    rs.getInt("cantidad"),              // Cantidad
                    rs.getDouble("precio"),             // Precio
                    rs.getDouble("total")               // Total
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}