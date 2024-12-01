package Funcionalidad;

import java.util.List;

public interface ProveedorDAO {
    boolean guardarProveedor(Proveedor proveedor);
    boolean actualizarProveedor(Proveedor proveedor);
    boolean eliminarProveedor(int id);
    List<Proveedor> obtenerTodosLosProveedores();
}

