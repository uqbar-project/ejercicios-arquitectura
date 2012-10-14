package model.domain;

public class Producto extends Persistible {

	private String nombre;
	private float costo;
	private float precio;

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getCosto() {
		return this.costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public float getPrecio() {
		return this.precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float ganancia() {
		return this.getPrecio() - this.getCosto();
	}

	public String getDescription() {
		return this.getNombre() + "\t" + this.getCosto() + "\t"
				+ this.getPrecio() + "\t" + this.ganancia();
	}

}
