package Funcionalidad;

public class Proveedor {
    private String rnc;
    private String nombre;
    private String telefono;
    private String direccion;
    private String razonSocial;

    public Proveedor(String rnc, String nombre, String telefono, String direccion, String razonSocial) {
        this.rnc = rnc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.razonSocial = razonSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRNC() {
        return rnc;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    // Agregar setters si es necesario
}
