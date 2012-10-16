package model.domain.persistence.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.domain.Item;
import model.domain.Producto;
import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;
import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;
import ar.edu.unq.iaci.comp2.prouctos.app.database.JdbcTransactionManager;

public class ItemJdbcHome implements ItemHome {

	@Override
	public Item findById(int code) {
		List<Item> lista = this.buscar("id = " + code);
		if (lista.isEmpty()) {
			return null;
		} else {
			return lista.get(0);
		}
	}

	@Override
	public void insert(Item item) {
		JdbcTransactionManager manager = this.getTransactionManager();

		// si el primer no esta en la base lo agrego
		if (item.getProducto().getId() < 0) {
			ProductoHome productoHome = (ProductoHome) ApplicationContext
					.getInstance().get(ProductoHome.class);
			productoHome.insert(item.getProducto());
		}

		PreparedStatement statement = manager
				.getPreparedStatement("INSERT INTO Item (cantidad, producto) VALUES  (?,?) ");
		ResultSet generatedKeys = null;
		try {
			statement.setFloat(1, item.getCantidad());
			statement.setFloat(2, item.getProducto().getId());

			int resultado = statement.executeUpdate();
			if (resultado != 1) {
				throw new RuntimeException("Se modificaron " + resultado
						+ " filas en lugar de una sola");
			}
			generatedKeys = statement.getGeneratedKeys();
			generatedKeys.next();
			item.setId(generatedKeys.getInt(1));
			manager.safeClose(generatedKeys);
			manager.safeClose(statement);

		} catch (Exception e) {
			manager.safeClose(generatedKeys);
			manager.safeClose(statement);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void update(Item item) {
		JdbcTransactionManager manager = this.getTransactionManager();

		PreparedStatement statement = manager
				.getPreparedStatement("UPDATE ITEM SET cantidad = ?, SET producto = ? WHERE id = ?");
		try {
			statement.setInt(1, item.getCantidad());
			statement.setFloat(2, item.getProducto().getId());
			statement.setFloat(3, item.getId());

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
	// Una opcion seria borrar tambien el producto, ya que en el insert tambien
	// lo agrego.
	// por ahora solo borro el item.
	public void delete(Item item) {

		JdbcTransactionManager manager = this.getTransactionManager();

		PreparedStatement statement = manager
				.getPreparedStatement("DELETE FROM ITEM WHERE id = ?");
		try {
			statement.setFloat(1, item.getId());

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
	public List<Item> buscarPorNombre(String string) {
		// Como no estoy pasando un signo de pregunta en el where, tengo que
		// ponerle a manos las comillas simples
		return this.buscar("producto.nombre like '" + string + "'");
	}

	@Override
	public List<Item> cantidadMenorIgualA(Integer cantidad) {
		return this.buscar("item.cantidad <= " + cantidad);
	}

	@Override
	public List<Item> buscarPorNombreYCantidad(String nombre, Integer cantidad) {
		return this.buscar("item.cantidad <= " + cantidad
				+ " AND producto.nombre like '" + nombre + "'");
	}

	@Override
	public List<Item> todos() {
		// TODO Auto-generated method stub
		return null;
	}

	protected List<Item> buscar(String where) {
		JdbcTransactionManager manager = this.getTransactionManager();
		if (where != null) {
			where = " AND (" + where + ")";
		} else {
			where = "";
		}
		PreparedStatement statement = manager
				.getPreparedStatement("SELECT (producto.id, producto.nombre, producto.costo, producto.precio, item.id, item.costo) "
						+ "from PRODUCTO, ITEM where producto.id = item.producto"
						+ where);
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
			List<Item> list = new ArrayList<Item>();
			while (rs.next()) {
				Producto producto = new Producto();
				producto.setId(rs.getInt("producto.id"));
				producto.setNombre(rs.getString("producto.nombre"));
				producto.setCosto(rs.getFloat("producto.costo"));
				producto.setPrecio(rs.getFloat("producto.precio"));

				Item item = new Item();
				item.setId(rs.getInt("item.id"));
				item.setCantidad(rs.getInt("item.cantidad"));
				item.setProducto(producto);
				list.add(item);
			}
			manager.safeClose(rs);
			manager.safeClose(statement);
			return list;
		} catch (Exception e) {
			manager.safeClose(rs);
			manager.safeClose(statement);
			throw new RuntimeException(e);
		}
	}

	protected JdbcTransactionManager getTransactionManager() {
		return (JdbcTransactionManager) ApplicationContext.getInstance().get(
				TransactionManager.class);

	}

}
