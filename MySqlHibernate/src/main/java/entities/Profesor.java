package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Profesores")
public class Profesor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfesor")
    private Integer idProfesor;
    
    @Column(name = "Nombre", length = 45)
    private String nombre;
    
    @Column(name = "Apellidos", length = 45)
    private String apellidos;
    
    @Column(name = "FechaNacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "Antiguedad")
    private Integer antiguedad;
    
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();
    
    public Profesor() {}
    
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
    
    @Override
    public String toString() {
        return "ID: " + idProfesor + " | Nombre: " + nombre + " | Apellidos: " + apellidos + 
               " | FechaNacimiento: " + fechaNacimiento + " | Antiguedad: " + antiguedad;
    }
}