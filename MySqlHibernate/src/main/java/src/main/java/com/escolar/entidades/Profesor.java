package com.escolar.entidades;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profesores")
public class Profesor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Integer idProfesor;
    
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    
    @Column(name = "apellidos", nullable = false, length = 45)
    private String apellidos;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "antiguedad")
    private Integer antiguedad;
    
    // Relación uno a muchos con Matricula
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();
    
    // Constructores
    public Profesor() {
    }
    
    public Profesor(String nombre, String apellidos, LocalDate fechaNacimiento, Integer antiguedad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.antiguedad = antiguedad;
    }
    
    // Getters y Setters
    public Integer getIdProfesor() {
        return idProfesor;
    }
    
    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public Integer getAntiguedad() {
        return antiguedad;
    }
    
    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }
    
    public List<Matricula> getMatriculas() {
        return matriculas;
    }
    
    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    
    // Métodos auxiliares para gestionar relaciones
    public void addMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setProfesor(this);
    }
    
    public void removeMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setProfesor(null);
    }
    
    @Override
    public String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", antiguedad=" + antiguedad +
                '}';
    }
}