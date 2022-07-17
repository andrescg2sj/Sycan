package org.sj.tools.sycan.circedit;

import java.awt.Frame;

public class MainPrg {

	 private static DocViewWindow win = null;

	 static public Frame getMainWindow() {
		  return win;
	 }

	 public static void main(String args[]) {
		  int DEF_WIDTH = 800;
		  int DEF_HEIGHT = 600;

		  Doc myDoc = new CircDoc();
		  View myView = new CircView();

		  win = new CircWindow("Circuits", myView, myDoc);
		  //		  win.setSize(DEF_WIDTH, DEF_HEIGHT);
		  win.setVisible(true);
	 }
}