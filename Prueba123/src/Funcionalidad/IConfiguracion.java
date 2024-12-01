package Funcionalidad;

public interface IConfiguracion {
    /**
     * Carga los datos de configuración desde la base de datos.
     */
    void cargarDatosConfiguracion();

    /**
     * Guarda los datos de configuración en la base de datos.
     *
     * @param rnc            El RNC de la empresa.
     * @param nombreEmpresa  El nombre de la empresa.
     * @param telefono       El número de teléfono.
     * @param direccion      La dirección de la empresa.
     */
    void guardarConfiguracion(String rnc, String nombreEmpresa, String telefono, String direccion);

    /**
     * Valida que los campos no estén vacíos o nulos.
     *
     * @param rnc            El RNC a validar.
     * @param nombreEmpresa  El nombre de la empresa a validar.
     * @param telefono       El teléfono a validar.
     * @param direccion      La dirección a validar.
     * @return               True si todos los campos son válidos; False en caso contrario.
     */
    boolean validarCampos(String rnc, String nombreEmpresa, String telefono, String direccion);
}
