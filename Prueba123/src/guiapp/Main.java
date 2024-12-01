package guiapp;

import java.awt.EventQueue;
import java.util.logging.Logger;
import sistema.PanelClientes;
import sistema.PanelConfiguracion;
import sistema.PanelProductos;
import sistema.PanelProveedor;
import sistema.PanelVenta;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Inicializar la conexión a la base de datos
                Conexion_bdd.getConnection(); // Establece la conexión a la base de datos

                // Crear las instancias de los paneles
                PanelVenta panelVenta = new PanelVenta();             // Panel de Venta
                PanelProductos panelProducto = new PanelProductos(); // Panel de Productos
                PanelClientes panelCliente = new PanelClientes();    // Panel de Clientes
                PanelProveedor panelProveedor = new PanelProveedor(); // Panel de Proveedor
                PanelConfiguracion panelConfiguracion = new PanelConfiguracion(); // Panel de Configuración
                
                // Crear la ventana con los paneles apropiados
                GuiApp window = new GuiApp(panelVenta, panelCliente, panelProducto, panelProveedor, panelConfiguracion);
                window.getFrame().setVisible(true); // Mostrar la ventana

            } catch (Exception e) {
                logger.severe("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace(); // Imprimir la traza de la excepción
            }
        });
    }
}