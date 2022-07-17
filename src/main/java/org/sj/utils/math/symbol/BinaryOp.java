package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;

/**
 * Operador binario
 */
public abstract class BinaryOp extends MathNode {

	 static final int NUM_NODES = 2;

	 MathNode A;
	 MathNode B;

	 
	 public BinaryOp(MathNode op1, MathNode op2) {
		  A = op1;
		  B = op2;
	 }

	 public int numNodes() {
		  return NUM_NODES;
	 }

	 public int weight() {
		  return 1 + A.weight() + B.weight();
	 }

	 
}
