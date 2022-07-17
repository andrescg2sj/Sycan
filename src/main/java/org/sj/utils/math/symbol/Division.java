package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;

/**
 * Operador binario
 */
public class Division extends BinaryOp { 

	 
	 public Division(MathNode op1, MathNode op2) {
		  super(op1, op2);
	 }


	 public Complex eval(Context ctx)
	 {
		  return Complex.div(A.eval(ctx), B.eval(ctx));
	 }

	 public String toString(){
		  return "["+A.toString()+"/"+B.toString()+"]";
	 }


	 public Division clone() {
		  return new Division(A, B);
	 }

	 public MathNode getNumerator() {
		  return A;
	 }

	 public MathNode getDenominator() {
		  return B;
	 }

	 public MathNode getNode(int i) {
		  switch(i) {
		  case 0:
				return A;
		  case 1:
				return B;
		  }

		  return null;
	 }

	 public MathNode fork(int i, MathNode obj) {
		  switch(i) {
		  case 0:
				return new Division(obj, B);
		  case 1:
				return new Division(A, obj);
		  }

		  return clone();
	 }

	 public String _DBG_showTree()
	 {
		  return "["+A._DBG_showTree()+"/"+B._DBG_showTree()+"]";
	 }

	 
}
