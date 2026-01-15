package com.escolar.entidades;

import javax.persistence.*;

@Entity
@Table(name = "matriculas")
public class Matricula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer idMatricula;
    
    @Column(name = "asignatura", nullable = false, length = 45)
    private String asignatura;
    
    @Column(name = "curso")
    private Integer curso;
    
    // Relación muchos a uno con Profesor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_profesorado", nullable = false)
    private Profesor profesor;
    
    // Relación muchos a uno con Alumno
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_alumnado", nullable = false)
    private Alumno alumno;
    
    // Constructores
    public Matricula() {
    }
    
    public Matricula(String asignatura, Integer curso) {
        this.asignatura = asignatura;
        this.curso = curso;
    }
    
    public Matricula(String asignatura, Integer curso, Profesor profesor, Alumno alumno) {
        this.asignatura = asignatura;
        this.curso = curso;
        this.profesor = profesor;
        this.alumno = alumno;
    }
    
    // Getters y Setters
    public Integer getIdMatricula() {
        return idMatricula;
    }
    
    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }
    
    public String getAsignatura() {
        return asignatura;
    }
    
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
    
    public Integer getCurso() {
        return curso;
    }
    
    public void setCurso(Integer curso) {
        this.curso = curso;
    }
    
    public Profesor getProfesor() {
        return profesor;
    }
    
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    
    public Alumno getAlumno() {
        return alumno;
    }
    
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    
    @Override
    public String toString() {
        return "Matricula{" +
                "idMatricula=" + idMatricula +
                ", asignatura='" + asignatura + '\'' +
                ", curso=" + curso +
                ", profesor=" + (profesor != null ? profesor.getNombre() + " " + profesor.getApellidos() : "null") +
                ", alumno=" + (alumno != null ? alumno.getNombre() + " " + alumno.getApellidos() : "null") +
                '}';
    }
}