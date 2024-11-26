package Funcionalidad;

public class Cliente {
    private String cedulaRnc;
    private String nombre;
    private String telefono;
    private String direccion;

    // Constructor
    public Cliente(String cedulaRnc, String nombre, String telefono, String direccion) {
        this.cedulaRnc = cedulaRnc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters y setters
    public String getCedulaRnc() {
        return cedulaRnc;
    }

    public void setCedulaRnc(String cedulaRnc) {
        this.cedulaRnc = cedulaRnc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cedulaRnc='" + cedulaRnc + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}

