package util;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class GeneradorPDFVenta extends GeneradorPDF {
    private JTable tablaVentas;

    public GeneradorPDFVenta(JTable tablaVentas) {
        this.tablaVentas = tablaVentas;
    }

    @Override
    protected TableModel getModeloTabla() {
        return tablaVentas.getModel(); // Retorna el modelo de la tabla
    }
}

