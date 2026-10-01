package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author keneth-gonzalez
 */
public class Cliente extends Persona {
private int idCliente;
    private String direccion;

    // Constructor vacío (necesario para new Cliente())
    public Cliente() {
        super();
    }

    public Cliente(int idCliente, String nombre, String telefono, String correo, String direccion) {
        super(nombre, telefono, correo);
        this.idCliente = idCliente;
        this.direccion = direccion;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

}
