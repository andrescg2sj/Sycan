package org.sj.tools.sycan.circedit;

import java.awt.Canvas;
import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Button;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.sj.utils.math.complex.Complex;

import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Context;

import org.sj.utils.math.symbol.mathrender.Renderable;
import org.sj.utils.math.symbol.mathrender.GraphMaker;

class Board extends Canvas {

    Renderable r;
    Expression exp;

    public Board(Expression e) {
		  exp = e;
		  r = null;
    }

    void makeExp(Graphics g) {
		  if(g == null) System.out.println("NULL");

		  GraphMaker gm = new GraphMaker(g);

		  try {
				this.r = gm.makeGraph(exp);
		  } catch(Exception e) {
				// ...
		  }

    }

    public void paint(Graphics g) {
		  Rectangle bounds = getBounds();
		  if(r == null) {
				makeExp(g);
		  }
		  Rectangle rect = r.getBounds();

		  int x = (int) bounds.getWidth() / 2 - (int) rect.getWidth()/2;
		  int y = (int) bounds.getHeight() / 2;
		  r.render(g, x, y);
    }
}

public class FormulaWindow extends SimpleWindow implements ActionListener {

    Board brd;
	 CircDoc doc;
	 Expression exp;

	 static final int DEF_WIDTH = 400;
	 static final int DEF_HEIGHT = 300;

    public FormulaWindow(String title, Expression exp, CircDoc doc) {
		  super(title);

		  this.exp = exp;
		  this.doc = doc;
		  brd = new Board(exp);
		  setSize(DEF_WIDTH, DEF_HEIGHT);

		  setLayout(new BorderLayout());
		  add(BorderLayout.CENTER, brd);

		  Panel p = new Panel();
		  Button btn = new Button("Plot");
		  btn.addActionListener(this);
		  p.add(btn);
		  add(BorderLayout.SOUTH, p);
    }


	 public void actionPerformed(ActionEvent ae) {

		  String names[] = doc.getCircVarNames();

		  /* obtener parámetros */
		  System.out.println("get Params...");
		  ParamDlg dlg = new ParamDlg(MainPrg.getMainWindow(), names);
		  dlg.setVisible(true);
		  if(!dlg.getResult()) {
				return;
		  }

		  System.out.println("get Exp & Ctx...");
		  if(exp == null) {
				System.err.println("EXP == NULL!!");
		  }

		  Context ctx = new Context();
		  System.out.println("exp: " + exp.toString());

		  /* get parameters */
		  String pairs[][] = dlg.getValues();
		  for(int i = 0; i < pairs.length; i++) {
				String name = pairs[i][0];
				String value = pairs[i][1];
				double v = Double.parseDouble(value);
				System.out.println("Assign: "+name+" = " + v);
				ctx.setValue(name, v);
		  }


 		  PlotWindow pw = new PlotWindow(exp, ctx);
 		  pw.setVisible(true);

	 }
}
