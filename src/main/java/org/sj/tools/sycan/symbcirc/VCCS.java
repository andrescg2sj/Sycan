package org.sj.tools.sycan.symbcirc;

/**
 * Fuente de corriente controlada por tensión
 * ig = f·v_z = f·(v_x - v_y)
 *    f : factor
 */
public class VCCS extends VC_Src {

	 private static int count = 0;


	 public VCCS(int a, int b, String n, int x, int y)
	 {
		  super(a, b, n,  x, y);
	 }

	 public VCCS(int a, int b, int x, int y)
	 {
		  super(a, b, x, y);
	 }

	 protected String defaultName()
	 {
		  return "VCCS"+(count++);
	 }

	 public String toString() {
		  return "VCCS("+A+","+B+","+X+","+Y+") = "+name+" (1/Ohm);";
	 }

	 public Object clone() {
		  return new VCCS(A, B, name, X, Y);
	 }
}