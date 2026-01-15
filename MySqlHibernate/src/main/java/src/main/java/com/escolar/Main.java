package com.escolar;

import com.escolar.menu.MenuPrincipal;
import com.escolar.util.HibernateUtil;

/**
 * Clase principal para ejecutar la aplicación
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("  SISTEMA DE GESTIÓN ESCOLAR CON HIBERNATE         ");
        System.out.println("  Tarea 3.1 - CRUD Completo                        ");
        System.out.println();
        
        try {
            // Inicializar Hibernate (crea las tablas automáticamente)
            HibernateUtil.getSessionFactory();
            
            System.out.println("✓ Conexión establecida correctamente");
            System.out.println("✓ Tablas creadas/verificadas automáticamente");
            System.out.println();
            
            // Mostrar menú principal
            MenuPrincipal.mostrarMenuPrincipal();
            
        } catch (Exception e) {
            System.err.println("✗ Error fatal al iniciar la aplicación:");
            System.err.println("  " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n¡Hasta pronto!");
        }
    }
}