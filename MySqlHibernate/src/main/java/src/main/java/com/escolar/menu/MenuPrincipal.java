package com.escolar.menu;

import com.escolar.dao.*;
import com.escolar.entidades.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.escolar.util.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

	private static Scanner scanner = new Scanner(System.in);
	private static ProfesorDAO profesorDAO = new ProfesorDAO();
	private static AlumnoDAO alumnoDAO = new AlumnoDAO();
	private static MatriculaDAO matriculaDAO = new MatriculaDAO();

	public static void mostrarMenuPrincipal() {
		int opcion;
		do {
			System.out.println("\n" + "=".repeat(50));
			System.out.println("   SISTEMA DE GESTIÓN ESCOLAR - HIBERNATE");
			System.out.println("=".repeat(50));
			System.out.println("1. Insertar");
			System.out.println("2. Listar");
			System.out.println("3. Modificar");
			System.out.println("4. Borrar");
			System.out.println("5. Eliminar Tablas (DROP)");
			System.out.println("0. Salir");
			System.out.println("=".repeat(50));
			System.out.print("Seleccione una opción: ");

			opcion = leerEntero();

			switch (opcion) {
			case 1:
				menuInsertar();
				break;
			case 2:
				menuListar();
				break;
			case 3:
				menuModificar();
				break;
			case 4:
				menuBorrar();
				break;
			case 5:
				menuEliminarTablas();
				break;
			case 0:
				System.out.println("\n✓ Cerrando la aplicación...");
				HibernateUtil.shutdown();
				break;
			default:
				System.out.println("⚠ Opción no válida");
			}
		} while (opcion != 0);
	}

	// ==================== MENÚ INSERTAR ====================

	private static void menuInsertar() {
		System.out.println("\n--- INSERTAR EN TABLA ---");
		System.out.println("1. Profesor");
		System.out.println("2. Alumno");
		System.out.println("3. Matrícula");
		System.out.println("0. Volver");
		System.out.print("Seleccione tabla: ");

		int opcion = leerEntero();

		switch (opcion) {
		case 1:
			insertarProfesor();
			break;
		case 2:
			insertarAlumno();
			break;
		case 3:
			insertarMatricula();
			break;
		case 0:
			break;
		default:
			System.out.println("⚠ Opción no válida");
		}
	}

	private static void insertarProfesor() {
		try {
			System.out.println("\n--- INSERTAR PROFESOR ---");
			System.out.print("Nombre: ");
			String nombre = scanner.nextLine();

			System.out.print("Apellidos: ");
			String apellidos = scanner.nextLine();

			System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
			String fechaStr = scanner.nextLine();
			LocalDate fechaNacimiento = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			System.out.print("Antigüedad (años): ");
			Integer antiguedad = leerEntero();

			Profesor profesor = new Profesor(nombre, apellidos, fechaNacimiento, antiguedad);
			profesorDAO.insertar(profesor);

		} catch (Exception e) {
			System.err.println("✗ Error al insertar profesor: " + e.getMessage());
		}
	}

	private static void insertarAlumno() {
		try {
			System.out.println("\n--- INSERTAR ALUMNO ---");
			System.out.print("Nombre: ");
			String nombre = scanner.nextLine();

			System.out.print("Apellidos: ");
			String apellidos = scanner.nextLine();

			System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
			String fechaStr = scanner.nextLine();
			LocalDate fechaNacimiento = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			Alumno alumno = new Alumno(nombre, apellidos, fechaNacimiento);
			alumnoDAO.insertar(alumno);

		} catch (Exception e) {
			System.err.println("✗ Error al insertar alumno: " + e.getMessage());
		}
	}

	private static void insertarMatricula() {
		try {
			System.out.println("\n--- INSERTAR MATRÍCULA ---");
			System.out.println("IMPORTANTE: Debe existir al menos un alumno y un profesor");

			// Seleccionar alumno
			System.out.print("\nNombre del alumno: ");
			String nombreAlumno = scanner.nextLine();
			List<Alumno> alumnos = alumnoDAO.buscarPorNombre(nombreAlumno);

			if (alumnos == null || alumnos.isEmpty()) {
				System.out.println("✗ No se encontró ningún alumno con ese nombre.");
				System.out.println("  Debe insertar primero el alumno en la base de datos.");
				return;
			}

			Alumno alumnoSeleccionado = seleccionarAlumno(alumnos);
			if (alumnoSeleccionado == null)
				return;

			// Seleccionar profesor
			System.out.print("\nNombre del profesor: ");
			String nombreProfesor = scanner.nextLine();
			List<Profesor> profesores = profesorDAO.buscarPorNombre(nombreProfesor);

			if (profesores == null || profesores.isEmpty()) {
				System.out.println("✗ No se encontró ningún profesor con ese nombre.");
				System.out.println("  Debe insertar primero el profesor en la base de datos.");
				return;
			}

			Profesor profesorSeleccionado = seleccionarProfesor(profesores);
			if (profesorSeleccionado == null)
				return;

			// Datos de la matrícula
			System.out.print("\nAsignatura: ");
			String asignatura = scanner.nextLine();

			System.out.print("Curso: ");
			Integer curso = leerEntero();

			Matricula matricula = new Matricula(asignatura, curso, profesorSeleccionado, alumnoSeleccionado);
			matriculaDAO.insertar(matricula);

		} catch (Exception e) {
			System.err.println("✗ Error al insertar matrícula: " + e.getMessage());
		}
	}

	// ==================== MENÚ LISTAR ====================

	private static void menuListar() {
		System.out.println("\n--- LISTAR TABLA ---");
		System.out.println("1. Profesores");
		System.out.println("2. Alumnos");
		System.out.println("3. Matrículas");
		System.out.println("4. Todas las tablas");
		System.out.println("0. Volver");
		System.out.print("Seleccione opción: ");

		int opcion = leerEntero();

		switch (opcion) {
		case 1:
			listarProfesores();
			break;
		case 2:
			listarAlumnos();
			break;
		case 3:
			listarMatriculas();
			break;
		case 4:
			listarTodasLasTablas();
			break;
		case 0:
			break;
		default:
			System.out.println("⚠ Opción no válida");
		}
	}

	private static void listarProfesores() {
		System.out.println("\n--- LISTAR PROFESORES ---");
		System.out.println("1. Listar todos");
		System.out.println("2. Filtrar por nombre");
		System.out.println("3. Filtrar por apellidos");
		System.out.println("4. Filtrar por fecha de nacimiento");
		System.out.println("5. Filtrar por antigüedad");
		System.out.print("Seleccione opción: ");

		int opcion = leerEntero();
		List<Profesor> profesores = null;

		switch (opcion) {
		case 1:
			profesores = profesorDAO.listarTodos();
			break;
		case 2:
			System.out.print("Nombre: ");
			String nombre = scanner.nextLine();
			profesores = profesorDAO.buscarPorNombre(nombre);
			break;
		case 3:
			System.out.print("Apellidos: ");
			String apellidos = scanner.nextLine();
			profesores = profesorDAO.buscarPorApellidos(apellidos);
			break;
		case 4:
			System.out.print("Fecha (dd/MM/yyyy): ");
			String fechaStr = scanner.nextLine();
			LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			System.out.print("Operador (< o >): ");
			String operador = scanner.nextLine();
			profesores = profesorDAO.buscarPorFechaNacimiento(fecha, operador);
			break;
		case 5:
			System.out.print("Antigüedad: ");
			Integer antiguedad = leerEntero();
			System.out.print("Operador (< o >): ");
			String op = scanner.nextLine();
			profesores = profesorDAO.buscarPorAntiguedad(antiguedad, op);
			break;
		}

		mostrarListaProfesores(profesores);
	}

	private static void listarAlumnos() {
		System.out.println("\n--- LISTAR ALUMNOS ---");
		System.out.println("1. Listar todos");
		System.out.println("2. Filtrar por nombre");
		System.out.println("3. Filtrar por apellidos");
		System.out.println("4. Filtrar por fecha de nacimiento");
		System.out.print("Seleccione opción: ");

		int opcion = leerEntero();
		List<Alumno> alumnos = null;

		switch (opcion) {
		case 1:
			alumnos = alumnoDAO.listarTodos();
			break;
		case 2:
			System.out.print("Nombre: ");
			String nombre = scanner.nextLine();
			alumnos = alumnoDAO.buscarPorNombre(nombre);
			break;
		case 3:
			System.out.print("Apellidos: ");
			String apellidos = scanner.nextLine();
			alumnos = alumnoDAO.buscarPorApellidos(apellidos);
			break;
		case 4:
			System.out.print("Fecha (dd/MM/yyyy): ");
			String fechaStr = scanner.nextLine();
			LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			System.out.print("Operador (< o >): ");
			String operador = scanner.nextLine();
			alumnos = alumnoDAO.buscarPorFechaNacimiento(fecha, operador);
			break;
		}

		mostrarListaAlumnos(alumnos);
	}

	private static void listarMatriculas() {
		System.out.println("\n--- LISTAR MATRÍCULAS ---");
		System.out.println("1. Listar todas");
		System.out.println("2. Filtrar por asignatura");
		System.out.println("3. Filtrar por curso");
		System.out.print("Seleccione opción: ");

		int opcion = leerEntero();
		List<Matricula> matriculas = null;

		switch (opcion) {
		case 1:
			matriculas = matriculaDAO.listarTodos();
			break;
		case 2:
			System.out.print("Asignatura: ");
			String asignatura = scanner.nextLine();
			matriculas = matriculaDAO.buscarPorAsignatura(asignatura);
			break;
		case 3:
			System.out.print("Curso: ");
			Integer curso = leerEntero();
			System.out.print("Operador (< = >): ");
			String operador = scanner.nextLine();
			matriculas = matriculaDAO.buscarPorCurso(curso, operador);
			break;
		}

		mostrarListaMatriculas(matriculas);
	}

	private static void listarTodasLasTablas() {
		System.out.println("\n" + "=".repeat(60));
		System.out.println("TODAS LAS TABLAS");
		System.out.println("=".repeat(60));

		System.out.println("\n--- PROFESORES ---");
		mostrarListaProfesores(profesorDAO.listarTodos());

		System.out.println("\n--- ALUMNOS ---");
		mostrarListaAlumnos(alumnoDAO.listarTodos());

		System.out.println("\n--- MATRÍCULAS ---");
		mostrarListaMatriculas(matriculaDAO.listarTodos());
	}

	// ==================== MENÚ MODIFICAR ====================

	private static void menuModificar() {
		System.out.println("\n--- MODIFICAR REGISTRO ---");
		System.out.println("1. Profesor");
		System.out.println("2. Alumno");
		System.out.println("3. Matrícula");
		System.out.println("0. Volver");
		System.out.print("Seleccione tabla: ");

		int opcion = leerEntero();

		switch (opcion) {
		case 1:
			modificarProfesor();
			break;
		case 2:
			modificarAlumno();
			break;
		case 3:
			modificarMatricula();
			break;
		case 0:
			break;
		default:
			System.out.println("⚠ Opción no válida");
		}
	}

	private static void modificarProfesor() {
		try {
			System.out.println("\n--- MODIFICAR PROFESOR ---");
			System.out.print("ID del profesor a modificar: ");
			Integer id = leerEntero();

			Profesor profesor = profesorDAO.obtenerPorId(id);
			if (profesor == null) {
				System.out.println("✗ No se encontró el profesor con ID: " + id);
				return;
			}

			System.out.println("Datos actuales: " + profesor);

			// Iniciar transacción
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			try {
				System.out.print("\nNuevo nombre (actual: " + profesor.getNombre() + "): ");
				String nombre = scanner.nextLine();
				if (!nombre.isEmpty())
					profesor.setNombre(nombre);

				System.out.print("Nuevos apellidos (actual: " + profesor.getApellidos() + "): ");
				String apellidos = scanner.nextLine();
				if (!apellidos.isEmpty())
					profesor.setApellidos(apellidos);

				System.out.print("Nueva antigüedad (actual: " + profesor.getAntiguedad() + "): ");
				String antiguedadStr = scanner.nextLine();
				if (!antiguedadStr.isEmpty()) {
					profesor.setAntiguedad(Integer.parseInt(antiguedadStr));
				}

				session.update(profesor);

				// Mostrar cambios
				System.out.println("\n--- DATOS MODIFICADOS ---");
				System.out.println(profesor);
				System.out.print("\n¿Confirmar cambios? (S/N): ");
				String confirmar = scanner.nextLine();

				if (confirmar.equalsIgnoreCase("S")) {
					transaction.commit();
					System.out.println("✓ Cambios confirmados (COMMIT)");
				} else {
					transaction.rollback();
					System.out.println("✗ Cambios deshechos (ROLLBACK)");
				}

			} catch (Exception e) {
				transaction.rollback();
				throw e;
			} finally {
				session.close();
			}

		} catch (Exception e) {
			System.err.println("✗ Error al modificar: " + e.getMessage());
		}
	}

	private static void modificarAlumno() {
		try {
			System.out.println("\n--- MODIFICAR ALUMNO ---");
			System.out.print("ID del alumno a modificar: ");
			Integer id = leerEntero();

			Alumno alumno = alumnoDAO.obtenerPorId(id);
			if (alumno == null) {
				System.out.println("✗ No se encontró el alumno con ID: " + id);
				return;
			}

			System.out.println("Datos actuales: " + alumno);

			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			try {
				System.out.print("\nNuevo nombre (actual: " + alumno.getNombre() + "): ");
				String nombre = scanner.nextLine();
				if (!nombre.isEmpty())
					alumno.setNombre(nombre);

				System.out.print("Nuevos apellidos (actual: " + alumno.getApellidos() + "): ");
				String apellidos = scanner.nextLine();
				if (!apellidos.isEmpty())
					alumno.setApellidos(apellidos);

				session.update(alumno);

				System.out.println("\n--- DATOS MODIFICADOS ---");
				System.out.println(alumno);
				System.out.print("\n¿Confirmar cambios? (S/N): ");
				String confirmar = scanner.nextLine();

				if (confirmar.equalsIgnoreCase("S")) {
					transaction.commit();
					System.out.println("✓ Cambios confirmados (COMMIT)");
				} else {
					transaction.rollback();
					System.out.println("✗ Cambios deshechos (ROLLBACK)");
				}

			} catch (Exception e) {
				transaction.rollback();
				throw e;
			} finally {
				session.close();
			}

		} catch (Exception e) {
			System.err.println("✗ Error al modificar: " + e.getMessage());
		}
	}

	private static void modificarMatricula() {
		try {
			System.out.println("\n--- MODIFICAR MATRÍCULA ---");
			System.out.print("ID de la matrícula a modificar: ");
			Integer id = leerEntero();

			Matricula matricula = matriculaDAO.obtenerPorId(id);
			if (matricula == null) {
				System.out.println("✗ No se encontró la matrícula con ID: " + id);
				return;
			}

			System.out.println("Datos actuales: " + matricula);

			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			try {
				System.out.print("\nNueva asignatura (actual: " + matricula.getAsignatura() + "): ");
				String asignatura = scanner.nextLine();
				if (!asignatura.isEmpty())
					matricula.setAsignatura(asignatura);

				System.out.print("Nuevo curso (actual: " + matricula.getCurso() + "): ");
				String cursoStr = scanner.nextLine();
				if (!cursoStr.isEmpty()) {
					matricula.setCurso(Integer.parseInt(cursoStr));
				}

				session.update(matricula);

				System.out.println("\n--- DATOS MODIFICADOS ---");
				System.out.println(matricula);
				System.out.print("\n¿Confirmar cambios? (S/N): ");
				String confirmar = scanner.nextLine();

				if (confirmar.equalsIgnoreCase("S")) {
					transaction.commit();
					System.out.println("✓ Cambios confirmados (COMMIT)");
				} else {
					transaction.rollback();
					System.out.println("✗ Cambios deshechos (ROLLBACK)");
				}

			} catch (Exception e) {
				transaction.rollback();
				throw e;
			} finally {
				session.close();
			}

		} catch (Exception e) {
			System.err.println("✗ Error al modificar: " + e.getMessage());
		}
	}

	// ==================== MENÚ BORRAR ====================

	private static void menuBorrar() {
		System.out.println("\n--- BORRAR DATOS ---");
		System.out.println("1. Profesor");
		System.out.println("2. Alumno");
		System.out.println("3. Matrícula");
		System.out.println("4. Todos los datos de una tabla");
		System.out.println("0. Volver");
		System.out.print("Seleccione opción: ");

		int opcion = leerEntero();

		switch (opcion) {
		case 1:
			borrarProfesor();
			break;
		case 2:
			borrarAlumno();
			break;
		case 3:
			borrarMatricula();
			break;
		case 4:
			borrarTodosLosDatos();
			break;
		case 0:
			break;
		default:
			System.out.println("⚠ Opción no válida");
		}
	}

	private static void borrarProfesor() {
		try {
			System.out.println("\n--- BORRAR PROFESOR ---");
			System.out.print("ID del profesor a borrar: ");
			Integer id = leerEntero();

			Profesor profesor = profesorDAO.obtenerPorId(id);
			if (profesor == null) {
				System.out.println("✗ No se encontró el profesor con ID: " + id);
				return;
			}

			System.out.println("Datos a borrar: " + profesor);
			System.out.print("¿Confirmar eliminación? (S/N): ");
			String confirmar = scanner.nextLine();

			if (confirmar.equalsIgnoreCase("S")) {
				profesorDAO.eliminar(id);
			} else {
				System.out.println("✗ Operación cancelada");
			}

		} catch (Exception e) {
			System.err.println("✗ Error: No se puede eliminar el profesor porque tiene matrículas asociadas.");
			System.err.println("  Debe eliminar primero las matrículas relacionadas.");
		}
	}

	private static void borrarAlumno() {
		try {
			System.out.println("\n--- BORRAR ALUMNO ---");
			System.out.print("ID del alumno a borrar: ");
			Integer id = leerEntero();

			Alumno alumno = alumnoDAO.obtenerPorId(id);
			if (alumno == null) {
				System.out.println("✗ No se encontró el alumno con ID: " + id);
				return;
			}

			System.out.println("Datos a borrar: " + alumno);
			System.out.print("¿Confirmar eliminación? (S/N): ");
			String confirmar = scanner.nextLine();

			if (confirmar.equalsIgnoreCase("S")) {
				alumnoDAO.eliminar(id);
			} else {
				System.out.println("✗ Operación cancelada");
			}

		} catch (Exception e) {
			System.err.println("✗ Error: No se puede eliminar el alumno porque tiene matrículas asociadas.");
			System.err.println("  Debe eliminar primero las matrículas relacionadas.");
		}
	}

	private static void borrarMatricula() {
		try {
			System.out.println("\n--- BORRAR MATRÍCULA ---");
			System.out.print("ID de la matrícula a borrar: ");
			Integer id = leerEntero();

			Matricula matricula = matriculaDAO.obtenerPorId(id);
			if (matricula == null) {
				System.out.println("✗ No se encontró la matrícula con ID: " + id);
				return;
			}

			System.out.println("Datos a borrar: " + matricula);
			System.out.print("¿Confirmar eliminación? (S/N): ");
			String confirmar = scanner.nextLine();

			if (confirmar.equalsIgnoreCase("S")) {
				matriculaDAO.eliminar(id);
			} else {
				System.out.println("✗ Operación cancelada");
			}

		} catch (Exception e) {
			System.err.println("✗ Error al borrar: " + e.getMessage());
		}
	}

	private static void borrarTodosLosDatos() {
		System.out.println("\n--- BORRAR TODOS LOS DATOS ---");
		System.out.println("⚠ ADVERTENCIA: Se borrarán TODOS los datos de la tabla seleccionada");
		System.out.println("1. Todos los profesores");
		System.out.println("2. Todos los alumnos");
		System.out.println("3. Todas las matrículas");
		System.out.print("Seleccione opción: ");

		int opcion = leerEntero();

		System.out.print("¿Está seguro? Escriba 'CONFIRMAR' para continuar: ");
		String confirmar = scanner.nextLine();

		if (!confirmar.equals("CONFIRMAR")) {
			System.out.println("✗ Operación cancelada");
			return;
		}

		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			transaction = session.beginTransaction();

			switch (opcion) {
			case 1:
				session.createQuery("DELETE FROM Profesor").executeUpdate();
				System.out.println("✓ Todos los profesores han sido eliminados");
				break;
			case 2:
				session.createQuery("DELETE FROM Alumno").executeUpdate();
				System.out.println("✓ Todos los alumnos han sido eliminados");
				break;
			case 3:
				session.createQuery("DELETE FROM Matricula").executeUpdate();
				System.out.println("✓ Todas las matrículas han sido eliminadas");
				break;
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("✗ Error: " + e.getMessage());
		} finally {
			session.close();
		}
	}

	// ==================== MENÚ ELIMINAR TABLAS (DROP) ====================

private static void menuEliminarTablas() {
        System.out.println("\n--- ELIMINAR TABLAS (DROP) ---");
        System.out.println("⚠ ADVERTENCIA: Esta acción eliminará las tablas de la base de datos");
        System.out.println("1. Eliminar tabla Matrícula");
        System.out.println("2. Eliminar tabla Alumno");
        System.out.println("3. Eliminar tabla Profesor");
        System.out.println("4. Eliminar todas las tablas");
        System.out.println("0. Volver");
        System.out.print("Seleccione opción: ");
        
        int opcion = leerEntero();
        
        if (opcion == 0) return;
        
        System.out.print("¿Está seguro? Escriba 'DROP' para continuar: ");
        String confirmar = scanner.nextLine();
        
        if (!confirmar.equals("DROP")) {
            System.out.println("✗ Operación cancelada");
            return;
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            switch (opcion) {
                case 1:
                    session.createNativeQuery("DROP TABLE IF EXISTS matriculas").executeUpdate();
                    System.out.println("✓ Tabla 'matriculas' eliminada");
                    break;
                case 2:
                    System.out.println("⚠ Debe eliminar primero la tabla 'matriculas' (tiene FK)");
                    break;
                case 3:
                    System.out.println("⚠ Debe eliminar primero la tabla 'matriculas' (tiene FK)");
                    break;
                case 4:
                    session.createNativeQuery("DROP TABLE IF EXISTS matriculas").executeUpdate();
                    session.createNativeQuery("DROP TABLE IF EXISTS alumnos").executeUpdate();
                    session.createNativeQuery("DROP TABLE IF EXISTS profesores").executeUpdate();
                    System.out.println("✓ Todas las tablas han sido eliminadas");
                    break;
            }
            
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al eliminar tabla: " + e.getMessage());
        } finally {
            session.close();
        }
    }

	// ==================== MÉTODOS AUXILIARES ====================

	private static void mostrarListaProfesores(List<Profesor> profesores) {
        if (profesores == null || profesores.isEmpty()) {
            System.out.println("⚠ No se encontraron profesores");
            return;
        }
        
        System.out.println("\n" + "-".repeat(80));
        System.out.printf("%-5s %-20s %-20s %-15s %-10s%n", 
            "ID", "NOMBRE", "APELLIDOS", "FECHA NAC.", "ANTIGÜEDAD");
        System.out.println("-".repeat(80));
        for (Profesor p : profesores) {
            System.out.printf("%-5d %-20s %-20s %-15s %-10d%n",
                p.getIdProfesor(),
                p.getNombre(),
                p.getApellidos(),
                p.getFechaNacimiento(),
                p.getAntiguedad());
        }
        System.out.println("-".repeat(80));
        System.out.println("Total: " + profesores.size() + " profesor(es)");
    }
    
    private static void mostrarListaAlumnos(List<Alumno> alumnos) {
        if (alumnos == null || alumnos.isEmpty()) {
            System.out.println("⚠ No se encontraron alumnos");
            return;
        }
        
        System.out.println("\n" + "-".repeat(70));
        System.out.printf("%-5s %-20s %-20s %-15s%n", 
            "ID", "NOMBRE", "APELLIDOS", "FECHA NAC.");
        System.out.println("-".repeat(70));
        
        for (Alumno a : alumnos) {
            System.out.printf("%-5d %-20s %-20s %-15s%n",
                a.getIdAlumnado(),
                a.getNombre(),
                a.getApellidos(),
                a.getFechaNacimiento());
        }
        System.out.println("-".repeat(70));
        System.out.println("Total: " + alumnos.size() + " alumno(s)");
    }
    
    private static void mostrarListaMatriculas(List<Matricula> matriculas) {
        if (matriculas == null || matriculas.isEmpty()) {
            System.out.println("⚠ No se encontraron matrículas");
            return;
        }
        
        System.out.println("\n" + "-".repeat(100));
        System.out.printf("%-5s %-20s %-10s %-30s %-30s%n", 
            "ID", "ASIGNATURA", "CURSO", "PROFESOR", "ALUMNO");
        System.out.println("-".repeat(100));
        
        for (Matricula m : matriculas) {
            String profesor = m.getProfesor() != null ? 
                m.getProfesor().getNombre() + " " + m.getProfesor().getApellidos() : "N/A";
            String alumno = m.getAlumno() != null ? 
                m.getAlumno().getNombre() + " " + m.getAlumno().getApellidos() : "N/A";
                
            System.out.printf("%-5d %-20s %-10d %-30s %-30s%n",
                m.getIdMatricula(),
                m.getAsignatura(),
                m.getCurso(),
                profesor,
                alumno);
        }
        System.out.println("-".repeat(100));
        System.out.println("Total: " + matriculas.size() + " matrícula(s)");
    }
    
    private static Alumno seleccionarAlumno(List<Alumno> alumnos) {
        if (alumnos.size() == 1) {
            return alumnos.get(0);
        }
        
        System.out.println("\nSe encontraron " + alumnos.size() + " alumnos:");
        for (int i = 0; i < alumnos.size(); i++) {
            System.out.println((i + 1) + ". " + alumnos.get(i));
        }
        
        System.out.print("Seleccione el número de alumno: ");
        int opcion = leerEntero();
        
        if (opcion < 1 || opcion > alumnos.size()) {
            System.out.println("✗ Opción no válida");
            return null;
        }
        
        return alumnos.get(opcion - 1);
    }
    
    private static Profesor seleccionarProfesor(List<Profesor> profesores) {
        if (profesores.size() == 1) {
            return profesores.get(0);
        }
        
        System.out.println("\nSe encontraron " + profesores.size() + " profesores:");
        for (int i = 0; i < profesores.size(); i++) {
            System.out.println((i + 1) + ". " + profesores.get(i));
        }
        
        System.out.print("Seleccione el número de profesor: ");
        int opcion = leerEntero();
        
        if (opcion < 1 || opcion > profesores.size()) {
            System.out.println("✗ Opción no válida");
            return null;
        }
        
        return profesores.get(opcion - 1);
    }
    
    private static int leerEntero() {
        try {
            int numero = Integer.parseInt(scanner.nextLine());
            return numero;
        } catch (NumberFormatException e) {
            System.out.println("⚠ Debe introducir un número válido");
            return -1;
        }
    }

}