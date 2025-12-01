package clases;

import java.sql.SQLException;
import java.util.List;
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
		String columnaFiltro;
		int opFiltro;
		int opModificar;
		int tipo;
		int opBorrar;

		List<String> columnas;
		List<String> tablas;

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
							bd.createTable("Profesores");
						case 2:
							// Crea tabla Alumnado
							bd.createTable("Alumnado");
						case 3:
							// Crea tabla Matricula
							bd.createTable("Matricula");
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
						case 1 -> tabla = "Profesores";
						case 2 -> tabla = "Alumnado";
						case 3 -> tabla = "Matricula";
						default -> System.out.println("Opción de tabla no válida.");
						}

						// Si la opción de tabla es válida
						if (!tabla.equals("")) {
							if (filtrar.equalsIgnoreCase("S")) {

								System.out.println("¿Por qué columna quieres filtrar?");
								columnas = bd.obtenerColumnas(tabla);

								// Mostrar columnas
								for (int i = 0; i < columnas.size(); i++) {
									System.out.println((i + 1) + ". " + columnas.get(i));
								}

								System.out.print("Elige una columna: ");
								int col = sc.nextInt();
								sc.nextLine();

								columnaFiltro = columnas.get(col - 1);

								System.out.print("Introduce el valor por el que filtrar: ");
								valorFiltro = sc.nextLine();

								// Llamada correcta:
								bd.listarWhere(tabla, columnaFiltro, valorFiltro);

							} else {
								// Llama a listar sin filtro
								bd.listar(tabla);
							}

						}

					}

					// OPCIÓN 4 — MODIFICAR REGISTROS
					case 4 -> {

						// Obtener y mostrar tablas existentes
						tablas = bd.obtenerTablas();

						if (tablas == null || tablas.isEmpty()) {
							System.out.println("No hay tablas disponibles en la base de datos.");
							break;
						}

						System.out.println("\nTABLAS DISPONIBLES:");
						for (int i = 0; i < tablas.size(); i++) {
							System.out.println((i + 1) + ". " + tablas.get(i));
						}

						// Seleccionar tabla
						System.out.print("\nElige el número de la tabla donde modificar: ");
						int opTabla = sc.nextInt();
						sc.nextLine();

						tabla = tablas.get(opTabla - 1);

						// Obtener columnas de la tabla seleccionada
						columnas = bd.obtenerColumnas(tabla);

						if (columnas == null || columnas.isEmpty()) {
							System.out.println("No se pudieron obtener las columnas de la tabla.");
							break;
						}

						System.out.println("\nCOLUMNAS DE " + tabla + ":");
						for (int i = 0; i < columnas.size(); i++) {
							System.out.println((i + 1) + ". " + columnas.get(i));
						}

						// Seleccionar campo de filtro
						System.out.print("\nElige el número del campo por el que filtrar: ");
						opFiltro = sc.nextInt();
						sc.nextLine();

						campoFiltro = columnas.get(opFiltro - 1);

						// Valor del filtro
						System.out.print("Valor del filtro: ");
						valorFiltro = sc.nextLine();

						// Seleccionar campo a modificar
						System.out.print("\nElige el número del campo a modificar: ");
						opModificar = sc.nextInt();
						sc.nextLine();

						campoModificar = columnas.get(opModificar - 1);

						// Nuevo valor
						System.out.print("Nuevo valor para '" + campoModificar + "': ");
						nuevoValor = sc.nextLine();

						// Ejecutar modificación
						bd.modificar(tabla, campoFiltro, valorFiltro, campoModificar, nuevoValor);

						// Confirmación
						System.out.print("\n¿Confirmar cambios? (S/N): ");
						String r = sc.nextLine();

						if (r.equalsIgnoreCase("S")) {
							bd.StartModificar(true);
							System.out.println("Cambios confirmados.");
						} else {
							bd.StartModificar(false);
							System.out.println("Cambios cancelados.");
						}
					}

					// OPCIÓN 5 — BORRAR REGISTROS
					case 5 -> {

					    System.out.println("¿Desea borrar?");
					    System.out.println("1. Todos los datos de todas las tablas");
					    System.out.println("2. Datos de una tabla concreta");

					    opBorrar = sc.nextInt();
					    sc.nextLine();  // limpiar buffer

					    try {
					        // Iniciar transacción
					        bd.iniciarTransaccion();

					        //  OPCIÓN 1 — BORRAR TODAS LAS TABLAS EN ORDEN SEGURO
					        if (opBorrar == 1) {

					            System.out.println("\nSe borrarán TODAS las tablas en orden seguro…");

					            // Obtener todas las tablas 
					            tablas= bd.obtenerTablas();

					        
					            for (String t : tablas) {
					                System.out.println("→ Borrando tabla: " + t);
					                bd.borrarTablaCompleta(t);
					            }

					        } 
					        
					        //  OPCIÓN 2 — BORRAR UNA SOLA TABLA
					        else if (opBorrar == 2) {

					            // Mostrar tablas disponibles
					            tablas = bd.obtenerTablas();

					            System.out.println("\nTABLAS DISPONIBLES:");
					            for (int i = 0; i < tablas.size(); i++) {
					                System.out.println((i + 1) + ". " + tablas.get(i));
					            }

					            // Seleccionar tabla
					            System.out.print("\nSeleccione número de la tabla: ");
					            int opTabla = sc.nextInt();
					            sc.nextLine();

					            tabla = tablas.get(opTabla - 1);

					            System.out.println("\n1. Borrar TODA la tabla");
					            System.out.println("2. Borrar usando un filtro");
					            tipo = sc.nextInt();
					            sc.nextLine();

					            //  Opción 2.1 → BORRAR UNA TABLA COMPLETA
					            if (tipo == 1) {
					                System.out.println("→ Borrando tabla completa: " + tabla);
					                bd.borrarTablaCompleta(tabla);
					            } 

					            //  Opción 2.2 → BORRAR POR FILTRO (con selección de columna)
					            else {

					                // Mostrar columnas disponibles
					                columnas = bd.obtenerColumnas(tabla);

					                System.out.println("\nCOLUMNAS DE " + tabla + ":");
					                for (int i = 0; i < columnas.size(); i++) {
					                    System.out.println((i + 1) + ". " + columnas.get(i));
					                }

					                // Seleccionar columna para filtrar
					                System.out.print("\nSeleccione número del campo filtro: ");
					                int opCampo = sc.nextInt();
					                sc.nextLine();

					                campo = columnas.get(opCampo - 1);

					                // Valor del filtro
					                System.out.print("Valor filtro: ");
					                valor = sc.nextLine();

					                System.out.println("\n→ Borrando registros donde " + campo + " = '" + valor + "'");
					                bd.borrarFiltrando(tabla, campo, valor);
					            }
					        }

					        // CONFIRMACIÓN FINAL
					        System.out.print("\n¿Confirmar borrado? (S/N): ");
					        conf = sc.nextLine();

					        if (conf.equalsIgnoreCase("S")) {
					            bd.confirmar();
					            System.out.println("Cambios confirmados.");
					        } else {
					            bd.cancelar();
					            System.out.println("Cambios deshechos (rollback).");
					        }

					    } catch (SQLException e) {
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
		} else {
			System.out.println("No se pudo conectar con exito");
		}
	}
}
