package LoginApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import guiapp.GuiApp;
import sistema.PanelClientes;
import sistema.PanelConfiguracion;
import sistema.PanelProductos;
import sistema.PanelProveedor;
import sistema.PanelVenta;

public class LoginApp {
    public static void main(String[] args) {
        // Llamamos al Login
        iniciarLogin();
    }

    private static void iniciarLogin() {
        // VENTANA DE LOGIN
        JFrame frame = new JFrame("Inicio de Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        // JFRAME
        frame.getContentPane().setBackground(new Color(240, 248, 255));

        // TITULO
        JLabel titleLabel = new JLabel("Iniciar Sesión");
        titleLabel.setBounds(120, 20, 200, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(titleLabel);

        // USUARIO
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setBounds(50, 80, 100, 25);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(150, 80, 200, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 1));
        frame.add(userField);

        // CONTRASEÑA
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setBounds(50, 130, 100, 25);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 130, 200, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 1));
        frame.add(passField);

        // BOTÓN LOGIN
        JButton loginButton = new JButton("Ingresar");
        loginButton.setBounds(150, 190, 200, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(100, 149, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        frame.add(loginButton);

        // ACCIÓN DEL BOTÓN
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // VALIDAR CREDENCIALES
                if ("VDE".equals(username) && "VDE23".equals(password)) {
                    JOptionPane.showMessageDialog(frame, "INICIO DE SESION EXITOSO", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Abrir GuiApp en el hilo de Swing
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            abrirVentanaPrincipal();
                        }
                    });

                    frame.dispose(); // CERRAR LOGIN
                } else {
                    JOptionPane.showMessageDialog(frame, "USUARIO O CONTRASEÑA INCORRECTOS", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // MOSTRAR VENTANA DE LOGIN
        frame.setLocationRelativeTo(null); // CENTRAR VENTANA
        frame.setVisible(true);
    }

    private static void abrirVentanaPrincipal() {
        // Crear los paneles
        JPanel panelVenta = new PanelVenta();
        JPanel panelClientes = new PanelClientes();
        JPanel panelProductos = new PanelProductos();
        JPanel panelProveedor = new PanelProveedor();
        JPanel panelConfiguracion = new PanelConfiguracion();

        // Crear la ventana principal con los paneles
        GuiApp window = new GuiApp(panelVenta, panelClientes, panelProductos, panelProveedor, panelConfiguracion);
        window.getFrame().setVisible(true);  // Usar el método público para obtener el frame
    }
}
