package Funcionalidad;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Crear un cliente
        Cliente cliente = new Cliente("001-1234567-8", "Juan Pérez", "809-123-4567", "Calle Principal 123");
        System.out.println(cliente);

        // Crear un producto
        Producto producto = new Producto("Laptop", 35000.00, 20);
        System.out.println(producto);

        // Crear una venta
        Venta venta = new Venta(cliente, producto, 2, new Date());
        System.out.println(venta);

        // Verificar total de la venta
        System.out.println("Total de la venta: " + venta.getTotal());
    }
}


