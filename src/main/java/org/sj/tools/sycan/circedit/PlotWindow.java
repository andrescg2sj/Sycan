package org.sj.tools.sycan.circedit;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Context;
import org.sj.utils.math.complex.Complex;

/**
 * Plots a function
 */
/* TODO */
public class PlotWindow extends SimpleWindow {


	 static final int DEF_WIDTH = 600;
	 static final int DEF_HEIGHT = 400;


	 /** function to be plotted */
	 Expression func;

	 /** parameter values */
	 Context ctx;

		  

	 /* graph parameters */
	 static final int LEFT_OFF = 50;
	 static final int TOP_OFF = 30;
	 static final int RIGHT_OFF = 20;
	 static final int BOTTOM_OFF = 30;
	 static final int TEXT_OFF = 20;

	 static final int TEXT_POS_X = 5;
	 static final int TEXT_POS_Y = 15;
	 
 
	 int maxdB = 30;
	 int mindB = -30;
	 int minFrecExp = 1;
	 int maxFrecExp = 5;

	 /* useful derived values */

	 /*   graph bounds */
	 int min_x, min_y, max_x, max_y;

	 /*   graph grid steps */
	 int step_x, step_y;
	 
 
	 private class Plotter extends Canvas {

		  /** calculate derived values */
		  public void calcData() {	
				Rectangle r = getBounds();

				min_y = TOP_OFF;
				max_y = (int) r.getHeight() - BOTTOM_OFF;
				min_x = LEFT_OFF;
				max_x = (int) r.getWidth() - RIGHT_OFF;

				step_x = (max_x - min_x) / (maxFrecExp - minFrecExp);
				step_y = (int) (max_y - min_y) / ((maxdB - mindB) / 10);
		  }

		  public int dBtoScr(double dB)  {
				return max_y - (int) ((double) (dB - mindB)/10.0*step_y);
		  }

		  public int frecToScr(double f)  {
				return min_x + (int) ((double) step_x*(Math.log(f)/Math.log(10) - minFrecExp));
		  }

		  public void paint(Graphics g) {


				calcData();
				Rectangle r = getBounds();

				/* clear */
				g.setColor(Color.WHITE);
				g.fillRect(0,0, (int) r.getWidth(), (int) r.getHeight());

				/* calculate graph bounds */
				/* vertical lines */
				int frecExpRange = maxFrecExp - minFrecExp;

				for(int i = 0; i < frecExpRange; i++) {
					 int f0 = (int) Math.pow(10, i+minFrecExp);
					 int x0 = frecToScr(f0);
					 g.setColor(Color.BLACK);
					 g.drawLine(x0, min_y, x0, max_y);
					 String txt = "" + f0;
					 g.drawString(txt, x0, max_y + TEXT_OFF);
				 
					 for(int j = 2; j < 10; j++) {
						  int f = (int) (j* Math.pow(10, i+minFrecExp));
						  int x = frecToScr(f);
						  g.setColor(Color.GRAY);
						  g.drawLine(x, min_y, x, max_y);
					 }
				}

				/* horizontal lines */
				int min = (int) Math.ceil(mindB/10.0);
				int max = (int) Math.floor(maxdB/10.0);
				int range = max-min;
				g.setColor(Color.BLACK);
				for(int i = 0; i < range; i++) {
					 int dB = (min + i) * 10;
					 String txt = "" + dB;
					 int y = dBtoScr(dB);

					 g.drawString(txt, min_x / 3, y);
					 g.drawLine(min_x, y, max_x, y);
				}

				/* 0 dB line */
				int y0 = dBtoScr(0);
				g.setColor(Color.BLUE);
				g.drawLine(min_x, y0, max_x, y0);

				/* graph outline */
				g.setColor(Color.BLACK);
				g.drawRect(min_x, min_y, (max_x - min_x), (max_y - min_y));


				/* plot function */
				System.out.println("Plot:");
				int prev_x = min_x;
				int prev_y = dBtoScr(0);
				double log10 = Math.log(10);

				for(int i = 0; i < frecExpRange; i++) {

					 System.out.println("i = "+i);
					 for(int j = 1; j < 10; j++) {
						  int f = (int) (j* Math.pow(10, i+minFrecExp));
						  int x = frecToScr(f);

						  ctx.setValue("s", 0, 2*Math.PI*f);
						  Complex z = func.eval(ctx);
						  double v = 20*Math.log(z.module())/log10;

						  if(v < -30000) {
								continue;
						  }
						  int y = dBtoScr(v);
						  //System.out.println("("+x+", "+y+") f="+f+" Hz, v="+ v+ " dB");

						  g.setColor(Color.GREEN);
						  g.drawLine(x, y, prev_x, prev_y);
						  prev_x = x;
						  prev_y = y;
					 }
				}

				/* show expression */
				g.setColor(Color.BLACK);
				g.drawString("Function: "+func.toString(), LEFT_OFF, TOP_OFF/2);
		  }
	 }

	 


	 Plotter plotter;

	 public PlotWindow(Expression exp, Context c) {
		  super("Plot");
		  setSize(DEF_WIDTH, DEF_HEIGHT);
		  func = exp;
		  ctx = c;

		  plotter = new Plotter();
		  add(plotter);
		  
	 }


	 
}