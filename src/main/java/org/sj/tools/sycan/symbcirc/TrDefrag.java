package org.sj.tools.sycan.symbcirc;

import java.util.Vector;

/**
 * Representa un intervalo de enteros
 */
class IntInterval {

	 int min, max;

	 public IntInterval(int a, int b) {
		  int min = a;
		  int max = b;

		  if(max < min) {
				int s = max;
				max = min;
				min = s;
		  }
	 }

	 public boolean contains(int x) {
		  return (x >= min) && (x <= max);
	 }

	 public void insert(int x) {
		  min = minOf(x, min);
		  max = maxOf(x, max);
	 }

	 public int getMax() {
		  return max;
	 }

	 public int getMin() {
		  return min;
	 }

	 public static int maxOf(int a, int b) {
		  return (a > b) ? a : b;
	 }

	 public static int minOf(int a, int b) {
		  return (a < b) ? a : b;
	 }
}


/**
 * Elimina huecos en la secuencia de nodos
 */
public class TrDefrag extends NodeTransform {

	 public TrDefrag() {
		  super();
	 }

	 /**
	  * aplicar transformacin
	  */
	 public void calc(Circuit c) {
		  //System.out.println("Begin defrag:");
		  circ = c;

		  /* Los nodos no cambian de ndice */
		  branch_trans = new int[circ.numElements()];
		  for(int i = 0; i < branch_trans.length; i++) {
				branch_trans[i] = i;
		  }

		  /* inicializar */
		  node_trans = new int[circ.getMax() + 1];
		  for(int i = 1; i < node_trans.length; i++) {
				node_trans[i] = -1;
		  }
		  node_trans[0] = 0;
		  
		  Element elems[] = new Element[circ.numElements()];

		  for(int i = 0; i < circ.numElements(); i++) {
				elems[i] = (Element) circ.get(i).clone();
		  }
		  
		  /* procesar */

		  int node = 1;
		  for(int i = 0; i < circ.numElements(); i++) {
				Element elem = circ.get(i);
				int A = elem.getNodeA();
				int B = elem.getNodeB();

				if(node_trans[A] == -1) {
					 node_trans[A] = node++;
				}

				if(node_trans[B] == -1) {
					 node_trans[B] = node++;
				}

				elems[i].setNodeA(node_trans[A]);
				elems[i].setNodeB(node_trans[B]);
		  }

		  /* final */
		  for(int i = 1; i < node_trans.length; i++) {
				if(node_trans[i] == -1) {
					 node_trans[i] = i;
				}
		  }

		  for(int i = 0; i < circ.numElements(); i++) {
				if(elems[i] instanceof VC_Src) {
					 VC_Src src = (VC_Src) elems[i];
					 int X = src.getNodeX();
					 int Y = src.getNodeY();

					 src.setNodeX(node_trans[X]);
					 src.setNodeY(node_trans[Y]);
				}
		  }


		  _DEBUG_printTrans();
		  //System.out.println("end defrag(node_t: "+node_trans.length +
		  //", branch_t: " + branch_trans.length+";");
		  result = new Circuit(elems);
	 }

	 

	 
}