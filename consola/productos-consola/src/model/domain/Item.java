package model.domain;

public class Item extends Persistible {
	private Producto producto;
	private int cantidad;

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public String getDescription() {
		return this.cantidad + "\t" + this.producto.getDescription();
	}

	public float vender(int cuantos) {
		this.cantidad -= cuantos;
		return cuantos * this.producto.ganancia();
	}

}
