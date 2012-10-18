package ar.edu.unq.iaci.comp2.prouctos.app.acciones;

import model.domain.Item;
import model.domain.persistence.ItemHome;
import ar.edu.unq.iaci.comp2.prouctos.app.Accion;
import ar.edu.unq.iaci.comp2.prouctos.app.ApplicationContext;

public class Vender implements Accion {

	@Override
	public void ejecutar(String[] parametros) {
		ItemHome itemHome = (ItemHome) ApplicationContext.getInstance().get(
				ItemHome.class);
		Item item = itemHome.buscarPorNombre(parametros[1]).get(0);
		float ganancia = item.vender(Integer.parseInt(parametros[2]));
		itemHome.update(item);
		System.out
				.println("La ganancia de la venta de " + parametros[2]
						+ " unidades del producto " + parametros[1] + " es "
						+ ganancia);
	}

	@Override
	public boolean correspondeA(String[] parametros) {
		return parametros.length == 3 && parametros[0].equals("vender");
	}

	@Override
	public String ayuda() {
		return "vender [NOMBRE] [CANTIDAD]";
	}

}
