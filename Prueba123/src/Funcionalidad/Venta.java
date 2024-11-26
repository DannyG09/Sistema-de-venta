package Funcionalidad;

import java.util.Date;

public class Venta {
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private double total;
    private Date fecha;

    // Constructor
    public Venta(Cliente cliente, Producto producto, int cantidad, Date fecha) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = producto.getPrecio() * cantidad;
        this.fecha = fecha;
    }

    // Getters y setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.total = producto.getPrecio() * cantidad; // Recalcular el total
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.total = producto.getPrecio() * cantidad; // Recalcular el total
    }

    public double getTotal() {
        return total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "cliente=" + cliente +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", total=" + total +
                ", fecha=" + fecha +
                '}';
    }
}
