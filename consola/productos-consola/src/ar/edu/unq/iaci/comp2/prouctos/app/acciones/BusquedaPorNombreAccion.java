package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import java.util.List;

import model.domain.Item;

public class BusquedaPorNombreAccion extends BusquedaItemAccion {
	@Override
	public boolean aceptar(String[] args) {
		return args.length == 2 && args[0].equals("buscarPorNombre");
	}

	@Override
	public String ayuda() {
		return "buscarPorNombre [NOMBRE]";
	}

	@Override
	protected List<Item> buscar(String[] args) {
		return this.getItemHome().buscarPorNombre(args[1]);
	}
}
