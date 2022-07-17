package org.sj.tools.sycan.symbcirc;

//import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Variable;

public class Resistor extends Admitance {

	 /** contador de resistencias creadas */
	 private static int count = 0;

	 /** resistencia */
	 private float R;


	 public Resistor(int a, int b, float r)
	 {
		  super(a, b);
		  R = r;
	 }

	 public Resistor(int a, int b, float r, String name)
	 {
		  super(a, b, name);
		  R = r;
	 }

	 public Expression getY()
	 {
		  Expression e = new Expression(new Variable(name));
		  
		  return e.getInverse();
	 }

	 public Object clone() {
		  return new Resistor(A, B, R, name);
	 }

	 protected String defaultName() {
		  return "R"+(count++);
	 }

	 public String toString() {
		  return "R("+A+","+B+") = "+R+" (Ohm);";
	 }
		  
}