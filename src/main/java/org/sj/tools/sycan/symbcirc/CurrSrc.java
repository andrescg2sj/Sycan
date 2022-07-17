package org.sj.tools.sycan.symbcirc;

import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Variable;

/**
 * Current Source
 *
 *
 * *  o     + (A)
 *    |
 *    |
 *   ---      |
 * /  |  \    |
 * |  |  |    |  i
 * \  V  /   \|/ 
 *   ----     V
 *    |
 *    o     - (B)
 *
 *
 *
 *
 * @since 0.1
 *
 */
public class CurrSrc extends Element {


	 /** contador de fuentes de corriente creadas */
	 private static int count = 0;

	 /** corriente del generador */
	 private Complex I;

	 public CurrSrc(int a, int b, Complex i)
	 {
		  super(a, b);
		  I = i;
	 }

	 public CurrSrc(int a, int b, float i)
	 {
		  this(a, b, new Complex(i, 0));
	 }

	 public CurrSrc(int a, int b, Complex i, String name)
	 {
		  super(a, b, name);
		  I = i;
	 }



	 public Expression getI()
	 {
		  return new Expression(new Variable(name));
	 }

	 protected String defaultName() {
		  return "I"+(count++);
	 }

	 public Object clone() {
		  return new CurrSrc(A, B, I, name);
	 }

	 public String toString() {
		  return "CS("+A+","+B+") = "+I+";";
	 }
	 
}
 