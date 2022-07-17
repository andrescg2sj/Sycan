package org.sj.tools.sycan.circedit.draw;

import java.awt.geom.Point2D;
import java.awt.Graphics;

public class Circle implements Drawable {

	 Point2D center;
	 float radius;

	 public Circle(float x1, float y1, float r) {
		  center = new Point2D.Float(x1, y1);
		  radius = r;
	 }

	 public Circle(Point2D c, float r) {
		  if(c == null) {
				center = new Point2D.Float(0, 0);
		  } else {
				center = c;
		  }

		  radius = r;
	 }


	 public void draw(Graphics g, Base b) {
		  Point2D d = b.trans(center);

		  g.drawOval((int) (d.getX() - radius), (int) (d.getY() - radius),
						 (int) (2*radius), (int) (2*radius));
	 }
}