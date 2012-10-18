package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.Accion;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;

/**
 * Borra toda la base de datos
 * 
 * @author leo
 * 
 */
public class LimpiarBase implements Accion {

	@Override
	public void ejecutar(String[] parametros) {
		ItemHome itemHome = this.buscarItemHome();
		itemHome.borrarTodo();

		ProductoHome productoHome = this.buscarProductoHome();
		productoHome.borrarTodo();

	}

	protected ProductoHome buscarProductoHome() {
		return (ProductoHome) ApplicationContext.obtener(ProductoHome.class);
	}

	protected ItemHome buscarItemHome() {
		return (ItemHome) ApplicationContext.obtener(ItemHome.class);
	}

	@Override
	public boolean correspondeA(String[] parametros) {
		return parametros.length == 1 && parametros[0].equals("limpiar");
	}

	@Override
	public String ayuda() {
		return "limpiar";
	}

}
