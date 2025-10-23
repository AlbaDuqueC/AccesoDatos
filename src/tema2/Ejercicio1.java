package tema2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1 {

	// Atributtos staticos

	// Ruta del fichero
	static String FICHERO = "C:\\Users\\alba.duque\\carpetas.txt";

	// Objeto fichero de tipo file
	static File ARCHIVO = new File(FICHERO);

	public static void main(String[] args) {

		// Atributos
		String cad = "";

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
				while (cad != null) {

					direccion = new File("C:\\Users\\alba.duque\\" + cad);

					if (direccion.mkdir()) {

						System.out.println("Se creo la carpeta con exito");

					} else {

						System.out.println("No se pudo crear la carpeta");

					}

					// Vuelve a escanear la siguiente linea
					cad = leer.readLine();
				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	

}
