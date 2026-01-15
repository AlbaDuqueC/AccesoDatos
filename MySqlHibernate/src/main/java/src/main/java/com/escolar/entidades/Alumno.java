package com.escolar.entidades;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumnado")
    private Integer idAlumnado;
    
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    
    @Column(name = "apellidos", nullable = false, length = 45)
    private String apellidos;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    // Relación uno a muchos con Matricula
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();
    
    // Constructores
    public Alumno() {
    }
    
    public Alumno(String nombre, String apellidos, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    // Getters y Setters
    public Integer getIdAlumnado() {
        return idAlumnado;
    }
    
    public void setIdAlumnado(Integer idAlumnado) {
        this.idAlumnado = idAlumnado;
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
    
    public List<Matricula> getMatriculas() {
        return matriculas;
    }
    
    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    
    // Métodos auxiliares para gestionar relaciones
    public void addMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setAlumno(this);
    }
    
    public void removeMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setAlumno(null);
    }
    
    @Override
    public String toString() {
        return "Alumno{" +
                "idAlumnado=" + idAlumnado +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}