package org.sj.tools.sycan.circedit;

import java.awt.Button;
import java.awt.MenuItem;

import java.awt.event.ActionListener;
import java.util.Hashtable;


public abstract class ControllerImpl implements Controller, ActionListener {

	 Hashtable<Object,Integer> actions;

	 DocViewWindow win;
	 Doc doc;
	 View view;

	 public ControllerImpl(DocViewWindow cvw) {
		  win  = cvw;
		  doc = win.getDoc();
		  view = win.getView();

		  actions = new Hashtable<Object,Integer>();
	 }

	 public void assign(Button btn, int action_code) {
		  btn.addActionListener(this);		  
		  registerAction(btn, action_code);
	 }

	 public void assign(MenuItem mi, int action_code) {
		  mi.addActionListener(this);		  
		  registerAction(mi, action_code);
	 }

	 protected void registerAction(Object source, int action_code) {
		  actions.put(source, new Integer(action_code));
	 }


	 protected void unregisterAction(Object source) {
		  // TO-DO
	 }


	 public int getAction(Object obj) throws Exception {
		  Integer num = actions.get(obj);
		  if(num == null) {
				throw new Exception("Not found.");
		  }
		  return num.intValue();
	 }

}
	 
