package org.sj.tools.sycan.circedit.draw;

import java.awt.geom.Point2D;
import java.awt.Graphics;

public class Line implements Drawable {

	 Point2D p1, p2;

	 public Line(float x1, float y1, float x2, float y2) {
		  p1 = new Point2D.Float(x1, y1);
		  p2 = new Point2D.Float(x2, y2);
	 }

	 public Line(Point2D _p1, Point2D _p2) {
		  if(_p1 == null) {
				p1 = new Point2D.Float(0, 0);
		  } else {
				p1 = _p1;
		  }
		  
		  if(_p2 == null) {
				p2 = new Point2D.Float(0, 0);
		  } else {
				p2 = _p2;
		  }
		  
	 }


	 public void draw(Graphics g, Base b) {
		  Point2D q1 = b.transform(p1);
		  Point2D q2 = b.transform(p2);
		  g.drawLine((int) q1.getX(), (int) q1.getY(),
						 (int) q2.getX(), (int) q2.getY());
	 }
}