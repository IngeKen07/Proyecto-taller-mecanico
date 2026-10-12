/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Conexion.ConexionBD;
import modelo.Repuesto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author keneth-gonzalez
 */
public class RepuestoDAO {
    
   public boolean existeRepuesto(int idRepuesto) throws SQLException {
        String sql = "SELECT COUNT(*) FROM repuesto WHERE id_repuesto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRepuesto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean registrar(Repuesto r) throws SQLException {
        if (existeRepuesto(r.getIdRepuesto())) {
            throw new SQLException("El ID del repuesto ya existe.");
        }
        String sql = "INSERT INTO repuesto (id_repuesto, nombre, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getIdRepuesto());
            ps.setString(2, r.getNombre());
            ps.setDouble(3, r.getPrecio());
            ps.setInt(4, r.getStock());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Repuesto r) throws SQLException {
        String sql = "UPDATE repuesto SET nombre=?, precio=?, stock=? WHERE id_repuesto=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setDouble(2, r.getPrecio());
            ps.setInt(3, r.getStock());
            ps.setInt(4, r.getIdRepuesto());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idRepuesto) throws SQLException {
        String sql = "DELETE FROM repuesto WHERE id_repuesto=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRepuesto);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Repuesto> listar() throws SQLException {
        List<Repuesto> lista = new ArrayList<>();
        String sql = "SELECT id_repuesto, nombre, precio, stock FROM repuesto";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuesto(rs.getInt("id_repuesto"));
                r.setNombre(rs.getString("nombre"));
                r.setPrecio(rs.getDouble("precio"));
                r.setStock(rs.getInt("stock"));
                lista.add(r);
            }
        }
        return lista;
    }
    
}
