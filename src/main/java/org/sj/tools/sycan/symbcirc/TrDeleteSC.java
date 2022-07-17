package org.sj.tools.sycan.symbcirc;


import java.util.List;
import java.util.LinkedList;
import java.util.Vector;


/**
 * Eliminar cortocircuitos de un circuito
 *
 *   Conviene que el circuito de entrada est "defragmentado"
 * (es decir, que  la lista de todos los identificadores de nodos
 * sea un conjunto de enteros consecutivos, empezando por cero).
 */
public class TrDeleteSC extends NodeTransform {

	 private class IntegerNode {
		  int number;
		  IntegerNode next;

		  public IntegerNode(int num) {
				number = num;
				next = null;
		  }

		  public void setNext(IntegerNode n) {
				if(next == null) {
					 next = n;
				} else if(n.number < next.number) {
					 next.setNext(n);
				} else if(n.number > next.number) {
					 n.setNext(next);
				}
		  }

		  public int getTail() {
				IntegerNode n = this;
				while(n.next != null) {
					 n = n.next;
				}
				return n.number;
		  }

		  public String toString() {
				String str = ""+ number;
				if(next != null) {
					 str +=  " -> " + getTail() + " ("+next.number+")";
				}
				return str;
		  }
	 }



	 public TrDeleteSC() {
		  super();
	 }

	 public void calc(Circuit c)
	 {

 
		  //System.out.println("Begin del-sc: ================================");
		  circ = c;

		  int circMax = circ.getMax();

		  IntegerNode nodeId[] = new IntegerNode[circMax+1];
		  for(int i = 0; i < circMax+1; i++) {
				nodeId[i] = new IntegerNode(i);
		  }

		  Vector<Element> elems = new Vector<Element>(); /* Nuevo circuito */

		  branch_trans = new int[circ.numElements()];

		  for(int i = 0; i < circ.numElements(); i++) {
				Element elem = circ.get(i);
				int A = elem.getNodeA();
				int B = elem.getNodeB();

				//System.out.println("Iteration: " + i + " --------------------");

				if((elem instanceof ShortCirc) &&
					(((ShortCirc) elem).isErasable())) {

					 ShortCirc sc = (ShortCirc) elem;

					 int min = (A < B) ? A : B;
					 int max = A+B-min;

					 nodeId[max].setNext(nodeId[min]);

					 branch_trans[i] = -1;
				} else {
					 branch_trans[i] = elems.size();
					 elems.add((Element) elem.clone());
				}

				_DBG_show_nodes(nodeId);
		  }


		  /* calcular transformacion */
		  node_trans = new int[circMax+1];
		  for(int i=0; i <= circMax; i++) {
				node_trans[i] = nodeId[i].getTail();
		  }


		  /* transformar nodos */
		  for(int i = 0; i < elems.size(); i++) {
				Element elem = elems.get(i);
				int A = elem.getNodeA();
				int B = elem.getNodeB();
				
				elem.setNodeA(node_trans[A]);
				elem.setNodeB(node_trans[B]);

				if(elem instanceof VC_Src) {
					 VC_Src src = (VC_Src) elem;
					 int X = src.getNodeX();
					 int Y = src.getNodeY();
				
					 src.setNodeX(node_trans[X]);
					 src.setNodeY(node_trans[Y]);
				}

				if(elem instanceof CC_Src) {
					 CC_Src src = (CC_Src) elem;
					 int b = src.getBranch();
					 src.setBranch(branch_trans[b]);
				}
		  }


		  /* -------- DEBUG --------------- */
		  _DEBUG_printTrans();

		  //System.out.println("end del-sc;   ================================");

		  result =  new Circuit(elems);
	 }

	 public void _DBG_show_nodes(IntegerNode in[]) {
		  for(int i = 0; i < in.length; i++) {
				//System.out.println(in[i].toString());
		  }
	 }


		 
 
}