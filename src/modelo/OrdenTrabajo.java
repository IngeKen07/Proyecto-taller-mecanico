package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Date;
/**
 *
 * @author keneth-gonzalez
 */
public class OrdenTrabajo {
 private int idOrden;
    private int idCliente;
    private String placa;
    private int idMecanico;
    private Date fecha;
    private String estado;
    private double total;

    // Constructor vacío (Soluciona el error en FrmPrincipal)
    public OrdenTrabajo() {
    }

    // Constructor completo
    public OrdenTrabajo(int idOrden, int idCliente, String placa, int idMecanico, Date fecha, String estado, double total) {
        this.idOrden = idOrden;
        this.idCliente = idCliente;
        this.placa = placa;
        this.idMecanico = idMecanico;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    // Getters y Setters
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public int getIdMecanico() { return idMecanico; }
    public void setIdMecanico(int idMecanico) { this.idMecanico = idMecanico; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    
    
}
