package org.sj.tools.sycan.circedit;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Button;
import java.awt.TextField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ParamDlg extends Dialog implements ActionListener {

	 String names[];


	 /** returned value (true=Ok clicked; false = Cancel clicked) */
	 boolean result = false;

	 static final int DEF_WIDTH = 300;
	 static final int DEF_FIELD_HEIGHT = 30;

	 TextField fields[];

	 Button btnOk;
	 Button btnCancel;

	 public ParamDlg(Frame owner, String names[]) {
		  super(owner, "Values", true);
		  this.names = names;

		  setSize(DEF_WIDTH, DEF_FIELD_HEIGHT*(names.length+1));
		  fields = new TextField[names.length];

		  /* set up all the labels, text fields and buttons */
		  setLayout(new GridLayout(names.length+1, 2));

		  for(int i = 0; i < names.length; i++) {
				add(new Label(names[i]));
				fields[i] = new TextField("1");
				add(fields[i]);
		  }

		  btnOk = new Button("Ok");
		  btnCancel = new Button("Cancel");

		  btnOk.addActionListener(this);
		  btnCancel.addActionListener(this);

		  add(btnOk);
		  add(btnCancel);
	 }

	 public String[][] getValues() {
		  String values[][] = new String[names.length][2];
		  for (int i = 0; i < names.length; i++) {
				values[i][0] = names[i];
				values[i][1] = fields[i].getText();
		  }
		  return values;
	 }

	 public boolean getResult() {
		  return result;
	 }

	 public void actionPerformed(ActionEvent event) {
		  Object src = event.getSource();

		  if(src == btnOk) {
				result = true;
		  }

		  setVisible(false);
	 }
	 
}