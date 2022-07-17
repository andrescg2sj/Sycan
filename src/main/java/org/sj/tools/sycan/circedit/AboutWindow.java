package org.sj.tools.sycan.circedit;

import java.awt.TextArea;

/**
 * Muestra informacin acerca del programa
 */
public class AboutWindow extends SimpleWindow {

    static final int DEF_WIDTH = 400;
    static final int DEF_HEIGHT = 300;
    

    public AboutWindow() {
	super("About...");

	String text = "SCAN: Symbolic Circuit Analyzer\r\n" +
	    "Developed in LSI B-105 - Technical University of Madrid\r\n" +
	    "Alvaro Araujo Pinto - araujo@die.upm.es\r\n" +
	    "Andres C. Gonzalez Gonzalez - agonzalez@die.upm.es";

	TextArea ta = new TextArea(text);
	ta.setEditable(false);

	add(ta);

	setSize(DEF_WIDTH, DEF_HEIGHT);

    }

    




}
