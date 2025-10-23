package tema2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio2 {

	// Atributtos staticos

	// Ruta del fichero
	static String FICHERO = "C:\\Users\\alba.duque\\carpetas.txt";

	// Objeto fichero de tipo file
	static File ARCHIVO = new File(FICHERO);
	
	static String CONTENIDO_HTML= "<html>\r\n"
			+ "   <head>\r\n"
			+ "      <title> [Nombre de la carpeta] </title>\r\n"
			+ "   </head>\r\n"
			+ "   <body>\r\n"
			+ "      <h1>[Ruta + nombre de la carpeta]</h1>\r\n"
			+ "      <h3>Autor: [nombre_del_alumno]</h3>\r\n"
			+ "   </body>\r\n"
			+ "</html>";

	public static void main(String[] args) {

		// Atributos
		String cad = "";

		String direccionHtml = "";

		File direccion;


		// Control de si el buffereReader va bien
		try (BufferedReader leer = new BufferedReader(new FileReader(FICHERO));) {

			/* Si el archivo no existe creara un fichero */
			if (!ARCHIVO.exists()) {

				if (ARCHIVO.createNewFile()) {

					System.out.println("Archivo creado");

				} else {

					System.out.println("No se pudo crear el archivo");

				}

			} else {

				// Introducimo la linea
				cad = leer.readLine();

				/* Si la linea no es nula creara la carpeta */
				while (cad != null && !cad.isBlank()) {

					direccion = new File("C:\\Users\\alba.duque\\" + cad);

					direccionHtml = direccion.toString();

					if (direccion.mkdir()) {

						System.out.println("Se creo la carpeta con exito");

					} else {

						System.out.println("No se pudo crear la carpeta");

					}

					// Creacion del la direccion donde insertaremos el html
					
					BufferedWriter writer = new BufferedWriter(new FileWriter(direccionHtml + "\\index.html", true));

					writer.newLine();
					writer.write(CONTENIDO_HTML);
					writer.flush();
					

					// Vuelve a escanear la siguiente linea
					cad = leer.readLine();
				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		

	}

}
