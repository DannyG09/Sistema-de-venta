package sistema;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class prueba {
    JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel panelLateral;

    // Constructor con parámetros para recibir los paneles
    public prueba(JPanel panelVenta, JPanel panelClientes, JPanel panelProductos, JPanel panelProveedor, JPanel panelConfiguracion) {
        initialize(panelVenta, panelClientes, panelProductos, panelProveedor, panelConfiguracion);
    }

    private void initialize(JPanel panelVenta, JPanel panelClientes, JPanel panelProductos, JPanel panelProveedor, JPanel panelConfiguracion) {
        // Crear la ventana principal
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(0, 0, 0));
        frame.setBounds(100, 100, 833, 495);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Panel lateral
        panelLateral = new JPanel();
        panelLateral.setBounds(0, 0, 126, 456);
        panelLateral.setBackground(new Color(0, 0, 0));
        panelLateral.setLayout(null);
        frame.getContentPane().add(panelLateral);

        try {
            // Ruta de la imagen del logo
            String rutaLogo = "C:\\Users\\danny_noso1ht\\Downloads\\Logo de D'guerrero (1).jpg";
            ImageIcon iconLogo = new ImageIcon(rutaLogo);

            // Escalar la imagen del logo para ajustarse al tamaño del JLabel
            Image imagenEscalada = iconLogo.getImage().getScaledInstance(126, 111, Image.SCALE_SMOOTH);
            ImageIcon logoEscalado = new ImageIcon(imagenEscalada);

            // Crear y configurar el JLabel con el logo
            JLabel lblLogo = new JLabel();
            lblLogo.setIcon(logoEscalado);
            lblLogo.setBounds(0, 0, 126, 111);
            panelLateral.add(lblLogo);

        } catch (Exception e) {
            // Mostrar mensaje si ocurre un error cargando el logo
            JLabel lblError = new JLabel("Logo no disponible");
            lblError.setForeground(Color.WHITE);
            lblError.setBounds(10, 10, 100, 20);
            panelLateral.add(lblError);
            System.err.println("Error al cargar el logo: " + e.getMessage());
        }

        // Botón para "Venta"
        JButton btnVenta = new JButton("Venta");
        btnVenta.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnVenta.setBounds(10, 120, 95, 23);
        btnVenta.addActionListener(e -> tabbedPane.setSelectedIndex(0)); // Selecciona la pestaña de "Venta"
        panelLateral.add(btnVenta);

        // Botón para "Clientes"
        JButton btnClientes = new JButton("Clientes");
        btnClientes.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnClientes.setBounds(10, 160, 95, 23);
        btnClientes.addActionListener(e -> tabbedPane.setSelectedIndex(1)); // Selecciona la pestaña de "Clientes"
        panelLateral.add(btnClientes);

        // Botón para "Productos"
        JButton btnProductos = new JButton("Productos");
        btnProductos.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnProductos.setBounds(10, 200, 95, 23);
        btnProductos.addActionListener(e -> tabbedPane.setSelectedIndex(2)); // Selecciona la pestaña de "Productos"
        panelLateral.add(btnProductos);

        // Botón para "Proveedor"
        JButton btnProveedor = new JButton("Proveedor");
        btnProveedor.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnProveedor.setBounds(10, 240, 95, 23);
        btnProveedor.addActionListener(e -> tabbedPane.setSelectedIndex(3)); // Selecciona la pestaña de "Proveedor"
        panelLateral.add(btnProveedor);

        // Botón para "Configuración"
        JButton btnConfiguracion = new JButton("Configuración");
        btnConfiguracion.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnConfiguracion.setBounds(10, 280, 95, 23);
        btnConfiguracion.addActionListener(e -> tabbedPane.setSelectedIndex(4)); // Selecciona la pestaña de "Configuración"
        panelLateral.add(btnConfiguracion);

        // Configuración del JTabbedPane
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(124, 49, 683, 396);
        frame.getContentPane().add(tabbedPane);

        // Agregar las pestañas
        tabbedPane.addTab("Venta", null, panelVenta, null);
        tabbedPane.addTab("Clientes", null, panelClientes, null);
        tabbedPane.addTab("Productos", null, panelProductos, null);
        tabbedPane.addTab("Proveedor", null, panelProveedor, null);
        tabbedPane.addTab("Configuración", null, panelConfiguracion, null);
    }

    public static void main(String[] args) {
        // Crear los paneles
        JPanel panelVenta = new PanelVenta();
        JPanel panelClientes = new PanelClientes();
        JPanel panelProductos = new PanelProductos();
        JPanel panelProveedor = new PanelProveedor();
        JPanel panelConfiguracion = new PanelConfiguracion();

        // Crear la ventana principal
        prueba window = new prueba(panelVenta, panelClientes, panelProductos, panelProveedor, panelConfiguracion);
        window.frame.setVisible(true);
    }
}
