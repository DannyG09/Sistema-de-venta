package Funcionalidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import guiapp.Conexion_bdd;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public boolean guardarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = Conexion_bdd.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            boolean resultado = pstmt.executeUpdate() > 0;
            pstmt.close();
            return resultado;
        } catch (SQLException e) {
            System.err.println("Error al guardar el producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try {
            Connection conn = Conexion_bdd.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getId());
            boolean resultado = pstmt.executeUpdate() > 0;
            pstmt.close();
            return resultado;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try {
            Connection conn = Conexion_bdd.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            boolean resultado = pstmt.executeUpdate() > 0;
            pstmt.close();
            return resultado;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try {
            Connection conn = Conexion_bdd.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener los productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public Producto obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try {
            Connection conn = Conexion_bdd.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                rs.close();
                pstmt.close();
                return producto;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener el producto por ID: " + e.getMessage());
        }
        return null;
    }
}
