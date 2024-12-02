package Funcionalidad;

public class Venta {
    private int codigoProducto;
    private String producto;
    private String categoria;
    private int clienteId;
    private int cantidad;
    private double precio;
    private double total;

    // Constructor
    public Venta(int codigoProducto, String producto, String categoria, int clienteId, int cantidad, double precio) {
        this.codigoProducto = codigoProducto;
        this.producto = producto;
        this.categoria = categoria;
        this.clienteId = clienteId;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = calcularTotal();  // Método para calcular el total
    }

    // Método para calcular el total
    public double calcularTotal() {
        return cantidad * precio;
    }

    // Getters y Setters
    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
