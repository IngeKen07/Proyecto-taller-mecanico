/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author keneth-gonzalez
 */
public class ReporteDAO {
    public double obtenerTotalIngresos() throws SQLException {
        String sql = "SELECT NVL(SUM(monto), 0) FROM pago";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    public List<Object[]> obtenerOrdenesPorMecanico() throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT m.nombre, COUNT(o.id_orden) AS total_ordenes " +
                     "FROM mecanico m " +
                     "LEFT JOIN orden_trabajo o ON m.id_mecanico = o.id_mecanico " +
                     "GROUP BY m.nombre";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{rs.getString("nombre"), rs.getInt("total_ordenes")});
            }
        }
        return lista;
    }

    public List<Object[]> obtenerServiciosMasSolicitados() throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        // Se ajustó s.descripcion por s.nombre y la tabla detalle por orden_servicio
        String sql = "SELECT s.nombre, COUNT(ds.id_servicio) AS cantidad " +
                     "FROM servicio s " +
                     "JOIN orden_servicio ds ON s.id_servicio = ds.id_servicio " +
                     "GROUP BY s.nombre " +
                     "ORDER BY cantidad DESC";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{rs.getString("nombre"), rs.getInt("cantidad")});
            }
        }
        return lista;
    }
}
