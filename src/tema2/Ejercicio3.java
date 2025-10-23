package tema2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio3 {

	public static void main(String[] args) {

		String ficheroEntrada = "src\\tema2\\ficheros\\palabrasPegadas.txt";
		String ficheroSalida = "src\\tema2\\ficheros\\palabrasSeparadas.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(ficheroEntrada));
				BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida))) {
			
			// Leer el fichero 
			StringBuilder contenido = new StringBuilder();
			
			String linea;
			
			while ((linea = br.readLine()) != null) {
				
				contenido.append(linea.trim());
			}

			String texto = contenido.toString();
			
			int longitud = texto.length();

			// Comprobar que la longitud sea múltiplo de 5
			if (longitud % 5 != 0) {
				
				System.out.println("El número total de caracteres no es multiplo de 5.");
			}

			// Dividir en palabras de 5 letras
			for (int i = 0; i < longitud; i += 5) {
				
				
				int fin = Math.min(i + 5, longitud);
				
				String palabra = texto.substring(i, fin);
				
				bw.write(palabra);
				
				bw.newLine();
			}

			System.out.println("Se ha creado el archivo: " + ficheroSalida + ". Y se ha ordenado las palabras en el fichero");

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		

	}

}
