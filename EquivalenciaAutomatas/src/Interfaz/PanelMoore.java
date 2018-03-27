package Interfaz;

import javax.swing.*;

import Mundo.Estado;

import java.awt.GridLayout;
import java.util.*;
public class PanelMoore extends JPanel{

	
	ArrayList<JTextField> arrM1;
	ArrayList<JTextField> arrM2;
	public PanelMoore(int tam) {
		
		
		
		setLayout(new GridLayout(tam, 10));
		
		JLabel lEstado=new JLabel("Estados");
		JLabel lStim1=new JLabel("Estimulo 1");
		JLabel lStim2=new JLabel("Estimulo 2");
		JLabel lResp=new JLabel("Response");
		
		JButton bAceptM1=new JButton("Aceptar M1");
		JButton bAceptM2=new JButton("Aceptar M2");
	}
	
	public void generateFields(int numStatesM1,int numStatesM2) {
		
		for (int i = 0; i < numStatesM1; i++) {
			JTextField nAct=new JTextField("");
			arrM1.add(nAct);
		}
		
		add(new JLabel("____________"));
		add(new JLabel("____________"));
		add(new JLabel("____________"));
		
		for (int i = 0; i < numStatesM1; i++) {
			
			
			JTextField nAct=new JTextField("");
			arrM2.add(nAct);
		}
	
		
	}
}
