package org.sj.tools.sycan.circedit;


import java.util.Iterator;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

//class MouseHandler implements MouseListener, MouseMotionListener {



public class CircView extends View implements Const {

	 static final int DEF_WIDTH = 500;
	 static final int DEF_HEIGHT = 400;


	 /** para dibujar cortocircuitos */
	 Point lastB, lastMiddle;

	 public CircView()
	 {
		  setSize(DEF_WIDTH, DEF_HEIGHT);
	 }

	 /* --- Mtodos heredados --- */

	 public int processMessage(int msgIndex, Object obj)
	 {
		  return 0;
	 }


	 public void onDraw(Graphics g, Rectangle bounds)
	 {
		  /* Dibujar malla */
// 		  int Nw = (int) bounds.getWidth() / GRID_SIZE;
// 		  int Nh = (int) bounds.getHeight() / GRID_SIZE;

		  for(int y = 0; y < (int) bounds.getHeight(); y+=GRID_SIZE) {
				for(int x = 0; x < (int) bounds.getWidth(); x+=GRID_SIZE) {
					 g.drawLine(x-1, y, x+1, y);
					 g.drawLine(x, y-1, x, y+1);
				}
		  }

		  Iterator<GraphicElement> it = getCircDoc().getIterator();

		  while(it.hasNext()) {
				GraphicElement ge = it.next();

				ge.draw(g);
		  }
	 }


	 public Object requestObject(int objType, int objIndex)
	 {
		  return null;
	 }


	 /* --- Otros mtodos --- */

	 public CircDoc getCircDoc() {
		  return (CircDoc) getDoc();
	 }

	 public void updateView() {
		  repaint();
	 }

	 public Point snapToGrid(Point p) {
		  int x = GRID_SIZE * (int) Math.round(p.getX() / (double) GRID_SIZE);
		  int y = GRID_SIZE * (int) Math.round(p.getY() / (double) GRID_SIZE);
		  return new Point(x, y);
	 }

	 public void beginSCPreview(Point A) {
		  lastMiddle = A;
		  lastB = A;
	 }


	 public void drawSCPreview(Point A, Point B, boolean draw_up) {
		  Graphics g = getGraphics();
		  g.setXORMode(Color.WHITE);
		  Point middle = GraphicShortCirc.calcMiddle(A, B, draw_up);
		  if((lastMiddle != null) && (lastB != null)) {
				g.drawLine((int) A.getX(), (int) A.getY(),
							  (int) lastMiddle.getX(),  (int) lastMiddle.getY());
				g.drawLine((int) lastB.getX(), (int) lastB.getY(),
							  (int) lastMiddle.getX(),  (int) lastMiddle.getY());
		  }
		  g.drawLine((int) A.getX(), (int) A.getY(),
						 (int) middle.getX(),  (int) middle.getY());
		  g.drawLine((int) B.getX(), (int) B.getY(),
						 (int) middle.getX(),  (int) middle.getY());
		  g.setPaintMode();
		  lastB = B;
		  lastMiddle = middle;
	 }

	 

}