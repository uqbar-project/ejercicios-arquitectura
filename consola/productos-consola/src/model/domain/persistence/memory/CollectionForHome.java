package model.domain.persistence.memory;

import java.util.ArrayList;
import java.util.List;

import model.domain.Persistible;

/**
 * Clase de ayuda para no repetir codigo entre las homes para test
 * 
 * @author leo
 * 
 */
// Esto lo digo para que el compilador no tire warnings
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionForHome {

	int id = 1;
	private final List list = new ArrayList();

	public Object findById(int id) {
		for (Object object : this.getList()) {
			Persistible persistible = (Persistible) object;
			if (persistible.getId() == id) {
				return persistible;
			}
		}
		return null;
	}

	public void insert(Object object) {
		Persistible persitible = (Persistible) object;
		persitible.setId(this.createId());
		this.getList().add(persitible);
	}

	private int createId() {
		return this.id++;
	}

	public void update(Object object) {
		// no hace falta hacer nada
	}

	public void delete(Object object) {
		this.getList().remove(object);
	}

	protected List getList() {
		return this.list;
	}

	@SuppressWarnings("unchecked")
	protected List cloneList() {
		return new ArrayList(this.getList());
	}

	public void clear() {
		this.list.clear();
	}

}
