/**
 * Clase Tienda para poder convertir cada fila de la tabla en un objeto y poder acceder fácilmente a los valores del registro
 */
package com.example.catalogo;

public class tienda {
	private int tienda_ID;
	private String tienda_nombre;
	private String tienda_descripcion;
	private String tienda_imagen;
	
	public tienda(int tienda_ID, String tienda_nombre, String tienda_descripcion, String tienda_imagen){
		this.tienda_ID = tienda_ID;
		this.tienda_nombre = tienda_nombre;
		this.tienda_descripcion = tienda_descripcion;
		this.tienda_imagen = tienda_imagen;
	}

	public int getTienda_ID() {
		return tienda_ID;
	}

	public void setTienda_ID(int tienda_ID) {
		this.tienda_ID = tienda_ID;
	}

	public String getTienda_nombre() {
		return tienda_nombre;
	}

	public void setTienda_nombre(String tienda_nombre) {
		this.tienda_nombre = tienda_nombre;
	}

	public String getTienda_descripcion() {
		return tienda_descripcion;
	}

	public void setTienda_descripcion(String tienda_descripcion) {
		this.tienda_descripcion = tienda_descripcion;
	}

	public String getTienda_imagen() {
		return tienda_imagen;
	}

	public void setTienda_imagen(String tienda_imagen) {
		this.tienda_imagen = tienda_imagen;
	}
	
}
