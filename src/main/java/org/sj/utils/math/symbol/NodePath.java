package org.sj.utils.math.symbol;

import java.util.Vector;

class NodeStep {
	 MathNode node;

	 /** nmero de subnodo que es node de su padre */
	 int branch;

	 public NodeStep(int b, MathNode n) {
		  set(b, n);
	 }

	 public void set(int b, MathNode n) {
		  branch = b;
		  node = n;
	 }
}

/**
 * Camino en un arbol (secuencia de nodos desde la raz hasta un nodo)
 */
public class NodePath {

	 public static final int ROOT = -1;

	 private static final int DEF_CAPACITY = 10;
	 private static final int DEF_INCREMENT = 5;

	 Vector<NodeStep> path;

	 public NodePath(MathNode node) {
		  path = new Vector<NodeStep>(DEF_CAPACITY, DEF_INCREMENT);
		  append(ROOT, node);
	 }

	 protected void append(int branch, MathNode node) {
		  path.add(new NodeStep(branch, node));
	 }

	 public int length() {
		  return path.size();
	 }

	 /**
s	  * Crea y devuelve un rbol en que se han clonado todos los nodos
	  * referenciados por el NodePath, donde el nodo 'branch' del ltimo
	  * nodo se asigna a 'tail'.
	  */
	 public MathNode fork(int branch, MathNode tail) {
// 		  //System.out.println("fork(branch="+branch+", tail="+tail+")");

		  int br = branch;
		  MathNode node = tail;
		  for(int i = path.size() - 1; i >= 0; i--) {
// 				//System.out.println("    i="+i+" br="+br);
				String prevNode = path.get(i).node._DBG_showTree();
// 				//System.out.println("      prevNode="+prevNode+" node="+node._DBG_showTree());
				node = path.get(i).node.fork(br, node);
// 				//System.out.println("      []node="+path.get(i).node._DBG_showTree()+", node="+node._DBG_showTree());
				br = path.get(i).branch; // se guarda para la prxima vuelta
// 				//System.out.println("      br="+br);
// 				//System.out.println("     -P:"+ this);
				path.get(i).set(br, node); // el camino se modifica, apuntando
				// al nuevo arbol.
// 				//System.out.println("     +P:"+ this);
		  }
		  append(branch, tail);
		  return node;
	 }

	 public void advance(int branch) {
		  MathNode last = path.lastElement().node;
		  append(branch, last.getNode(branch));
	 }

	 public void stepBack() {
		  if(path.size() > 0) {
				path.setSize(path.size() - 1);
		  }
	 }


	 /**
	  * devuelve el primer nodo de la ruta.
	  */
	 public MathNode getRoot() {
		  return path.firstElement().node;
	 }

	 /**
	  * devuelve el ltimo nodo de la lista, es decir, el apuntado
	  * por esta ruta.
	  */
	 public MathNode getTail() {
		  return path.lastElement().node;
	 }

	 /**
	  * Devuelve el nmero de subnodos del ltimo del camino.
	  */
	 public int numTailNodes() {
		  return path.lastElement().node.numNodes();
	 }

	 /**
	  * reemplaza el nodo apuntado por la ruta
	  */
	 public MathNode replaceTail(MathNode node) {
		  if(path.size() == 1) {
				/* Hace falta esto ? */
				path.firstElement().set(ROOT, node);
				return node;
		  }
		  int br = path.lastElement().branch;
		  stepBack();
		  return fork(br, node);
	 }

	 public String toString() {
		  String str = "";
		  for(int i = 0; i < path.size(); i++) {
				str += "<'"+path.get(i).node._DBG_showTree()+"', "+path.get(i).branch+"/"+path.get(i).node.numNodes()+"> ";
		  }
		  str+= "  '"+getRoot().toString()+"'";
		  return str;
	 }
}