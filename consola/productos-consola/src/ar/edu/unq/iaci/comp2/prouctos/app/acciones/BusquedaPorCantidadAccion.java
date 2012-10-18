package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import java.util.List;

import model.domain.Item;

public class BusquedaPorCantidadAccion extends BusquedaItemAccion {
	@Override
	public String ayuda() {
		return "buscarCantidadMenorA [CANTIDAD]";
	}

	@Override
	public boolean correspondeA(String[] parametros) {
		return parametros.length == 2 && parametros[0].equals("buscarCantidadMenorA");
	}

	@Override
	protected List<Item> buscar(String[] parametros) {
		return this.getItemHome()
				.cantidadMenorIgualA(Integer.parseInt(parametros[1]));
	}
}
