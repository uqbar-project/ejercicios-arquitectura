package ar.edu.unq.iaci.comp2.prouctos.app;

public interface TransactionManager {
	public void begin();

	public void commit();

	public void rollback();

}
