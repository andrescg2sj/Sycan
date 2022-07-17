package org.sj.tools.sycan.circedit;

import java.awt.Frame;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Component;

/**
 * Ventana lista para ser utilizada
 * @author Andrs Gonzlez Gonzlez
 * @since  19/06/2003 
 */
public class SimpleWindow extends Frame implements WindowListener {

	/**
	 * Constructor
	 *
	 * @param titulo String con el contenido de la barra de titulo de la ventana
	 * @param _container true para que se aada un container a la ventana
	 */
	public SimpleWindow(String titulo) {
		super(titulo);
		addWindowListener(this);
	
	}
	

	public SimpleWindow() {
		this("");
	}
	
	

	
	public void close()
	{
		dispose();
		//System.exit(0);
	}
	
	public void windowActivated(WindowEvent e) {;}
	public void windowClosed(WindowEvent e) {;}
	public void windowDeactivated(WindowEvent e) {;}
	public void windowDeiconified(WindowEvent e) {;}
	public void windowIconified(WindowEvent e) {;}
	public void windowOpened(WindowEvent e) {;}
	
	public void windowClosing(WindowEvent e) {
		close();
	}

}
