package ar.edu.unq.iaci.comp2.prouctos.app;

public interface Accion {

	public void ejecutar(String[] parametros);

	public boolean correspondeA(String[] parametros);

	public String ayuda();

}
