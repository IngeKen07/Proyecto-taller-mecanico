/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Conexion.ConexionBD;
import modelo.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author keneth-gonzalez
 */
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
        String sql = "INSERT INTO servicio (id_servicio, descripcion, precio) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getIdServicio());
            ps.setString(2, s.getNombre());
            ps.setDouble(3, s.getPrecio());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Servicio s) throws SQLException {
        String sql = "UPDATE servicio SET descripcion=?, precio=? WHERE id_servicio=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setDouble(2, s.getPrecio());
            ps.setInt(3, s.getIdServicio());
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
        String sql = "SELECT id_servicio, descripcion, precio FROM servicio";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Servicio s = new Servicio();
                s.setIdServicio(rs.getInt("id_servicio"));
                s.setNombre(rs.getString("descripcion"));
                s.setPrecio(rs.getDouble("precio"));
                lista.add(s);
            }
        }
        return lista;
    }
    
}
