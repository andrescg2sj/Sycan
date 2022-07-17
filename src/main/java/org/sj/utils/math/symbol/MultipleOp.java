package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;
import java.util.Vector;

/**
 * Operador multiple con propiedad conmutativa y asociativa
 */
public abstract class MultipleOp extends MathNode { 

	 Vector<MathNode> operand;

	 public MultipleOp(Vector<MathNode> ops) {
		  operand = ops;
		  expandSame();
		  sort();
	 }

	 /** @param srt true para ordenar los nodos por peso */
	 public MultipleOp(Vector<MathNode> ops, boolean srt) {
		  operand = ops;
		  if(srt) {
				expandSame(); // TODO: tal vez sea necesario meterlo en el sort
				sort();
		  }
	 }
	 
	 public MultipleOp(MathNode op1, MathNode op2) {
		  /* TODO - si son operadores multiples del mismo tipo, expandir */
		  operand = new Vector<MathNode>(2, 2);
		  operand.add(op1);
		  operand.add(op2);

		  expandSame();
		  sort();
	 }


	 public MultipleOp(MathNode op1, MathNode op2, boolean srt) {
		  /* TODO - si son operadores multiples del mismo tipo, expandir */
		  operand = new Vector<MathNode>(2, 2);
		  operand.add(op1);
		  operand.add(op2);
		  if(srt) {
				expandSame();
				sort();
		  }
	 }


	 public int numNodes() {
		  return operand.size();
	 }

	 public MathNode getNode(int i) {
		  return operand.get(i);
	 }

	 public int weight() {
		  int w = 1;
		  for(int i = 0; i < operand.size(); i++) {
				w += operand.get(i).weight();
		  }
		  return w;
	 }

	 /**
	  * Ordena los elementos del vector en funcin de su peso
	  */
	 public void sort() {
		  int N = operand.size();
		  int pair[][] = new int[N][2];

		  for(int i = 0; i < N; i++) {
				pair[i][0] = operand.get(i).weight();
				pair[i][1] = i;
		  }

		  qsort(pair, 0, N-1);

		  Vector<MathNode> ordered = new Vector<MathNode>(N, 1);
		  for(int i = 0; i < N; i++) {
				ordered.add(operand.get(pair[i][1]));
		  }
		  operand = ordered;
	 }

	 protected void qsort(int list[][], int inf, int sup) {

		  int x = list[(inf+sup)/2][0];
		  int i = inf;
		  int s = sup;
		  do {
				while(list[i][0] < x) {
					 i++;
				}
				while(list[s][0] > x) {
					 s--;
				}
				if(i <= s) {
					 int a = list[i][0];
					 int b = list[i][1];
					 list[i][0] = list[s][0];
					 list[i][1] = list[s][1];
					 list[s][0] = a;
					 list[s][1] = b;
					 i++;
					 s--;
					 
				}
		  } while(i <= s);
		  if(inf < s) {
				qsort(list, inf, s);
		  }
		  if(i < sup) {
				qsort(list, i, sup);
		  }
		  		  
	 }

	 /**
	  * Expandir operando i
	  *
	  * Incorporar todos sus trminos a este operador
	  *
	  * @param i ndice del operando a expandir
	  */
	 protected void expand(int i) {
		  MathNode op = operand.get(i);
		  if(op instanceof MultipleOp) {
				MultipleOp mpr = (MultipleOp) op;
				operand.remove(i);
				operand.addAll(mpr.operand);
		  }
	 }


	 /**
	  * expandir operandos del mismo tipo
	  */
	 protected void expandSame()
	 {
		  int count = 0;
		  do {
				count = 0;
				for(int i = operand.size() - 1; i >= 0; i--) {
					 MathNode x = operand.get(i);
					 if(isSameOperator(x)) {
						  expand(i);
						  count++;
					 }
				}
		  } while(count > 0);
	 }

	 /**
	  * Obtiene una version de este nodo con los subnodos del
	  * tipo expandidos
	  */

	 protected MathNode getExpanded()
	 {
		  Vector<MathNode> ops = new Vector<MathNode>(numNodes(), numNodes());
		  for(int i = 0; i < numNodes(); i++) {
				MathNode n = getNode(i);
				if(isSameOperator(n)) {
					 for(int j = 0; j < n.numNodes(); j++) {
						  MathNode m = n.getNode(j);
						  ops.add(m);
					 }
				} else {
					 ops.add(n);
				}
		  }

		  return makeSame(ops);
	 }


	 /**
	  * crear un operador mltiple del mismo tipo
	  */
	 public abstract MathNode makeSame(Vector<MathNode> ops);

	 public boolean containsSame() {
		  for(int i = 0; i < numNodes(); i++) {
				if(isSameOperator(getNode(i))) return true;
		  }
		  return false;
	 }

	 /**
	  * permite a las subclases comprobar si uno de sus operandos
	  * es a su vez un operador del mismo tipo
	  */
	 protected abstract boolean isSameOperator(MathNode x);

	 public boolean equals(Object x) {
		  if(x == null) return false;
		  if(!(x instanceof MathNode) || !isSameOperator((MathNode) x)) {
			  return false;
		  }
		  MultipleOp m = (MultipleOp) x;
		  if(m.numNodes() != numNodes()) {
				return false;
		  }
		  for(int i = 0; i < numNodes(); i++) {
				if(!getNode(i).equals(m.getNode(i))) {
					 return false;
				}
		  }
		  return true;
		  		  
	 }

	 


}
