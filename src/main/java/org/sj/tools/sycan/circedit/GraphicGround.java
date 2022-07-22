package org.sj.tools.sycan.circedit;

import org.sj.tools.sycan.symbcirc.Element;

import org.sj.tools.sycan.circedit.draw.Base;
import org.sj.tools.sycan.circedit.draw.Drawing;
import org.sj.tools.sycan.circedit.draw.DrawingFactory;
import java.awt.Point;
import java.awt.Graphics;

/**
 * 
 */
public class GraphicGround extends GraphicPart  {

	 /** posicion */
	 protected Point center;

	 public GraphicGround(Point _center) 
	 {
		  super("GND", TYPE_GROUND);
		  center = _center;
	 }

	 public boolean isInside(Point p) {
		  return	(p.getX() >= (center.getX() - COMP_SIZE/2)) &&
				(p.getX() <= (center.getX() + COMP_SIZE/2)) &&
				(p.getY() >= center.getY()) &&
				(p.getY() <= (center.getY() + COMP_SIZE));
	 }


	 public GraphicNode[] getNodes() {
		  GraphicNode nodes[] = new GraphicNode[1];
		  nodes[0] = new GraphicNode(getNodePos(), NODE_A);	
		  return nodes;
	 }

	 /**
	  * Generar
	  */

	 public Point getNodePos() {
		  return (Point) center.clone();
	 }



	 public void changeDir() {
		  ;
	 }



	 public void draw(Graphics g)
	 {
		  Base b = new Base(center, DIR_RIGHT);
		  Drawing drawing = DrawingFactory.getInstance().getDrawing(type);
		  drawing.draw(g, b);

 	 }

	 public String toString() {
		  String strC = String.format("(%d, %d)",
												(int) center.getX(),
												(int) center.getY());
		  return "GND " + strC;
	 }
}
