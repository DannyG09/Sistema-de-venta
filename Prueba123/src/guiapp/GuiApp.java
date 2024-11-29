package guiapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import sistema.PanelClientes;
import sistema.PanelConfiguracion;
import sistema.PanelProductos;
import sistema.PanelProveedor;
import sistema.PanelVenta;

public class GuiApp {
    JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel panelLateral;
    
    // Constructor con parámetros para recibir los paneles
    public GuiApp(JPanel panelVenta, JPanel panelClientes, JPanel panelProductos, JPanel panelProveedor, JPanel panelConfiguracion) {
        initialize(panelVenta, panelClientes, panelProductos, panelProveedor, panelConfiguracion);
    }

    private void initialize(JPanel panelVenta, JPanel panelClientes, JPanel panelProductos, JPanel panelProveedor, JPanel panelConfiguracion) {
        // Crear la ventana principal
        frame = new JFrame();
        frame.setBackground(new Color(192, 192, 192));
        frame.getContentPane().setBackground(new Color(0, 0, 0));
        frame.setBounds(100, 100, 898, 583);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Panel lateral
        panelLateral = new JPanel();
        panelLateral.setBounds(0, 0, 126, 544);
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
        btnVenta.setBounds(12, 149, 95, 23);
        btnVenta.addActionListener(e -> tabbedPane.setSelectedIndex(0)); // Selecciona la pestaña de "Venta"
        panelLateral.add(btnVenta);

        // Botón para "Clientes"
        JButton btnClientes = new JButton("Clientes");
        btnClientes.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnClientes.setBounds(12, 189, 95, 23);
        btnClientes.addActionListener(e -> tabbedPane.setSelectedIndex(1)); // Selecciona la pestaña de "Clientes"
        panelLateral.add(btnClientes);

        // Botón para "Productos"
        JButton btnProductos = new JButton("Productos");
        btnProductos.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnProductos.setBounds(12, 229, 95, 23);
        btnProductos.addActionListener(e -> tabbedPane.setSelectedIndex(2)); // Selecciona la pestaña de "Productos"
        panelLateral.add(btnProductos);

        // Botón para "Proveedor"
        JButton btnProveedor = new JButton("Proveedor");
        btnProveedor.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnProveedor.setBounds(12, 269, 95, 23);
        btnProveedor.addActionListener(e -> tabbedPane.setSelectedIndex(3)); // Selecciona la pestaña de "Proveedor"
        panelLateral.add(btnProveedor);

        // Botón para "Configuración"
        JButton btnConfiguracion = new JButton("Configuración");
        btnConfiguracion.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnConfiguracion.setBounds(12, 309, 95, 23);
        btnConfiguracion.addActionListener(e -> tabbedPane.setSelectedIndex(4)); // Selecciona la pestaña de "Configuración"
        panelLateral.add(btnConfiguracion);

        // Configuración del JTabbedPane
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setForeground(new Color(255, 255, 255));
        tabbedPane.setBackground(new Color(255, 255, 255));
        tabbedPane.setBounds(124, 49, 734, 464);
        frame.getContentPane().add(tabbedPane);

        // Agregar las pestañas
        tabbedPane.addTab("Venta", null, panelVenta, null);
        tabbedPane.addTab("Clientes", null, panelClientes, null);
        tabbedPane.addTab("Productos", null, panelProductos, null);
        tabbedPane.addTab("Proveedor", null, panelProveedor, null);
        tabbedPane.addTab("Configuración", null, panelConfiguracion, null);
        
        JLabel lblNewLabel = new JLabel("D'GUERRERO TECHNOLOGY");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 21));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(350, 23, 299, 16);
        frame.getContentPane().add(lblNewLabel);
    }

    public static void main(String[] args) {
        // Establecer el Look and Feel Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, usar Look and Feel Cross-Platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Manejo de excepción si no se puede establecer un Look and Feel
                ex.printStackTrace();
            }
        }

        // Crear los paneles
        JPanel panelVenta = new PanelVenta();
        JPanel panelClientes = new PanelClientes();
        JPanel panelProductos = new PanelProductos();
        JPanel panelProveedor = new PanelProveedor();
        JPanel panelConfiguracion = new PanelConfiguracion();

        // Crear la ventana principal
        GuiApp window = new GuiApp(panelVenta, panelClientes, panelProductos, panelProveedor, panelConfiguracion);
        window.frame.setVisible(true);
    }
}
