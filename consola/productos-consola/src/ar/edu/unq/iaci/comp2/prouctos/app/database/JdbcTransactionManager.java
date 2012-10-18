package ar.edu.unq.iaci.comp2.prouctos.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;

import com.mysql.jdbc.Statement;

public class JdbcTransactionManager implements TransactionManager {

	/** Nombre de la clase que se usa para acceder a la base de datos */
	private String driverClass;
	/** String de jdbc para poder conectarse a la base de datos */
	private String connectionString;
	/** usuario de la base de datos */
	private String user;

	/** password de la base de datos */
	private String pass;
	/**
	 * Es la conexion que se usa para acceder a la base de datos
	 */
	private Connection connection;

	/**
	 * Obtiene una conexcion y se la guarda
	 */
	@Override
	public void begin() {

		try {
			// Carga la clase del driver
			Class.forName(this.driverClass);
			// Obtiene la coneccion
			this.connection = DriverManager.getConnection(
					this.connectionString, this.getUser(), this.getPass());
			// Le indica que no commitee hasta que se le indique
			this.connection.setAutoCommit(false);
		} catch (Exception e) {
			// ante cualquier problema, tiro la excepcion hacia arriba de la
			// forma que le gusta a java
			throw new RuntimeException(e);
		}
	}

	@Override
	public void commit() {
		try {
			this.getConnection().commit();
			this.safeClose();
		} catch (SQLException e) {
			// si hay un error tengo que salir con excepcion, pero antes tengo
			// que cerrar la conexion.
			this.safeClose();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Cierra la conexion, si ocurrio un error lo ignora. Los metodos del estilo
	 * "safe" (que no tiran excepcion) solo son usados en casos especiales, en
	 * los cuales puede haber otra excepción en juego.
	 */
	private void safeClose() {
		try {
			this.connection.close();
		} catch (Exception e) {
			// no tengo que hacer nada
		}
	}

	@Override
	public void rollback() {
		try {
			this.getConnection().rollback();
		} catch (SQLException e) {
			// El rollback se ejecuta mientras hay una exception, asi que no hay
			// que hacer nada
		}
		this.safeClose();
	}

	public String getDriverClass() {
		return this.driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getConnectionString() {
		return this.connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * Pide un preparedStatement a la conexion.
	 * 
	 * @param sql
	 * @return
	 */
	public PreparedStatement getPreparedStatement(String sql) {
		try {
			return this.getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
		} catch (Exception ex) {
			throw new RuntimeException("Error al generar el preparedStatement"
					+ sql, ex);
		}
	}

	/**
	 * Cierra en forma segura un resultSet
	 * 
	 * @param rs
	 */
	public void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				// las cosas que se cierran no deben lanzar excepcion porque
				// estan siendo usadas en contextos
				// en los cuales ya hay una excepcion
			}
		}
	}

	/**
	 * Cierra en forma segura un PreparedStatement (no tira excepcion)
	 * 
	 * @param st
	 */
	public void safeClose(PreparedStatement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				// las cosas que se cierran no deben lanzar excepcion porque
				// estan siendo usadas en contextos
				// en los cuales ya hay una excepcion
			}
		}
	}

}