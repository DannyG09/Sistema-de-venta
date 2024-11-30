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
        // Configurar el Look and Feel Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, se establece un Look and Feel genérico
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Manejo de excepción en caso de falla
                ex.printStackTrace();
            }
        }

        // Llamamos al Login
        iniciarLogin();
    }

    private static void iniciarLogin() {
        // VENTANA DE LOGIN
        JFrame frame = new JFrame("Inicio de Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(571, 488);
        frame.getContentPane().setLayout(null);

        // JFRAME
        frame.getContentPane().setBackground(new Color(0, 0, 64));

        // TITULO
        JLabel titleLabel = new JLabel("");
        titleLabel.setIcon(new ImageIcon("C:\\Users\\danny_noso1ht\\Downloads\\iniciar.png"));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setBounds(94, 46, 200, 71);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(titleLabel);

        // USUARIO
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(new Color(255, 255, 255));
        userLabel.setBounds(44, 155, 100, 25);
        userLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        frame.getContentPane().add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(144, 155, 200, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 1));
        frame.getContentPane().add(userField);

        // CONTRASEÑA
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(new Color(255, 255, 255));
        passLabel.setBounds(44, 205, 100, 25);
        passLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        frame.getContentPane().add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(144, 205, 200, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 1));
        frame.getContentPane().add(passField);

        // BOTÓN LOGIN
        JButton loginButton = new JButton("Ingresar");
        loginButton.setBounds(94, 264, 200, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(100, 149, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        frame.getContentPane().add(loginButton);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\danny_noso1ht\\Downloads\\login.jpg"));
        lblNewLabel.setBounds(368, 0, 187, 449);
        frame.getContentPane().add(lblNewLabel);

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
        window.getFrame().setVisible(true); // Usar el método público para obtener el frame
    }
}
