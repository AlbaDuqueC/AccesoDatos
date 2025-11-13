package clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccesoBD {

	private static Connection con;

	private static Statement st;

	// CONECTAR

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

	// CREATE TABLE

	public static boolean createTableTodas() throws SQLException {

		String creacion = tablaProfes() + tablaAlumnado() + tablaMatricula();

		return st.execute(creacion);

	}

	public static int createTable(String nombre) throws SQLException {

		int crear = 0;

		String creacion;

		switch (nombre.toLowerCase()) {
		case "profesores" -> {

			if (!st.execute(tablaProfes())) {
				crear = -1;
			}

		}
		case "alumnado" -> {

			if (!st.execute(tablaAlumnado())) {
				crear = -1;
			}

		}
		case "matricula" -> {

			ResultSet rta = st.executeQuery("SELECT * FROM Alumnado");
			ResultSet rtp = st.executeQuery("SELECT * FROM Profesores");

			if (!st.execute(tablaMatricula())) {
				crear = -1;
			}

		}
		default -> {
			crear = -2;
		}

		}

		return crear;

	}

	// INSERTAR

	public static boolean insertarProfes(int id, String nombre, String apellido, String FechaNacimiento, int Antiguedad)
			throws SQLException {

		boolean inserta = true;

		String insertarProfe = "INSERT INTO Profesores ( '" + id + "' , '" + nombre + "' , '" + apellido + "' , '"
				+ FechaNacimiento + "' , '" + Antiguedad + "' )";

		if (!st.execute(insertarProfe)) {

			inserta = false;

		}

		return inserta;

	}

	public static boolean insertarAlumno(int id, String nombre, String apellido, String fecha) throws SQLException {

		boolean inserta = true;

		String insertarAlumno = "INSERT INTO Alumnado ( '" + id + "' , '" + nombre + "' , '" + apellido + "' , '"
				+ fecha + "' )";

		if (!st.execute(insertarAlumno)) {

			inserta = false;

		}

		return inserta;
	}

	public static boolean insertarMatricula(int id, int idProfesor, int idAlumno, String asignatura, int curso)
			throws SQLException {

		boolean inserta = true;

		String insertarAlumno = "INSERT INTO Matricula ( '" + id + "' , '" + idProfesor + "' , '" + idAlumno + "' , '"
				+ asignatura + "' , '" + curso + "' )";

		if (!st.execute(insertarAlumno)) {

			inserta = false;

		}

		return inserta;

	}

	// LISTAR

	public static void listar(String tabla) throws SQLException {

		String lita = "SELECT * FROM " + tabla;

		ResultSet rs = st.executeQuery(lita);

		System.out.println(rs);

	}

	public static void listarWhere(String tabla, String valor) throws SQLException {

		String lista = "SELECT * FROM " + tabla + "WHERE " + "LIKE %" + valor + "%";

		ResultSet rs = st.executeQuery(lista);

		

	}

	// MODIFICAR

	public static boolean modificar(String tabla, String columna, String dato, String filtro) throws SQLException {

		boolean modifico = true;

		String start = "START TRANSACTION;";

		String SelcTabla = "SELECT * FROM " + tabla + " WHERE " + filtro;

		String modifica = " UPDATE  " + tabla + " SET " + columna + " = " + dato + " WHERE " + filtro;

		ResultSet rs;

		if (st.execute(start)) {

			rs = st.executeQuery(SelcTabla);

			System.out.println(rs);

			if (st.execute(modifica)) {

				rs = st.executeQuery(SelcTabla);

				System.out.println(rs);

			} else {
				modifico = false;
			}

		} else {
			modifico = false;
		}

		return modifico;

	}

	public static boolean StartModificar(boolean start) throws SQLException {

		boolean modifico = true;

		String stm;

		if (start) {

			stm = "COMMIT";

		} else {

			stm = "ROLLBACK";

		}

		if (!st.execute(stm)) {

			modifico = false;

		}

		return modifico;

	}

	// FUNCIONES PRIVADAS

	private static String tablaProfes() {

		String tablaProfes = "CREATE TABLE Profesores (idProfesor int Primary Key, Nombre varchar(45), Apellidos varchar(45), FechaNacimiento date, Antiguedad int);";

		return tablaProfes;
	}

	private static String tablaAlumnado() {
		String tablaAlumnado = "CREATE TABLE Alumnado (idAlumno int Primary key, Nombre varchar(45), Apellidos varchar(45), FechaNacimiento date)";

		return tablaAlumnado;
	}

	private static String tablaMatricula() {

		String tablaMatricula = "CREATE TABLE Matricula (idMatricula int Primary Key, idProfesorado int, idAlumnado int, Asignatura varchar(45), Curso int, Foreing key (idProfesorado) references Profesores(idProdesor),Foreing key (idAlumnado) references Alumnado (idAlumnado) ) ";

		return tablaMatricula;

	}
	
	private static void imprimirProfes(ResultSet rs) {
	
		
		
	}

}
