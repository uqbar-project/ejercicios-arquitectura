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
 * Esta es la clase por la que empieza mi aplicación
 * 
 */
public class Main {

	/** La lista de las posibles acciones a ejecutar */
	private final List<Accion> acciones = new ArrayList<Accion>();
	/**
	 * Si los parámetros recibidos no concuerdan con una acción en particular,
	 * se ejecuta esta accion default
	 */
	private Accion accionDefault;

	public static void main(String[] args) {
		// Instancio un main, para tener un objeto con el cual trabajar.
		Main main = new Main();
		// Configuro la aplicación
		main.iniciar();
		// Le pido al objeto main que ejecute la acción que corresponde a los
		// parámetros
		main.ejecutarAccion(args);
	}

	/**
	 * Hace todas las configuraciones que necesita la aplicación
	 */
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

	/**
	 * Este método llena la lista de acciones con las acciones espećificas del
	 * negocio. Si se necesita agregar un nuevo tipo de acción, entonces hay que
	 * agregar una línea dentro de este método
	 */
	private void configurarAcciones() {
		this.acciones.add(new AgregarProductoAccion());
		this.acciones.add(new Vender());
		this.acciones.add(new BusquedaItemAccion());
		this.acciones.add(new BusquedaPorNombreAccion());
		this.acciones.add(new BusquedaPorCantidadAccion());
		this.acciones.add(new LimpiarBase());
	}

	/**
	 * Pone en el ApplicationContext los objetos que pueden ser accedidos desde
	 * cualquier lado, a través de una interfaz.
	 */
	private void configurarApplicationContext() {
		// Esta línea genera la configuración para usar la base de datos.
		new DataBaseConfiguration().execute();
		// Si no se quiere usar una base de datos, y usar objetos en memoria,
		// entonces hay que reemplazar
		// la linea anterior por la siguiente.
		// new MemoryConfiguration().execute();
	}

	/**
	 * Ejecuta la acción que corresponde a estos parámetros
	 */
	private void ejecutarAccion(String[] parametros) {

		// Busco la acción que corresponde al parámetro.
		Accion accion = this.buscarAccion(parametros);
		// Busco el objeto que me maneja la transaccion
		TransactionManager transacionManager = this.getTransactionManager();

		System.out.println("Ejecutando " + accion.getClass().getSimpleName()
				+ " con argumentos: " + Arrays.toString(parametros));

		// Inicio la transaccion, ejecuto la accion, y si hay algun problema
		// entonces hago un rollback.
		// Si hay un error salgo con la excepción!
		transacionManager.begin();
		try {
			accion.ejecutar(parametros);
			transacionManager.commit();
		} catch (RuntimeException e) {
			transacionManager.rollback();
			throw e;
		}
	}

	/**
	 * Metodo helper para obtener un transactionManager del ApplicationContext.
	 */
	protected TransactionManager getTransactionManager() {
		return (TransactionManager) ApplicationContext
				.obtener(TransactionManager.class);
	}

	/**
	 * Busca una accion, y si no se encuentra devuelve la default
	 */
	private Accion buscarAccion(String[] args) {
		for (Accion accion : this.acciones) {
			if (accion.correspondeA(args)) {
				return accion;
			}
		}
		return this.accionDefault;
	}

}
