package LoginApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import guiapp.Conexion_bdd;
import guiapp.GuiApp;
import sistema.PanelClientes;
import sistema.PanelConfiguracion;
import sistema.PanelProductos;
import sistema.PanelProveedor;
import sistema.PanelVenta;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.URL;

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
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
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
        frame.setSize(759, 488);
        frame.getContentPane().setLayout(null);

        // JFRAME
        frame.getContentPane().setBackground(new Color(0, 0, 64));

        // TITULO
        JLabel titleLabel = new JLabel("");
        titleLabel.setIcon(loadImageIcon("iniciar.png"));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setBounds(314, 67, 200, 71);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(titleLabel);

        // USUARIO
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(new Color(255, 255, 255));
        userLabel.setBounds(264, 178, 100, 25);
        userLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        frame.getContentPane().add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(364, 178, 200, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 1));
        frame.getContentPane().add(userField);

        // CONTRASEÑA
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(new Color(255, 255, 255));
        passLabel.setBounds(264, 228, 100, 25);
        passLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        frame.getContentPane().add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(364, 228, 200, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 1));
        frame.getContentPane().add(passField);

        // BOTÓN LOGIN
        JButton loginButton = new JButton("Ingresar");
        loginButton.setBounds(314, 287, 200, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(100, 149, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        frame.getContentPane().add(loginButton);

        // Imagen lateral izquierda
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setForeground(new Color(51, 51, 51));
        lblNewLabel.setIcon(loadImageIcon("Gris y Blanco Moderno Foto Lateral Celular Descuentos Negocio Tu Historia (1).png"));
        lblNewLabel.setBounds(0, 0, 224, 449);
        frame.getContentPane().add(lblNewLabel);

        // Logo en la esquina superior derecha
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(loadImageIcon("Logo de D'guerrero (1).png"));
        lblNewLabel_1.setBounds(611, 0, 165, 129);
        frame.getContentPane().add(lblNewLabel_1);

        // Botón de acción
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // VALIDAR CREDENCIALES
                if (validarCredenciales(username, password)) {
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

    private static boolean validarCredenciales(String username, String password) {
        Connection conn = Conexion_bdd.getConnection(); // Mantener la conexión abierta
        try {
            // Hashear la contraseña ingresada con SHA-256
            String hashedPassword = hashPassword(password);

            // Query para validar las credenciales
            String query = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            // Si hay resultados, el usuario existe y la contraseña es correcta
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al validar las credenciales: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al aplicar el hash SHA-256: " + e.getMessage());
        }
        return false; // Si no se encuentra el usuario
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static ImageIcon loadImageIcon(String fileName) {
        URL resource = getResourcePath(fileName);
        if (resource != null) {
            return new ImageIcon(resource);
        } else {
            System.err.println("Error: No se pudo cargar la imagen: " + fileName);
            return null;
        }
    }

    private static URL getResourcePath(String fileName) {
        URL resource = LoginApp.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            System.err.println("No se encontró el recurso: " + fileName);
        } else {
            System.out.println("Recurso cargado correctamente: " + fileName);
        }
        return resource;
    }
}