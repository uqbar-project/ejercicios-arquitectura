package model.domain.persistence;

import java.util.List;

import model.domain.Item;

public interface ItemHome {

	Item findById(int code);

	void insert(Item object);

	void update(Item object);

	void delete(Item object);

	List<Item> buscarPorNombre(String string);

	List<Item> cantidadMenorIgualA(Integer cantidad);

	List<Item> buscarPorNombreYCantidad(String nombre, Integer cantidad);

	List<Item> todos();

	void borrarTodo();

}
