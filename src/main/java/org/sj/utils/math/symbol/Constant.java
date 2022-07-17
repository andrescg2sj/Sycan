package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;


public class Constant extends Leaf {

	 Complex value;

	 public Constant(Complex v)
	 {
		  value = v;
	 }

	 public Constant(double x)
	 {
		  value = new Complex(x, 0);
	 }

	 public boolean isZero()
	 {
		  return value.isZero();
	 }

	 public Complex eval(Context x) {
		  return value;
	 }

	 public String toString() {
		  return value.toString();
	 }

	 public boolean equals(Object obj) {
	     if(obj instanceof Constant) {
		 Constant c = (Constant) obj;
		 return value.equals(c.value);
	     }
	     return false;
	 }

	 public Constant clone() {
		  return new Constant(value);
	 }

	 
}
