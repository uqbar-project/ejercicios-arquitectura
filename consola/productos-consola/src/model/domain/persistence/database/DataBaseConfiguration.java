package model.domain.persistence.database;

import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;
import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;
import ar.edu.unq.iaci.comp2.prouctos.app.database.JdbcTransactionManager;

/**
 * Agrega la configuración de la base de datos y las homes que pueden usar la
 * base de datos.
 */
public class DataBaseConfiguration {

	/**
	 * agrega las configuraciones
	 */
	public void execute() {
		this.addTransactionManager();
		this.addHomes();
	}

	/**
	 * Agrega un transaction manager con la información de como obtener una
	 * conexión, luego pone ese objeto en el ApplicationContext para que pueda
	 * ser accedidos por todos
	 */
	private void addTransactionManager() {
		JdbcTransactionManager transactionManager = new JdbcTransactionManager();
		transactionManager.setDriverClass("com.mysql.jdbc.Driver");
		transactionManager
				.setConnectionString("jdbc:mysql://localhost/supermercado");
		transactionManager.setUser("cajero");
		transactionManager.setPass("cajero");
		// Agrega el transactionManager, es importante notar que la forma de
		// buscar
		// el objeto es con la interfaz genérica TransactionManager y no con la
		// clase concreta JdbcTransactionManager
		// ya que el que lo usa (el main) no tiene que interesarle si hay una
		// base de datos o no.
		ApplicationContext.registrar(TransactionManager.class,
				transactionManager);
	}

	/**
	 * Agrega las homes al application context
	 */
	private void addHomes() {
		// Al igual que el transactionManager, cuando se registra una home se
		// usa como clave la interfaz abstracta (ProductoHome e ItemHome), a
		// pesar de que los objetos que se agregan son de clases concretas que
		// usan la base de datos (ProductoJdbcHome e ItemJdbcHome). Esto es
		// porque aquellas acciones que necesitan usar las homes no saben si se
		// trata de homes que usan una base de datos o homes que guardan los
		// objetos en memoria, las acciones solo saben hablar con la interface
		// "ProductoHome"

		ApplicationContext
				.registrar(ProductoHome.class, new ProductoJdbcHome());

		ApplicationContext.registrar(ItemHome.class, new ItemJdbcHome());

	}
}
