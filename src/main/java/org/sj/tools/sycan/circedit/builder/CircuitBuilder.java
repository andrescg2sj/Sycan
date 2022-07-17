package org.sj.tools.sycan.circedit.builder;

//////////////////////////////////
//
// ATENCION!! No está terminado.
//
//////////////////////////////////


import java.awt.Point;
import java.awt.geom.Point2D;
import org.sj.utils.math.complex.Complex;

import java.util.Vector;

import org.sj.tools.sycan.circedit.Const;
import org.sj.tools.sycan.circedit.GraphicElement;
import org.sj.tools.sycan.circedit.GraphicTLElement;
import org.sj.tools.sycan.circedit.GraphicShortCirc;
import org.sj.tools.sycan.circedit.GraphicGround;
import org.sj.tools.sycan.circedit.GraphicNode;

import org.sj.tools.sycan.symbcirc.Element;
import org.sj.tools.sycan.symbcirc.Resistor;
import org.sj.tools.sycan.symbcirc.Capacitor;
import org.sj.tools.sycan.symbcirc.Inductor;
import org.sj.tools.sycan.symbcirc.VoltSrc;
import org.sj.tools.sycan.symbcirc.CurrSrc;
import org.sj.tools.sycan.symbcirc.ShortCirc;
import org.sj.tools.sycan.symbcirc.Circuit;

class NodeGroup implements Const {
	 int x, y;
	 int nodes[][];

	 /** campos: (nº elemento, id nodo (A o B)) */
	 static final int NUM_FIELDS = 2;

	 public NodeGroup(int size, int x, int y) {
		  nodes = new int[size][NUM_FIELDS];
		  this.x = x;
		  this.y = y;
	 }

	 public void set(int i, int elem, int node) {
		  nodes[i][0] = elem;
		  nodes[i][1] = node;
	 }

	 public int getSize(){
		  return nodes.length;
	 }
	 

	 public int getElem(int i){
		  return nodes[i][0];
	 }
	 
	 public int getNode(int i){
		  return nodes[i][1];
	 }


	 public static boolean nodesOverlap(int px, int py, int qx, int qy)
	 {
		  int dx = (int) Math.abs(px - qx);
		  int dy = (int) Math.abs(py - qy);
		  return (dx <= NODE_RAD) && (dy <= NODE_RAD);
	 }
	 



	 public static boolean nodesOverlap(Point p, Point q)
	 {
		  return nodesOverlap((int) p.getX(), (int) p.getY(),
									 (int) q.getX(), (int) q.getY());
	 }

	 
}


class NodeList {

	 static final int ELEM_ID = 0 ;
	 static final int NODE_ID = 1 ;
	 static final int X_CRD = 2 ;
	 static final int Y_CRD = 3 ;
	 static final int GROUP_ID = 4 ; // !!! NO SE USA

	 /** número de campos: (nº elemento, id nodo (A o B), x, y, id grupo) */
	 static final int NUM_FIELDS = 5 ;


	 protected int list[][];

	 /** número de entradas introducidas */
	 protected int size;
	 
	 public NodeList(int numEntries) {
		  list = new int[numEntries][NUM_FIELDS];
		  size = 0;
	 }

	 public boolean add(int elem, int node, int x, int y) {
		  if(size >= list.length) {
				return false;
		  }

		  list[size][ELEM_ID] = elem;
		  list[size][NODE_ID] = node;
		  list[size][X_CRD] = x;
		  list[size][Y_CRD] = y;
		  list[size][GROUP_ID] = -1;
		  size++;
		  return true;
	 }

	 void sort() {
		  
		  /* TODO: ordenar según (x, y) :
			  (a,b) < (c,d) <=> ( (a < c) || (a = c) && (b < d) ) */
		  qsort(0, list.length-1);
	 }

	 void qsort(int inf, int sup) {
		  int med = (inf+sup)/2;
		  int x = list[med][X_CRD];
		  int y = list[med][Y_CRD];
		  int i = inf;
		  int s = sup;
		  do {
				while(compare(list[i][X_CRD],list[i][Y_CRD],x,y) < 0) {
					 i++;
				}
				while(compare(list[s][X_CRD],list[s][Y_CRD], x, y) > 0) {
					 s--;
				}
				if(i <= s) {
					 swap(i, s);
					 i++;
					 s--;
					 
				}
		  } while(i <= s);
		  if(inf < s) {
				qsort(inf, s);
		  }
		  if(i < sup) {
				qsort(i, sup);
		  }
	 }

	 void swap(int i, int s) {
		  for(int j = 0; j < NUM_FIELDS; j++) {
				int v = list[i][j];
				list[i][j] = list[s][j];
				list[s][j] = v;
		  }
	 }


	 /**
	   @ return 1  si (a,b) es mayor que (c,d)		
		         -1 si (a,b) es menor que (c,d)
					0  si son iguales
	  */
	 static int compare(int a, int b, int c, int d) {
		  if(a < c) {
				return -1;
		  } else if(a > c) {
				return 1;
		  } else {
				if(b < d) {
					 return -1;
				} else if(b > d) {
					 return 1;
				}
				return 0;
		  }
	 }
		  

	 /**
	  * Construir y devolver grupos
	  */
	 public NodeGroup[] getGroups() {
		  System.out.println("Ordenar:");
		  sort();
		  System.out.println(" - ok");
		  Vector<NodeGroup> groups = new Vector<NodeGroup>();
		  NodeGroup gr;
		  int x = -1;
		  int y = -1; // suponemos que no hay negativos en la lista

		  size = 0;
		  for(int i=0; i < list.length; i++) {
				//System.out.print("  extremo: " + i
				// + " (" +list[i][2] +","+list[i][3]+")");
				//				if((x == list[i][2]) && (y == list[i][3])) {
				if(NodeGroup.nodesOverlap(x, y, list[i][2], list[i][3])) {
					 System.out.println(" igual");
				} else {
					 if(size > 0) {
						  //System.out.print(" nuevo grupo:" + size);
						  /* crear nuevo grupo */
						  gr = new NodeGroup(size, x, y);
						  for(int j=0; j < size; j++) {
								int k = i - j - 1;
								if(k < 0) System.out.println("WARN. k < 0");

								gr.set(j, list[k][0], list[k][1]);
								//System.out.print("   e:" + list[k][0]
								//  + " n:"+list[k][1]);

						  }
						  groups.add(gr);
						  size = 0;
					 }

					 x = list[i][X_CRD];
					 y = list[i][Y_CRD];
					 System.out.println("");

				}
				size++;
		  }

		  /* ultimo grupo */
		  gr = new NodeGroup(size, x, y);
		  for(int j=0; j < size; j++) {
				int k = list.length - j - 1;
				if(k < 0) System.out.println("WARN. k < 0");

				gr.set(j, list[k][0], list[k][1]);
				//System.out.print("   e:" + list[k][0]
				//+ " n:"+list[k][1]);

		  }
		  groups.add(gr);



		  /* convertir en array */
		  int N = groups.size();
		  NodeGroup[] all = new NodeGroup[N];
		  for(int i = 0; i < N; i++) {
				all[i] = groups.get(i);
		  }		  
		  return all;
	 }
}

class ExtNode {
	 /** índice del elemento gráfico al que corresponde */
	 int id_elem;
	 /** grupo en el que se incluye. Se asigna después */
	 int id_group; 
	 GraphicNode gn;
	 public ExtNode(int ie, GraphicNode n) {
		  id_elem = ie;
		  gn = n;
	 }
}


public class CircuitBuilder implements Const {

	 /** lista original de elementos gráficos */
	 Vector<GraphicElement> geList;


	 /* --- datos intermedios: --- */

	 ExtNode enodes[][];

	 public CircuitBuilder(Vector<GraphicElement> vge) {
		  geList = vge;
	 }


	 public Circuit build() throws Exception {

		  _DBG_print_list();

		  /* 1. Para cada elemento gráfico, obtener los nodos y añadir a
			  una lista */

		  int N = geList.size();

		  int numNodes = 0;

		  
		  enodes = new ExtNode[N][]; 
		  for(int i = 0; i < N; i++) {
				GraphicElement ge = geList.get(i);
				GraphicNode nodes[] = ge.getNodes();

				ExtNode en[] = new ExtNode[ nodes.length];
				numNodes += nodes.length;
				for(int j = 0; j < nodes.length; j++) {
					 en[j] = new ExtNode(i, nodes[j]);
				}
				enodes[i] = en;
		  }


		  /*2. Crear grupos */
		  NodeList nl = new NodeList(numNodes);
		  for(int i = 0; i < N; i++) {
				ExtNode en[] = enodes[i];
				for(int j = 0; j < en.length; j++) {
					 GraphicNode gn = en[j].gn;

					 int nId = gn.getNodeId();
					 Point p = gn.getPosition();
					 nl.add(en[j].id_elem, nId, (int) p.getX(), (int) p.getY());
				}
		  }

		  NodeGroup ngs[] = nl.getGroups();

		  int group_id = 1;
		  for(int i = 0; i < ngs.length; i++) {

				int id = group_id++;

				/* buscar alguna conexión masa en el grupo */
				for(int j = 0; j < ngs[i].getSize(); j++) {
					 int k = ngs[i].getElem(j);
					 GraphicElement ge = geList.get(k);

					 if(ge instanceof GraphicGround) {
						  id = 0;
						  group_id--;
						  break;
					 }
				}

				/* asignar indice de grupo a nodos correspondientes */
				for(int j = 0; j < ngs[i].getSize(); j++) {
					 int e = ngs[i].getElem(j);
					 int n = ngs[i].getNode(j);

					 // !!! DEPENDE DE QUE EL IDENTIFIADOR DE UN NODO SEA
					 // IGUAL A SU LUGAR EN EL ARRAY
					 ExtNode ens[] = enodes[e];
					 ens[n].id_group = id;
				}
		  }



		  /*3. Para cada elemento gráfico, recuperar nods, obtener grupos y
			 y construir elementos del circuito final */

		  
		  Vector<Element> elems = new Vector<Element>(geList.size());

		  for(int i = 0; i < geList.size(); i++) {

				GraphicElement ge = geList.get(i);
				if(ge instanceof GraphicGround) {
					 continue;
				}

				//ExtNode ens[] = enodes[i];
				if(ge instanceof GraphicTLElement) {
					 GraphicTLElement tle = (GraphicTLElement) ge;

					 int A = enodes[i][NODE_A].id_group;
					 int B = enodes[i][NODE_B].id_group;
					 String type = tle.getType();
					 if(type.equals(TYPE_RESISTOR)) {
						  System.out.println("  build: resistor");
						  elems.add (new Resistor(A, B, 1, tle.getName()));
					 }else  if(type.equals(TYPE_INDUCTOR)) {
						  System.out.println("  build: inductor");
						  elems.add( new Inductor(A, B, 1, tle.getName()));
					 }else  if(type.equals(TYPE_CAPACITOR)) {
						  System.out.println("  build: capacitor");
						  elems.add( new Capacitor(A, B, 1, tle.getName()));
					 }else  if(type.equals(TYPE_VOLT_SRC)) {
						  elems.add(new VoltSrc(A, B, new Complex(1.0, 0.0),
														tle.getName()));
					 }else  if(type.equals(TYPE_CURR_SRC)) {
						  elems.add( new CurrSrc(A, B, new Complex(1.0, 0.0),
														 tle.getName()));
					 }
				} else if(ge instanceof GraphicShortCirc) {
					 int A = enodes[i][NODE_A].id_group;
					 int B = enodes[i][NODE_B].id_group;
					 elems.add(new ShortCirc(A, B));
				}
		  }

		  //return elems;
		  

		  
		  /* --------------------------------------------------- */

		  Circuit circ = new Circuit(elems);
		  return circ;
	 }


	 /**
	  * Obtener el índice de nodo asociado al punto dado.
	  * Este método se debe invocar sólo si ya se ha invocado antes
	  * el método build().
	  */
	 public int getNodeId(Point p)
	 {

		  if(enodes == null) {
				throw new Error("No element-node matrix created.");
		  }

		  for(int i = 0; i < geList.size(); i++) {
				GraphicElement ge = geList.get(i);

				if(ge instanceof GraphicTLElement) {
					 GraphicTLElement tle = (GraphicTLElement) ge;

					 Point pA = tle.getNodeAPos();
					 Point pB = tle.getNodeBPos();

					 if(NodeGroup.nodesOverlap(p, pA)) {
						  return enodes[i][NODE_A].id_group;
					 }
					 if(NodeGroup.nodesOverlap(p, pB)) {
						  return enodes[i][NODE_B].id_group;
					 }
				}

				if(ge instanceof GraphicShortCirc) {
					 GraphicShortCirc gsc = (GraphicShortCirc) ge;
					 if(gsc.isInside(p)) {
						  return enodes[i][NODE_A].id_group;
					 }
				}
				
		  }
		  return NO_NODE;
	 }


	 /**
	  * dado un grupo de nodos, asigna el identificador de nodo 'node_id'
	    en la estructura 'nodos' a los extremos contenidos en el grupo */
	 static void assignNodes(int nodes[][], NodeGroup ng, int node_id) {
		  for(int i = 0; i < ng.getSize(); i++) {
				int e = ng.getElem(i);
				int n = ng.getNode(i);
				nodes[e][n] = node_id;
		  }
	 }

	 public void _DBG_print_list()
	 {

		  System.out.println("Graphic Circuit:");
		  for(int i = 0; i < geList.size(); i++) {
				GraphicElement ge = geList.get(i);
				System.out.println(ge.toString());
		  }
	 }

}