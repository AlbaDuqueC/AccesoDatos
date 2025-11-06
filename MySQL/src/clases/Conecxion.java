package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecxion {
	
	public static Connection conecxion() throws SQLException{
			
			
			String conexion= "jdbc:mysql://dns11036.phdns11.es:3306/ad2526_alba_duque";
			
			String usuario="ad2526_alba_duque";
			
			String contraseña="12345";
			
			Connection con = DriverManager.getConnection(conexion, usuario, contraseña);
			
			return con;	
				
			
		}

}
