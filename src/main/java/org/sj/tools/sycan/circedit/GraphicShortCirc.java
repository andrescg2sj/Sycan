package org.sj.tools.sycan.circedit;

import org.sj.tools.sycan.symbcirc.Element;

import java.util.Vector;

import org.sj.tools.sycan.circedit.draw.Drawing;
import org.sj.tools.sycan.circedit.draw.DrawingFactory;
import java.awt.Rectangle;
import java.awt.Point;
//import java.awt.geom.Point2D;
import java.awt.Graphics;

/**
 * Línea o nodo.
 */
public class GraphicShortCirc extends GraphicElement {


	 /* === Atributos clave === */
	 /** Posiciones de los nodos */
	 Point nodeA, nodeB;

	 /** dibujar por encima (true) o por debajo (false) de la diagonal */
	 boolean draw_up;


	 /* === Atributos intermedios === */
	 Point middle;


	 public GraphicShortCirc(String _name, Point _nodeA,
									 Point _nodeB, boolean _draw_up) 
	 {
		  super(_name, TYPE_SHORT_CIRC);

		  draw_up = _draw_up;
		  
		  
		  nodeA = _nodeA;
		  nodeB = _nodeB;
		  calcMiddle();
	 }


	 /* TODO: Esto está copiado de GraphicTLElement: reunir en superclase */
	 public GraphicNode[] getNodes() {
		  GraphicNode nodes[] = new GraphicNode[2];
		  nodes[0] = new GraphicNode(getNodeAPos(), NODE_A);	
		  nodes[1] = new GraphicNode(getNodeBPos(), NODE_B);
		  return nodes;
	 }


	 public boolean isInside(Point p)
	 {
		  Rectangle r = new Rectangle(nodeA);
		  r.add(middle);
		  r.grow(NODE_RAD/2, NODE_RAD/2);
		  
		  if(r.contains(p)) {
				return true;
		  }
		  r = new Rectangle(nodeB);
		  r.add(middle);
		  r.grow(NODE_RAD/2, NODE_RAD/2);

		  return r.contains(p);
	 }


	 public void changeDir()
	 {
		  draw_up ^= true;
		  calcMiddle();
	 }

	 void calcMiddle()
	 {
		  middle = calcMiddle(this.nodeA, this.nodeB, this.draw_up);

// 		  if(draw_up ^ (nodeA.getY() > nodeB.getY())) { 
// 				middle = new Point((int) nodeA.getX(), (int) nodeB.getY());
// 		  } else { 
// 			   middle = new Point((int) nodeB.getX(), (int)  nodeA.getY());
// 		  }
	 }

	 static Point calcMiddle(Point A, Point B, boolean draw_up)
	 {
		  if(!draw_up ^ (A.getY() > B.getY())) { 
				return new Point((int) A.getX(), (int) B.getY());
		  } else { 
				return new Point((int) B.getX(), (int)  A.getY());
		  }
	 }



	 public Point getNodeAPos() {
		  return nodeA;
	 }


	 public Point getNodeBPos() {
		  return nodeB;
	 }
	 

	 public void draw(Graphics g)
	 {

		  /* TODO : repasar */

		  g.drawOval((int) nodeA.getX() - NODE_RAD,
						 (int) nodeA.getY() - NODE_RAD,
						 NODE_RAD*2, NODE_RAD*2);

		  g.drawOval((int) nodeB.getX() - NODE_RAD,
						 (int) nodeB.getY() - NODE_RAD,
						 NODE_RAD*2, NODE_RAD*2);

		  g.drawLine((int) nodeA.getX(), (int) nodeA.getY(),
						 (int) middle.getX(),  (int) middle.getY());
		  g.drawLine((int) nodeB.getX(), (int) nodeB.getY(),
						 (int) middle.getX(),  (int) middle.getY());
	 }



	 public String toString() {
		  Point A = getNodeAPos();
		  Point B = getNodeBPos();
		  String strA = String.format("(%d, %d)",
												(int) A.getX(),
												(int) A.getY());
		  String strB = String.format("(%d, %d)",
												(int) B.getX(),
												(int) B.getY());
		  return "SC " + strA + " " + strB;
	 }



}

