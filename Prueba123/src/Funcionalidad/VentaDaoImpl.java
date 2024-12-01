package Funcionalidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDaoImpl implements VentaDao {
    private Connection connection;

    public VentaDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void registrarVenta(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas (codigo_producto, producto, categoria, cliente_id, cantidad, precio, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, venta.getCodigoProducto());
            statement.setString(2, venta.getProducto());
            statement.setString(3, venta.getCategoria());
            statement.setInt(4, venta.getClienteId());
            statement.setInt(5, venta.getCantidad());
            statement.setDouble(6, venta.getPrecio());
            statement.setDouble(7, venta.getTotal());

            statement.executeUpdate();
        }
    }

    @Override
    public void eliminarVenta(int codigoProducto) throws SQLException {
        String sql = "DELETE FROM ventas WHERE codigo_producto = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codigoProducto);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Venta> getAllVentas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int codigoProducto = resultSet.getInt("codigo_producto");
                String producto = resultSet.getString("producto");
                String categoria = resultSet.getString("categoria");
                int clienteId = resultSet.getInt("cliente_id");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");
                double total = resultSet.getDouble("total");

                Venta venta = new Venta(codigoProducto, producto, categoria, clienteId, cantidad, precio, total);
                ventas.add(venta);
            }
        }

        return ventas;
    }

    @Override
    public List<Venta> obtenerVentas() throws SQLException {  // Implementación del método que falta
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas"; // Aquí puedes agregar más filtros si lo necesitas
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int codigoProducto = resultSet.getInt("codigo_producto");
                String producto = resultSet.getString("producto");
                String categoria = resultSet.getString("categoria");
                int clienteId = resultSet.getInt("cliente_id");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");
                double total = resultSet.getDouble("total");

                Venta venta = new Venta(codigoProducto, producto, categoria, clienteId, cantidad, precio, total);
                ventas.add(venta);
            }
        }

        return ventas;
    }
}
