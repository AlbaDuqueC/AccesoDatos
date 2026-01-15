package com.escolar.dao;

import com.escolar.entidades.Profesor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.escolar.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class ProfesorDAO extends GenericDAO<Profesor> {
    
    public ProfesorDAO() {
        super(Profesor.class);
    }
    
    /**
     * Busca profesores por nombre (exacto)
     */
    public List<Profesor> buscarPorNombre(String nombre) {
        String hql = "FROM Profesor WHERE nombre = ?0";
        return ejecutarConsulta(hql, nombre);
    }
    
    /**
     * Busca profesores por apellidos (LIKE)
     */
    public List<Profesor> buscarPorApellidos(String apellidos) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Profesor WHERE apellidos LIKE :apellidos";
            Query<Profesor> query = session.createQuery(hql, Profesor.class);
            query.setParameter("apellidos", "%" + apellidos + "%");
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar por apellidos: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca profesores por fecha de nacimiento (mayor o menor)
     */
    public List<Profesor> buscarPorFechaNacimiento(LocalDate fecha, String operador) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Profesor WHERE fechaNacimiento " + operador + " :fecha";
            Query<Profesor> query = session.createQuery(hql, Profesor.class);
            query.setParameter("fecha", fecha);
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar por fecha: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca profesores por antigüedad (mayor o menor)
     */
    public List<Profesor> buscarPorAntiguedad(Integer antiguedad, String operador) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Profesor WHERE antiguedad " + operador + " :antiguedad";
            Query<Profesor> query = session.createQuery(hql, Profesor.class);
            query.setParameter("antiguedad", antiguedad);
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar por antigüedad: " + e.getMessage());
            return null;
        }
    }
}