
package Dao;
import Conexion.ConexionBD;
import modelo.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {
  public boolean existeServicio(int idServicio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM servicio WHERE id_servicio = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean registrar(Servicio s) throws SQLException {
        if (existeServicio(s.getIdServicio())) {
            throw new SQLException("El ID del servicio ya existe.");
        }
      
        String sql = "INSERT INTO servicio (id_servicio, descripcion, precio, costo_mano_obra) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getIdServicio());
            ps.setString(2, s.getNombre());
            ps.setDouble(3, s.getPrecio());
            ps.setDouble(4, s.getPrecio()); 
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Servicio s) throws SQLException {
        String sql = "UPDATE servicio SET descripcion=?, precio=?, costo_mano_obra=? WHERE id_servicio=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setDouble(2, s.getPrecio());
            ps.setDouble(3, s.getPrecio());
            ps.setInt(4, s.getIdServicio());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idServicio) throws SQLException {
        String sql = "DELETE FROM servicio WHERE id_servicio=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Servicio> listar() throws SQLException {
        List<Servicio> lista = new ArrayList<>();
       
        String sql = "SELECT id_servicio, descripcion, NVL(precio, costo_mano_obra) AS precio_final FROM servicio";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Servicio s = new Servicio();
                s.setIdServicio(rs.getInt("id_servicio"));
                s.setNombre(rs.getString("descripcion"));
                s.setPrecio(rs.getDouble("precio_final"));
                lista.add(s);
            }
        }
        return lista;
    }
    
}
