package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import java.util.List;

import model.domain.Item;

public class BusquedaPorCantidadAccion extends BusquedaItemAccion {
	@Override
	public String ayuda() {
		return "buscarCantidadMenorA [CANTIDAD]";
	}

	@Override
	public boolean aceptar(String[] args) {
		return args.length == 2 && args[0].equals("buscarCantidadMenorA");
	}

	@Override
	protected List<Item> buscar(String[] args) {
		return this.getItemHome()
				.cantidadMenorIgualA(Integer.parseInt(args[1]));
	}
}
