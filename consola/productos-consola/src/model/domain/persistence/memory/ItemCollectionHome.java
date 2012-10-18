package model.domain.persistence.memory;

import java.util.ArrayList;
import java.util.List;

import model.domain.Item;
import model.domain.Persistible;
import model.domain.persistence.ItemHome;

@SuppressWarnings("unchecked")
public class ItemCollectionHome implements ItemHome {

	private final CollectionForHome collectionForHome = new CollectionForHome();

	@Override
	public List<Item> buscarPorNombre(String string) {
		return this
				.filtrarPorNombre(string, this.collectionForHome.cloneList());
	}

	@Override
	public List<Item> cantidadMenorIgualA(Integer cantidad) {
		return this.filtrarPorCantidad(cantidad,
				this.collectionForHome.cloneList());
	}

	@Override
	public List<Item> buscarPorNombreYCantidad(String nombre, Integer cantidad) {
		return this.filtrarPorCantidad(cantidad, this.buscarPorNombre(nombre));
	}

	// TODO abstraer a un predicate para evitar duplicar el algoritmo
	private List<Item> filtrarPorNombre(String string, List<Item> source) {
		if (source == null || string == null) {
			return source;
		}
		List<Item> list = new ArrayList<Item>();
		for (Persistible itemObject : source) {
			Item item = (Item) itemObject;
			if (item.getProducto().getNombre().contains(string)) {
				list.add(item);
			}
		}
		return list;
	}

	private List<Item> filtrarPorCantidad(Integer cantidad, List<Item> source) {
		if (cantidad == null) {
			return source;
		}
		List<Item> list = new ArrayList<Item>();
		for (Persistible itemObject : source) {
			Item item = (Item) itemObject;
			if (item.getCantidad() <= cantidad) {
				list.add(item);
			}
		}
		return list;
	}

	@Override
	public Item findById(int code) {
		return (Item) this.collectionForHome.findById(code);
	}

	@Override
	public void insert(Item object) {
		this.collectionForHome.insert(object);

	}

	@Override
	public void update(Item object) {
		this.collectionForHome.update(object);
	}

	@Override
	public void delete(Item object) {
		this.collectionForHome.insert(object);
	}

	@Override
	public List<Item> todos() {
		return this.collectionForHome.cloneList();
	}

	@Override
	public void borrarTodo() {
		this.collectionForHome.clear();
	}
}
