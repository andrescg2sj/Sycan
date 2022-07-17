package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;
import java.util.Vector;

/**
 * Operador suma
 */
public class Product extends MultipleOp { 

	 
	 public Product(MathNode op1, MathNode op2) {
		  super(op1, op2);
	 }

	 public Product(MathNode op1, MathNode op2, boolean srt) {
		  super(op1, op2, srt);
	 }

	 public Product(Vector<MathNode> ops) {
		  super(ops);
	 }

	 public Product(Vector<MathNode> ops, boolean srt) {
		  super(ops, srt);
	 }

	 protected boolean isSameOperator(MathNode x) {
		  return (x instanceof Product);
	 }

	 public MathNode makeSame(Vector<MathNode> ops)
	 {
		  return new Product(ops, false);
	 }


	 public Complex eval(Context ctx) {
		  Complex z = new Complex(1, 0);
		  for(int i = 0; i< operand.size(); i++) {
				MathNode obj = operand.get(i);
				z.mult(obj.eval(ctx));
		  }
		  return z;
	 }

	 public Product clone() {
		  return new Product(operand, false);
	 }

	 public Product fork(int i, MathNode obj) {
// 		  if(i < 0 || i > operand.size()) {
// 				throw new Exception("");
// 		  }

		  Vector<MathNode> v = new Vector<MathNode>(operand);
		  v.setElementAt(obj, i);
		  return new Product(v, false);
	 }


	 public String toString() {
		  if(operand.size() == 0) {return "0"; /* TODO : deberia ser 1?*/}
		  if(operand.size() == 1) {return operand.firstElement().toString();}
		  MathNode first = operand.firstElement();

		  String str = "(";
		  if((first instanceof Constant) && ((Constant)first).value.is(-1,0)) {
				str += "-" + operand.get(1).toString();
		  } else {
			  str += first.toString() + "" + operand.get(1).toString();
		  }
		  for (int i = 2; i < operand.size(); i++) {
				str += "" + operand.get(i).toString();
		  }
		  str += ")";
		  return str;
	 }

	 public  String _DBG_showTree()
	 {

		  if(operand.size() == 0) {return "()"; }
		  if(operand.size() == 1) {
				return "("+operand.firstElement()._DBG_showTree()+")";
		  }
		  MathNode first = operand.firstElement();

		  String str = "(" + first._DBG_showTree();
		  for (int i = 1; i < operand.size(); i++) {
				str += "" + operand.get(i)._DBG_showTree();
		  }
		  str += ")";
		  return str;

	 }


	 
}
