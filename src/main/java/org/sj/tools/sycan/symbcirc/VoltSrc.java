package org.sj.tools.sycan.symbcirc;
import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Variable;

/**
 * Voltage source
 * @since 0.1
 *
 */
public class VoltSrc extends Element {


	 /** contador de generadores de tensin creados */
	 private static int count = 0;

	 /** tensin del generador */
	 private Complex V;

	 public VoltSrc(int a, int b, Complex v)
	 {
		  super(a, b);
		  V = v;
	 }

	 public VoltSrc(int a, int b, float v)
	 {
		  this(a, b, new Complex(v, 0));
	 }


	 public VoltSrc(int a, int b, Complex v, String name)
	 {
		  super(a, b, name);
		  V = v;
	 }


	 public Expression getV()
	 {
		  return new Expression(new Variable(name));
	 }

	 protected String defaultName() {
		  return "V"+(count++);
	 }


	 public Object clone() {
		  return new VoltSrc(A, B, V, name);
	 }

	 public String toString() {
		  return "VS("+A+","+B+") = "+V+";";
	 }
	 
}
 