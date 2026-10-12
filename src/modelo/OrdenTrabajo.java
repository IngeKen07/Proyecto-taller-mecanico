package modelo;

import java.util.Date;

public class OrdenTrabajo {
 private int idOrden;
    private int idCliente;
    private String placa;
    private int idMecanico;
    private Date fecha;
    private String estado;
    private double total;


    public OrdenTrabajo() {
    }


    public OrdenTrabajo(int idOrden, int idCliente, String placa, int idMecanico, Date fecha, String estado, double total) {
        this.idOrden = idOrden;
        this.idCliente = idCliente;
        this.placa = placa;
        this.idMecanico = idMecanico;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

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