package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import model.domain.Item;
import model.domain.Producto;
import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.Accion;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;

public class AgregarProductoAccion implements Accion {

	@Override
	public void ejecutar(String[] parametros) {
		Producto producto = this.crearProducto(parametros);
		this.crearItem(parametros, producto);

	}

	protected void crearItem(String[] args, Producto producto) {
		Item item = new Item();
		item.setProducto(producto);
		item.setCantidad(Integer.parseInt(args[4]));
		ItemHome itemHome = (ItemHome) ApplicationContext
				.obtener(ItemHome.class);
		itemHome.insert(item);
		System.out.println("Item insertado");
	}

	protected Producto crearProducto(String[] args) {
		Producto producto = new Producto();
		producto.setNombre(args[1]);
		producto.setCosto(Integer.parseInt(args[2]));
		producto.setPrecio(Integer.parseInt(args[3]));
		ProductoHome productoHome = (ProductoHome) ApplicationContext
				.obtener(ProductoHome.class);
		productoHome.insert(producto);
		System.out.println("Producto insertado");
		return producto;
	}

	@Override
	public boolean correspondeA(String[] parametros) {
		return parametros.length == 5 && parametros[0].equals("agregar");
	}

	@Override
	public String ayuda() {
		return "agregar [NOMBRE] [COSTO] [PRECIO] [CANTIDAD]";
	}
}