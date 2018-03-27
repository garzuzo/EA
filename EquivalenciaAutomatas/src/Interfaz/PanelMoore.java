package Interfaz;

import javax.swing.*;

import Mundo.Estado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.*;

public class PanelMoore extends JPanel {

	
	ArrayList<JTextField> estadosM1;
	ArrayList<JTextField> estimulosM1;
	ArrayList<JTextField> SigEstadoM1;

	ArrayList<JTextField> estadosM2;
	ArrayList<JTextField> estimulosM2;
	ArrayList<JTextField> SigEstadoM2;
	
	public ArrayList<JTextField> getEstadosM1() {
		return estadosM1;
	}

	public ArrayList<JTextField> getEstimulosM1() {
		return estimulosM1;
	}

	public ArrayList<JTextField> getSigEstadoM1() {
		return SigEstadoM1;
	}

	public ArrayList<JTextField> getEstadosM2() {
		return estadosM2;
	}

	public ArrayList<JTextField> getEstimulosM2() {
		return estimulosM2;
	}

	public ArrayList<JTextField> getSigEstadoM2() {
		return SigEstadoM2;
	}



	int tamField = 2;

	public PanelMoore(int tamM1, int tamM2) {

		int numRows = tamM1 + tamM2 + 2;

		estadosM1 = new ArrayList<JTextField>();
		estimulosM1 = new ArrayList<JTextField>();
		SigEstadoM1 = new ArrayList<JTextField>();
		estadosM2 = new ArrayList<JTextField>();
		estimulosM2 = new ArrayList<JTextField>();
		SigEstadoM2 = new ArrayList<JTextField>();
		setLayout(new BorderLayout());

		generateFields(tamM1, tamM2);
	}

	/**
	 * Genera todos los campos necesarios para crear las maquinas tipo moore
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
		allM1.setLayout(new GridLayout(numStatesM1 + 1, 4));

		panelM1.add(new JLabel("Maquina de Estados 1 (M1)"), BorderLayout.NORTH);

		JLabel lEstado = new JLabel("E");
		allM1.add(lEstado);

		JTextField lStim1 = new JTextField(tamField);
		lStim1.setToolTipText("Agregar estimulo");
		allM1.add(lStim1);

		JTextField lStim2 = new JTextField(tamField);
		lStim2.setToolTipText("Agregar estimulo");
		allM1.add(lStim2);

		allM1.add(new JLabel("R"));

		for (int i = 0; i < numStatesM1; i++) {
			JTextField estadoAct = new JTextField(tamField);
			estadosM1.add(estadoAct);
			allM1.add(estadoAct);
			

			JTextField SigEstadoM11 = new JTextField(tamField);
			SigEstadoM1.add(SigEstadoM11);
			allM1.add(SigEstadoM11);

		

			JTextField SigEstadoM12 = new JTextField(tamField);
			SigEstadoM1.add(SigEstadoM12);
			allM1.add(SigEstadoM12);

			

			JTextField estimuloAct = new JTextField(tamField);
			estimulosM1.add(estimuloAct);
			allM1.add(estimuloAct);


		}

		panelM1.add(allM1, BorderLayout.CENTER);
		add(panelM1, BorderLayout.NORTH);

		JPanel panelM2 = new JPanel();
		panelM2.setLayout(new BorderLayout());

		panelM2.add(new JLabel("Maquina de Estados 2 (M2)"), BorderLayout.NORTH);

		JPanel allM2 = new JPanel();
		allM2.setLayout(new GridLayout(numStatesM2 + 1, 4));

		JLabel lEstado2 = new JLabel("E");
		allM2.add(lEstado2);

	

		JTextField lStim12 = new JTextField(tamField);
		lStim12.setToolTipText("Agregar estimulo");
		allM2.add(lStim12);

		

		JTextField lStim22 = new JTextField(tamField);
		lStim22.setToolTipText("Agregar estimulo");
		allM2.add(lStim22);
		allM2.add(new JLabel("R"));
		panelM2.add(allM2, BorderLayout.CENTER);

		for (int i = 0; i < numStatesM2; i++) {

			JTextField estadoAct = new JTextField(tamField);
			estadosM2.add(estadoAct);
			allM2.add(estadoAct);
			

			JTextField SigEstadoM11 = new JTextField(tamField);
			SigEstadoM2.add(SigEstadoM11);
			allM2.add(SigEstadoM11);

		

			JTextField SigEstadoM12 = new JTextField(tamField);
			SigEstadoM2.add(SigEstadoM12);
			allM2.add(SigEstadoM12);

			

			JTextField estimuloAct = new JTextField(tamField);
			estimulosM2.add(estimuloAct);
			allM2.add(estimuloAct);

		}
		panelM2.setBackground(Color.LIGHT_GRAY);
		panelM2.add(allM2, BorderLayout.CENTER);
		add(panelM2, BorderLayout.CENTER);

	}

}
