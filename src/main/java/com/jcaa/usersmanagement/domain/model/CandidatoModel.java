package com.jcaa.usersmanagement.domain.model;
import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;
import java.time.LocalDate;


public class CandidatoModel {
    private final Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String fotografia;
    private LocalDate fechaNacimiento;
    private CandidatoTipo tipo;
    private String nombreTutor;

    public CandidatoModel (Integer id, String nombre, String direccion, String telefono, String fotografia,
                           LocalDate fechaNacimiento, CandidatoTipo tipo, String nombreTutor){
        this.id=id;
        this.nombre=nombre;
        this.direccion=direccion;
        this.telefono=telefono;
        this.fotografia=fotografia;
        this.fechaNacimiento=fechaNacimiento;
        this.tipo=tipo;
        this.nombreTutor=nombreTutor;

    }
    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getFotografia() { return fotografia; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public CandidatoTipo getTipo() { return tipo; }
    public String getNombreTutor() { return nombreTutor; }

    public void setNombre(String nombre) {
        this.nombre = nombre;}

    public void setDireccion(String direccion) {
        this.direccion = direccion;}

    public void setTelefono(String telefono) {
        this.telefono = telefono;}

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;}

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;}

    public void setTipo(CandidatoTipo tipo) {
        this.tipo = tipo;}

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;}

}
