package tema2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1 {
	
	static String FICHERO="C:\\\\Users\\\\alba.duque\\\\carpeta.txt";
	

	public static void main(String[] args) {
		
		try (BufferedReader leer= new BufferedReader(new FileReader(FICHERO));) {
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
