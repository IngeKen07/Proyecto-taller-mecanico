package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author keneth-gonzalez
 */
public class DetalleRepuesto {
    private int idDetalle;
    private int idOrden;
    private Repuesto repuesto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleRepuesto(int idDetalle, int idOrden, Repuesto repuesto, int cantidad, double precioUnitario, double subtotal) {
        this.idDetalle = idDetalle;
        this.idOrden = idOrden;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
    
    
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }
    public Repuesto getRepuesto() { return repuesto; }
    public void setRepuesto(Repuesto repuesto) { this.repuesto = repuesto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
}
