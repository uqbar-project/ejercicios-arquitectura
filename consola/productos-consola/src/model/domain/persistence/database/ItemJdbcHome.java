package model.domain.persistence.database;

import java.util.List;

import model.domain.Item;
import model.domain.persistence.ItemHome;

public class ItemJdbcHome implements ItemHome {

	@Override
	public Item findById(int code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Item object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Item object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Item object) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Item> buscarPorNombre(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> cantidadMenorIgualA(Integer cantidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> buscarPorNombreYCantidad(String nombre, Integer cantidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> todos() {
		// TODO Auto-generated method stub
		return null;
	}

}
