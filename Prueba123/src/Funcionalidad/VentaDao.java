package Funcionalidad;

import java.sql.SQLException;
import java.util.List;

public interface VentaDao {
    void registrarVenta(Venta venta) throws SQLException;
    void eliminarVenta(int codigoProducto) throws SQLException;
    List<Venta> getAllVentas() throws SQLException;
    List<Venta> obtenerVentas() throws SQLException;  // Método que falta
	boolean insertar(Venta nuevaVenta);
}
