package Dao;

import Conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PagoDAO {

    public List<String> listarOrdenesPendientes() throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT id_orden, total FROM orden_trabajo WHERE UPPER(estado) = 'PENDIENTE'";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add("Orden #" + rs.getInt("id_orden") + " - Total: $" + rs.getDouble("total"));
            }
        }
        return lista;
    }

    public boolean registrarPago(int idOrden, String metodoPago, double monto) throws SQLException {
        String sqlPago = "INSERT INTO pago (id_pago, id_orden, monto, fecha) VALUES (?, ?, ?, SYSDATE)";
 
        String sqlUpdateOrden = "UPDATE orden_trabajo SET estado = 'FINALIZADA' WHERE id_orden = ?";

        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);
            try {
             
                int nuevoIdPago = 1;
                String sqlMaxId = "SELECT NVL(MAX(id_pago), 0) + 1 FROM pago";
                try (Statement st = con.createStatement();
                     ResultSet rs = st.executeQuery(sqlMaxId)) {
                    if (rs.next()) {
                        nuevoIdPago = rs.getInt(1);
                    }
                }

             
                try (PreparedStatement psPago = con.prepareStatement(sqlPago)) {
                    psPago.setInt(1, nuevoIdPago);
                    psPago.setInt(2, idOrden);
                    psPago.setDouble(3, monto);
                    psPago.executeUpdate();
                }

           
                try (PreparedStatement psOrden = con.prepareStatement(sqlUpdateOrden)) {
                    psOrden.setInt(1, idOrden);
                    psOrden.executeUpdate();
                }

                con.commit();
                return true;
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
    
}