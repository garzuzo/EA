package Mundo;

public class EstadoMoore {

	String estado;

	public EstadoMoore(String estado, String estimulo1, EstadoMoore sigEstado1, String estimulo2,
			EstadoMoore sigEstado2, String respuesta) {

		this.estado = estado;
		this.estimulo1 = estimulo1;
		this.sigEstado1 = sigEstado1;
		this.estimulo2 = estimulo2;
		this.sigEstado2 = sigEstado2;
		this.respuesta = respuesta;
	}

	String estimulo1;
	EstadoMoore sigEstado1;
	String estimulo2;
	EstadoMoore sigEstado2;
	String respuesta;
	//verifica si se puede acceder mediante el estado inicial
boolean alcanzable;
	public EstadoMoore(String estado) {
		this.estado = estado;
	}
}
