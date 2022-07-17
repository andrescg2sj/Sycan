package org.sj.tools.sycan.symbcirc;

/**
 * Fuente  controlada por corriente
 */
public abstract class CC_Src extends CtrlSrc {

	 /** rama donde se mide la corrinte que controla esta fuente */
	 protected int branch;

	 public CC_Src(int a, int b, String n, int br)
	 {
		  super(a, b, n);
		  branch = br;
	 }

	 public CC_Src(int a, int b, int br)
	 {
		  super(a, b);
		  branch = br;
	 }

	 public int getBranch()
	 {
		  return branch;
	 }

	 public void setBranch(int br)
	 {
		  branch = br;
	 }

}