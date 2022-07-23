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

	String text = "Sycan: Symbolic Circuit Analyzer\r\n" +
	    "Andres C. Gonzalez Gonzalez - andresg@jesuitas.es\r\n" +
			"andrescg2sj.github.com";

	TextArea ta = new TextArea(text);
	ta.setEditable(false);

	add(ta);

	setSize(DEF_WIDTH, DEF_HEIGHT);

    }

    




}
