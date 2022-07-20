package org.sj.tools.sycan.symbcirc;

import org.sj.utils.math.complex.Complex;

public class VirtualShortCirc extends Element {
	
	 public VirtualShortCirc(int a, int b)
	 {
		  super(a, b);
		  //erasable = true;
	 }

	@Override
	protected String defaultName() {
		
		return "VSC";
	}

	@Override
	public Object clone() {
		return new VirtualShortCirc(A, B);
	}

}
