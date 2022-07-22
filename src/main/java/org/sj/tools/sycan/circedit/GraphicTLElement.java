package org.sj.tools.sycan.circedit;

import org.sj.tools.sycan.symbcirc.Element;

import org.sj.tools.sycan.circedit.draw.Base;
import org.sj.tools.sycan.circedit.draw.Drawing;
import org.sj.tools.sycan.circedit.draw.DrawingFactory;
import java.awt.Point;
//import java.awt.geom.Point2D;
import java.awt.Graphics;

/**
 * Graphic Two-legged element
 */
public class GraphicTLElement extends GraphicPart  {

	 /** position*/
	 protected Point center;

	 /** direction in which the component is drawn */
	 protected int dir;


	 public GraphicTLElement(String _name, String _type,
									 Point _center, int _dir) 
	 {
		  super(_name, _type);
		  center = _center;
		  dir = _dir;
	 }


	 public int getNode(Point p) {
		 
		  Base b = new Base(center, dir);
		  Point q = b.inverse(p);
		  int dx = (int) q.getX();
		  int dy = (int) q.getY();
		  
		 /*
		 GraphicNode nodes[] = getNodes();
		 for(int i=0; i<nodes.length; i++) {
			 if(nodes[i].isInside(p)) {
				 return i;
			 }
		 }*/
		 

		  
		  //TODO: Refactor
		  if((Math.abs(dy) <= NODE_RAD) &&
			  (Math.abs(dx) >= (ELEM_SIZE/2-NODE_RAD)) &&
			  (Math.abs(dx) <= (ELEM_SIZE/2+NODE_RAD)) ) {
				return NODE_A;
		  }

		  if((Math.abs(dy) <= NODE_RAD) &&
			  (Math.abs(dx) >= (-ELEM_SIZE/2-NODE_RAD)) &&
			  (Math.abs(dx) <= (-ELEM_SIZE/2+NODE_RAD)) ) {
				return NODE_B;
		  }

		  
		  return NO_NODE; 
	 }

	 public boolean isInside(Point p) {
		  Base b = new Base(center, dir);
		  Point q = b.inverse(p);
		  int dx = (int) q.getX();
		  int dy = (int) q.getY();
		  
		  return (Math.abs(dx) <= COMP_SIZE/2) && (Math.abs(dy) <= COMP_SIZE);
	 }

	 public GraphicNode[] getNodes() {
		  GraphicNode nodes[] = new GraphicNode[2];
		  nodes[0] = new GraphicNode(getNodeAPos(), NODE_A);	
		  nodes[1] = new GraphicNode(getNodeBPos(), NODE_B);
		  return nodes;
	 }

	 /**
	  * Generar
	  */

	 public Point getNodeAPos() {
		  Base b = new Base(center, dir);
		  Point q = b.transform(new Point(ELEM_SIZE/2, 0));
		  return q;
	 }


	 public Point getNodeBPos() {
		  Base b = new Base(center, dir);
		  Point q = b.transform(new Point(-ELEM_SIZE/2, 0));
		  return q;
	 }

	 public void changeDir() {
		  dir = (dir+1)%NUM_DIRS;
	 }



	 public void draw(Graphics g)
	 {
		  Base b = new Base(center, dir);
		  Drawing drawing = DrawingFactory.getInstance().getDrawing(type);
		  drawing.draw(g, b);

		  /* escribir nombre */
		  g.drawString(name, (int) center.getX() + TEXT_OFF_X,
							(int) center.getY() + TEXT_OFF_Y);
	 }


	 public String toString() {
		  String strType = "";

		  for(int i = 0; i < type.length(); i++) {
				char ch = type.charAt(i);
				if(Character.getType(ch) == Character.UPPERCASE_LETTER) {
					 strType += ch;
				}
		  }

		  Point A = getNodeAPos();
		  Point B = getNodeBPos();
		  String strA = String.format("(%d, %d)",
												(int) A.getX(),
												(int) A.getY());
		  String strB = String.format("(%d, %d)",
												(int) B.getX(),
												(int) B.getY());
		  return strType + " " + strA + " " + strB + " " + name;
	 }




}