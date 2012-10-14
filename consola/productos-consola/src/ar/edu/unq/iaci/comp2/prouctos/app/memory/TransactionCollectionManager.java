package ar.edu.unq.iaci.comp2.prouctos.app.memory;

import ar.edu.unq.iaci.comp2.prouctos.app.TransactionManager;

public class TransactionCollectionManager implements TransactionManager {

	@Override
	public void begin() {
		System.out
				.println("Se solicito iniciar una transaccion, pero se esta trabajando en memoria");
	}

	@Override
	public void commit() {
		System.out
				.println("Se solicito commit de una transaccion, pero se esta trabajando en memoria");
	}

	@Override
	public void rollback() {
		System.out
				.println("Se solicito rollbak de una transaccion, pero se esta trabajando en memoria");
	}

}
