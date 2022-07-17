package org.sj.tools.sycan.symbcirc;


import java.util.Vector;

public class Circuit {

	 Element elem[];

	 int max = -1; // ndice mximo de nodo


	 public Circuit(Element e[]) {
		  elem = e;
	 }

	 public Circuit(Vector<Element> e) {
		  elem = new Element[e.size()];
		  e.toArray(elem);
		  
	 }


	 /**
	  *
	  */
	 public int numElements() {
		  return elem.length;
	 }

	 public Element get(int i) {
		  return elem[i];
	 }

	 /**
	  * devuelve el mximo ndice de nodo
	  */
	 public int getMax() {
		  if(max == -1) {
				/* calcular mximo */	
				for(int i = 0; i < elem.length; i++) {
					 if(elem[i].getNodeA() > max) {
						  max = elem[i].getNodeA();
					 }
					 if(elem[i].getNodeB() > max) {
						  max = elem[i].getNodeB();
					 }
				}
		  }
		  return max;
	 }


	 /**
	  * Busca nodos sueltos (con menos de 2 componentes unidos a ese nodo)
	  *
	  * @return true si existe algn nodo con menos de 2 elementos unidos
	  *         false en caso contrario
	  */
	 public boolean checkUnbound() {
		  int nodes[] = new int[getMax() + 1];
		  for(int i = 0; i < elem.length; i++) {
				nodes[elem[i].getNodeA()]++;
				nodes[elem[i].getNodeB()]++;
		  }
		  for(int i = 0; i < nodes.length; i++) {
				if(nodes[i] < 2) {
					 return true;
				}
		  }
		  return false;
	 }

	 public String toString() {
		  String str = "";
		  for(int i = 0; i < elem.length; i++) {
				str += elem[i].toString() + "\r\n";
		  }
		  return str;
	 }

	 


	 public static void main(String args[])
	 {
		  /*
		  if(args.length < 1) {
				//System.out.println("Se necesita nombre de archivo.");
		  }
		  */

		  //elem = new Element[6];

		  

		  
	 }
}