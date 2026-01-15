package com.escolar.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.escolar.util.HibernateUtil;

import java.util.List;

/**
 * Clase genérica para operaciones CRUD con Hibernate
 */
public abstract class GenericDAO<T> {
    
    private Class<T> entityClass;
    
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    /**
     * Inserta una entidad en la base de datos
     */
    public void insertar(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            System.out.println("✓ Registro insertado correctamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("✗ Error al insertar: " + e.getMessage());
            throw new RuntimeException("No se pudo insertar el registro", e);
        }
    }
    
    /**
     * Obtiene una entidad por su ID
     */
    public T obtenerPorId(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(entityClass, id);
        } catch (Exception e) {
            System.err.println("✗ Error al obtener por ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lista todas las entidades
     */
    public List<T> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (Exception e) {
            System.err.println("✗ Error al listar: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Actualiza una entidad
     */
    public void actualizar(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            System.out.println("✓ Registro actualizado correctamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("✗ Error al actualizar: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el registro", e);
        }
    }
    
    /**
     * Elimina una entidad por su ID
     */
    public void eliminar(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.delete(entity);
                transaction.commit();
                System.out.println("✓ Registro eliminado correctamente");
            } else {
                System.out.println("⚠ No se encontró el registro con ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("✗ Error al eliminar: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el registro. Verifique si tiene registros relacionados.", e);
        }
    }
    
    /**
     * Ejecuta una consulta HQL personalizada
     */
    protected List<T> ejecutarConsulta(String hql, Object... parametros) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<T> query = session.createQuery(hql, entityClass);
            for (int i = 0; i < parametros.length; i++) {
                query.setParameter(i, parametros[i]);
            }
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al ejecutar consulta: " + e.getMessage());
            return null;
        }
    }
}