package org.sj.tools.sycan.circedit;

public class DocViewWindow extends SimpleWindow {
	 private View view;
	 private Doc doc;
	
	 /**
	  * Constructor
	  *
	  * @param s String con el contenido de la barra de titulo de la ventana
	  * @param _v View con la vista del documento
	  * @param _d Doc con el documento
	  * @param _container true para que se aada un container a la ventana
	  */
	 public DocViewWindow(String s, View _v, Doc _d) {
		  super(s);
		  view = _v;
		  doc = _d;

		  view.setDoc(doc);
		  doc.setView(view);
				
		  add(view);
	 }

	
	 public DocViewWindow() {
		  super();
	 }
	
	 protected View getView() {
		  return view;
	 }
	
	 protected Doc getDoc() {
		  return doc;
	 }

		  

}