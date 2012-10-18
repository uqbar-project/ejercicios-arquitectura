package ar.edu.unq.iaci.comp2.prouctos.app;

/**
 * Son los comandos que pueden ser ejecutados por la aplicación.
 * 
 */
public interface Accion {

	/**
	 * Ejecuta la acción
	 * 
	 * @param parametros
	 *            es lo que envía el usuario desde la consola
	 */
	public void ejecutar(String[] parametros);

	/**
	 * Indica si la lista de parámetros corresponde a esta acción
	 * 
	 * @param parametros
	 *            es lo que envía el usuario desde la consola
	 */
	public boolean correspondeA(String[] parametros);

	/**
	 * Un string que se muestra cuando el usuario no sabe que se puede ejecutar.
	 * 
	 * @return
	 */
	public String ayuda();

}
