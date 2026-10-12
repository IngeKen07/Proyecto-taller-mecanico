package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author keneth-gonzalez
 */
public class Mecanico extends Persona {
    private int idMecanico;
    private String especialidad;

    public Mecanico() {
        super();
    }

    public Mecanico(int idMecanico, String nombre, String telefono, String correo, String especialidad) {
        super(nombre, telefono, correo);
        this.idMecanico = idMecanico;
        this.especialidad = especialidad;
    }

    public int getIdMecanico() { return idMecanico; }
    public void setIdMecanico(int idMecanico) { this.idMecanico = idMecanico; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
}
