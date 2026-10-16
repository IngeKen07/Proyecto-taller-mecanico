
package Dao;
import Conexion.ConexionBD;
import modelo.Mecanico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MecanicoDAO {
    public boolean existeMecanico(int idMecanico) throws SQLException {
        String sql = "SELECT COUNT(*) FROM mecanico WHERE id_mecanico = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMecanico);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean tieneOrdenesAsignadas(int idMecanico) throws SQLException {
        String sql = "SELECT COUNT(*) FROM orden_trabajo WHERE id_mecanico = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMecanico);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean registrar(Mecanico m) throws SQLException {
        if (existeMecanico(m.getIdMecanico())) {
            throw new SQLException("El ID de mecánico ya se encuentra registrado.");
        }
        String sql = "INSERT INTO mecanico (id_mecanico, nombre, telefono, especialidad) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, m.getIdMecanico());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getTelefono());
            ps.setString(4, m.getEspecialidad());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Mecanico m) throws SQLException {
        String sql = "UPDATE mecanico SET nombre=?, telefono=?, especialidad=? WHERE id_mecanico=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getTelefono());
            ps.setString(3, m.getEspecialidad());
            ps.setInt(4, m.getIdMecanico());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idMecanico) throws SQLException {
        if (tieneOrdenesAsignadas(idMecanico)) {
            throw new SQLException("No se puede eliminar el mecánico porque tiene órdenes de trabajo asignadas.");
        }
        String sql = "DELETE FROM mecanico WHERE id_mecanico=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMecanico);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Mecanico> listar() throws SQLException {
        List<Mecanico> lista = new ArrayList<>();
        String sql = "SELECT id_mecanico, nombre, telefono, especialidad FROM mecanico";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Mecanico m = new Mecanico();
                m.setIdMecanico(rs.getInt("id_mecanico"));
                m.setNombre(rs.getString("nombre"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString("especialidad"));
                lista.add(m);
            }
        }
        return lista;
    }
    
}
