
package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;

public abstract class GeneradorPDF {

    // Información de la empresa
    private String nombreEmpresa = "Nombre de la Empresa";
    private String rnc = "RNC 123456789";
    private String telefono = "Tel: 809-123-4567";
    private String direccion = "Dirección: Calle 123, Ciudad, País";
    private String rutaLogo = "C:\\Users\\danny_noso1ht\\Downloads\\Logo de D'guerrero (1).png"; // Ruta del logo

    // Método abstracto para obtener el modelo de la tabla
    protected abstract TableModel getModeloTabla();

    // Método para generar el PDF
    public void generarPDF(String tituloPDF, String rutaPDF) {
        try {
            // Crear el objeto Document
            Document document = new Document();
            // Crear el escritor que apunta al archivo PDF
            PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));
            // Abrir el documento
            document.open();

            // Cargar y agregar el logo
            Image logo = Image.getInstance(rutaLogo);
            logo.scaleToFit(100, 100); // Escalar la imagen al tamaño adecuado (ajústalo a tu gusto)
            
            // Establecer la posición de la imagen en la esquina derecha
            logo.setAbsolutePosition(document.getPageSize().getWidth() - logo.getScaledWidth() - 20, document.getPageSize().getHeight() - logo.getScaledHeight() - 20);
            document.add(logo);

            // Espacio debajo del logo
            document.add(new Paragraph("\n"));

            // Agregar la información de la empresa
            Font fontEmpresa = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph(nombreEmpresa, fontEmpresa));
            document.add(new Paragraph(rnc, fontEmpresa));
            document.add(new Paragraph(telefono, fontEmpresa));
            document.add(new Paragraph(direccion, fontEmpresa));

            // Espacio debajo de la información de la empresa
            document.add(new Paragraph("\n"));

            // Agregar un título al documento
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph(tituloPDF, fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Espacio debajo del título
            document.add(new Paragraph("\n"));

            // Variable para acumular el total de todas las ventas
            double sumaTotal = 0.0;

            // Obtener el modelo de la tabla
            TableModel modeloTabla = getModeloTabla();

            // Iterar por las filas de la tabla y agregar cada venta al PDF
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                Object codigoProducto = modeloTabla.getValueAt(i, 0); // Código del Producto
                Object nombreProducto = modeloTabla.getValueAt(i, 1); // Nombre del Producto
                Object categoriaProducto = modeloTabla.getValueAt(i, 2); // Categoría del Producto
                Object nombreCliente = modeloTabla.getValueAt(i, 3); // Nombre del Cliente
                Object cantidad = modeloTabla.getValueAt(i, 4); // Cantidad
                Object precio = modeloTabla.getValueAt(i, 5); // Precio
                Object total = modeloTabla.getValueAt(i, 6); // Total

                // Convertir el total a un número para acumular
                double totalVenta = total != null ? Double.parseDouble(total.toString()) : 0.0;
                sumaTotal += totalVenta;

                // Crear una línea para cada venta
                String lineaVenta = String.format(
                    "Código: %s | Producto: %s | Categoría: %s | Cliente: %s | Cantidad: %s | Precio: %s | Total: %.2f",
                    codigoProducto != null ? codigoProducto.toString() : "",
                    nombreProducto != null ? nombreProducto.toString() : "",
                    categoriaProducto != null ? categoriaProducto.toString() : "",
                    nombreCliente != null ? nombreCliente.toString() : "",
                    cantidad != null ? cantidad.toString() : "",
                    precio != null ? precio.toString() : "",
                    totalVenta
                );

                // Agregar la línea como un párrafo al documento
                document.add(new Paragraph(lineaVenta));
            }

            // Espacio antes del total general
            document.add(new Paragraph("\n"));

            // Agregar el total general al PDF
            Font fontTotal = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            String textoTotal = String.format("TOTAL GENERAL DE VENTAS: $%.2f", sumaTotal);
            Paragraph totalGeneral = new Paragraph(textoTotal, fontTotal);
            totalGeneral.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalGeneral);

            // Cerrar el documento
            document.close();

            // Mostrar un mensaje de éxito
            JOptionPane.showMessageDialog(null, "PDF generado correctamente en: " + rutaPDF, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Manejo de errores
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
