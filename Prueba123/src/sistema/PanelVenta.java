package sistema;

import guiapp.Conexion_bdd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.io.FileOutputStream;
import java.sql.*;

public class PanelVenta extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField textFielNombredeprocto, textFieldProducto, textFieldCantidad, textFieldPrecio, textFieldTotal, textFieldStock, textFieldClienteId;
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
        modeloTablaVenta = new DefaultTableModel(new String[]{"Código Producto", "Cliente", "Cantidad", "Precio", "Total"}, 0);
        tableVenta = new JTable(modeloTablaVenta);
        JScrollPane scrollPaneVenta = new JScrollPane(tableVenta);
        scrollPaneVenta.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneVenta.setBounds(10, 85, 712, 260);
        add(scrollPaneVenta);

        // Etiquetas y campos de texto
        JLabel lblCodigoProducto = new JLabel("Código Producto:");
        lblCodigoProducto.setBounds(30, 11, 120, 14);
        add(lblCodigoProducto);

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
        lblTotal.setBounds(362, 361, 89, 14);
        add(lblTotal);

        JLabel lblClienteId = new JLabel("ID Cliente:");
        lblClienteId.setBounds(79, 361, 67, 14);
        add(lblClienteId);

        textFielNombredeprocto = new JTextField();
        textFielNombredeprocto.setBounds(43, 37, 147, 30);
        textFielNombredeprocto.addActionListener(e -> buscarProducto());
        add(textFielNombredeprocto);

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
        textFieldTotal.setBounds(447, 356, 181, 23);
        textFieldTotal.setEditable(false);
        add(textFieldTotal);

        textFieldClienteId = new JTextField();
        textFieldClienteId.setBounds(139, 356, 140, 23);
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
        btnGenerarPDF.addActionListener(e -> generarPDFVenta());
        add(btnGenerarPDF);
        
       
    }

    private void buscarProducto() {
        String codigoProducto = textFielNombredeprocto.getText();
        String query = "SELECT nombre, precio, stock FROM productos WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codigoProducto);
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
                stmtVenta.setString(1, textFielNombredeprocto.getText()); // Código del Producto
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
                modeloTablaVenta.addRow(new Object[] {
                		textFielNombredeprocto.getText(), textFieldProducto.getText(), cantidad, precio, total
                });

                // Limpiar campos
                limpiarCampos();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarPDFVenta() {
        // Ruta del archivo PDF
        String rutaPDF = "ventas.pdf";

        try {
            // Crear el objeto Document
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            // Crear el escritor que apunta al archivo PDF
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));
            // Abrir el documento
            document.open();

            // Agregar un título al documento
            com.itextpdf.text.Font fontTitulo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph("Resumen de Ventas", fontTitulo);
            titulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(titulo);

            // Espacio debajo del título
            document.add(new com.itextpdf.text.Paragraph("\n"));

            // Iterar por las filas de la tabla y agregar cada venta al PDF
            for (int i = 0; i < modeloTablaVenta.getRowCount(); i++) {
                String producto = (String) modeloTablaVenta.getValueAt(i, 0);
                String cliente = (String) modeloTablaVenta.getValueAt(i, 1);
                int cantidad = (int) modeloTablaVenta.getValueAt(i, 2);
                double precio = (double) modeloTablaVenta.getValueAt(i, 3);
                double total = (double) modeloTablaVenta.getValueAt(i, 4);

                // Crear una línea para cada venta
                String lineaVenta = String.format("Producto: %s | Cliente: %s | Cantidad: %d | Precio: %.2f | Total: %.2f",
                        producto, cliente, cantidad, precio, total);

                // Agregar la línea como un párrafo al documento
                document.add(new com.itextpdf.text.Paragraph(lineaVenta));
            }

            // Cerrar el documento
            document.close();

            // Mostrar un mensaje de éxito
            JOptionPane.showMessageDialog(this, "PDF generado correctamente en: " + rutaPDF, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Manejo de errores
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        String query = "SELECT v.producto, v.cantidad, v.precio, v.total, c.nombre FROM Ventas v JOIN Clientes c ON v.cliente_id = c.id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                modeloTablaVenta.addRow(new Object[]{
                    rs.getString("producto"),
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio"),
                    rs.getDouble("total")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}