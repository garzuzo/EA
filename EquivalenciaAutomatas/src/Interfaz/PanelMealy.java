package Interfaz;

import java.awt.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;

public class PanelMealy extends JPanel {

	private ArrayList<JTextField> estadosM1;
	private ArrayList<JTextField> respM1;
	private ArrayList<JTextField> SigEstadoM1;

	private ArrayList<JTextField> estadosM2;
	private ArrayList<JTextField> respM2;
	private ArrayList<JTextField> SigEstadoM2;

	private JTextField estimulo1;

	private int numEstims;
	private int numResps;
	
	public JTextField getEstimulo1() {
		return estimulo1;
	}

	public JTextField getEstimulo2() {
		return estimulo2;
	}

	private JTextField estimulo2;

	public ArrayList<JTextField> getEstadosM1() {
		return estadosM1;
	}

	public ArrayList<JTextField> getRespM1() {
		return respM1;
	}

	public ArrayList<JTextField> getSigEstadoM1() {
		return SigEstadoM1;
	}

	public ArrayList<JTextField> getEstadosM2() {
		return estadosM2;
	}

	public ArrayList<JTextField> getRespM2() {
		return respM2;
	}

	public ArrayList<JTextField> getSigEstadoM2() {
		return SigEstadoM2;
	}

	int tamField = 2;
int numColumnas;
	public PanelMealy(int tamM1, int tamM2,int numEstims, int numResps) {

		int numRows = tamM1 + tamM2 + 2;
		 numColumnas=1+(5*numEstims);
		estadosM1 = new ArrayList<JTextField>();
		respM1 = new ArrayList<JTextField>();
		SigEstadoM1 = new ArrayList<JTextField>();
		estadosM2 = new ArrayList<JTextField>();
		respM2 = new ArrayList<JTextField>();
		SigEstadoM2 = new ArrayList<JTextField>();
		this.numEstims=numEstims;
		this.numResps=numResps;
		setLayout(new BorderLayout());

		generateFields(tamM1, tamM2);
	}

	/**
	 * Genera todos los campos necesarios para crear las maquinas tipo mealy
	 * 
	 * @param numStatesM1
	 *            es el numero de estados de la maquina 1
	 * @param numStatesM2
	 *            es el numero de estados de la maquina 2
	 */
	public void generateFields(int numStatesM1, int numStatesM2) {
		JPanel panelM1 = new JPanel();
		panelM1.setLayout(new BorderLayout());

		panelM1.setBackground(Color.gray);
		// estados, transiciones M1
		JPanel allM1 = new JPanel();
		
		allM1.setLayout(new GridLayout(numStatesM1 + 1, numColumnas));

		panelM1.add(new JLabel("Maquina de Estados 1 (M1)"), BorderLayout.NORTH);

		JLabel lEstado = new JLabel("E");
		allM1.add(lEstado);
		
		for (int i = 0; i < numEstims; i++) {
		allM1.add(new JLabel(""));
		allM1.add(new JLabel(""));

		JTextField lStim1 = new JTextField(tamField);
		lStim1.setToolTipText("Agregar estimulo");
		allM1.add(lStim1);

		allM1.add(new JLabel(""));
		allM1.add(new JLabel(""));
		
	}
		

		for (int i = 0; i < numStatesM1; i++) {
			JTextField estadoAct = new JTextField(tamField);
			estadosM1.add(estadoAct);
			allM1.add(estadoAct);
			for (int j = 0; j < numEstims; j++) {
				
			
				allM1.add(new JLabel("("));
				
				JTextField SigEstadoM11 = new JTextField(tamField);
				SigEstadoM1.add(SigEstadoM11);
				allM1.add(SigEstadoM11);
				allM1.add(new JLabel(","));
				
				//respuesta actual
				JTextField estimuloAct1 = new JTextField(tamField);
				respM1.add(estimuloAct1);
				allM1.add(estimuloAct1);
				
				allM1.add(new JLabel(")"));
			}

		}

		panelM1.add(allM1, BorderLayout.CENTER);
		add(panelM1, BorderLayout.NORTH);

		JPanel panelM2 = new JPanel();
		panelM2.setLayout(new BorderLayout());

		panelM2.add(new JLabel("Maquina de Estados 2 (M2)"), BorderLayout.NORTH);

		JPanel allM2 = new JPanel();
		allM2.setLayout(new GridLayout(numStatesM2 + 1, numColumnas));

		JLabel lEstado2 = new JLabel("E");
		allM2.add(lEstado2);
		
		for (int i = 0; i < numEstims; i++) {
			
			allM2.add(new JLabel(""));
			allM2.add(new JLabel(""));
			
			JTextField estimulo1 = new JTextField(tamField);
			estimulo1.setToolTipText("Agregar estimulo");
			allM2.add(estimulo1);
			
			allM2.add(new JLabel(""));
			allM2.add(new JLabel(""));
		}
		panelM2.add(allM2, BorderLayout.CENTER);

		for (int i = 0; i < numStatesM2; i++) {

			JTextField estadoAct = new JTextField(tamField);
			estadosM2.add(estadoAct);
			allM2.add(estadoAct);
			for (int j = 0; j < numEstims; j++) {
				
					
			allM2.add(new JLabel("("));

			JTextField estimuloAct1 = new JTextField(tamField);
			SigEstadoM2.add(estimuloAct1);
			allM2.add(estimuloAct1);
			allM2.add(new JLabel(","));

			//respuesta actual
			JTextField estimuloAct2 = new JTextField(tamField);
			respM2.add(estimuloAct2);
			allM2.add(estimuloAct2);

			allM2.add(new JLabel(")"));

			}
		}
		panelM2.setBackground(Color.LIGHT_GRAY);
		panelM2.add(allM2, BorderLayout.CENTER);
		add(panelM2, BorderLayout.CENTER);

	}
}
