package sistema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelConfiguracion extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtRnc, txtNombreEmpresa, txtTelefono, txtDireccion;

    public PanelConfiguracion() {
        setLayout(null);
        initializeComponents();
    }

    private void initializeComponents() {
        // Etiquetas y campos de texto
        JLabel lblRnc = new JLabel("RNC:");
        lblRnc.setBounds(30, 40, 80, 20);
        add(lblRnc);

        txtRnc = new JTextField();
        txtRnc.setBounds(120, 40, 150, 20);
        add(txtRnc);

        JLabel lblNombreEmpresa = new JLabel("Nombre Empresa:");
        lblNombreEmpresa.setBounds(30, 80, 120, 20);
        add(lblNombreEmpresa);

        txtNombreEmpresa = new JTextField();
        txtNombreEmpresa.setBounds(150, 80, 150, 20);
        add(txtNombreEmpresa);

        JLabel lblTelefonoConfig = new JLabel("Teléfono:");
        lblTelefonoConfig.setBounds(30, 120, 80, 20);
        add(lblTelefonoConfig);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(120, 120, 150, 20);
        add(txtTelefono);

        JLabel lblDireccionConfig = new JLabel("Dirección:");
        lblDireccionConfig.setBounds(30, 160, 80, 20);
        add(lblDireccionConfig);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(120, 160, 250, 20);
        add(txtDireccion);

        // Botones de acción
        JButton btnGuardarConfiguracion = new JButton("Guardar");
        btnGuardarConfiguracion.setBounds(30, 200, 100, 30);
        add(btnGuardarConfiguracion);

        JButton btnCancelarConfiguracion = new JButton("Cancelar");
        btnCancelarConfiguracion.setBounds(140, 200, 100, 30);
        add(btnCancelarConfiguracion);

        // Acción para guardar los datos de configuración
        btnGuardarConfiguracion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarConfiguracion();
            }
        });

        // Acción para cancelar (limpiar los campos)
        btnCancelarConfiguracion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarConfiguracion();
            }
        });
    }

    // Método para guardar la configuración
    private void guardarConfiguracion() {
        String rnc = txtRnc.getText();
        String nombreEmpresa = txtNombreEmpresa.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();

        // Aquí podrías guardar la configuración en una base de datos o archivo
        System.out.println("Configuración Guardada:");
        System.out.println("RNC: " + rnc);
        System.out.println("Nombre Empresa: " + nombreEmpresa);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Dirección: " + direccion);
    }

    // Método para cancelar la configuración y limpiar los campos
    private void cancelarConfiguracion() {
        txtRnc.setText("");
        txtNombreEmpresa.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        System.out.println("Configuración cancelada.");
    }
}
