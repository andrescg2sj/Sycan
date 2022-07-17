package org.sj.tools.sycan.circedit.draw;

import org.sj.tools.sycan.circedit.Const;
import java.awt.geom.Point2D;
import java.awt.Graphics;

public class Arc implements Drawable, Const {

	 Point2D center;
	 float radius;
	 int startAngle;
	 int arcAngle;

	 public Arc(float x1, float y1, float r, int sA, int aA) {
		  center = new Point2D.Float(x1, y1);
		  radius = r;
		  startAngle = sA;
		  arcAngle = aA;
	 }

	 public Arc(Point2D c, float r, int sA, int aA) {
		  if(c == null) {
				center = new Point2D.Float(0, 0);
		  } else {
				center = c;
		  }
		  radius = r;
		  startAngle = sA;
		  arcAngle = aA;
	 }


	 public void draw(Graphics g, Base b) {
		  Point2D d = b.trans(center);

		  int offAngle = 0;
		  switch(b.dir) {
		  case DIR_RIGHT: offAngle = 0; break;
		  case DIR_UP: offAngle = -90; break;
		  case DIR_LEFT: offAngle = 180; break;
		  case DIR_DOWN: offAngle = -270;break;
		  }

		  g.drawArc((int) (d.getX() - radius), (int) (d.getY() - radius),
						(int) (2*radius), (int) (2*radius),
						startAngle+offAngle, arcAngle);
	 }
}