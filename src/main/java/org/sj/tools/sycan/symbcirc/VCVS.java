package org.sj.tools.sycan.symbcirc;

/**
 * Fuente de corriente controlada por tensión
 * vg = f·v_z = f·(v_x - v_y)
 *    f : factor
 */
public class VCVS extends VC_Src {

	 private static int count = 0;


	 public VCVS(int a, int b, String n, int x, int y)
	 {
		  super(a, b, n,  x, y);
	 }

	 public VCVS(int a, int b, int x, int y)
	 {
		  super(a, b, x, y);
	 }

	 protected String defaultName()
	 {
		  return "VCVS"+(count++);
	 }

	 public String toString() {
		  return "VCVS("+A+","+B+","+X+","+Y+") = "+name+" ( );";
	 }

	 public Object clone() {
		  return new VCVS(A, B, name, X, Y);
	 }
}