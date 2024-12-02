package Funcionalidad;

import java.util.List;

public interface ProductoDAO {
    boolean guardarProducto(Producto producto);
    boolean actualizarProducto(Producto producto);
    boolean eliminarProducto(int id);
    Producto obtenerProductoPorId(int id);
    List<Producto> obtenerTodosLosProductos();
}

