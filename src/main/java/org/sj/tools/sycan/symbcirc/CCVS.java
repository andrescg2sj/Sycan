package org.sj.tools.sycan.symbcirc;

/**
 * Fuente de corriente controlada por tensión
 * vg = f·v_z = f·(v_x - v_y)
 *    f : factor
 */
public class CCVS extends CC_Src {

	 private static int count = 0;

	 public CCVS(int a, int b, String n, int br)
	 {
		  super(a, b, n,  br);
	 }

	 public CCVS(int a, int b, int br)
	 {
		  super(a, b, br);
	 }

	 protected String defaultName()
	 {
		  return "CCVS"+(count++);
	 }

	 public String toString() {
		  return "CCVS("+A+","+B+","+branch+") = "+name+" ( );";
	 }

	 public Object clone() {
		  return new CCVS(A, B, name, branch);
	 }
}