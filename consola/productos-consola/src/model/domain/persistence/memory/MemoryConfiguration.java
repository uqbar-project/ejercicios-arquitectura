package model.domain.persistence.memory;

import model.domain.Item;
import model.domain.Producto;
import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;
import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;
import ar.edu.unq.iaci.comp2.prouctos.app.memory.TransactionCollectionManager;

public class MemoryConfiguration {

	public void execute() {
		this.addTransactionManager();
		this.addHomes();
		this.addObjects();
	}

	private void addTransactionManager() {
		ApplicationContext.getInstance().put(TransactionManager.class,
				new TransactionCollectionManager());
	}

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
		ItemHome itemHome = (ItemHome) ApplicationContext.getInstance().get(
				ItemHome.class);
		itemHome.insert(item);
	}

	protected Producto createProducto(String nombre, float costo, float precio) {
		Producto producto = new Producto();
		producto.setCosto(costo);
		producto.setPrecio(precio);
		producto.setNombre(nombre);
		ProductoHome productoHome = (ProductoHome) ApplicationContext
				.getInstance().get(ProductoHome.class);
		productoHome.insert(producto);
		return producto;
	}

	private void addHomes() {
		ApplicationContext.getInstance().put(ProductoHome.class,
				new ProductoCollectionHome());
		ApplicationContext.getInstance().put(ItemHome.class,
				new ItemCollectionHome());
	}
}
