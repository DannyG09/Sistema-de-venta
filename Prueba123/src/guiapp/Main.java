package guiapp;

import java.awt.EventQueue;
import sistema.PanelClientes;
import sistema.PanelConfiguracion;
import sistema.PanelProductos;
import sistema.PanelProveedor;
import sistema.PanelVenta;

public class Main {    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Inicializar la base de datos
                    Conexion_bdd.iniciarBaseDeDatos(); // Llama al método estático para inicializar la base de datos

                    // Crear las instancias de los paneles
                    PanelVenta panelVenta = new PanelVenta();             // Panel de Venta
                    PanelProductos panelProducto = new PanelProductos(); // Panel de Productos
                    PanelClientes panelCliente = new PanelClientes();   // Panel de Clientes
                    PanelProveedor panelProveedor = new PanelProveedor(); // Panel de Proveedor
                    PanelConfiguracion panelConfiguracion = new PanelConfiguracion(); // Panel de Configuración
                    
                    // Crear la ventana con los paneles apropiados
                    GuiApp window = new GuiApp(panelVenta, panelCliente, panelProducto, panelProveedor, panelConfiguracion);
                    window.frame.setVisible(true); // Mostrar la ventana
                } catch (Exception e) {
                    e.printStackTrace(); // Imprimir la traza de la excepción
                }
            }
        });
    }
}
