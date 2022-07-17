package org.sj.tools.sycan.symbcirc;


import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Variable;

/**
 * Fuente controlada por una variable del circuito
 *
 */
public abstract class CtrlSrc extends Element {

	 public CtrlSrc(int a, int b, String n)
	 {
		  super(a, b, n);
	 }

	 public CtrlSrc(int a, int b)
	 {
		  super(a, b);
	 }

	 public Expression getFactor() 
	 {
		  return new Expression(new Variable(name));
	 }

}