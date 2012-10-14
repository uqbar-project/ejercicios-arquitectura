package model.domain.persistence;

import model.domain.Producto;

public interface ProductoHome {
	Producto findById(int code);

	void insert(Producto object);

	void update(Producto object);

	void delete(Producto object);
}
