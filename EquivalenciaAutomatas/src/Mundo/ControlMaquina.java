package Mundo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JTextField;

public class ControlMaquina {

	boolean tipoMealy;
	HashMap<String, EstadoMealy> hmEstadosMealy;
	HashMap<String, EstadoMoore> hmEstadosMoore;

	// En el momento de hacer la suma de m1 y m2 sirve para verificar que no se vaya
	// a repetir nombres
	HashSet<String> estadosM1;

	String estimulo1;
	String estimulo2;

	public ControlMaquina(boolean tipoMealy, String estimulo1, String estimulo2) {
		this.tipoMealy = tipoMealy;
		this.estimulo1 = estimulo1;
		this.estimulo2 = estimulo2;
		estadosM1=new HashSet<String>();
		hmEstadosMealy=new HashMap<String,EstadoMealy>();
		hmEstadosMoore=new HashMap<String,EstadoMoore>();
	}

	public void crearMaquina1(ArrayList<JTextField> statesM1, ArrayList<JTextField> respM1,
			ArrayList<JTextField> SigEstadoM1) {

		// for (int i = 0; i < statesM1.size(); i++) {
		// estadosM1.add(statesM1.get(i).getText());
		// }

		if (tipoMealy) {
			for (int i = 0, j = 0; i < respM1.size(); i += 2, j++) {

				String resp1 = respM1.get(i).getText();
				String resp2 = respM1.get(i + 1).getText();

				String sgnte1 = SigEstadoM1.get(i).getText();
				EstadoMealy next1 = null;
				if (hmEstadosMealy.containsKey(sgnte1)) {
					next1 = hmEstadosMealy.get(sgnte1);
				} else {
					next1 = new EstadoMealy(sgnte1);
					hmEstadosMealy.put(sgnte1, next1);
				}

				String sgnte2 = SigEstadoM1.get(i + 1).getText();
				EstadoMealy next2 = null;
				if (hmEstadosMealy.containsKey(sgnte2)) {
					next2 = hmEstadosMealy.get(sgnte2);
				} else {
					next2 = new EstadoMealy(sgnte2);
					hmEstadosMealy.put(sgnte2, next2);
				}

				String nomEstado = statesM1.get(j).getText();
				estadosM1.add(nomEstado);
				if (hmEstadosMealy.containsKey(nomEstado)) {
					EstadoMealy eMAct = hmEstadosMealy.get(nomEstado);
					eMAct.estimulo1 = estimulo1;
					eMAct.estimulo2 = estimulo2;
					eMAct.respuesta1 = resp1;
					eMAct.respuesta2 = resp2;
					eMAct.sigEstado1 = next1;
					eMAct.sigEstado2 = next2;
				} else {
					EstadoMealy eMAct = new EstadoMealy(nomEstado, estimulo1, next1, resp1, estimulo2, next2, resp2);
					hmEstadosMealy.put(nomEstado, eMAct);
				}
			}

		} else {

			for (int i = 0, j = 0; j < statesM1.size(); i += 2, j++) {
				String nomEstado = statesM1.get(j).getText();
				estadosM1.add(nomEstado);
				String sgnte1 = SigEstadoM1.get(i).getText();
				EstadoMoore next1 = null;
				if (hmEstadosMoore.containsKey(sgnte1)) {
					next1 = hmEstadosMoore.get(sgnte1);
				} else {
					next1 = new EstadoMoore(sgnte1);
					hmEstadosMoore.put(sgnte1, next1);
				}

				String sgnte2 = SigEstadoM1.get(i + 1).getText();
				EstadoMoore next2 = null;
				if (hmEstadosMoore.containsKey(sgnte2)) {
					next2 = hmEstadosMoore.get(sgnte2);
				} else {
					next2 = new EstadoMoore(sgnte2);
					hmEstadosMoore.put(sgnte2, next2);
				}

				String resp = respM1.get(j).getText();
				if (hmEstadosMoore.containsKey(nomEstado)) {
					EstadoMoore eMAct = hmEstadosMoore.get(nomEstado);
					eMAct.estimulo1 = estimulo1;
					eMAct.estimulo2 = estimulo2;
					eMAct.respuesta = resp;

					eMAct.sigEstado1 = next1;
					eMAct.sigEstado2 = next2;
				} else {

					EstadoMoore eMAct = new EstadoMoore(nomEstado, estimulo1, next1, estimulo2, next2, resp);
					hmEstadosMoore.put(nomEstado, eMAct);
				}

			}
		}

	}

	public void crearMaquina2(ArrayList<JTextField> statesM2, ArrayList<JTextField> respM2,
			ArrayList<JTextField> SigEstadoM2) {

		// Cambio nombres de estados con el mismo nombre

		if (tipoMealy) {
			for (int i = 0, j = 0; i < respM2.size(); i += 2, j++) {

				String resp1 = respM2.get(i).getText();
				String resp2 = respM2.get(i + 1).getText();

				String sgnte1 = SigEstadoM2.get(i).getText();

				// PASO 1:verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(sgnte1))
					sgnte1 += sgnte1;

				EstadoMealy next1 = null;
				if (hmEstadosMealy.containsKey(sgnte1)) {
					next1 = hmEstadosMealy.get(sgnte1);
				} else {
					next1 = new EstadoMealy(sgnte1);
					hmEstadosMealy.put(sgnte1, next1);
				}

				String sgnte2 = SigEstadoM2.get(i + 1).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(sgnte2))
					sgnte2 += sgnte2;

				EstadoMealy next2 = null;
				if (hmEstadosMealy.containsKey(sgnte2)) {
					next2 = hmEstadosMealy.get(sgnte2);
				} else {
					next2 = new EstadoMealy(sgnte2);
					hmEstadosMealy.put(sgnte2, next2);
				}

				String nomEstado = statesM2.get(j).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(nomEstado))
					nomEstado += nomEstado;

				if (hmEstadosMealy.containsKey(nomEstado)) {
					EstadoMealy eMAct = hmEstadosMealy.get(nomEstado);
					eMAct.estimulo1 = estimulo1;
					eMAct.estimulo2 = estimulo2;
					eMAct.respuesta1 = resp1;
					eMAct.respuesta2 = resp2;
					eMAct.sigEstado1 = next1;
					eMAct.sigEstado2 = next2;
				} else {
					EstadoMealy eMAct = new EstadoMealy(nomEstado, estimulo1, next1, resp1, estimulo2, next2, resp2);
					hmEstadosMealy.put(nomEstado, eMAct);
				}
			}
		} else {
			for (int i = 0, j = 0; i < SigEstadoM2.size(); i += 2, j++) {
				String nomEstado = statesM2.get(j).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(nomEstado))
					nomEstado += nomEstado;

				estadosM1.add(nomEstado);

				String sgnte1 = SigEstadoM2.get(i).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(sgnte1))
					sgnte1 += sgnte1;

				EstadoMoore next1 = null;
				if (hmEstadosMoore.containsKey(sgnte1)) {
					next1 = hmEstadosMoore.get(sgnte1);
				} else {
					next1 = new EstadoMoore(sgnte1);
					hmEstadosMoore.put(sgnte1, next1);
				}

				String sgnte2 = SigEstadoM2.get(i + 1).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(sgnte2))
					sgnte2 += sgnte2;

				EstadoMoore next2 = null;
				if (hmEstadosMoore.containsKey(sgnte2)) {
					next2 = hmEstadosMoore.get(sgnte2);
				} else {
					next2 = new EstadoMoore(sgnte2);
					hmEstadosMoore.put(sgnte2, next2);
				}

				String resp = respM2.get(j).getText();

				if (hmEstadosMoore.containsKey(nomEstado)) {
					EstadoMoore eMAct = hmEstadosMoore.get(nomEstado);
					eMAct.estimulo1 = estimulo1;
					eMAct.estimulo2 = estimulo2;
					eMAct.respuesta = resp;

					eMAct.sigEstado1 = next1;
					eMAct.sigEstado2 = next2;
				} else {

					EstadoMoore eMAct = new EstadoMoore(nomEstado, estimulo1, next1, estimulo2, next2, resp);
					hmEstadosMoore.put(nomEstado, eMAct);
				}

			}
		}
	}

}
