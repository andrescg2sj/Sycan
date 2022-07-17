package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;

/**
 * MathNode sin subnodos
 */
public abstract class Leaf extends MathNode {


	 public int numNodes() {
		  return 0;
	 }

	 public MathNode getNode(int i) {
		  return null;
	 }

	 public MathNode fork(int i, MathNode obj) {
		  return clone();
	 }

	 public int weight() {
		  return 1;
	 }

	 public abstract Leaf clone();

	 public String _DBG_showTree()
	 {
		  return toString();
	 }
	 
}