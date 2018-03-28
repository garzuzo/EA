package Interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import Mundo.ControlMaquina;


/**
 * 
 * @author garzuzo
 *
 */
public class InterfazPrincipal extends JFrame implements ActionListener {

	private ControlMaquina controlMaquina;
	private ArrayList<JTextField> estadosM1;
	private ArrayList<JTextField> respM1;
	private ArrayList<JTextField> SigEstadoM1;

	private ArrayList<JTextField> estadosM2;
	private ArrayList<JTextField> respM2;
	private ArrayList<JTextField> SigEstadoM2;

	private String estimulo1;
	private String estimulo2;

	private int numEstadosM1;
	private int numEstadosM2;
	

	public static final String selectTipoM = "tipoM";
	public static final String resolver = "res";
	public static final String aceptar = "aceptar";
	JRadioButton rbMealy;
	JRadioButton rbMoore;

	PanelMealy panelMealy;
	PanelMoore panelMoore;

	JTextField tTamM1;
	JTextField tTamM2;
	JButton bAceptar;
	JButton bResolver;

	public InterfazPrincipal() {

		setLayout(new BorderLayout());
		JPanel panelIncludeAll = new JPanel();
		panelIncludeAll.setLayout(new BorderLayout());

		JPanel pAceptar = new JPanel();
		pAceptar.setLayout(new FlowLayout());
		bAceptar = new JButton("Aceptar");

		bAceptar.addActionListener(this);
		bAceptar.setActionCommand(aceptar);
		pAceptar.add(bAceptar);
		JPanel panelOpciones = new JPanel();
		panelOpciones.setLayout(new FlowLayout());

		JLabel sel = new JLabel("Selecciona el tipo de Maquina de estado");

		panelOpciones.add(sel);

		rbMealy = new JRadioButton("Mealy");
		rbMoore = new JRadioButton("Moore");
		ButtonGroup rbGroup = new ButtonGroup();
		rbGroup.add(rbMealy);
		rbGroup.add(rbMoore);
		rbMealy.setSelected(true);
		panelOpciones.add(rbMealy);
		panelOpciones.add(rbMoore);
		JPanel panelTam = new JPanel();

		panelTam.setLayout(new FlowLayout());
		panelTam.add(new JLabel("Numero de estados M1:"));

		tTamM1 = new JTextField(3);
		panelTam.add(tTamM1);
		panelTam.add(new JLabel("Numero de estados M1:"));
		tTamM2 = new JTextField(3);
		panelTam.add(tTamM2);

		panelIncludeAll.add(panelOpciones, BorderLayout.NORTH);
		panelIncludeAll.add(panelTam, BorderLayout.CENTER);
		panelIncludeAll.add(pAceptar, BorderLayout.SOUTH);
		add(panelIncludeAll, BorderLayout.NORTH);
		JPanel bRes = new JPanel();
		bRes.setLayout(new FlowLayout());
		bResolver = new JButton("Resolver");
		bResolver.addActionListener(this);
		bResolver.setActionCommand(resolver);
		bRes.add(bResolver);
		add(bRes, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InterfazPrincipal ip = new InterfazPrincipal();
		ip.setVisible(true);
		ip.setSize(1000, 700);
		ip.setLocationRelativeTo(null);
		ip.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		String comando = ae.getActionCommand();

		if (comando.equals(aceptar)) {

			numEstadosM1 = Integer.parseInt(tTamM1.getText());
			numEstadosM2 = Integer.parseInt(tTamM2.getText());
			if (rbMealy.isSelected()) {
				tipoMealy();
			} else {
				tipoMoore();
			}
			this.pack();
			this.revalidate();

		} else if (comando.equals(resolver)) {

			boolean esMealy=rbMealy.isSelected();
			if(esMealy) {
		estimulo1=panelMealy.getEstimulo1().getText();
		estimulo2=panelMealy.getEstimulo2().getText();		
			}else {
				
				estimulo1=panelMoore.getEstimulo1().getText();
				estimulo2=panelMoore.getEstimulo2().getText();	
			}
			controlMaquina = new ControlMaquina(esMealy,estimulo1,estimulo2);

			controlMaquina.crearMaquina1(estadosM1, respM1, SigEstadoM1);
			controlMaquina.crearMaquina2(estadosM2, respM2, SigEstadoM2);
			controlMaquina.verificarAlcanzables();
controlMaquina.imprimirComprobante();
		}

	}

	public void tipoMealy() {
		if (panelMealy != null)
			remove(panelMealy);
		if (panelMoore != null)
			remove(panelMoore);

		panelMealy = new PanelMealy(numEstadosM1, numEstadosM2);
		add(panelMealy, BorderLayout.CENTER);
		estadosM1 = panelMealy.getEstadosM1();
		respM1 = panelMealy.getRespM1();
		SigEstadoM1 = panelMealy.getSigEstadoM1();
		estadosM2 = panelMealy.getEstadosM2();
		respM2 = panelMealy.getRespM2();
		SigEstadoM2 = panelMealy.getSigEstadoM2();
	}

	public void tipoMoore() {
		if (panelMoore != null)
			remove(panelMoore);
		if (panelMealy != null)
			remove(panelMealy);

		panelMoore = new PanelMoore(numEstadosM1, numEstadosM2);
		add(panelMoore, BorderLayout.CENTER);
		estadosM1 = panelMoore.getEstadosM1();
		respM1 = panelMoore.getRespM1();
		SigEstadoM1 = panelMoore.getSigEstadoM1();
		estadosM2 = panelMoore.getEstadosM2();
		respM2 = panelMoore.getRespM2();
		SigEstadoM2 = panelMoore.getSigEstadoM2();
	}

}
