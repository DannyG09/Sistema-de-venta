package sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelClientes extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField7, textField8, textField9, textField10;
    private JTable table;
    private DefaultTableModel modeloTablaClientes;

    public PanelClientes() {
        setLayout(null);
        initializeComponents();
    }

    private void initializeComponents() {
        // Modelo de tabla
        modeloTablaClientes = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Dirección", "RNC"}, 0);
        table = new JTable(modeloTablaClientes);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(257, 49, 370, 283);
        add(scrollPane);

        // Etiquetas
        JLabel lblCedulaId = new JLabel("Cédula/ID:");
        lblCedulaId.setBounds(43, 52, 67, 14);
        add(lblCedulaId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(42, 97, 46, 14);
        add(lblNombre);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(42, 144, 46, 14);
        add(lblTelefono);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(42, 193, 68, 14);
        add(lblDireccion);

        JLabel lblRNC = new JLabel("RNC:");
        lblRNC.setBounds(42, 237, 46, 14);
        add(lblRNC);

        // Campos de texto
        textField7 = new JTextField();
        textField7.setBounds(117, 49, 120, 20);
        add(textField7);

        textField8 = new JTextField();
        textField8.setBounds(117, 94, 120, 20);
        add(textField8);

        textField9 = new JTextField();
        textField9.setBounds(117, 141, 120, 20);
        add(textField9);

        textField10 = new JTextField();
        textField10.setBounds(117, 190, 120, 20);
        add(textField10);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(25, 276, 89, 23);
        add(btnGuardar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(137, 275, 89, 23);
        add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(25, 309, 89, 23);
        add(btnEliminar);

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(137, 309, 89, 23);
        add(btnNuevo);

        // Listeners de botones
        btnGuardar.addActionListener(e -> System.out.println("Guardar Cliente."));
        btnActualizar.addActionListener(e -> System.out.println("Actualizar Cliente."));
        btnEliminar.addActionListener(e -> System.out.println("Eliminar Cliente."));
        btnNuevo.addActionListener(e -> System.out.println("Nuevo Cliente."));
    }
}
