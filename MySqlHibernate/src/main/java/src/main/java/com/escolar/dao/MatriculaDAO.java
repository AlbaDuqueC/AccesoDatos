package com.escolar.dao;

import com.escolar.entidades.Matricula;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.escolar.util.HibernateUtil;

import java.util.List;

public class MatriculaDAO extends GenericDAO<Matricula> {
    
    public MatriculaDAO() {
        super(Matricula.class);
    }
    
    /**
     * Busca matrículas por asignatura (exacto)
     */
    public List<Matricula> buscarPorAsignatura(String asignatura) {
        String hql = "FROM Matricula WHERE asignatura = ?0";
        return ejecutarConsulta(hql, asignatura);
    }
    
    /**
     * Busca matrículas por curso (mayor, menor o igual)
     */
    public List<Matricula> buscarPorCurso(Integer curso, String operador) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Matricula WHERE curso " + operador + " :curso";
            Query<Matricula> query = session.createQuery(hql, Matricula.class);
            query.setParameter("curso", curso);
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar por curso: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca matrículas por alumno
     */
    public List<Matricula> buscarPorAlumno(Integer idAlumno) {
        String hql = "FROM Matricula WHERE alumno.idAlumnado = ?0";
        return ejecutarConsulta(hql, idAlumno);
    }
    
    /**
     * Busca matrículas por profesor
     */
    public List<Matricula> buscarPorProfesor(Integer idProfesor) {
        String hql = "FROM Matricula WHERE profesor.idProfesor = ?0";
        return ejecutarConsulta(hql, idProfesor);
    }
}