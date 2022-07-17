package org.sj.tools.sycan.circedit.draw;

import org.sj.tools.sycan.circedit.Const;
import java.awt.geom.Point2D;
import java.awt.Point;

public class Base implements Const {

	 private Point2D origin;
	 int dir;

	 public Base(Point2D o, int d) {
		  dir = d % 4;
		  origin = o;
	 }

	 public Base(double x, double y, int d) {
		  dir = d % 4;
		  origin = new Point2D.Double(x, y);
	 }

	 /**
	  * A partir de un punto en coordenadas relativas a esta base,
	  * obtiene otro en coordenadas relativas a la base cannica:
	  * ((0,0), (1, 0), (0,1))
	  *
	  * @param p punto en coordenades de esta base
	  */
	 Point2D trans(Point2D p) {
		  float x = (float) origin.getX();
		  float y = (float) origin.getY();

		  switch(dir) {
		  case DIR_RIGHT: // normal
				return new Point2D.Float((float) (x + p.getX()),
												 (float) (y + p.getY()));
		  case DIR_UP:
				return new Point2D.Float((float) (x + p.getY()),
												 (float) (y - p.getX()));
		  case DIR_LEFT:
				return new Point2D.Float((float) (x - p.getX()),
												 (float) (y - p.getY()));
		  case DIR_DOWN:
				return new Point2D.Float((float) (x - p.getY()),
												 (float) (y + p.getX()));
		  }

		  return new Point2D.Float(0,0);
	 }


	 public Point trans(Point p) {
		  Point2D q = new Point2D.Float((float) p.getX(), (float) p.getY());
		  Point2D r = trans(q);
		  return new Point((int) r.getX(), (int) r.getY());
	 }

	 /**
	  * Transformacin inversa.
	  * A partir de un punto en coordenadas absolutas,
	  * obtiene otro en coordenadas relativas a esta base
	  *
	  * @param p punto en coordenades de esta base
	  */
	 Point2D inverse(Point2D p) {
		  float x = (float) origin.getX();
		  float y = (float) origin.getY();

		  switch(dir) {
		  case DIR_RIGHT: // normal
				return new Point2D.Float((float) (p.getX() - x),
												 (float) (p.getY() - y));
		  case DIR_UP:
				return new Point2D.Float((float) (y - p.getY()),
												 (float) (p.getX() - x));
		  case DIR_LEFT:
				return new Point2D.Float((float) (x - p.getX()),
												 (float) (y - p.getY()));
		  case DIR_DOWN:
				return new Point2D.Float((float) (p.getY() - y),
												 (float) (x - p.getX()));
		  }

		  return new Point2D.Float(0,0);
	  
	 }

	 
	 public Point inverse(Point p) {
		  Point2D q = new Point2D.Float((float) p.getX(), (float) p.getY());
		  Point2D r = inverse(q);
		  return new Point((int) r.getX(), (int) r.getY());
	 }
}