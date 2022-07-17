package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;
import java.util.Vector;

/**
 * Operador suma
 */
public class Sum extends MultipleOp { 

	 

	 public Sum(Vector<MathNode> ops) {
		  super(ops);
	 }

	 public Sum(Vector<MathNode> ops, boolean srt) {
		  super(ops, srt);
	 }


	 public Sum(MathNode op1, MathNode op2) {
		  super(op1, op2);
	 }

	 public Sum(MathNode op1, MathNode op2, boolean srt) {
		  super(op1, op2, srt);
	 }


	 protected boolean isSameOperator(MathNode x) {
		  return (x instanceof Sum);
	 }


	 public Complex eval(Context ctx) {
		  Complex z = new Complex();
		  for(int i = 0; i< operand.size(); i++) {
				MathNode obj = operand.get(i);
				z.add(obj.eval(ctx));
		  }
		  return z;
	 }

	 public MathNode makeSame(Vector<MathNode> ops)
	 {
		  return new Sum(ops, false);
	 }

	 public Sum clone() {
		  return new Sum(operand, false);
	 }

	 public Sum fork(int i, MathNode obj) {
// 		  if(i < 0 || i > operand.size()) {
// 				return clone();
// 		  }

		  Vector<MathNode> v = new Vector<MathNode>(operand);
		  v.setElementAt(obj, i);
		  return new Sum(v, false);
	 }


	 public  String _DBG_showTree()
	 {

		  if(operand.size() == 0) {return "{}"; }
		  if(operand.size() == 1) {
				return "{"+operand.firstElement()._DBG_showTree()+"}";}
		  MathNode first = operand.firstElement();

		  String str = "{" + first._DBG_showTree();
		  for (int i = 1; i < operand.size(); i++) {
				str += "+" + operand.get(i)._DBG_showTree();
		  }
		  str += "}";
		  return str;

	 }

	 public String toString() {
		  if(operand.size() == 0) {return "0";}
		  String str = "{"+operand.firstElement().toString();
		  for (int i = 1; i < operand.size(); i++) {
				str += " + " + operand.get(i).toString();
		  }
		  str +="}";
		  return str;
	 }

	 
}
