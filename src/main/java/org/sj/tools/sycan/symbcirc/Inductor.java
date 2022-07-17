package org.sj.tools.sycan.symbcirc;

//import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Variable;
import org.sj.utils.math.symbol.Product;

public class Inductor extends Admitance {

	 /** contador de inductores creados */
	 private static int count = 0;


	 /** inductancia */
	 private float L;


	 public Inductor(int a, int b, float l)
	 {
		  super(a, b);
		  L = l;
	 }

	 public Inductor(int a, int b, float l, String name)
	 {
		  super(a, b, name);
		  L = l;
	 }

	 public Expression getY()
	 {

		  Expression s = new Expression(new Variable("s"));
		  Expression L = new Expression(new Variable(name));
		  
		  return Expression.prod(s, L).getInverse();
	 }


	 protected String defaultName() {
		  return "L"+(count++);
	 }

	 public Object clone() {
		  return new Inductor(A, B, L, name);
	 }

	 public String toString() {
		  return "L("+A+","+B+") = "+L+" (H);";
	 }
		  
}