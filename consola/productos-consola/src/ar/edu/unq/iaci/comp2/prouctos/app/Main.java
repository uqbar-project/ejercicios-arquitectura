/**
 * 
 */
package ar.edu.unq.iaci.comp2.prouctos.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.edu.unq.iaci.comp2.prouctos.app.acciones.AgregarProductoAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.BusquedaItemAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.BusquedaPorCantidadAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.BusquedaPorNombreAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.acciones.HelpAccion;
import ar.edu.unq.iaci.comp2.prouctos.app.memory.TransactionCollectionManager;

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
		this.configurarHomes();
		this.configurarTransactionManager();
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
		this.acciones.add(new BusquedaItemAccion());
		this.acciones.add(new BusquedaPorNombreAccion());
		this.acciones.add(new BusquedaPorCantidadAccion());
	}

	private void configurarTransactionManager() {
		ApplicationContext.getInstance().put(TransactionManager.class,
				new TransactionCollectionManager());
	}

	private void configurarHomes() {
		new Fixture().execute();
		// ApplicationContext.getInstance().put(ProductoHome.class,
		// new ProductoCollectionHome());
		// ApplicationContext.getInstance().put(ItemHome.class,
		// new ItemCollectionHome());
	}

	private void ejecutarAccion(String[] args) {
		TransactionManager transacionManager = (TransactionManager) ApplicationContext
				.getInstance().get(TransactionManager.class);
		Accion accion = this.buscarAccion(args);
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

	private Accion buscarAccion(String[] args) {
		for (Accion accion : this.acciones) {
			if (accion.aceptar(args)) {
				return accion;
			}
		}
		return this.accionDefault;
	}

}
