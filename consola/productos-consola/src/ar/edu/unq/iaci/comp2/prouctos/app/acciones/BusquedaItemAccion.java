package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import java.util.List;

import model.domain.Item;
import model.domain.persistence.ItemHome;
import ar.edu.unq.iaci.comp2.prouctos.app.Accion;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;

public class BusquedaItemAccion implements Accion {

	@Override
	public void ejecutar(String[] parametros) {
		List<Item> items = this.buscar(parametros);
		System.out.println("Cant\tNombre\tCosto\tPrecio\tGanancia");
		for (Item item : items) {
			System.out.println(item.getDescription());
		}
	}

	protected List<Item> buscar(String[] parametros) {
		return this.getItemHome().todos();
	}

	protected ItemHome getItemHome() {
		return (ItemHome) ApplicationContext.obtener(ItemHome.class);
	}

	@Override
	public boolean correspondeA(String[] parametros) {
		return parametros.length == 1 && parametros[0].equals("buscar");
	}

	@Override
	public String ayuda() {
		return "buscar";
	}

}
