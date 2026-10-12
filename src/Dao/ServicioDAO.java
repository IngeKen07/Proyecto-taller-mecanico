package Dao;
import Conexion.ConexionBD;
import model.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author gustavo-fuentes
 */
public class ServicioDAO {
    public boolean existeServicio(int idServicio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM servicio WHERE id_servicio = ?";
        try (Connection con = ConexionBD.getConexion());
            PreparedStatemen ps = con.prepareStatement(sql)) {
           ps.setInt(1, idServicio);
            try (ResultSet rs = ps.executeQuery() {
                if (rs.next()) return rs.getInt(1) > 0;
            }
    }
    return false;

    public boolean registrar(Servicio s) throws SQLException {
        if (existeServicio(s.getIdServicio())) {
            throw new SQLException("EL ID del servicio ya existe."):
        }
        String sql = "INSERT INTO servicio (id_servicio, descripcion, precio) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql) {
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


    
   /** private Map<String, Servicio> catalogo = new HashMap<>();

    public void agregarServicio(Servicio s) {
        catalogo.put(s.getId(), s);
    }

    public Servicio buscarPorId(String id) {
        return catalogo.get(id);
    }

    public List<Servicio> obtenerServicios() {
        return new ArrayList<>(catalogo.values());
    }

    
    public void exportarCSV(String rutaArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Servicio s : catalogo.values()) {
                bw.write(s.toCSV());
                bw.newLine();
            }
        }
    }

    
    public void importarCSV(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String id = datos[0].trim();
                    String descripcion = datos[1].trim();
                    double costo = Double.parseDouble(datos[2].trim());

                    if (costo >= 0) {
                        agregarServicio(new Servicio(id, descripcion, costo));
                    }
                }
            }
        }
    }
}
*/
