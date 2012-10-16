package model.domain;

public class Persistible {
	// El id negativo significa que no esta en la base
	private int id = -1;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
