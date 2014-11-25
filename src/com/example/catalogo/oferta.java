/**
 * Clase Oferta para poder convertir cada fila de la tabla en un objeto y poder acceder fácilmente a los valores del registro
 */
package com.example.catalogo;

public class oferta {
	private int oferta_id;
	private String oferta_nombre;
	private String oferta_descripcion;
	private String oferta_idTienda;
	
	public oferta(int oferta_id, String oferta_nombre, String oferta_descripcion, String oferta_idTienda) {
		this.oferta_id = oferta_id;
		this.oferta_nombre = oferta_nombre;
		this.oferta_descripcion = oferta_descripcion;
		this.oferta_idTienda = oferta_idTienda;
	}

	public int getOferta_id() {
		return oferta_id;
	}

	public void setOferta_id(int oferta_id) {
		this.oferta_id = oferta_id;
	}

	public String getOferta_nombre() {
		return oferta_nombre;
	}

	public void setOferta_nombre(String oferta_nombre) {
		this.oferta_nombre = oferta_nombre;
	}

	public String getOferta_descripcion() {
		return oferta_descripcion;
	}

	public void setOferta_descripcion(String oferta_descripcion) {
		this.oferta_descripcion = oferta_descripcion;
	}

	public String getOferta_idTienda() {
		return oferta_idTienda;
	}

	public void setOferta_idTienda(String oferta_idTienda) {
		this.oferta_idTienda = oferta_idTienda;
	}
	
	
}
