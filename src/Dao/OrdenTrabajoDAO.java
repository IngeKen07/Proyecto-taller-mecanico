package Dao;

import Conexion.ConexionBD;
import modelo.OrdenTrabajo;

import java.sql.*;

public class OrdenTrabajoDAO {

    public int registrarOrden(OrdenTrabajo ot) throws SQLException {
       
        int nuevoId = 1;
        String sqlMaxId = "SELECT NVL(MAX(id_orden), 0) + 1 FROM orden_trabajo";
        
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sqlMaxId)) {
            if (rs.next()) {
                nuevoId = rs.getInt(1);
            }
        }

       
        String sql = "INSERT INTO orden_trabajo ("
                   + "id_orden, placa_vehiculo, id_mecanico, problema_reportado, estado, "
                   + "fecha_creacion, total_mano_obra, total_repuestos, total) "
                   + "VALUES (?, ?, ?, ?, 'PENDIENTE', SYSDATE, 0, 0, 0)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, nuevoId);
            ps.setString(2, ot.getPlaca());
            ps.setInt(3, ot.getIdMecanico());
            ps.setString(4, "Mantenimiento general");

            ps.executeUpdate();
            return nuevoId;
        }
    }

    public boolean agregarServicioAOrden(int idOrden, int idServicio, double precio) throws SQLException {
        
        int nuevoIdDetalle = 1;
        String sqlMaxId = "SELECT NVL(MAX(id_detalle), 0) + 1 FROM detalle_servicio";
        
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sqlMaxId)) {
            if (rs.next()) {
                nuevoIdDetalle = rs.getInt(1);
            }
        }

    
        String sql = "INSERT INTO detalle_servicio (id_detalle, id_orden, id_servicio, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoIdDetalle);
            ps.setInt(2, idOrden);
            ps.setInt(3, idServicio);
            ps.setDouble(4, precio);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean agregarRepuestoAOrden(int idOrden, int idRepuesto, int cantidad, double precio) throws SQLException {
        try (Connection con = ConexionBD.getConexion()) {
       
            String checkStock = "SELECT NVL(cantidad_disponible, 0) AS stock_disp FROM repuesto WHERE id_repuesto = ?";
            try (PreparedStatement psStock = con.prepareStatement(checkStock)) {
                psStock.setInt(1, idRepuesto);
                try (ResultSet rs = psStock.executeQuery()) {
                    if (rs.next()) {
                        int stockActual = rs.getInt("stock_disp");
                        if (stockActual < cantidad) {
                            throw new SQLException("Stock insuficiente. Stock disponible: " + stockActual);
                        }
                    } else {
                        throw new SQLException("El repuesto con ID " + idRepuesto + " no existe.");
                    }
                }
            }

         
            int nuevoIdDetalle = 1;
            String sqlMaxId = "SELECT NVL(MAX(id_detalle), 0) + 1 FROM detalle_repuesto";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sqlMaxId)) {
                if (rs.next()) {
                    nuevoIdDetalle = rs.getInt(1);
                }
            }

           
            double subtotal = cantidad * precio;
            String sqlDetail = "INSERT INTO detalle_repuesto (id_detalle, id_orden, id_repuesto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psDetail = con.prepareStatement(sqlDetail)) {
                psDetail.setInt(1, nuevoIdDetalle);
                psDetail.setInt(2, idOrden);
                psDetail.setInt(3, idRepuesto);
                psDetail.setInt(4, cantidad);
                psDetail.setDouble(5, precio);
                psDetail.setDouble(6, subtotal);
                psDetail.executeUpdate();
            }

         
            String sqlUpdateStock = "UPDATE repuesto SET cantidad_disponible = cantidad_disponible - ?, stock = NVL(stock, cantidad_disponible) - ? WHERE id_repuesto = ?";
            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateStock)) {
                psUpdate.setInt(1, cantidad);
                psUpdate.setInt(2, cantidad);
                psUpdate.setInt(3, idRepuesto);
                psUpdate.executeUpdate();
            }
        }
        return true;
    }

    public void recalcularTotalOrden(int idOrden) throws SQLException {
        String sqlTotalServicios = "SELECT NVL(SUM(subtotal), 0) FROM detalle_servicio WHERE id_orden = ?";
        String sqlTotalRepuestos = "SELECT NVL(SUM(subtotal), 0) FROM detalle_repuesto WHERE id_orden = ?";

        double totalServicios = 0;
        double totalRepuestos = 0;

        try (Connection con = ConexionBD.getConexion()) {
            try (PreparedStatement ps1 = con.prepareStatement(sqlTotalServicios)) {
                ps1.setInt(1, idOrden);
                try (ResultSet rs1 = ps1.executeQuery()) {
                    if (rs1.next()) totalServicios = rs1.getDouble(1);
                }
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlTotalRepuestos)) {
                ps2.setInt(1, idOrden);
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) totalRepuestos = rs2.getDouble(1);
                }
            }

            double totalGeneral = totalServicios + totalRepuestos;
            String sqlUpdate = "UPDATE orden_trabajo SET total_mano_obra = ?, total_repuestos = ?, total = ? WHERE id_orden = ?";
            try (PreparedStatement psUp = con.prepareStatement(sqlUpdate)) {
                psUp.setDouble(1, totalServicios);
                psUp.setDouble(2, totalRepuestos);
                psUp.setDouble(3, totalGeneral);
                psUp.setInt(4, idOrden);
                psUp.executeUpdate();
            }
        }
    }
}