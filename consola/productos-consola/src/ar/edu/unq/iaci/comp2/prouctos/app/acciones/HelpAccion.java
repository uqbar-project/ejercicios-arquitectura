package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import java.util.List;

import ar.edu.unq.iaci.comp2.prouctos.app.Accion;

public class HelpAccion implements Accion {

	private List<Accion> acciones;

	@Override
	public void ejecutar(String[] parametros) {
		System.out.println("Posibilidades\n");
		for (Accion accion : this.acciones) {
			System.out.println("\t" + accion.ayuda());
		}
	}

	@Override
	public boolean correspondeA(String[] parametros) {
		return true;
	}

	@Override
	public String ayuda() {
		return "help";
	}

	public List<Accion> getAcciones() {
		return this.acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

}
