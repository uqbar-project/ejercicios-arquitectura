package model.domain.persistence.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.domain.Producto;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;
import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;
import ar.edu.unq.iaci.comp2.prouctos.app.database.JdbcTransactionManager;

public class ProductoJdbcHome implements ProductoHome {

	@Override
	public Producto findById(int code) {

		JdbcTransactionManager manager = this.getTransactionManager();
		PreparedStatement statement = manager
				.getPreparedStatement("SELECT id, nombre, costo, precio from producto where id = ?");
		ResultSet rs = null;
		try {
			statement.setInt(1, code);
			rs = statement.executeQuery();
			if (!rs.next()) {
				return null;
			}
			Producto producto = new Producto();
			producto.setId(rs.getInt("id"));
			producto.setNombre(rs.getString("nombre"));
			producto.setCosto(rs.getFloat("costo"));
			producto.setPrecio(rs.getFloat("precio"));
			manager.safeClose(rs);
			manager.safeClose(statement);
			return producto;
		} catch (Exception e) {
			manager.safeClose(rs);
			manager.safeClose(statement);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(Producto producto) {
		JdbcTransactionManager manager = this.getTransactionManager();

		PreparedStatement statement = manager
				.getPreparedStatement("INSERT INTO producto (nombre, costo, precio) VALUES  (?,?,?) ");
		ResultSet generatedKeys = null;
		try {
			statement.setString(1, producto.getNombre());
			statement.setFloat(2, producto.getCosto());
			statement.setFloat(3, producto.getPrecio());

			int resultado = statement.executeUpdate();
			if (resultado != 1) {
				throw new RuntimeException("Se modificaron " + resultado
						+ " filas en lugar de una sola");
			}
			generatedKeys = statement.getGeneratedKeys();
			generatedKeys.next();
			producto.setId(generatedKeys.getInt(1));
			manager.safeClose(generatedKeys);
			manager.safeClose(statement);

		} catch (Exception e) {
			manager.safeClose(generatedKeys);
			manager.safeClose(statement);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Producto producto) {
		JdbcTransactionManager manager = this.getTransactionManager();

		PreparedStatement statement = manager
				.getPreparedStatement("UPDATE producto SET nombre = ?, costo = ?, precio = ? WHERE id = ?");
		try {
			statement.setString(1, producto.getNombre());
			statement.setFloat(2, producto.getCosto());
			statement.setFloat(3, producto.getPrecio());
			statement.setFloat(4, producto.getId());

			int resultado = statement.executeUpdate();
			if (resultado != 1) {
				throw new RuntimeException("Se modificaron " + resultado
						+ " filas en lugar de una sola");
			}
			manager.safeClose(statement);
		} catch (Exception e) {
			manager.safeClose(statement);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Producto object) {
		JdbcTransactionManager manager = this.getTransactionManager();

		PreparedStatement statement = manager
				.getPreparedStatement("DELETE FROM producto WHERE id = ?");
		try {
			statement.setFloat(1, object.getId());

			int resultado = statement.executeUpdate();
			if (resultado != 1) {
				throw new RuntimeException("Se modificaron " + resultado
						+ " filas en lugar de una sola");
			}
			manager.safeClose(statement);
		} catch (Exception e) {
			manager.safeClose(statement);
			throw new RuntimeException(e);
		}
	}

	protected JdbcTransactionManager getTransactionManager() {
		return (JdbcTransactionManager) ApplicationContext.getInstance().get(
				TransactionManager.class);

	}

	@Override
	public void borrarTodo() {
		JdbcTransactionManager manager = this.getTransactionManager();

		PreparedStatement statement = manager
				.getPreparedStatement("DELETE from producto");

		try {
			statement.executeUpdate();
			manager.safeClose(statement);
		} catch (Exception e) {
			manager.safeClose(statement);
			throw new RuntimeException(e);
		}
	}

}
