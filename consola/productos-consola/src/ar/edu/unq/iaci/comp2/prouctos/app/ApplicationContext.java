package ar.edu.unq.iaci.comp2.prouctos.app;

import java.util.HashMap;
import java.util.Map;

/**
 * El application context es un lugar en el cual puedo registrar objetos. Los
 * objetos registrados pueden ser usados desde cualquier parte de la aplicación.
 * Esto es muy util cuando un objeto quiere ser creado al inicio de la
 * aplicacion (porque tiene que ser bien configurado) pero debe ser accedido
 * desde cualquier otra parte.
 * 
 * 
 * Un objeto es registrado bajo una clave. La clave es una clase/interface que
 * ese objeto posee como tipo (puede ser la clase a la que le hice new, una
 * superclase o una interface que implemente, de tal forma que si yo registre un
 * objeto usando una clave en particular, después pueda recuperar el objeto y
 * asignarlo a una variable de ese tipo.
 * 
 * Por ejemplo, si se ejecuta
 * <code>ApplicationContext.registrar(MiTipo.class, miObjeto);</code> Debe ser
 * válido en otro lugar del sistema ejecutar la siguiente linea:
 * <code>MiTipo unaReferencia = ApplicationContext.obtener(MiTipo.class)</code>
 * 
 * Algo muy importante del ApplicationContext es que me permite usar
 * polimorfismo. Ya que la clase concreta del objeto registrado puede ser
 * instancia de una subclase o de una clase que implemente una interface.
 * Entonces se pueden armar distintas configuraciones, haciendo que registren
 * objetos de distintas clases bajo la misma clave (tipo)
 * 
 * Nota de implementación: Se usan métodos y atributos de clase (static) para
 * lograr que el Application context sea único y accesible desde cualquier lado
 * 
 * @author leo
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ApplicationContext {

	private static Map map = new HashMap();

	public static Object obtener(Class tipo) {
		return map.get(tipo);
	}

	public static void registrar(Class tipo, Object objeto) {
		map.put(tipo, objeto);
	}

	public static void limpiar() {
		map = new HashMap();
	}

}
