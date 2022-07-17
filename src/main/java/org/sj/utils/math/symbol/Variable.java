package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;


public class Variable extends Leaf {


	 String name;
	 
	 public Variable(String n) {
		  name = n;
	 }

	 public Complex eval(Context x) {
		  if(x != null) {
				return x.getValue(name);
		  }
		  return new Complex();
	 }

	 public String toString() {
		  return name;
	 }


	 public boolean equals(Object n) {
	     if (n instanceof Variable) {
		 Variable v = (Variable) n;
		 boolean x = name.equals(v.name);
		 return x;
	     }
	     return false;
	 }

	 public Variable clone() {
		  return new Variable(name);
	 }

	 public int weight()	 {
		  return 2;
	 }

	 
}
