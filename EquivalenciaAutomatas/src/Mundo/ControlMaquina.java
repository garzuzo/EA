package Mundo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JTextField;

public class ControlMaquina {

	boolean tipoMealy;
	HashMap<String,EstadoMealy> estadosM1;
	HashSet<String> estadosM2;

	String estimulo1;
	String estimulo2;
	
	public ControlMaquina(boolean tipoMealy,String estimulo1,String estimulo2) {
		this.tipoMealy = tipoMealy;
		this.estimulo1=estimulo1;
		this.estimulo2=estimulo2;
	}

	public void crearMaquina1(ArrayList<JTextField> statesM1, ArrayList<JTextField> respM1,
			ArrayList<JTextField> SigEstadoM1) {

//		for (int i = 0; i < statesM1.size(); i++) {
//			estadosM1.add(statesM1.get(i).getText());
//		}

		if (tipoMealy) {
			for (int i=0,j=0; i < respM1.size(); i += 2,j++) {
				
				String resp1 = respM1.get(i).getText();
				String resp2 = respM1.get(i + 1).getText();
				
				String sgnte1 = respM1.get(i).getText();
				EstadoMealy next1=null;
				if(estadosM1.containsKey(sgnte1)) {
					next1=estadosM1.get(sgnte1);
				}else {
					
				}
					
				String sgnte2 = respM1.get(i + 1).getText();
				EstadoMealy next2=null;
				if(estadosM1.containsKey(sgnte2)) {
					next2=estadosM1.get(sgnte2);
				}else {
					
				}
					
				String nomEstado=statesM1.get(j).getText();
				EstadoMealy eMAct=new EstadoMealy(nomEstado,estimulo1, next1, resp1, estimulo2, next2, resp2);
				estadosM1.put(nomEstado, eMAct);
			}
			

		} else {
			for (int i = 0; i < respM1.size(); i ++) {
				String resp = respM1.get(i).getText();
				
			}
			for (int i = 0; i < SigEstadoM1.size(); i += 2) {
				String sgnte1 = respM1.get(i).getText();
				String sgnte2 = respM1.get(i + 1).getText();
			}
		}

	}

	public void crearMaquina2(ArrayList<JTextField> statesM2, ArrayList<JTextField> respM2,
			ArrayList<JTextField> SigEstadoM2) {
		for (int i = 0; i < statesM2.size(); i++) {
			estadosM2.add(statesM2.get(i).getText());
		}
		for (int i = 0; i < respM2.size(); i++) {

		}
		for (int i = 0; i < SigEstadoM2.size(); i++) {

		}

		if (tipoMealy) {

		} else {

		}
	}

}
