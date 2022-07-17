package org.sj.tools.sycan.symbcirc;

/**
 * Fuente de corriente controlada por tensión
 * vg = f·v_z = f·(v_x - v_y)
 *    f : factor
 */
public class CCCS extends CC_Src {

	 private static int count = 0;

	 public CCCS(int a, int b, String n, int br)
	 {
		  super(a, b, n,  br);
	 }

	 public CCCS(int a, int b, int br)
	 {
		  super(a, b, br);
	 }

	 protected String defaultName()
	 {
		  return "CCCS"+(count++);
	 }

	 public String toString() {
		  return "CCCS("+A+","+B+","+branch+") = "+name+" ( );";
	 }

	 public Object clone() {
		  return new CCCS(A, B, name, branch);
	 }
}