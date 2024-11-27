package Funcionalidad;

public class Producto {
    private int id; // Identificador único del producto
    private String nombre; // Nombre del producto
    private double precio; // Precio unitario del producto
    private int stock; // Cantidad en stock
    private String proveedor; // Nombre del proveedor del producto
    private String codigo; // Código único del producto
    private String categoria; // Categoría del producto

    // Constructor completo
    public Producto(int id, String nombre, double precio, int stock, String proveedor, String codigo, String categoria) {
        this.id = id;
        this.nombre = nombre;
        setPrecio(precio); // Validación de precio
        setStock(stock);   // Validación de stock
        this.proveedor = proveedor;
        this.codigo = codigo;
        this.categoria = categoria;
    }

    // Constructor simplificado (ID y código generados automáticamente)
    public Producto(String nombre, double precio, int stock, String proveedor, String categoria) {
        this.id = (int) (Math.random() * 1000); // Genera un ID único
        this.codigo = "PRD" + this.id;         // Genera un código basado en el ID
        this.nombre = nombre;
        setPrecio(precio); // Validación de precio
        setStock(stock);   // Validación de stock
        this.proveedor = proveedor;
        this.categoria = categoria;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Método para representar el producto como una cadena legible
    @Override
    public String toString() {
        return "Producto {" +
                "ID: " + id +
                ", Código: '" + codigo + '\'' +
                ", Nombre: '" + nombre + '\'' +
                ", Precio: " + precio +
                ", Stock: " + stock +
                ", Proveedor: '" + proveedor + '\'' +
                ", Categoría: '" + categoria + '\'' +
                '}';
    }
}
