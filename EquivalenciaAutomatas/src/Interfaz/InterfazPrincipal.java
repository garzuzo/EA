package Interfaz;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import Mundo.Estado;

public class InterfazPrincipal extends JFrame {

	private int numEstadosM1;
	private int numEstadosM2;
	private ArrayList<Estado> m1;
	private ArrayList<Estado> m2;
	JButton bSelecMaquina;
	public InterfazPrincipal() {

		JLabel sel = new JLabel("Selecciona el tipo de Maquina de estado");

		this.add(sel);

		JRadioButton rbMealy = new JRadioButton("Mealy");
		JRadioButton rbMoore = new JRadioButton("Moore");
		JRadioButtonMenuItem rbGroup = new JRadioButtonMenuItem();
		rbGroup.add(rbMealy);
		rbGroup.add(rbMoore);
		 bSelecMaquina = new JButton("Seleccionar");

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InterfazPrincipal ip = new InterfazPrincipal();
		ip.setVisible(true);
		ip.setLocationRelativeTo(null);
	}

}
