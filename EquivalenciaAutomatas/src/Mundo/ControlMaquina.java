package Mundo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 * 
 * @author garzuzo
 *
 */
public class ControlMaquina {

	
	
	boolean tipoMealy;
	/**
	 * Almacenamiento de estados mealy como suma de ambas maquinas m1 y m2
	 */
	HashMap<String, EstadoMealy> hmEstadosMealy;
	/**
	 * Almacenamiento de estados moore como suma de ambas maquinas m1 y m2
	 */
	HashMap<String, EstadoMoore> hmEstadosMoore;

	// En el momento de hacer la suma de m1 y m2 sirve para verificar que no se vaya
	// a repetir nombres
	HashSet<String> estadosM1;
	HashSet<String> estadosM2;

	
	String estimulo1;
	String estimulo2;
	
	String estadoInicialM1;
	String estadoInicialM2;

	// RESPUESTAS DE LOS AUTOMATAS
	HashSet<String> respuestas;

	/**
	 * 
	 * Constructor de la clase
	 * 
	 * @param tipoMealy
	 *            si es true es porque es una maquina tipo mealy
	 * @param estimulo1
	 * @param estimulo2
	 */
	public ControlMaquina(boolean tipoMealy, String estimulo1, String estimulo2) {
		this.tipoMealy = tipoMealy;
		this.estimulo1 = estimulo1;
		this.estimulo2 = estimulo2;
		estadosM1 = new HashSet<String>();
		estadosM2 = new HashSet<String>();
		hmEstadosMealy = new HashMap<String, EstadoMealy>();
		hmEstadosMoore = new HashMap<String, EstadoMoore>();
		respuestas = new HashSet<String>();
	}

	ArrayList<HashSet<String>> pAnterior;
	// llegara hasta el Pfinal
	ArrayList<HashSet<String>> pActual;

	/**
	 * 
	 * @return mensaje con el conjunto final (Pf)
	 */
	public String conjuntoFinal() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < pActual.size(); i++) {

			sb.append("{");
			for (String k : pActual.get(i)) {
				sb.append(k + ",");
			}
			sb.append("},");

		}
		return sb.toString();
	}

	/**
	 * Verifica que dos automates (mealy o moore) son equivalentes
	 * 
	 * @return true si m1 y m2 son equivalentes
	 */
	public boolean automatasEquivalentes() {

		Iterator<String> it = respuestas.iterator();
		String resp1 = it.next();
		String resp2 = it.next();

		if (tipoMealy) {

			pAnterior = new ArrayList<HashSet<String>>();
			HashSet<String> hsEstim11 = new HashSet<String>();
			HashSet<String> hsEstim12 = new HashSet<String>();
			HashSet<String> hsEstim21 = new HashSet<String>();
			HashSet<String> hsEstim22 = new HashSet<String>();

			// PASO 4a, particion inicial, se agrupan los que tienen la misma salida
			for (String key : hmEstadosMealy.keySet()) {

				EstadoMealy act = hmEstadosMealy.get(key);

				if (act.respuesta1.equals(resp1) && act.respuesta2.equals(resp1)) {
					hsEstim11.add(key);
				} else if (act.respuesta1.equals(resp1) && act.respuesta2.equals(resp2)) {
					hsEstim12.add(key);
				} else if (act.respuesta1.equals(resp2) && act.respuesta2.equals(resp1)) {
					hsEstim21.add(key);
				} else {
					hsEstim22.add(key);

				}

			}
			if (!hsEstim11.isEmpty())
				pAnterior.add(hsEstim11);
			if (!hsEstim12.isEmpty())
				pAnterior.add(hsEstim12);
			if (!hsEstim21.isEmpty())
				pAnterior.add(hsEstim21);
			if (!hsEstim22.isEmpty())
				pAnterior.add(hsEstim22);

			// clone pActual al empezar p1
			pActual = new ArrayList<HashSet<String>>();
			for (int i = 0; i < pAnterior.size(); i++) {
				HashSet<String> act = new HashSet<String>();
				for (String k : pAnterior.get(i)) {
					act.add(k);
				}
				pActual.add(act);
			}

			while (true) {

				for (int i = 0; i < pActual.size(); i++) {
					HashSet<String> act = new HashSet<String>();
					pActual.add(act);

					Iterator<String> itConjAct = pActual.get(i).iterator();
					// elemento al cual le voy a comparar todo el conjunto
					if (itConjAct.hasNext()) {
						String firstElement = itConjAct.next();
						EstadoMealy e1 = hmEstadosMealy.get(firstElement);
						String e1Sig1 = e1.sigEstado1.estado;
						String e1Sig2 = e1.sigEstado2.estado;
						while (itConjAct.hasNext()) {

							EstadoMealy e2 = hmEstadosMealy.get(itConjAct.next());
							if (!e2.estado.equals(firstElement)) {
								String e2Sig1 = e2.sigEstado1.estado;
								String e2Sig2 = e2.sigEstado2.estado;

								boolean noCumple = false;
								for (int j = 0; j < pAnterior.size() && !noCumple; j++) {

									if (pAnterior.get(j).contains(e1Sig1))
										if (!pAnterior.get(j).contains(e2Sig1))
											noCumple = true;

									if (pAnterior.get(j).contains(e1Sig2))
										if (!pAnterior.get(j).contains(e2Sig2))
											noCumple = true;
								}

								// si de ambos no estan en el mismo subconjunto, se separa del conjunto actual
								if (noCumple) {

									act.add(e2.estado);
									pActual.get(i).remove(e2.estado);

									itConjAct = pActual.get(i).iterator();

								}
							}
						}
					}
					if (act.isEmpty())
						pActual.remove(act);
				}

				// verifica si el subconjunto anterior es igual al subconjunto actual, para
				// terminar el algoritmo
				if (pActual.size() == pAnterior.size())
					break;

				// clone actualiza el pAnterior
				pAnterior = new ArrayList<HashSet<String>>();
				for (int i = 0; i < pActual.size(); i++) {
					HashSet<String> hsAct = new HashSet<String>();
					for (String k : pActual.get(i)) {
						hsAct.add(k);
					}
					pAnterior.add(hsAct);
				}

			}

			boolean estadosInicialesJuntos = false;

			for (int i = 0; i < pActual.size(); i++) {

				HashSet<String> hsAct = pActual.get(i);
				boolean elemM1 = false;
				boolean elemM2 = false;
				boolean eInicialM1 = false;
				boolean eInicialM2 = false;
				for (String k : hsAct) {

					if (estadosM1.contains(k)) {
						elemM1 = true;
					} else if (estadosM2.contains(k)) {
						elemM2 = true;
					}
					if (k.equals(estadoInicialM1)) {
						eInicialM1 = true;

					} else if (k.equals(estadoInicialM2)) {
						eInicialM2 = true;
					}

				}
				if (eInicialM1 && eInicialM2)
					estadosInicialesJuntos = true;
				if (!elemM1 || !elemM2)
					return false;

			}
			if (!estadosInicialesJuntos)
				return false;
		} else {
			pAnterior = new ArrayList<HashSet<String>>();
			HashSet<String> hsEstim1 = new HashSet<String>();
			HashSet<String> hsEstim2 = new HashSet<String>();

			// PASO 4a, particion inicial, se agrupan los que tienen la misma salida
			for (String key : hmEstadosMoore.keySet()) {

				EstadoMoore act = hmEstadosMoore.get(key);
				if (act.respuesta.equals(resp1)) {

					hsEstim1.add(key);
				} else {
					hsEstim2.add(key);
				}
			}

			// agrego solo los conjuntos que no estan vacios a p1
			if (!hsEstim1.isEmpty())
				pAnterior.add(hsEstim1);
			if (!hsEstim2.isEmpty())
				pAnterior.add(hsEstim2);
			// clone pActual al empezar p1
			pActual = new ArrayList<HashSet<String>>();
			for (int i = 0; i < pAnterior.size(); i++) {
				HashSet<String> act = new HashSet<String>();
				for (String k : pAnterior.get(i)) {
					act.add(k);
				}
				pActual.add(act);
			}

			while (true) {

				for (int i = 0; i < pActual.size(); i++) {
					HashSet<String> act = new HashSet<String>();
					pActual.add(act);
					Iterator<String> itConjAct = pActual.get(i).iterator();
					// elemento al cual le voy a comparar todo el conjunto
					if (itConjAct.hasNext()) {
						String firstElement = itConjAct.next();
						EstadoMoore e1 = hmEstadosMoore.get(firstElement);
						String e1Sig1 = e1.sigEstado1.estado;
						String e1Sig2 = e1.sigEstado2.estado;
						while (itConjAct.hasNext()) {

							EstadoMoore e2 = hmEstadosMoore.get(itConjAct.next());
							if (!e2.estado.equals(firstElement)) {
								String e2Sig1 = e2.sigEstado1.estado;
								String e2Sig2 = e2.sigEstado2.estado;

								boolean noCumple = false;
								for (int j = 0; j < pAnterior.size() && !noCumple; j++) {

									if (pAnterior.get(j).contains(e1Sig1))
										if (!pAnterior.get(j).contains(e2Sig1))
											noCumple = true;

									if (pAnterior.get(j).contains(e1Sig2))
										if (!pAnterior.get(j).contains(e2Sig2))
											noCumple = true;
								}

								// si de ambos no estan en el mismo subconjunto, se separa del conjunto actual
								if (noCumple) {

									act.add(e2.estado);
									pActual.get(i).remove(e2.estado);

									itConjAct = pActual.get(i).iterator();

								}
							}
						}
					}
					// si el conjunto que cree no es usado se elimina
					if (act.isEmpty())
						pActual.remove(act);
				}

				// verifica si el subconjunto anterior es igual al subconjunto actual, para
				// terminar el algoritmo
				if (pActual.size() == pAnterior.size())
					break;

				// clone actualiza el pAnterior
				pAnterior = new ArrayList<HashSet<String>>();
				for (int i = 0; i < pActual.size(); i++) {
					HashSet<String> hsAct = new HashSet<String>();
					for (String k : pActual.get(i)) {
						hsAct.add(k);
					}
					pAnterior.add(hsAct);
				}

			}

			boolean estadosInicialesJuntos = false;

			for (int i = 0; i < pActual.size(); i++) {

				HashSet<String> hsAct = pActual.get(i);
				boolean elemM1 = false;
				boolean elemM2 = false;
				boolean eInicialM1 = false;
				boolean eInicialM2 = false;
				for (String k : hsAct) {

					if (estadosM1.contains(k)) {
						elemM1 = true;
					} else if (estadosM2.contains(k)) {
						elemM2 = true;
					}
					if (k.equals(estadoInicialM1)) {
						eInicialM1 = true;

					} else if (k.equals(estadoInicialM2)) {
						eInicialM2 = true;
					}

				}
				if (eInicialM1 && eInicialM2)
					estadosInicialesJuntos = true;
				if (!elemM1 || !elemM2)
					return false;

			}
			if (!estadosInicialesJuntos)
				return false;
		}
		return true;
	}

	/**
	 * Comprueba que se han eliminado los estados inaccesibles
	 */
	public void imprimirComprobante() {
		if (!tipoMealy) {
			for (String key : hmEstadosMoore.keySet()) {

				EstadoMoore act = hmEstadosMoore.get(key);
				String msg = act.estado + " " + act.sigEstado1.estado + " " + act.sigEstado2.estado + " "
						+ act.respuesta;
				System.out.println(msg);

			}
		} else {
			for (String key : hmEstadosMealy.keySet()) {

				EstadoMealy act = hmEstadosMealy.get(key);
				String msg = act.estado + " " + act.sigEstado1.estado + " " + act.sigEstado2.estado + " "
						+ act.respuesta1 + " " + act.respuesta2;
				System.out.println(msg);
			}

		}
	}

	/**
	 * 
	 * se verifican los estados inalcanzables, los cuales se deben eliminar
	 */
	public void verificarAlcanzables() {

		if (tipoMealy) {
			ArrayList<String> inalcanzables = new ArrayList<String>();
			for (String key : hmEstadosMealy.keySet()) {

				EstadoMealy act = hmEstadosMealy.get(key);
				if (!act.alcanzable)
					inalcanzables.add(key);
			}

			for (int i = 0; i < inalcanzables.size(); i++) {
				hmEstadosMealy.remove(inalcanzables.get(i));
			}
		} else {
			ArrayList<String> inalcanzables = new ArrayList<String>();
			for (String key : hmEstadosMoore.keySet()) {

				EstadoMoore act = hmEstadosMoore.get(key);
				if (!act.alcanzable)
					inalcanzables.add(key);
			}

			for (int i = 0; i < inalcanzables.size(); i++) {
				hmEstadosMoore.remove(inalcanzables.get(i));
			}
		}

	}

	/**
	 * 
	 * @param statesM1
	 *            campos de texto con los estados de m1
	 * @param respM1
	 *            campos de texto con las respuestas de m1 respectivas a cada estado
	 * @param SigEstadoM1
	 *            campos de texto con los estados siguientes respectivos a cada
	 *            estado de m1
	 */
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

					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}

				} else {
					EstadoMealy eMAct = new EstadoMealy(nomEstado, estimulo1, next1, resp1, estimulo2, next2, resp2);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}

					hmEstadosMealy.put(nomEstado, eMAct);
				}
				respuestas.add(resp1);
				respuestas.add(resp2);
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
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}
				} else {

					EstadoMoore eMAct = new EstadoMoore(nomEstado, estimulo1, next1, estimulo2, next2, resp);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}
					hmEstadosMoore.put(nomEstado, eMAct);
				}
				respuestas.add(resp);

			}
		}

	}

	/**
	 * 
	 * @param statesM2
	 *            campos de texto con los estados de m2
	 * @param respM2
	 *            campos de texto con las respuestas de m2 respectivas a cada estado
	 * @param SigEstadoM2
	 *            campos de texto con los estados siguientes respectivos a cada
	 *            estado de m2
	 */
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

				estadosM2.add(nomEstado);
				if (hmEstadosMealy.containsKey(nomEstado)) {
					EstadoMealy eMAct = hmEstadosMealy.get(nomEstado);
					eMAct.estimulo1 = estimulo1;
					eMAct.estimulo2 = estimulo2;
					eMAct.respuesta1 = resp1;
					eMAct.respuesta2 = resp2;
					eMAct.sigEstado1 = next1;
					eMAct.sigEstado2 = next2;
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}
				} else {
					EstadoMealy eMAct = new EstadoMealy(nomEstado, estimulo1, next1, resp1, estimulo2, next2, resp2);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}
					hmEstadosMealy.put(nomEstado, eMAct);
				}
			}
		} else {
			for (int i = 0, j = 0; i < SigEstadoM2.size(); i += 2, j++) {
				String nomEstado = statesM2.get(j).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(nomEstado))
					nomEstado += nomEstado;

				String sgnte1 = SigEstadoM2.get(i).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(sgnte1))
					sgnte1 += sgnte1;

				estadosM2.add(nomEstado);
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
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}
				} else {

					EstadoMoore eMAct = new EstadoMoore(nomEstado, estimulo1, next1, estimulo2, next2, resp);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						next1.alcanzable = true;
						next2.alcanzable = true;

					}
					hmEstadosMoore.put(nomEstado, eMAct);
				}

			}
		}
	}

}
