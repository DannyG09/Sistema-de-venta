package guiapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import sistema.PanelClientes;
import sistema.PanelConfiguracion;
import sistema.PanelProductos;
import sistema.PanelProveedor;
import sistema.PanelVenta;

public class GuiApp {
    JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel panelLateral;
    
    // Método público para obtener el frame
    public JFrame getFrame() {
        return frame;
    }
    
    // Constructor con parámetros para recibir los paneles
    public GuiApp(JPanel panelVenta, JPanel panelClientes, JPanel panelProductos, JPanel panelProveedor, JPanel panelConfiguracion) {
        initialize(panelVenta, panelClientes, panelProductos, panelProveedor, panelConfiguracion);
    }

    private void initialize(JPanel panelVenta, JPanel panelClientes, JPanel panelProductos, JPanel panelProveedor, JPanel panelConfiguracion) {
        // Crear la ventana principal
        frame = new JFrame();
        frame.setBackground(new Color(192, 192, 192));
        frame.getContentPane().setBackground(new Color(0, 0, 0));
        frame.setBounds(100, 100, 941, 583);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Panel lateral
        panelLateral = new JPanel();
        panelLateral.setBounds(0, 0, 126, 544);
        panelLateral.setBackground(new Color(0, 0, 0));
        panelLateral.setLayout(null);
        frame.getContentPane().add(panelLateral);

        // Cargar el logo utilizando la nueva función loadImageIcon
        try {
            ImageIcon logoIcon = loadImageIcon("Logo de D'guerrero (Guiapp).png.png");
            if (logoIcon != null) {
                // Escalar la imagen del logo para ajustarse al tamaño del JLabel
                Image imagenEscalada = logoIcon.getImage().getScaledInstance(126, 111, Image.SCALE_SMOOTH);
                ImageIcon logoEscalado = new ImageIcon(imagenEscalada);

                // Crear y configurar el JLabel con el logo
                JLabel lblLogo = new JLabel();
                lblLogo.setIcon(logoEscalado);
                lblLogo.setBounds(0, 0, 126, 111);
                panelLateral.add(lblLogo);
            }
        } catch (Exception e) {
            // Mostrar mensaje si ocurre un error cargando el logo
            JLabel lblError = new JLabel("Logo no disponible");
            lblError.setForeground(Color.WHITE);
            lblError.setBounds(10, 10, 100, 20);
            panelLateral.add(lblError);
            System.err.println("Error al cargar el logo: " + e.getMessage());
        }

        // Botones
        createButton("Venta", 149, e -> tabbedPane.setSelectedIndex(0));
        createButton("Clientes", 189, e -> tabbedPane.setSelectedIndex(1));
        createButton("Productos", 229, e -> tabbedPane.setSelectedIndex(2));
        createButton("Proveedor", 269, e -> tabbedPane.setSelectedIndex(3));
        createButton("Configuración", 309, e -> tabbedPane.setSelectedIndex(4));

        // Configuración del JTabbedPane
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setForeground(new Color(0, 0, 0)); // Establecer el color del texto a negro
        tabbedPane.setBackground(new Color(255, 255, 255)); // Establecer el color de fondo a blanco
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Establecer una fuente legible

        tabbedPane.setBounds(124, 49, 789, 464);
        frame.getContentPane().add(tabbedPane);

        // Agregar las pestañas
        tabbedPane.addTab("Venta", null, panelVenta, null);
        tabbedPane.addTab("Clientes", null, panelClientes, null);
        tabbedPane.addTab("Productos", null, panelProductos, null);
        tabbedPane.addTab("Proveedor", null, panelProveedor, null);
        tabbedPane.addTab("Configuración", null, panelConfiguracion, null);

        // Título de la aplicación
        JLabel lblNewLabel = new JLabel("D'GUERRERO TECHNOLOGY");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 21));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(350, 23, 299, 16);
        frame.getContentPane().add(lblNewLabel);
    }

    // Crear un botón para evitar duplicación de código
    private void createButton(String text, int yPosition, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        button.setBounds(12, yPosition, 95, 23);
        button.addActionListener(action);
        panelLateral.add(button);
    }

    // Método para cargar una imagen desde los recursos
    private static ImageIcon loadImageIcon(String fileName) {
        URL resource = getResourcePath(fileName);
        if (resource != null) {
            return new ImageIcon(resource);
        } else {
            System.err.println("Error: No se pudo cargar la imagen: " + fileName);
            return null;
        }
    }

    // Método para obtener la ruta del recurso
    private static URL getResourcePath(String fileName) {
        URL resource = GuiApp.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            System.err.println("No se encontró el recurso: " + fileName);
        } else {
            System.out.println("Recurso cargado correctamente: " + fileName);
        }
        return resource;
    }

    public static void main(String[] args) {
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