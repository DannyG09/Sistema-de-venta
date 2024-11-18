package sistema;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.Font;

public class prueba {

    JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTable table;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_9;
    private JTextField textField_10;
    private JTextField textField_11;
    private JTable table_1;

    /**
     * Launch the application.
     */


    /**
     * Create the application.
     */
    public prueba() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 792, 495);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Panel lateral
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setBounds(0, 0, 126, 456);
        frame.getContentPane().add(panel);

        // Botón Nueva Venta (con un ActionListener agregado)
        JButton btnNuevaVenta = new JButton(" Venta");
        btnNuevaVenta.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnNuevaVenta.setBounds(10, 122, 95, 23);
        btnNuevaVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic: Imprimir mensaje en consola
                System.out.println("Botón 'Nueva Venta' presionado.");
                // Aquí puedes agregar la funcionalidad real, como abrir una ventana de nueva venta.
            }
        });
        panel.setLayout(null);
        panel.add(btnNuevaVenta);
        
        JButton btnNewButton = new JButton("Clientes");
        btnNewButton.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnNewButton.setBounds(10, 166, 95, 23);
        panel.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Provedor");
        btnNewButton_1.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnNewButton_1.setBounds(10, 211, 95, 23);
        panel.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Ventas");
        btnNewButton_2.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        btnNewButton_2.setBounds(10, 258, 95, 23);
        panel.add(btnNewButton_2);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\danny_noso1ht\\Downloads\\Logo de D'guerrero (1).jpg"));
        lblNewLabel.setBounds(0, 0, 126, 111);
        panel.add(lblNewLabel);

        // Imagen del encabezado
        JLabel lblEncabezado = new JLabel("");
        lblEncabezado.setBackground(new Color(0, 0, 0));
        lblEncabezado.setIcon(new ImageIcon("C:\\Users\\danny_noso1ht\\Downloads\\Captura de pantalla 2024-11-17 195915 (2).png"));
        lblEncabezado.setBounds(124, 0, 652, 50);
        frame.getContentPane().add(lblEncabezado);

        // Panel principal con pestañas
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(124, 49, 642, 396);
        frame.getContentPane().add(tabbedPane);
        
                // Ejemplo de una pestaña
                Panel panelTab1 = new Panel();
                tabbedPane.addTab("Inicio", null, panelTab1, null);
                panelTab1.setLayout(null);
                
                JLabel lblNewLabel_1 = new JLabel("Codigo");
                lblNewLabel_1.setFont(new Font("Times New Roman", Font.ITALIC, 13));
                lblNewLabel_1.setBounds(39, 24, 46, 14);
                panelTab1.add(lblNewLabel_1);
                
                JLabel lblNewLabel_2 = new JLabel("Descripcion");
                lblNewLabel_2.setFont(new Font("Times New Roman", Font.ITALIC, 13));
                lblNewLabel_2.setBounds(127, 24, 86, 14);
                panelTab1.add(lblNewLabel_2);
                
                JLabel lblNewLabel_3 = new JLabel("Cantidad");
                lblNewLabel_3.setFont(new Font("Times New Roman", Font.ITALIC, 13));
                lblNewLabel_3.setBounds(223, 24, 63, 14);
                panelTab1.add(lblNewLabel_3);
                
                JLabel lblNewLabel_4 = new JLabel("Precio");
                lblNewLabel_4.setFont(new Font("Times New Roman", Font.ITALIC, 13));
                lblNewLabel_4.setBounds(318, 24, 46, 14);
                panelTab1.add(lblNewLabel_4);
                
                JButton btnNewButton_3 = new JButton("");
                btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\danny_noso1ht\\Downloads\\png-transparent-red-x-illustration-red-x-letter-computer-icons-red-x-miscellaneous-angle-text-thumbnail (2).png"));
                btnNewButton_3.setBounds(520, 34, 46, 31);
                panelTab1.add(btnNewButton_3);
                
                textField = new JTextField();
                textField.setBounds(17, 49, 86, 20);
                panelTab1.add(textField);
                textField.setColumns(10);
                
                textField_1 = new JTextField();
                textField_1.setBounds(113, 49, 86, 20);
                panelTab1.add(textField_1);
                textField_1.setColumns(10);
                
                textField_2 = new JTextField();
                textField_2.setBounds(209, 49, 86, 20);
                panelTab1.add(textField_2);
                textField_2.setColumns(10);
                
                textField_3 = new JTextField();
                textField_3.setBounds(305, 49, 86, 20);
                panelTab1.add(textField_3);
                textField_3.setColumns(10);
                
                JLabel lblNewLabel_5 = new JLabel("Stock");
                lblNewLabel_5.setFont(new Font("Times New Roman", Font.ITALIC, 13));
                lblNewLabel_5.setBounds(417, 24, 46, 14);
                panelTab1.add(lblNewLabel_5);
                
                textField_4 = new JTextField();
                textField_4.setBounds(401, 49, 86, 20);
                panelTab1.add(textField_4);
                textField_4.setColumns(10);
                
                table = new JTable();
                table.setCellSelectionEnabled(true);
                table.setToolTipText("");
                table.setBounds(10, 80, 607, 234);
                panelTab1.add(table);
                
                JLabel lblNewLabel_6 = new JLabel("Cedula:");
                lblNewLabel_6.setFont(new Font("Times New Roman", Font.ITALIC, 13));
                lblNewLabel_6.setBounds(51, 325, 46, 14);
                panelTab1.add(lblNewLabel_6);
                
                textField_5 = new JTextField();
                textField_5.setBounds(17, 348, 106, 20);
                panelTab1.add(textField_5);
                textField_5.setColumns(10);
                
                JLabel lblNewLabel_7 = new JLabel("Nombre");
                lblNewLabel_7.setFont(new Font("Times New Roman", Font.ITALIC, 13));
                lblNewLabel_7.setBounds(199, 325, 46, 14);
                panelTab1.add(lblNewLabel_7);
                
                textField_6 = new JTextField();
                textField_6.setBounds(149, 348, 137, 20);
                panelTab1.add(textField_6);
                textField_6.setColumns(10);
                
                JScrollPane scrollPane_1 = new JScrollPane();
                scrollPane_1.setBounds(17, 80, 600, 23);
                panelTab1.add(scrollPane_1);
                
                
        
        Panel panel_1 = new Panel();
        tabbedPane.addTab("New tab", null, panel_1, null);
        panel_1.setLayout(null);
        
        DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Dirección", "RNC"}, 0);
        table_1 = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(276, 49, 329, 202);
        panel_1.add(scrollPane);
        
        JLabel lblNewLabel_8 = new JLabel("Cedula/ID");
        lblNewLabel_8.setBounds(43, 52, 67, 14);
        panel_1.add(lblNewLabel_8);
        
        JLabel lblNewLabel_9 = new JLabel("Nombre:");
        lblNewLabel_9.setBounds(42, 97, 46, 14);
        panel_1.add(lblNewLabel_9);
        
        JLabel lblNewLabel_10 = new JLabel("Tèlefono:");
        lblNewLabel_10.setBounds(42, 144, 46, 14);
        panel_1.add(lblNewLabel_10);
        
        JLabel lblNewLabel_11 = new JLabel("Direccion:");
        lblNewLabel_11.setBounds(42, 193, 68, 14);
        panel_1.add(lblNewLabel_11);
        
        JLabel lblNewLabel_12 = new JLabel("RNC:");
        lblNewLabel_12.setBounds(42, 237, 46, 14);
        panel_1.add(lblNewLabel_12);
        
        textField_7 = new JTextField();
        textField_7.setBounds(117, 49, 120, 20);
        panel_1.add(textField_7);
        textField_7.setColumns(10);
        
        textField_8 = new JTextField();
        textField_8.setBounds(117, 94, 120, 20);
        panel_1.add(textField_8);
        textField_8.setColumns(10);
        
        textField_9 = new JTextField();
        textField_9.setBounds(117, 141, 120, 20);
        panel_1.add(textField_9);
        textField_9.setColumns(10);
        
        textField_10 = new JTextField();
        textField_10.setBounds(117, 190, 120, 20);
        panel_1.add(textField_10);
        textField_10.setColumns(10);
        
        textField_11 = new JTextField();
        textField_11.setBounds(117, 234, 120, 20);
        panel_1.add(textField_11);
        textField_11.setColumns(10);
        
        table_1 = new JTable();
        table_1.setBounds(276, 49, 329, 202);
        panel_1.add(table_1);
        
     // Modelo de la tabla
      
        
        Panel panel_2 = new Panel();
        tabbedPane.addTab("New tab", null, panel_2, null);
        
        Panel panel_3 = new Panel();
        tabbedPane.addTab("New tab", null, panel_3, null);
        
        Panel panel_4 = new Panel();
        tabbedPane.addTab("New tab", null, panel_4, null);
    }
}
