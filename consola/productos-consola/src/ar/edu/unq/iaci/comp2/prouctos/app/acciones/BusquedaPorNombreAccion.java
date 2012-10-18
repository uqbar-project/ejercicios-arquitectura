package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import java.util.List;

import model.domain.Item;

public class BusquedaPorNombreAccion extends BusquedaItemAccion {
	@Override
	public boolean correspondeA(String[] parametros) {
		return parametros.length == 2 && parametros[0].equals("buscarPorNombre");
	}

	@Override
	public String ayuda() {
		return "buscarPorNombre [NOMBRE]";
	}

	@Override
	protected List<Item> buscar(String[] parametros) {
		return this.getItemHome().buscarPorNombre(parametros[1]);
	}
}
