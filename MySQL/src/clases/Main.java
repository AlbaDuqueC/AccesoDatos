package clases;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		// Crea un objeto para acceder a las funciones de la base de datos
		AccesoBD bd = new AccesoBD();

		// Scanner para leer entradas por teclado
		Scanner sc = new Scanner(System.in);

		// Controla el menú principal
		int opcion = -1;

		// Variables necesarias para leer datos del usuario
		String nombre;
		String apellido;
		String fn;
		int antiguedad;
		int idAlumno;
		int idProfesor;
		String asignatura;
		int curso;
		String tabla;
		String campoFiltro;
		String valorFiltro;
		String campoModificar;
		String nuevoValor;
		String campo;
		String valor;
		String conf;
		int opDrop;
		int opList;
		String filtrar;

		if (bd.conectar()) {

			// Bucle principal que se repite hasta que el usuario seleccione 0
			while (opcion != 0) {

				// Muestra el menú principal
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
				// Lee la opción seleccionada por el usuario
				sc.nextLine();
				// Limpia el buffer

				try {
					// Estructura switch con sintaxis "->"
					switch (opcion) {

					// OPCIÓN 1 — CREACIÓN DE TABLAS
					case 1 -> {
						// Submenú para elegir qué tabla crear
						System.out.println("1. Crear Profesores");
						System.out.println("2. Crear Alumnado");
						System.out.println("3. Crear Matricula");
						System.out.println("4. Crear TODAS");

						int opCrear = sc.nextInt();
						// Lee la opción del submenú
						sc.nextLine();

						// Switch interno para elegir tabla a crear
						switch (opCrear) {
						case 1:
							// Crea tabla Profesores
							bd.createTable("profesores");
						case 2:
							// Crea tabla Alumnado
							bd.createTable("alumnado");
						case 3:
							// Crea tabla Matricula
							bd.createTable("matricula");
						case 4:
							// Crea todas las tablas
							bd.createTableTodas();
						default:
							// Opción inválida
							System.out.println("Opción no válida");
						}
					}

					// OPCIÓN 2 — INSERTAR REGISTROS
					case 2 -> {
						// Muestra menú de inserción
						System.out.println("¿En qué tabla deseas insertar?");
						System.out.println("1. Profesores");
						System.out.println("2. Alumnado");
						System.out.println("3. Matrícula");

						int opIns = sc.nextInt();
						// Lee la opción del submenú
						sc.nextLine();

						// Switch interno para elegir en qué tabla insertar
						switch (opIns) {

						// Insertar profesor
						case 1 -> {
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
						}

						// Insertar alumno
						case 2 -> {
							System.out.print("Nombre: ");
							nombre = sc.nextLine();

							System.out.print("Apellidos: ");
							apellido = sc.nextLine();

							System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
							fn = sc.nextLine();

							bd.insertarAlumno(nombre, apellido, fn);
						}

						// Insertar matrícula
						case 3 -> {
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
						}

						}
					}

					// OPCIÓN 3 — LISTAR REGISTROS
					case 3 -> {

						// Pregunta al usuario qué tabla desea listar
						System.out.println("¿Qué tabla deseas listar?");
						System.out.println("1. Profesores");
						System.out.println("2. Alumnado");
						System.out.println("3. Matrícula");

						opList = sc.nextInt();
						sc.nextLine(); // Limpia buffer

						// Pregunta si desea filtrar los resultados
						System.out.print("¿Desea filtrar los resultados? (S/N): ");
						filtrar = sc.nextLine();

						// Variable que almacenará el nombre de la tabla
						tabla = "";

						// Asigna el nombre de la tabla según la opción del usuario
						switch (opList) {
						case 1 -> tabla = "profesores";
						case 2 -> tabla = "alumnado";
						case 3 -> tabla = "matricula";
						default -> System.out.println("Opción de tabla no válida.");
						}

						// Si la opción de tabla es válida
						if (!tabla.equals("")) {
							if (filtrar.equalsIgnoreCase("S")) {
								// Si desea filtrar, solicita valor
								System.out.print("Introduce el valor por el que filtrar: ");
								valorFiltro = sc.nextLine();
								// Llama a listarWhere con el valor filtrado
								bd.listarWhere(tabla, valorFiltro);
							} else {
								// Llama a listar sin filtro
								bd.listar(tabla);
							}

						}

					}

					// OPCIÓN 4 — MODIFICAR REGISTROS
					case 4 -> {
						// Lee tabla a modificar
						System.out.print("Tabla donde modificar: ");
						tabla = sc.nextLine();

						// Lee campo de filtro
						System.out.print("Campo por el que filtrar (ej: idProfesor): ");
						campoFiltro = sc.nextLine();

						// Lee valor de filtro
						System.out.print("Valor del filtro (ej: 8): ");
						valorFiltro = sc.nextLine();

						// Campo que se modificará
						System.out.print("Campo a modificar: ");
						campoModificar = sc.nextLine();

						// Nuevo valor para el campo
						System.out.print("Nuevo valor: ");
						nuevoValor = sc.nextLine();

						// Ejecuta la modificación
						bd.modificar(tabla, campoFiltro, valorFiltro, campoModificar, nuevoValor);

						// Solicita confirmación
						System.out.print("\n¿Confirmar cambios? (S/N): ");
						String r = sc.nextLine();

						// Confirmación con commit o rollback
						if (r.equalsIgnoreCase("S")) {
							bd.StartModificar(true);
						} else {
							bd.StartModificar(false);
						}
					}

					// OPCIÓN 5 — BORRAR REGISTROS
					case 5 -> {
						System.out.println("¿Desea borrar?");
						System.out.println("1. Todos los datos de todas las tablas");
						System.out.println("2. Datos de una tabla concreta");

						int opBorrar = sc.nextInt();
						sc.nextLine();

						try {
							// Iniciar transacción
							bd.iniciarTransaccion();

							if (opBorrar == 1) {
								// Borrar todo siguiendo orden de claves
								bd.borrarTablaCompleta("Matricula");
								bd.borrarTablaCompleta("Alumnado");
								bd.borrarTablaCompleta("Profesores");
							} else if (opBorrar == 2) {
								// Borrar de una sola tabla
								System.out.print("Tabla: ");
								tabla = sc.nextLine();

								System.out.println("1. Borrar toda la tabla");
								System.out.println("2. Borrar por filtro");
								int tipo = sc.nextInt();
								sc.nextLine();

								if (tipo == 1) {
									bd.borrarTablaCompleta(tabla);
								} else {
									System.out.print("Campo filtro: ");
									campo = sc.nextLine();

									System.out.print("Valor filtro: ");
									valor = sc.nextLine();

									bd.borrarFiltrando(tabla, campo, valor);
								}
							}

							// Confirmación de borrado
							System.out.print("¿Confirmar borrado? (S/N): ");
							conf = sc.nextLine();

							if (conf.equalsIgnoreCase("S")) {
								bd.confirmar();
								System.out.println("Cambios confirmados.");
							} else {
								bd.cancelar();
								System.out.println("Cambios deshechos (rollback).");
							}

						} catch (SQLException e) {
							// Si ocurre un error lo muestra
							System.out.println("Error: " + e.getMessage());
						}
					}

					// OPCIÓN 6 — ELIMINAR TABLAS (DROP TABLE)
					case 6 -> {
						System.out.println("¿Eliminar tablas?");
						System.out.println("1. Todas");
						System.out.println("2. Una concreta");

						opDrop = sc.nextInt();
						sc.nextLine();

						try {
							// Inicia transacción
							bd.iniciarTransaccion();

							if (opDrop == 1) {
								// Elimina todas las tablas
								bd.eliminarTodasLasTablas();
							} else {
								// Elimina tabla concreta
								System.out.print("Nombre de la tabla: ");
								tabla = sc.nextLine();
								bd.eliminarTabla(tabla);
							}

							// Confirmación
							System.out.print("¿Confirmar eliminación? (S/N): ");
							conf = sc.nextLine();

							if (conf.equalsIgnoreCase("S")) {
								bd.confirmar();
								System.out.println("Tablas eliminadas.");
							} else {
								bd.cancelar();
								System.out.println("Operación cancelada.");
							}

						} catch (SQLException e) {
							System.out.println("Error: " + e.getMessage());
							try {
								bd.cancelar();
							} catch (Exception ex) {
							}
						}
					}

					// OPCIÓN 0 — SALIR
					case 0 -> {
						System.out.println("Saliendo...");
					}

					}

				} catch (SQLException e) {
					// Error general de SQL
					System.out.println("Error en la operación de base de datos: " + e.getMessage());
				}
			}

			sc.close();
			// Cierra el Scanner al final del programa
		}else {
			System.out.println("No se pudo conectar con exito");
		}
	}
}
