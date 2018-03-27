package Mundo;

public class EstadoMealy {

	String estado;
	String estimulo1;
	EstadoMealy sigEstado1;
	String respuesta1;
	String estimulo2;
	EstadoMealy sigEstado2;
	String respuesta2;
	public EstadoMealy(String estado,String estimulo1, EstadoMealy sigEstado1, String respuesta1, String estimulo2,
			EstadoMealy sigEstado2, String respuesta2) {
		this.estado=estado;
		this.estimulo1 = estimulo1;
		this.sigEstado1 = sigEstado1;
		this.respuesta1 = respuesta1;
		this.estimulo2 = estimulo2;
		this.sigEstado2 = sigEstado2;
		this.respuesta2 = respuesta2;
	}
	
	public EstadoMealy(String estado) {
		this.estado=estado;
	}

}
