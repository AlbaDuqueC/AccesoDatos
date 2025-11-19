package clases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
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

	/**
	 * Crea todas las tablas necesarias en la base de datos
	 * @return debuelve un boolean dependiendo de si se han podido hacer las tablas correctamente o no
	 * @throws SQLException
	 */
	public static boolean createTableTodas() throws SQLException {

		String creacion = tablaProfes() + tablaAlumnado() + tablaMatricula();

		return st.execute(creacion);

	}

	/**
	 * Crea solo la tabla que introduzcas por parametro
	 * @param nombre Nombre de la tabla que quieras crear
	 * @return devuelve un numero dependiendo de lo que sudaceda
	 * @throws SQLException
	 */
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

	/**
	 * Inserta los datos de un profesor
	 * @param nombre Nombre del profesor
	 * @param apellido Apellido del profesor
	 * @param FechaNacimiento Fecha de nacimiento del profesor
	 * @param Antiguedad Numeoro de años que lleva dando clase (antiguedad )
	 * @return devuelve un boolean dependiendo de si se ha podido inertar bien
	 * @throws SQLException
	 */
	public static void insertarProfes( String nombre, String apellido, String FechaNacimiento, int Antiguedad)
			throws SQLException {

		try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Profesores(Nombre,Apellidos,FechaNacimiento,Antiguedad) VALUES (?,?,?,?)"
            );
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, FechaNacimiento);
            ps.setInt(4, Antiguedad);
            ps.executeUpdate();
            System.out.println("Profesor insertado.");
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

	/**
	 * Inserta los datos introducidos por parametro de un alumno para guardarlo en la base de datos
	 * @param nombre Nombre del Alumno
	 * @param apellido Apellido del Alumno
	 * @param fechaNacimiento Fecha de nacimiento del alumno
	 * @return Devuelve un boolean dependiendo si se ha podido hacer la operacion en la base de datos o no
	 * @throws SQLException
	 */
	public static void insertarAlumno( String nombre, String apellido, String fechaNacimiento) throws SQLException {

		PreparedStatement ps = con.prepareStatement(
	            "INSERT INTO Alumnado(Nombre,Apellidos,FechaNacimiento) VALUES (?,?,?)"
	        );
	        ps.setString(1, nombre);
	        ps.setString(2, apellido);
	        ps.setString(3, fechaNacimiento);
	        ps.executeUpdate();
	        System.out.println("Alumno insertado.");
	    }


	/**
	 * Inserta los datos introducidos por parametro de la Matricula
	 * @param id ID de la Matricula
	 * @param idProfesor ID del profesor
	 * @param idAlumno ID del alumno 
	 * @param asignatura
	 * @param curso
	 * @return
	 * @throws SQLException
	 */
	public static void insertarMatricula( int idProfesor, int idAlumno, String asignatura, int curso)
			throws SQLException {

		PreparedStatement ps = con.prepareStatement(
	            "INSERT INTO Matricula(idProfesorado,idAlumno,Asignatura,Curso) VALUES (?,?,?,?)"
	        );
	        ps.setInt(1, idProfesor);
	        ps.setInt(2, idAlumno);
	        ps.setString(3, asignatura);
	        ps.setInt(4, curso);
	        ps.executeUpdate();
	        System.out.println("Matrícula insertada.");
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

		String tablaProfes = "CREATE TABLE IF NOT EXISTS Profesores (" +
                "idProfesor INT AUTO_INCREMENT PRIMARY KEY," +
                "Nombre VARCHAR(45)," +
                "Apellidos VARCHAR(45)," +
                "FechaNacimiento DATE," +
                "Antiguedad INT)";
		return tablaProfes;
	}

	private static String tablaAlumnado() {
		String tablaAlumnado = "CREATE TABLE IF NOT EXISTS Alumnado (" +
                "idAlumno INT AUTO_INCREMENT PRIMARY KEY," +
                "Nombre VARCHAR(45)," +
                "Apellidos VARCHAR(45)," +
                "FechaNacimiento DATE)";
		return tablaAlumnado;
	}

	private static String tablaMatricula() {

		String tablaMatricula ="CREATE TABLE IF NOT EXISTS Matricula (" +
                "idMatricula INT AUTO_INCREMENT PRIMARY KEY," +
                "idProfesorado INT," +
                "idAlumno INT," +
                "Asignatura VARCHAR(45)," +
                "Curso INT," +
                "FOREIGN KEY(idProfesorado) REFERENCES Profesores(idProfesor)," +
                "FOREIGN KEY(idAlumno) REFERENCES Alumnado(idAlumno))";
		return tablaMatricula;

	}
	
	private static void imprimirProfes(ResultSet rs) {
	
		
		
	}
	

}
