package org.sj.tools.sycan.circedit.draw;


import org.sj.tools.sycan.circedit.Const;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Vector;


public class Drawing implements Drawable, Const {

	 private Vector<Drawable> elems;
	 private Vector<Point> nodes;
	 private Rectangle collisionRegion;

  	 public Drawing() {
		  elems = new Vector<Drawable>();
	 }

  	 public Drawing(Vector<Drawable> _elems) {
		  elems = _elems;
	 }
  	 
  	 public Drawing(Vector<Drawable> _elems, Vector<Point> _nodes) {
		  elems = _elems;
		  nodes = _nodes;
	 }
  	 
  	 public Drawing(Vector<Drawable> _elems, Vector<Point> _nodes, Rectangle _bounds) {
		  elems = _elems;
		  nodes = _nodes;
		  collisionRegion = _bounds;
	 }



	 public void add(Drawable d) {
		  elems.add(d);
	 }
	 
	 public Point getNodePos(int i, Base b) {
		 return b.transform(nodes.get(i));
	 }

	 public void draw(Graphics g, Base b) {
		  for(int i = 0; i < elems.size(); i++) {
				elems.get(i).draw(g, b);
		  }
	 }
	 
	 public boolean isInside(Point p, Base b) {
		 Rectangle bounds = b.transform(collisionRegion);
		 return bounds.contains(p);
	 }
	 
	 
}