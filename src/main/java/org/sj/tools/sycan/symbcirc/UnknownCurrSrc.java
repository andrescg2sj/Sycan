package org.sj.tools.sycan.symbcirc;

import org.sj.utils.math.complex.Complex;

public class UnknownCurrSrc extends CurrSrc {

	public UnknownCurrSrc(int a, int b, String name) {
		/*TODO: Refactor this inheritance. Is there any need of default values? */
		super(a, b, new Complex(), name);
	}

	@Override
	public String toString() {
		return "UCS("+A+","+B+")";
	}
	
	public UnknownCurrSrc clone() {
		return new UnknownCurrSrc(A, B, name);
	}
}
