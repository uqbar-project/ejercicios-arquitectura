package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import model.domain.persistence.ItemHome;
import model.domain.persistence.ProductoHome;
import ar.edu.unq.iaci.comp2.prouctos.app.Accion;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;

public class LimpiarBase implements Accion {

	@Override
	public void ejecutar(String[] parametros) {
		ItemHome itemHome = this.buscarItemHome();
		itemHome.borrarTodo();

		ProductoHome productoHome = this.buscarProductoHome();
		productoHome.borrarTodo();

	}

	protected ProductoHome buscarProductoHome() {
		return (ProductoHome) ApplicationContext.getInstance().get(
				ProductoHome.class);
	}

	protected ItemHome buscarItemHome() {
		return (ItemHome) ApplicationContext.getInstance().get(ItemHome.class);
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
