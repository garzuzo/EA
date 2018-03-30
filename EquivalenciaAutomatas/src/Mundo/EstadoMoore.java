package Mundo;

import java.util.ArrayList;

public class EstadoMoore {

	String estado;

	public EstadoMoore(String estado, ArrayList<EstadoMoore> sigEstados, String respuesta) {

		this.estado = estado;
		this.respuesta = respuesta;
	}

	ArrayList<EstadoMoore> sigEstados;
	String respuesta;
	// verifica si se puede acceder mediante el estado inicial
	boolean alcanzable;

	public EstadoMoore(String estado) {
		this.estado = estado;
	}
}
