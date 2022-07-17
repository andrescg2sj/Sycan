package org.sj.tools.sycan.circedit;

import java.awt.Frame;
import java.awt.Panel;
import java.awt.BorderLayout;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;


public class CircWindow extends DocViewWindow implements Const {

	 static final int DEF_WIDTH = 640;
	 static final int DEF_HEIGHT = 480;

	 Panel toolBar;

	 CircController control; /* TODO : esto va aqui o en el Main? */

	 /* ************************************************ */
	 /* menu items */
	 MenuItem file_new;
	 MenuItem file_exit;
	 MenuItem circ_export;
	 MenuItem circ_solve;
	 MenuItem help_about;
	 /* ************************************************ */

	 public CircWindow(String title, View vw, Doc doc) {
		  super(title, vw, doc);
		  setSize(DEF_WIDTH, DEF_HEIGHT);

		  control = new CircController(this);

		  initWindow();
		  
	 }

	 void initWindow() {

		  System.out.println("initWindow");

		  setLayout(new BorderLayout());
		  toolBar = new ToolBar(control);
		  

		  add(toolBar, BorderLayout.SOUTH);
		  add(getView(), BorderLayout.CENTER);

		  makeMenu();
		  
	 }


	 protected void makeMenu() {
		  MenuBar mb = new MenuBar();
		  Menu file = new Menu("File");
		  Menu circ = new Menu("Circuit");
		  Menu help = new Menu("Help");

		  file_new = new MenuItem("New");
		  file_exit = new MenuItem("Exit");
		  circ_export = new MenuItem("Export...");
		  circ_solve = new MenuItem("Solve");
		  help_about = new MenuItem("About...");


		  control.assign(file_new, C_ACT_CLEAR);
		  control.assign(file_exit, C_ACT_EXIT);

		  control.assign(circ_export, C_ACT_EXPORT_CIRC);
		  control.assign(circ_solve, C_ACT_SOLVE_CIRC);
		  control.assign(help_about, C_ACT_ABOUT);

		  file.add(file_new);
		  file.add(file_exit);

		  circ.add(circ_export);
		  circ.add(circ_solve);

		  help.add(help_about);

		  mb.add(file);
		  mb.add(circ);
		  mb.add(help);

		  setMenuBar(mb);
		  		  
	 }

	 public void close() {
		  dispose();
		  System.exit(0);
	 }
	 

	 

	 
}
