package org.sj.tools.sycan.circedit;

import java.awt.Graphics;
import java.awt.Point;

import org.sj.tools.sycan.circedit.draw.Base;
import org.sj.tools.sycan.circedit.draw.Drawing;
import org.sj.tools.sycan.circedit.draw.DrawingFactory;

//TODO: many of the methods could be refactored and used by other classes 
public class GraphicIdealOA extends GraphicPart {
	
	/** position*/
	 protected Point center;
	 
	 Drawing drawing;
	 
	 protected int dir = DIR_RIGHT;
	 
	 static String type = "IdealOA"; 
	
	public GraphicIdealOA(String _name, Point _center) {
		super (_name, type);
		center = _center;
		drawing = DrawingFactory.getInstance().getDrawing(type);
	}

	@Override
	public GraphicNode[] getNodes() {
		GraphicNode nodes[] = new GraphicNode[3];
		for(int i=0; i<nodes.length; i++) {
			nodes[i] = new GraphicNode(getNodePos(i), i);
		}
		return nodes;
	}
	
	public Point getNodePos(int i) {
		Base b = new Base(center, dir);
		Point p = drawing.getNodePos(i, b);
		return p;
	}
	

	@Override
	public void changeDir() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInside(Point p) {
		// TODO Move "collision" information to drawing object in other GraphicParts.
		return drawing.isInside(p,  new Base(center, dir));
	}

	@Override
	public void draw(Graphics g) {
		  Base b = new Base(center, dir);
		  
		  drawing.draw(g, b);

		  /* write name */
		  g.drawString(name, (int) center.getX() + TEXT_OFF_X,
							(int) center.getY() + TEXT_OFF_Y);

	}

}
