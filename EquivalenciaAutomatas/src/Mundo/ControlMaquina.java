package Mundo;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JTextField;

public class ControlMaquina {

	boolean tipoMealy;
	HashSet<String> estadosM1;
	HashSet<String> estadosM2;

	public ControlMaquina(boolean tipoMealy) {
		this.tipoMealy = tipoMealy;
	}

	public void crearMaquina1(ArrayList<JTextField> statesM1, ArrayList<JTextField> estimulosM1,
			ArrayList<JTextField> SigEstadoM1) {

		for (int i = 0; i < statesM1.size(); i++) {
			estadosM1.add(statesM1.get(i).getText());
		}

		for (int i = 0; i < estimulosM1.size(); i++) {

		}
		for (int i = 0; i < SigEstadoM1.size(); i++) {

		}
		if (tipoMealy) {

		} else {

		}

	}

	public void crearMaquina2(ArrayList<JTextField> statesM2, ArrayList<JTextField> estimulosM2,
			ArrayList<JTextField> SigEstadoM2) {
		for (int i = 0; i < statesM2.size(); i++) {
			estadosM2.add(statesM2.get(i).getText());
		}

		if (tipoMealy) {

		} else {

		}
	}

}
