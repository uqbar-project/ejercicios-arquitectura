package model.domain.persistence.database;

import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;
import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;
import ar.edu.unq.iaci.comp2.prouctos.app.database.JdbcTransactionManager;

public class DataBaseConfiguration {

	public void execute() {
		this.addTransactionManager();
		this.addHomes();
	}

	private void addTransactionManager() {
		JdbcTransactionManager transactionManager = new JdbcTransactionManager();
		transactionManager.setDriverClass("com.mysql.jdbc.Driver");
		transactionManager
				.setConnectionString("jdbc:mysql://localhost/supermercado");
		transactionManager.setUser("cajero");
		transactionManager.setPass("cajero");
		ApplicationContext.getInstance().put(TransactionManager.class,
				transactionManager);
	}

	private void addHomes() {
		ApplicationContext.getInstance().put(ProductoHome.class,
				new ProductoJdbcHome());

		ApplicationContext.getInstance()
				.put(ItemHome.class, new ItemJdbcHome());

	}
}
