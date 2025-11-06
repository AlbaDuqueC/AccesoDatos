package clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccesoBD {

	private static Connection con;

	private static Statement st;

	/**
	 * Llama a la clase Conecxion y agreada al atributo con la Conecxion, si esta
	 * falla devolvera false, si no devolvera true
	 * 
	 * @return boolean conectar
	 */
	public static boolean conectar() {

		boolean conectado = true;

		try {
			con = Conecxion.conecxion();

			st = con.createStatement();

		} catch (SQLException e) {

			e.printStackTrace();

			conectado = false;

		}

		return conectado;

	}

	public static boolean createTableTodas() throws SQLException {

		String creacion = tablaProfes() + tablaAlumnado() + tablaMatricula();
		

		return st.execute(creacion);

	}
	
	public static int createTable(String nombre) throws SQLException {
		
		int crear=0;
		
		String creacion;
		
		switch (nombre.toLowerCase()) {
		case "profesores" -> {
			
			if(!st.execute(tablaProfes())) {
				crear=-1;
			}

		}
		case "alumnado"->{
			
			if(!st.execute(tablaAlumnado())) {
				crear=-1;
			}
			
		}
		case "matricula"->{
			
			ResultSet rta= st.executeQuery("SELECT * FROM Alumnado");
			ResultSet rtp= st.executeQuery("SELECT * FROM Profesores");
			
			if(!st.execute(tablaMatricula())) {
				crear=-1;
			}
			
		}
		default->{
			crear=-2;
		}

		}
		
		
		return crear;
		
	}
	
	private static String tablaProfes() {
		
		String tablaProfes= "CREATE TABLE Profesores (idProfesor int Primary Key, Nombre varchar(45), Apellidos varchar(45), FechaNacimiento date, Antiguedad int);";

		return tablaProfes;
	}
	
	private static String tablaAlumnado() {
		String tablaAlumnado="CREATE TABLE Alumnado (idAlumno int Primary key, Nombre varchar(45), Apellidos varchar(45), FechaNacimiento date)";
		
		return tablaAlumnado;
	}
	
	private static String tablaMatricula() {
		
		String tablaMatricula= "CREATE TABLE Matricula (idMatricula int Primary Key, idProfesorado int, idAlumnado int, Asignatura varchar(45), Curso int, Foreing key (idProfesorado) references Profesores(idProdesor),Foreing key (idAlumnado) references Alumnado (idAlumnado) ) ";
		
		return tablaMatricula;
		
	}

}
