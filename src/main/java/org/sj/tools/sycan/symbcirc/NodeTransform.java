package org.sj.tools.sycan.symbcirc;

/**
 * Transformacin de nodos en un circuito.
 */
public abstract class NodeTransform {

	 /** circuito sobre el que se aplica la transformacin */
	 protected Circuit circ;

	 /** resultado la transformacin */
	 protected Circuit result;

	 /** relacin entre los nodos del circuito original y el transformado:
		  El valor del elemento i-esimo de este array es el ndice del nodo
		  del circuito resultante correspondiente al nodo i-esimo del original.
	    */
	 protected int node_trans[];

	 /** relacin entre las ramas o elementos del circuito original y el
	     transformado:
	     El valor de l elemento i-esimo de este array es el ndice del elemento
	     del circuito resultante correspondiente al elemento i-esimo del
		  original */
	 protected int branch_trans[];

	 /**
	  * Aplicar una transformacin de nodos a un circuito.
	  */
	 public NodeTransform() {
		  circ = null;
		  result = null;
	 }

	 /**
	  *aplicar transformacin
	  */
	 public Circuit getResult() {
		  return result;
	 }

	 public abstract void calc(Circuit c);
 

	 /**
	  * Devuelve la transformacin realizada sobre un nodo
	  * concreto. 
	  */
	 public int transNode(int i) {
		  if(result == null) {return -1;}
		  //System.out.println("transNode <- " + i+" size:("+node_trans.length+")");
		  return node_trans[i];
	 }

	 /**
	  * Devuelve la transformacin realizada sobre el ndice de un
	  * elemento (o rama) de circuito.
	  */
	 public int transBranch(int i) {
		  if(result == null) {return -1;}
		  return branch_trans[i];
	 }


	 void _DEBUG_printTrans() {
		  //System.out.println("Nodes:");
		  for(int i = 0; i < node_trans.length; i++) {
				//System.out.println(i+"->"+node_trans[i]);
		  }
		  //System.out.println("Branches:");
		  for(int i = 0; i < branch_trans.length; i++) {
				//System.out.println(i+"->"+branch_trans[i]);
		  }
	 }
 
}