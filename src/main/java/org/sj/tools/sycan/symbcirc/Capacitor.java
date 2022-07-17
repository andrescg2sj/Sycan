package org.sj.tools.sycan.symbcirc;

//import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Variable;
import org.sj.utils.math.symbol.Product;

public class Capacitor extends Admitance {


	 /** contador de capacitores creados */
	 private static int count = 0;

	 /** capacidad */
	 private float C;


	 public Capacitor(int a, int b, float c)
	 {
		  super(a, b);
		  C = c;
	 }


	 public Capacitor(int a, int b, float c, String name)
	 {
		  super(a, b, name);
		  C = c;
	 }


	 public Expression getY()
	 {

		  Expression s = new Expression(new Variable("s"));
		  Expression C = new Expression(new Variable(name));

		  return Expression.prod(s, C);
	 }


	 protected String defaultName() {
		  return "C"+(count++);
	 }


	 public Object clone() {
		  return new Capacitor(A, B, C, name);
	 }

	 public String toString() {
		  return "C("+A+","+B+") = "+C+" (F);";
	 }
		  
}