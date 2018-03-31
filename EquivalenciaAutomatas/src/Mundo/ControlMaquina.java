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

	String estadoInicialM1;
	String estadoInicialM2;

	// RESPUESTAS DE LOS AUTOMATAS
	private HashSet<String> respuestas;
	private HashSet<String> estimulos;
	private int numRespuestas;
	private int numEstimulos;

	/**
	 * 
	 * Constructor de la clase
	 * 
	 * @param tipoMealy
	 *            si es true es porque es una maquina tipo mealy
	 * @param estimulo1
	 * @param estimulo2
	 */
	public ControlMaquina(boolean tipoMealy, HashSet<String> estimulos, HashSet<String> respuestas) {
		this.tipoMealy = tipoMealy;

		estadosM1 = new HashSet<String>();
		estadosM2 = new HashSet<String>();
		hmEstadosMealy = new HashMap<String, EstadoMealy>();
		hmEstadosMoore = new HashMap<String, EstadoMoore>();
		this.respuestas = respuestas;
		numRespuestas = respuestas.size();
		this.estimulos = estimulos;
		numEstimulos = estimulos.size();
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
		if (tipoMealy) {

			pAnterior = new ArrayList<HashSet<String>>();
			HashMap<String, ArrayList<String>> combinacionesMealy = new HashMap<String, ArrayList<String>>();

			// PASO 4a, particion inicial, se agrupan los que tienen la misma salida
			for (String key : hmEstadosMealy.keySet()) {

				EstadoMealy act = hmEstadosMealy.get(key);

				String combinacionAct = act.combinacionMealy();

				if (combinacionesMealy.containsKey(combinacionAct)) {
					combinacionesMealy.get(combinacionAct).add(act.estado);
				} else {

					ArrayList<String> temp = new ArrayList<String>();
					temp.add(act.estado);
					combinacionesMealy.put(combinacionAct, temp);
				}
			}

			for (String k : combinacionesMealy.keySet()) {

				HashSet<String> hsResp = new HashSet<String>();
				ArrayList<String> estadosActResp = combinacionesMealy.get(k);
				for (int i = 0; i < estadosActResp.size(); i++) {
					hsResp.add(estadosActResp.get(i));
				}
				if (!hsResp.isEmpty())
					pAnterior.add(hsResp);

			}

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

						ArrayList<EstadoMealy> e1Sig1 = e1.sigEstados;

						while (itConjAct.hasNext()) {

							EstadoMealy e2 = hmEstadosMealy.get(itConjAct.next());
							if (!e2.estado.equals(firstElement)) {
								ArrayList<EstadoMealy> e2Sig1 = e2.sigEstados;

								boolean noCumple = false;
								for (int j = 0; j < pAnterior.size() && !noCumple; j++) {

									for (int k = 0; k < e1Sig1.size(); k++) {

										if (pAnterior.get(j).contains(e1Sig1.get(k).estado))
											if (!pAnterior.get(j).contains(e2Sig1.get(k).estado))
												noCumple = true;

									}

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

			// a la respuesta le asigna una posicion
			HashMap<String, Integer> respPos = new HashMap<String, Integer>();
			int cont = 0;
			for (String k : respuestas) {
				respPos.put(k, cont);
				cont++;
			}

			ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();

			// agrego subconjuntos, en total el numero de respuestas
			for (int i = 0; i < cont; i++) {
				temp.add(new ArrayList<String>());
			}
			// PASO 4a, particion inicial, se agrupan los que tienen la misma salida
			for (String key : hmEstadosMoore.keySet()) {

				EstadoMoore act = hmEstadosMoore.get(key);
				int pos = respPos.get(act.respuesta);
				temp.get(pos).add(act.estado);

			}
			for (int i = 0; i < temp.size(); i++) {

				ArrayList<String> act = temp.get(i);
				HashSet<String> hsAct = new HashSet<String>();
				for (int j = 0; j < act.size(); j++) {
					hsAct.add(act.get(j));
				}
				// agrego solo los conjuntos que no estan vacios a p1
				if (!hsAct.isEmpty())
					pAnterior.add(hsAct);
			}

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
						ArrayList<EstadoMoore> e1Sig1 = e1.sigEstados;

						while (itConjAct.hasNext()) {

							EstadoMoore e2 = hmEstadosMoore.get(itConjAct.next());
							if (!e2.estado.equals(firstElement)) {
								ArrayList<EstadoMoore> e2Sig1 = e2.sigEstados;

								boolean noCumple = false;

								// verifico que cada estado siguiente de los estados que se estan
								// comparando esten en el mismo subconjunto
								for (int j = 0; j < pAnterior.size() && !noCumple; j++) {

									for (int k = 0; k < e1Sig1.size(); k++) {
										if (pAnterior.get(j).contains(e1Sig1.get(k).estado)) {
											if (!pAnterior.get(j).contains(e2Sig1.get(k).estado)) {
												noCumple = true;
											}
										}

									}
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
				String msg = act.estado + " ";
				for (int i = 0; i < act.sigEstados.size(); i++) {
					msg += act.sigEstados.get(i).estado + " ";
				}
				msg += act.respuesta;

				System.out.println(msg);

			}
		} else {
			for (String key : hmEstadosMealy.keySet()) {

				EstadoMealy act = hmEstadosMealy.get(key);
				String msg = act.estado + " ";

				for (int i = 0; i < act.sigEstados.size(); i++) {
					msg += act.sigEstados.get(i) + " " + act.listRespuestas.get(i) + " ";
				}
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
			for (int i = 0, j = 0; i < respM1.size(); i += numEstimulos, j++) {

				ArrayList<String> estimAct = new ArrayList<String>();
				// respuestas del estado actual
				ArrayList<String> respAct = new ArrayList<String>();

				for (int k = i; k < i + numEstimulos; k++) {
					String sgnte = SigEstadoM1.get(k).getText();
					estimAct.add(sgnte);
					String resp = respM1.get(k).getText();
					respAct.add(resp);

				}
				ArrayList<EstadoMealy> estadosSgntes = new ArrayList<EstadoMealy>();
				for (int k = 0; k < numEstimulos; k++) {
					String sgnte1 = estimAct.get(k);
					EstadoMealy next1 = null;
					if (hmEstadosMealy.containsKey(sgnte1)) {
						next1 = hmEstadosMealy.get(sgnte1);
					} else {
						next1 = new EstadoMealy(sgnte1);
						hmEstadosMealy.put(sgnte1, next1);
					}
					estadosSgntes.add(next1);
				}

				String nomEstado = statesM1.get(j).getText();
				estadosM1.add(nomEstado);

				if (hmEstadosMealy.containsKey(nomEstado)) {
					EstadoMealy eMAct = hmEstadosMealy.get(nomEstado);

					eMAct.listRespuestas = respAct;
					eMAct.sigEstados = estadosSgntes;

					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(k).alcanzable = true;
						}

					}

				} else {
					EstadoMealy eMAct = new EstadoMealy(nomEstado, estadosSgntes, respAct);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(k).alcanzable = true;
						}

					}

					hmEstadosMealy.put(nomEstado, eMAct);
				}

			}

		} else {

			for (int i = 0, j = 0; j < statesM1.size(); i += numEstimulos, j++) {
				String nomEstado = statesM1.get(j).getText();
				estadosM1.add(nomEstado);
				ArrayList<String> estimAct = new ArrayList<String>();
				for (int k = i; k < i + numEstimulos; k++) {
					String sgnte = SigEstadoM1.get(k).getText();
					estimAct.add(sgnte);
				}

				ArrayList<EstadoMoore> estadosSgntes = new ArrayList<EstadoMoore>();

				for (int k = 0; k < estimAct.size(); k++) {

					String sgnte1 = estimAct.get(k);
					EstadoMoore next1 = null;
					if (hmEstadosMoore.containsKey(sgnte1)) {
						next1 = hmEstadosMoore.get(sgnte1);
					} else {
						next1 = new EstadoMoore(sgnte1);
						hmEstadosMoore.put(sgnte1, next1);
					}
					estadosSgntes.add(next1);
				}

				String resp = respM1.get(j).getText();
				if (hmEstadosMoore.containsKey(nomEstado)) {
					EstadoMoore eMAct = hmEstadosMoore.get(nomEstado);

					eMAct.respuesta = resp;

					eMAct.sigEstados = estadosSgntes;

					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(k).alcanzable = true;
						}

					}
				} else {

					EstadoMoore eMAct = new EstadoMoore(nomEstado, estadosSgntes, resp);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM1 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(k).alcanzable = true;
						}
					}
					hmEstadosMoore.put(nomEstado, eMAct);
				}

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
			for (int i = 0, j = 0; i < respM2.size(); i += numEstimulos, j++) {
				ArrayList<String> estimAct = new ArrayList<String>();
				// respuestas del estado actual
				ArrayList<String> respAct = new ArrayList<String>();

				for (int k = i; k < i + numEstimulos; k++) {
					String sgnte = SigEstadoM2.get(k).getText();
					estimAct.add(sgnte);
					String resp = respM2.get(k).getText();
					respAct.add(resp);
				}
				ArrayList<EstadoMealy> estadosSgntes = new ArrayList<EstadoMealy>();
				for (int k = 0; k < numEstimulos; k++) {
					String sgnte1 = estimAct.get(k);

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
					estadosSgntes.add(next1);
				}

				String nomEstado = statesM2.get(j).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(nomEstado))
					nomEstado += nomEstado;

				estadosM2.add(nomEstado);
				if (hmEstadosMealy.containsKey(nomEstado)) {
					EstadoMealy eMAct = hmEstadosMealy.get(nomEstado);

					eMAct.listRespuestas = respAct;

					eMAct.sigEstados = estadosSgntes;
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(k).alcanzable = true;
						}

					}
				} else {
					EstadoMealy eMAct = new EstadoMealy(nomEstado, estadosSgntes, respAct);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(i).alcanzable = true;
						}

					}
					hmEstadosMealy.put(nomEstado, eMAct);
				}
			}
		} else {
			for (int i = 0, j = 0; i < SigEstadoM2.size(); i += numEstimulos, j++) {
				String nomEstado = statesM2.get(j).getText();
				// PASO 1: verifico si tiene el mismo nombre de un estado de M1
				if (estadosM1.contains(nomEstado))
					nomEstado += nomEstado;

				ArrayList<String> estimAct = new ArrayList<String>();
				for (int k = i; k < i + numEstimulos; k++) {
					String sgnte = SigEstadoM2.get(k).getText();

					// PASO 1: verifico si tiene el mismo nombre de un estado de M1
					if (estadosM1.contains(sgnte))
						sgnte += sgnte;

					estimAct.add(sgnte);
				}

				ArrayList<EstadoMoore> estadosSgntes = new ArrayList<EstadoMoore>();

				for (int k = 0; k < estimAct.size(); k++) {

					String sgnte1 = estimAct.get(k);
					EstadoMoore next1 = null;
					if (hmEstadosMoore.containsKey(sgnte1)) {
						next1 = hmEstadosMoore.get(sgnte1);
					} else {
						next1 = new EstadoMoore(sgnte1);
						hmEstadosMoore.put(sgnte1, next1);
					}
					estadosSgntes.add(next1);
				}

				estadosM2.add(nomEstado);

				String resp = respM2.get(j).getText();

				if (hmEstadosMoore.containsKey(nomEstado)) {
					EstadoMoore eMAct = hmEstadosMoore.get(nomEstado);

					eMAct.respuesta = resp;

					eMAct.sigEstados = estadosSgntes;
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(k).alcanzable = true;
						}

					}
				} else {

					EstadoMoore eMAct = new EstadoMoore(nomEstado, estadosSgntes, resp);
					if (i == 0) {
						eMAct.alcanzable = true;
						estadoInicialM2 = nomEstado;
					}
					// si el estado actual es alcanzable, los que conecta son alcanzables
					if (eMAct.alcanzable) {
						for (int k = 0; k < eMAct.sigEstados.size(); k++) {
							eMAct.sigEstados.get(k).alcanzable = true;
						}

					}
					hmEstadosMoore.put(nomEstado, eMAct);
				}

			}
		}
	}

}
