package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author keneth-gonzalez
 */
public class DetalleServicio {
    private int idDetalle;
    private int idOrden;
    private Servicio servicio;
    private double subtotal;

    public DetalleServicio(int idDetalle, int idOrden, Servicio servicio, double subtotal) {
        this.idDetalle = idDetalle;
        this.idOrden = idOrden;
        this.servicio = servicio;
        this.subtotal = subtotal;
    }
    
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
    

