package org.sj.tools.sycan.symbcirc;

import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Constant;


/**
 * Short Circuit
 * @since 0.1
 *
 */
public class ShortCirc extends VoltSrc  {

	 protected boolean erasable;

	 public ShortCirc(int a, int b)
	 {
		  super(a, b, new Complex(), "");
		  erasable = true;
	 }

	 public ShortCirc(int a, int b, boolean er)
	 {
		  super(a, b, new Complex(), "");
		  erasable = er;
	 }

	 public Object clone() {
		  return new ShortCirc(A, B);
	 }

	 public Expression getV()
	 {
		  return new Expression(new Constant(0));
	 }

	 public boolean isErasable()
	 {
		  return erasable;
	 }


	 public String toString() {
		  return "SC("+A+","+B+");";
	 }
	 
}
 