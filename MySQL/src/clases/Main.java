package clases;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		AccesoBD bd = new AccesoBD();
		Scanner sc = new Scanner(System.in);
		int opcion = -1;

		String nombre;
		String apellido;
		String fn;
		int antiguedad;
		int idAlumno;
		int idProfesor;
		String asignatura;
		int curso;

		while (opcion != 0) {
			System.out.println("\n--- MENÚ PRINCIPAL ---");
			System.out.println("1. Crear tablas");
			System.out.println("2. Insertar");
			System.out.println("3. Listar");
			System.out.println("4. Modificar");
			System.out.println("5. Borrar datos");
			System.out.println("6. Eliminar tablas");
			System.out.println("0. Salir");
			System.out.print("Opción: ");
			opcion = sc.nextInt();
			sc.nextLine();

			try {
				switch (opcion) {
				case 1:
					System.out.println("1. Crear Profesores");
					System.out.println("2. Crear Alumnado");
					System.out.println("3. Crear Matricula");
					System.out.println("4. Crear TODAS");
					int opCrear = sc.nextInt();
					sc.nextLine();
					switch (opCrear) {
					case 1:
						bd.createTable("profesores");
					case 2:
						bd.createTable("alumnado");
					case 3:
						bd.createTable("matricula");
					case 4:
						bd.createTableTodas();
					default:
						System.out.println("Opción no válida");
					}

				case 2:
					System.out.println("¿En qué tabla deseas insertar?");
					System.out.println("1. Profesores");
					System.out.println("2. Alumnado");
					System.out.println("3. Matrícula");
					int opIns = sc.nextInt();
					sc.nextLine();
					switch (opIns) {
					case 1:
						System.out.print("Nombre: ");
						nombre = sc.nextLine();
						System.out.print("Apellidos: ");
						apellido = sc.nextLine();
						System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
						fn = sc.nextLine();
						System.out.print("Antigüedad: ");
						antiguedad = sc.nextInt();
						sc.nextLine();
						bd.insertarProfes(nombre, apellido, fn, antiguedad);
					case 2:
						System.out.print("Nombre: ");
						nombre = sc.nextLine();
						System.out.print("Apellidos: ");
						apellido = sc.nextLine();
						System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
						fn = sc.nextLine();
						bd.insertarAlumno(nombre, apellido, fn);
					case 3:
						System.out.print("ID Profesor: ");
						idProfesor = sc.nextInt();
						sc.nextLine();
						System.out.print("ID Alumno: ");
						idAlumno = sc.nextInt();
						sc.nextLine();
						System.out.print("Asignatura: ");
						asignatura = sc.nextLine();
						System.out.print("Curso: ");
						curso = sc.nextInt();
						sc.nextLine();
						bd.insertarMatricula(idProfesor, idAlumno, asignatura, curso);
					default:
						System.out.println("Opción no válida");
					}

				case 3:

				case 4:

				case 5:

				case 6:

				case 0:
					System.out.println("Saliendo...");

				}
			} catch (SQLException e) {
				System.out.println("Error en la operación de base de datos: " + e.getMessage());
			}
		}

		sc.close();
	}
}
