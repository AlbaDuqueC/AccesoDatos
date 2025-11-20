package clases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
	 * 
	 * @return debuelve un boolean dependiendo de si se han podido hacer las tablas
	 *         correctamente o no
	 * @throws SQLException
	 */
	public static boolean createTableTodas() throws SQLException {

		boolean crear = false;

		String creacion = tablaProfes() + tablaAlumnado() + tablaMatricula();

		iniciarTransaccion();

		if (st.execute(creacion)) {
			confirmar();
			crear = true;
		}

		return crear;

	}

	/**
	 * Crea solo la tabla que introduzcas por parametro
	 * 
	 * @param nombre Nombre de la tabla que quieras crear
	 * @return devuelve un numero dependiendo de lo que sudaceda
	 * @throws SQLException
	 */
	public static int createTable(String nombre) throws SQLException {

		int crear = 0;

		String creacion;

		iniciarTransaccion();

		switch (nombre.toLowerCase()) {
		case "profesores" -> {

			if (!st.execute(tablaProfes())) {
				crear = -1;
			}else {
				confirmar();
			}

		}
		case "alumnado" -> {

			if (!st.execute(tablaAlumnado())) {
				crear = -1;
			}else {
				confirmar();
			}

		}
		case "matricula" -> {

			ResultSet rta = st.executeQuery("SELECT * FROM Alumnado");
			ResultSet rtp = st.executeQuery("SELECT * FROM Profesores");

			if (!st.execute(tablaMatricula())) {
				crear = -1;
			}else {
				confirmar();
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
	 * 
	 * @param nombre          Nombre del profesor
	 * @param apellido        Apellido del profesor
	 * @param FechaNacimiento Fecha de nacimiento del profesor
	 * @param Antiguedad      Numeoro de años que lleva dando clase (antiguedad )
	 * @return devuelve un boolean dependiendo de si se ha podido inertar bien
	 * @throws SQLException
	 */
	public static void insertarProfes(String nombre, String apellido, String FechaNacimiento, int Antiguedad)
			throws SQLException {

		iniciarTransaccion();

		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO Profesores(Nombre,Apellidos,FechaNacimiento,Antiguedad) VALUES (?,?,?,?)");
		ps.setString(1, nombre);
		ps.setString(2, apellido);
		ps.setString(3, FechaNacimiento);
		ps.setInt(4, Antiguedad);
		ps.executeUpdate();
		
		confirmar();
		
		System.out.println("Profesor insertado.");
		
		
	}

	/**
	 * Inserta los datos introducidos por parametro de un alumno para guardarlo en
	 * la base de datos
	 * 
	 * @param nombre          Nombre del Alumno
	 * @param apellido        Apellido del Alumno
	 * @param fechaNacimiento Fecha de nacimiento del alumno
	 * @return Devuelve un boolean dependiendo si se ha podido hacer la operacion en
	 *         la base de datos o no
	 * @throws SQLException
	 */
	public static void insertarAlumno(String nombre, String apellido, String fechaNacimiento) throws SQLException {

		iniciarTransaccion();
		
		PreparedStatement ps = con
				.prepareStatement("INSERT INTO Alumnado(Nombre,Apellidos,FechaNacimiento) VALUES (?,?,?)");
		ps.setString(1, nombre);
		ps.setString(2, apellido);
		ps.setString(3, fechaNacimiento);
		ps.executeUpdate();
		
		confirmar();
		
		System.out.println("Alumno insertado.");
	}

	/**
	 * Inserta los datos introducidos por parametro de la Matricula
	 * 
	 * @param id         ID de la Matricula
	 * @param idProfesor ID del profesor
	 * @param idAlumno   ID del alumno
	 * @param asignatura Asignatura la cual queremos matricularnos
	 * @param curso      Curso en el que se va a matricular
	 * @return Devuelve un tipo entero
	 * @throws SQLException
	 */
	public static int insertarMatricula(int idProfesor, int idAlumno, String asignatura, int curso)
			throws SQLException {

		int insertar;
		
		iniciarTransaccion();
		
		PreparedStatement ps = con
				.prepareStatement("INSERT INTO Matricula(idProfesorado,idAlumno,Asignatura,Curso) VALUES (?,?,?,?)");
		ps.setInt(1, idProfesor);
		ps.setInt(2, idAlumno);
		ps.setString(3, asignatura);
		ps.setInt(4, curso);

		insertar = ps.executeUpdate();
		
		confirmar();

		return insertar;

	}

	// LISTAR

	/**
	 * Imprime la tabla que pasa por parametro
	 * 
	 * @param tabla Nombre de la tabla que hay que imprimir
	 * @throws SQLException
	 */
	public static void listar(String tabla) throws SQLException {

		String sql = "SELECT * FROM " + tabla;
		
		iniciarTransaccion();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		ResultSetMetaData meta = rs.getMetaData();
		int columnas = meta.getColumnCount();

		while (rs.next()) {
			for (int i = 1; i <= columnas; i++) {
				System.out.print(meta.getColumnName(i) + ": " + rs.getString(i) + " | ");
			}
			System.out.println();
		}
	}

	/**
	 * Lista dependiendo del valor que quieras buscar
	 * 
	 * @param tabla tabla que queramos imprimir
	 * @param valor valor por el cual queramos filtrar
	 * @throws SQLException
	 */
	public static void listarWhere(String tabla, String valor) throws SQLException {

		String lista = "SELECT * FROM " + tabla + "WHERE " + "LIKE %" + valor + "%";
		
		iniciarTransaccion();

		PreparedStatement ps = con.prepareStatement(lista);
		ps.setString(1, valor);
		ResultSet rs = ps.executeQuery();

		ResultSetMetaData meta = rs.getMetaData();
		int columnas = meta.getColumnCount();

		while (rs.next()) {
			for (int i = 1; i <= columnas; i++) {
				System.out.print(meta.getColumnName(i) + ": " + rs.getString(i) + " | ");
			}
			System.out.println();
		}

	}

	// MODIFICAR

	/**
	 * La funcion modificar sirve para modificar un dato de una tabla en concretp,
	 * la cual pasaremos por parametro
	 * 
	 * @param tabla          El nombre de la tabla que queramos modificar
	 * @param campoFiltro    El nombre del campo para filtrar
	 * @param valorFiltro    El valor que queramos filtrar
	 * @param campoModificar El campo que queramos modificar
	 * @param nuevoValor     El nuevo valor que queramos agregar
	 * @throws SQLException
	 */
	public static void modificar(String tabla, String campoFiltro, String valorFiltro, String campoModificar,
			String nuevoValor) throws SQLException {
		
		iniciarTransaccion();

		con.setAutoCommit(false);

		String sqlUpdate = "UPDATE " + tabla + " SET " + campoModificar + " = ? WHERE " + campoFiltro + " = ?";
		PreparedStatement ps = con.prepareStatement(sqlUpdate);
		ps.setString(1, nuevoValor);
		ps.setString(2, valorFiltro);
		ps.executeUpdate();

		String sqlSelect = "SELECT * FROM " + tabla + " WHERE " + campoFiltro + " = ?";
		PreparedStatement ps2 = con.prepareStatement(sqlSelect);
		ps2.setString(1, valorFiltro);
		ResultSet rs = ps2.executeQuery();

		System.out.println("\n REGISTROS MODIFICADOS (PENDIENTES DE CONFIRMAR) ");

		ResultSetMetaData meta = rs.getMetaData();
		int columnas = meta.getColumnCount();

		while (rs.next()) {
			for (int i = 1; i <= columnas; i++) {
				System.out.print(meta.getColumnName(i) + ": " + rs.getString(i) + " | ");
			}
			System.out.println();
		}

	}

	/**
	 * FUncion que confirma la modificacion hecha
	 * 
	 * @param start parametro que nos dira si quiere modificarlo o no
	 * @return devuelve un boolean que nos dira si se ha podido yhacer la
	 *         modificación o no
	 * @throws SQLException
	 */
	public static void StartModificar(boolean start) throws SQLException {

		String stm;

		if (start) {

			confirmar();

		} else {

			cancelar();

		}

	}

	// ELIMINAR

	/**
	 * Funcion que borra todos los datos de la tabla por completo
	 * 
	 * @param tabla Nombre de la tabla que queramos borrar
	 * @throws SQLException
	 */
	public void borrarTablaCompleta(String tabla) throws SQLException {
		String sql = "DELETE FROM " + tabla;
		
		iniciarTransaccion();
		
		Statement st = con.createStatement();
		st.executeUpdate(sql);
	}

	/**
	 * Funcion que borra por filtrado
	 * 
	 * @param tabla       Nombre de la tabla para borrar
	 * @param campoFiltro Nombre del campo para filtrar
	 * @param valorFiltro Valor por el cual filtrar
	 * @throws SQLException
	 */
	public void borrarFiltrando(String tabla, String campoFiltro, String valorFiltro) throws SQLException {
		
		String sql = "DELETE FROM " + tabla + " WHERE " + campoFiltro + " = ?";
		
		iniciarTransaccion();
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, valorFiltro);
		ps.executeUpdate();
	}

	/**
	 * Elimina todas las tablas
	 * 
	 * @throws SQLException
	 */
	public void eliminarTodasLasTablas() throws SQLException {
		// Orden correcto: primero las dependientes
		String[] tablas = { "Matricula", "Alumnado", "Profesores" };
		
		iniciarTransaccion();
		
		for (String t : tablas) {
			Statement st = con.createStatement();
			st.executeUpdate("DROP TABLE IF EXISTS " + t);
		}
	}

	/**
	 * Elimina una tabla en concreto
	 * 
	 * @param tabla
	 * @throws SQLException
	 */
	public static void eliminarTabla(String tabla) throws SQLException {
		
		iniciarTransaccion();
		
		Statement st = con.createStatement();
		st.executeUpdate("DROP TABLE IF EXISTS " + tabla);
	}

	// FUNCIONES PUBLICAS NECESARIAS

	/**
	 * Funcion que devuelve las tablas que hay
	 * 
	 * @return Devuelve un array con el nombre de las tablas
	 */
	public String[] getTablasSistema() {
		return new String[] { "Matricula", "Alumnado", "Profesores" };
	}

	/**
	 * Funcion que inicia la trasaccion
	 * 
	 * @throws SQLException
	 */
	public static void iniciarTransaccion() throws SQLException {
		con.setAutoCommit(false);
	}

	/**
	 * Funcion que confirma, hace un commit
	 * 
	 * @throws SQLException
	 */
	public static void confirmar() throws SQLException {
		con.commit();
		con.setAutoCommit(true);
	}

	/**
	 * Funcion que cancela, hace un rollback
	 * 
	 * @throws SQLException
	 */
	public static void cancelar() throws SQLException {
		con.rollback();
		con.setAutoCommit(true);
	}

	// FUNCIONES PRIVADAS

	/**
	 * Funcion privada que devuelve la creacion de la tabla d Profesores
	 * 
	 * @return Devuleve un string con el codifgo de la creacion de la tabla
	 */
	private static String tablaProfes() {

		String tablaProfes = "CREATE TABLE Profesores ( idProfesor INT AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(45), Apellidos VARCHAR(45), FechaNacimiento DATE, Antiguedad INT);";
		return tablaProfes;
	}

	/**
	 * Funcion privada que devuelve la creacion de la tabla d Alumnado
	 * 
	 * @return Devuleve un string con el codifgo de la creacion de la tabla
	 */
	private static String tablaAlumnado() {
		String tablaAlumnado = "CREATE TABLE Alumnado ( idAlumno INT AUTO_INCREMENT PRIMARY KEY,"
				+ "Nombre VARCHAR(45), Apellidos VARCHAR(45), FechaNacimiento DATE);";
		return tablaAlumnado;
	}

	/**
	 * Funcion privada que devuelve la creacion de la tabla d matricula
	 * 
	 * @return Devuleve un string con el codifgo de la creacion de la tabla
	 */
	private static String tablaMatricula() {

		String tablaMatricula = "CREATE TABLE Matricula ( idMatricula INT AUTO_INCREMENT PRIMARY KEY,"
				+ "idProfesorado INT, idAlumno INT, Asignatura VARCHAR(45), Curso INT, "
				+ "FOREIGN KEY(idProfesorado) REFERENCES Profesores(idProfesor),"
				+ "FOREIGN KEY(idAlumno) REFERENCES Alumnado(idAlumno));";
		return tablaMatricula;

	}

}
