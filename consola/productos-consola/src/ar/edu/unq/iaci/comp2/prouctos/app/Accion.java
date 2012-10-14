package ar.edu.unq.iaci.comp2.prouctos.app;

public interface Accion {

	public void ejecutar(String[] args);

	public boolean aceptar(String[] args);

	public String ayuda();

}
