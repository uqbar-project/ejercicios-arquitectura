package ar.edu.unq.iaci.comp2.prouctos.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;

public class JdbcTransactionManager implements TransactionManager {

	private String driverClass;
	private String connectionString;
	private String user;
	private String pass;
	private Connection connection;

	@Override
	public void begin() {
		try {
			Class.forName(this.driverClass);
			this.connection = DriverManager.getConnection(
					this.connectionString, this.getUser(), this.getPass());
			this.connection.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void commit() {
		try {
			this.getConnection().commit();
			this.safeClose();
		} catch (SQLException e) {
			this.safeClose();
			throw new RuntimeException(e);
		}
	}

	private void safeClose() {
		try {
			this.connection.close();
		} catch (Exception e) {
			// nothing
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
}