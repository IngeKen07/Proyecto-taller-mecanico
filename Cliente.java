package modelo;


public class Cliente extends Persona {
private int idCliente;
    private String direccion;

 
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