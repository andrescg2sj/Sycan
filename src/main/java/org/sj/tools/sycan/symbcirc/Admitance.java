package org.sj.tools.sycan.symbcirc;

//import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Expression;

public abstract class Admitance extends Element {


	 Admitance(int a, int b, String n)
	 {
		  super(a, b, n);
	 }

	 Admitance(int a, int b)
	 {
		  super(a, b);
	 }

	 /**
	  * obtener admitancia
	  */
	 public abstract Expression getY();


	 public String toString() {
		  return "Y("+A+","+B+");";
	 }
		  
}