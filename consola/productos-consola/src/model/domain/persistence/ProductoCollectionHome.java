package model.domain.persistence;

import model.domain.Producto;

public class ProductoCollectionHome implements ProductoHome {

	private final CollectionForHome collectionForHome = new CollectionForHome();

	@Override
	public Producto findById(int code) {
		return (Producto) this.collectionForHome.findById(code);
	}

	@Override
	public void insert(Producto object) {
		this.collectionForHome.insert(object);

	}

	@Override
	public void update(Producto object) {
		this.collectionForHome.update(object);
	}

	@Override
	public void delete(Producto object) {
		this.collectionForHome.delete(object);
	}

}
