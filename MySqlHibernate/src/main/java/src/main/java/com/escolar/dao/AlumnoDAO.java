package com.escolar.dao;

import com.escolar.entidades.Alumno;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.escolar.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class AlumnoDAO extends GenericDAO<Alumno> {
    
    public AlumnoDAO() {
        super(Alumno.class);
    }
    
    /**
     * Busca alumnos por nombre (exacto)
     */
    public List<Alumno> buscarPorNombre(String nombre) {
        String hql = "FROM Alumno WHERE nombre = ?0";
        return ejecutarConsulta(hql, nombre);
    }
    
    /**
     * Busca alumnos por apellidos (LIKE)
     */
    public List<Alumno> buscarPorApellidos(String apellidos) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Alumno WHERE apellidos LIKE :apellidos";
            Query<Alumno> query = session.createQuery(hql, Alumno.class);
            query.setParameter("apellidos", "%" + apellidos + "%");
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar por apellidos: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca alumnos por fecha de nacimiento (mayor o menor)
     */
    public List<Alumno> buscarPorFechaNacimiento(LocalDate fecha, String operador) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Alumno WHERE fechaNacimiento " + operador + " :fecha";
            Query<Alumno> query = session.createQuery(hql, Alumno.class);
            query.setParameter("fecha", fecha);
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar por fecha: " + e.getMessage());
            return null;
        }
    }
}