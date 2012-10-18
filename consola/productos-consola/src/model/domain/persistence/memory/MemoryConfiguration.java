package model.domain.persistence.memory;

import model.domain.Item;
import model.domain.Producto;
import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;
import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;
import ar.edu.unq.iaci.comp2.prouctos.app.memory.TransactionCollectionManager;

/**
 * Agrega al application context los objetos necesarios para trabajar contra una
 * "falsa" persistencia, ya que guarda los objetos en memoria
 * 
 */
public class MemoryConfiguration {

	/**
	 * Agrega el transactionManager, las homes, y algunos objetos de prueba
	 */
	public void execute() {
		this.addTransactionManager();
		this.addHomes();
		this.addObjects();
	}

	private void addTransactionManager() {
		// Este codigo es muy parecido a al de DatabaseConfiguration, pero
		// cambia el objeto que registra bajo la misma key. Es decir, cuando se
		// le pida al ApplicationContext algo con la key "TransactionManager",
		// el mismo va a devolver un objeto que implementa esa interface, pero
		// la clase concreta es TransactionCollectionManager
		ApplicationContext.registrar(TransactionManager.class,
				new TransactionCollectionManager());
	}

	/**
	 * Agrega objetos a las homes.
	 */
	protected void addObjects() {
		this.add(5, "Manzana", 1, 3);
		this.add(3, "Naranja", 2, 4);
		this.add(4, "Papa", 1, 3);

	}

	protected void add(int cantidad, String nombre, float costo, float precio) {
		Producto producto = this.createProducto(nombre, costo, precio);
		this.createItem(cantidad, producto);
	}

	protected void createItem(int cantidad, Producto producto) {
		Item item = new Item();
		item.setProducto(producto);
		item.setCantidad(cantidad);
		ItemHome itemHome = (ItemHome) ApplicationContext
				.obtener(ItemHome.class);
		itemHome.insert(item);
	}

	protected Producto createProducto(String nombre, float costo, float precio) {
		Producto producto = new Producto();
		producto.setCosto(costo);
		producto.setPrecio(precio);
		producto.setNombre(nombre);
		ProductoHome productoHome = (ProductoHome) ApplicationContext
				.obtener(ProductoHome.class);
		productoHome.insert(producto);
		return producto;
	}

	/**
	 * Agrega las homes
	 */
	private void addHomes() {
		// Las homes que agregan invocan a collecciones en memoria en lugar de
		// ir a una base de datos. Se registran en nombre de la interface
		// (ProductoHome e ItemHome) pero las clases concretas son otras
		// (ProductoCollectionHome e ItemCollectionHome)
		ApplicationContext.registrar(ProductoHome.class,
				new ProductoCollectionHome());
		ApplicationContext.registrar(ItemHome.class, new ItemCollectionHome());
	}
}
