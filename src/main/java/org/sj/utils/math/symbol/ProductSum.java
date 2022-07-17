package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;
import java.util.Vector;

/**
 * Suma de productos
 */
public class ProductSum extends Sum { 
	 

	 public ProductSum(Sum s) {
		  super(s.operand, false);
	 }



	 public Sum clone() {
		  return new ProductSum(this);
	 }

	 public boolean isCommonFactor(MathNode n) {
		  for(int i = 0; i < numNodes(); i++) {
				MathNode m = getNode(i);
				if(!n.equals(m)) {
					 if(m instanceof Product) {
						  Product p = (Product) m;
						  if(!p.operand.contains(n)) {
								return false;
						  }
					 } else {
						  return false;
					 }
				}
		  }
		  return true;
	 }

	 public MathNode getCommonFactor() {
		  for(int i = 0; i < numNodes(); i++) {
				MathNode n = getNode(i);
				if(isCommonFactor(n)) return n;

				if(n instanceof Product) {
					 Product p = (Product) n;
					 for(int j = 0; j < p.numNodes(); j++) {
						  MathNode m = p.getNode(j);
						  if(isCommonFactor(m)) return m;
					 }
				}
		  }
		  return null;
	 }

	 public MathNode extractCommonFactor() {
		  ////System.out.println("extract ("+this + ")");
		  MathNode c = getCommonFactor();
		  ////System.out.println("  common factor:" + c);
		  if(c == null) {
				return null;
		  }

		  Vector<MathNode> addends = new Vector<MathNode>(numNodes());

		  for(int i = 0; i < numNodes(); i++) {
				MathNode a = divideNode(i, c); 
				addends.add(a);
				////System.out.println("  addend["+i+"]: " + a);
		  }
		  
		  return new Product(c, new Sum(addends));
	 }

	 public MathNode divideNode(int i, MathNode n) {
		  MathNode m = getNode(i);
		  if(m.equals(n)) {
				return new Constant(1);
		  }
		  
		  if(m instanceof Product) {
				Product p = (Product) m;
				Vector<MathNode> factors = new Vector<MathNode>(p.operand);
				if(factors.remove(n)) {
					 return new Product(factors);
				}
		  }

		  return new Division(m, n);
	 }

}
