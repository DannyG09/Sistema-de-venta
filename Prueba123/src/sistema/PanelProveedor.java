
package sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelProveedor extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldRNC, textFieldNombre, textFieldTelefono, textFieldDireccion, textFieldRazonSocial;
    private JTable tableProveedor;
    private DefaultTableModel modeloTablaProveedor;

    public PanelProveedor() {
        setLayout(null);
        initializeComponents();
    }

    private void initializeComponents() {
        // Etiquetas y campos de texto
        JLabel lblRNC = new JLabel("RNC:");
        lblRNC.setBounds(30, 60, 46, 14);
        add(lblRNC);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 101, 46, 14);
        add(lblNombre);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(30, 146, 60, 14);
        add(lblTelefono);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(26, 193, 66, 14);
        add(lblDireccion);

        JLabel lblRazonSocial = new JLabel("Razón Social:");
        lblRazonSocial.setBounds(10, 239, 82, 14);
        add(lblRazonSocial);

        textFieldRNC = new JTextField();
        textFieldRNC.setBounds(102, 57, 86, 20);
        add(textFieldRNC);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(102, 98, 86, 20);
        add(textFieldNombre);

        textFieldTelefono = new JTextField();
        textFieldTelefono.setBounds(102, 146, 86, 20);
        add(textFieldTelefono);

        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(102, 190, 86, 20);
        add(textFieldDireccion);

        textFieldRazonSocial = new JTextField();
        textFieldRazonSocial.setBounds(102, 236, 86, 20);
        add(textFieldRazonSocial);

        // Tabla
        modeloTablaProveedor = new DefaultTableModel(new String[]{"ID", "RNC", "Nombre", "Teléfono", "Dirección", "Razón Social"}, 0);
        tableProveedor = new JTable(modeloTablaProveedor);
        JScrollPane scrollPaneProveedor = new JScrollPane(tableProveedor);
        scrollPaneProveedor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneProveedor.setBounds(213, 60, 469, 269);
        add(scrollPaneProveedor);

        // Botones de acción
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(10, 277, 89, 23);
        add(btnGuardar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(112, 277, 89, 23);
        add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(10, 321, 89, 23);
        add(btnEliminar);

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(114, 321, 89, 23);
        add(btnNuevo);

        // Acciones para los botones
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarProveedor();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarProveedor();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProveedor();
            }
        });

        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    // Métodos para las acciones
    private void guardarProveedor() {
        // Guardar datos en la tabla
        modeloTablaProveedor.addRow(new Object[]{
            modeloTablaProveedor.getRowCount() + 1,
            textFieldRNC.getText(),
            textFieldNombre.getText(),
            textFieldTelefono.getText(),
            textFieldDireccion.getText(),
            textFieldRazonSocial.getText()
        });
        limpiarCampos();
        System.out.println("Proveedor guardado.");
    }

    private void actualizarProveedor() {
        // Actualizar datos en la tabla (implementación adicional requerida)
        System.out.println("Actualizar Proveedor.");
    }

    private void eliminarProveedor() {
        // Eliminar la fila seleccionada
        int selectedRow = tableProveedor.getSelectedRow();
        if (selectedRow >= 0) {
            modeloTablaProveedor.removeRow(selectedRow);
            System.out.println("Proveedor eliminado.");
        } else {
            System.out.println("Seleccione un proveedor para eliminar.");
        }
    }

    private void limpiarCampos() {
        textFieldRNC.setText("");
        textFieldNombre.setText("");
        textFieldTelefono.setText("");
        textFieldDireccion.setText("");
        textFieldRazonSocial.setText("");
        System.out.println("Campos limpiados.");
    }
}
