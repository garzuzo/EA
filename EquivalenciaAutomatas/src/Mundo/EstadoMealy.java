package Mundo;

import java.util.ArrayList;

public class EstadoMealy {

	String estado;
	ArrayList<EstadoMealy> sigEstados;
	ArrayList<String> listRespuestas;
	
	
	//verifica si se puede acceder mediante el estado inicial
	boolean alcanzable;
	public EstadoMealy(String estado,ArrayList<EstadoMealy>sigEstado,ArrayList<String>listRespuestas) {
		this.estado=estado;
		this.listRespuestas=listRespuestas;
		this.sigEstados=sigEstado;
	}
	
	public EstadoMealy(String estado) {
		this.estado=estado;
	}
	public String combinacionMealy() {
		String comb="";
		for (int i = 0; i < listRespuestas.size(); i++) {
			comb+=listRespuestas.get(i)+".";
		}
		return comb;
	}

}
