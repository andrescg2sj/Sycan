package org.sj.tools.sycan.symbcirc;

/**
 * Fuente  controlada por tensión
 * v_z = v_x - v_y
 */
public abstract class VC_Src extends CtrlSrc {

	 /** Nodos cuya diferencia de tensiones controlan esta fuente */
	 protected int X;
	 protected int Y;

	 public VC_Src(int a, int b, String n, int x, int y)
	 {
		  super(a, b, n);
		  X = x;
		  Y = y;
	 }

	 public VC_Src(int a, int b, int x, int y)
	 {
		  super(a, b);
		  X = x;
		  Y = y;
	 }


	 public int getNodeX()
	 {
		  return X;
	 }

	 public int getNodeY()
	 {
		  return Y;
	 }
	 

	 public void setNodeX(int x)
	 {
		  X = x;
	 }

	 public void setNodeY(int y)
	 {
		  Y = y;
	 }

}