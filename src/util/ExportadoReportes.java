
package util;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportadoReportes {
    
    public static void exportarTablaAHTML(JTable tabla, String titulo, File archivo) {
        try (FileWriter out = new FileWriter(archivo)) {
            TableModel model = tabla.getModel();
            out.write("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
            out.write("<title>" + titulo + "</title>");
            out.write("<style>");
            out.write("body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f6f9; }");
            out.write("h1 { color: #2c3e50; text-align: center; }");
            out.write("table { width: 80%; margin: 20px auto; border-collapse: collapse; background: white; box-shadow: 0 1px 3px rgba(0,0,0,0.2); }");
            out.write("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.write("th { background-color: #34495e; color: white; }");
            out.write("tr:hover { background-color: #f1f1f1; }");
            out.write("</style></head><body>");

            out.write("<h1>" + titulo + "</h1>");
            out.write("<table><thead><tr>");
            
            for (int i = 0; i < model.getColumnCount(); i++) {
                out.write("<th>" + model.getColumnName(i) + "</th>");
            }
            out.write("</tr></thead><tbody>");

            for (int i = 0; i < model.getRowCount(); i++) {
                out.write("<tr>");
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object val = model.getValueAt(i, j);
                    out.write("<td>" + (val != null ? val.toString() : "") + "</td>");
                }
                out.write("</tr>");
            }

            out.write("</tbody></table></body></html>");
            JOptionPane.showMessageDialog(null, "¡Reporte HTML generado con éxito!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar HTML: " + e.getMessage());
        }
    }

    public static void exportarTablaAExcel(JTable tabla, File archivo) {
        try (FileWriter out = new FileWriter(archivo)) {
            TableModel model = tabla.getModel();
            
            for (int i = 0; i < model.getColumnCount(); i++) {
                out.write(model.getColumnName(i) + (i == model.getColumnCount() - 1 ? "" : ","));
            }
            out.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object val = model.getValueAt(i, j);
                    out.write((val != null ? val.toString() : "") + (j == model.getColumnCount() - 1 ? "" : ","));
                }
                out.write("\n");
            }
            JOptionPane.showMessageDialog(null, "¡Reporte exportado correctamente a Excel!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar a Excel: " + e.getMessage());
        }
    }
    
    
}
