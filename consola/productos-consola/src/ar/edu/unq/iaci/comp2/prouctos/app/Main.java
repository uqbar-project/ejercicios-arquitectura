/**
 * 
 */
package ar.edu.unq.iaci.comp2.prouctos.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.domain.persistence.database.DataBaseConfiguration;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.AgregarProductoAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.BusquedaItemAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.BusquedaPorCantidadAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.BusquedaPorNombreAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.HelpAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.LimpiarBase;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.Vender;

/**
 * @author leo
 * 
 */
public class Main {

	private final List<Accion> acciones = new ArrayList<Accion>();
	private Accion accionDefault;

	public static void main(String[] args) {
		Main main = new Main();
		main.iniciar();
		main.ejecutarAccion(args);
	}

	private void iniciar() {
		this.configurarApplicationContext();
		this.configurarAcciones();
		this.configurarAccionDefault();
	}

	private void configurarAccionDefault() {
		HelpAccion helpAccion = new HelpAccion();
		helpAccion.setAcciones(this.acciones);
		this.accionDefault = helpAccion;
	}

	private void configurarAcciones() {
		this.acciones.add(new AgregarProductoAccion());
		this.acciones.add(new Vender());
		this.acciones.add(new BusquedaItemAccion());
		this.acciones.add(new BusquedaPorNombreAccion());
		this.acciones.add(new BusquedaPorCantidadAccion());
		this.acciones.add(new LimpiarBase());
	}

	private void configurarApplicationContext() {
		// new MemoryConfiguration().execute();
		new DataBaseConfiguration().execute();
	}

	private void ejecutarAccion(String[] args) {

		Accion accion = this.buscarAccion(args);
		TransactionManager transacionManager = this.getTransactionManager();

		System.out.println("Ejecutando " + accion.getClass().getSimpleName()
				+ " con argumentos: " + Arrays.toString(args));

		transacionManager.begin();
		try {
			accion.ejecutar(args);
			transacionManager.commit();
		} catch (RuntimeException e) {
			transacionManager.rollback();
			throw e;
		}
	}

	protected TransactionManager getTransactionManager() {
		return (TransactionManager) ApplicationContext.getInstance().get(
				TransactionManager.class);
	}

	private Accion buscarAccion(String[] args) {
		for (Accion accion : this.acciones) {
			if (accion.correspondeA(args)) {
				return accion;
			}
		}
		return this.accionDefault;
	}

}
