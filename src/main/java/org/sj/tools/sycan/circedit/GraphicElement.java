package org.sj.tools.sycan.circedit;

import java.awt.Graphics;
import java.awt.Point;

public abstract class GraphicElement implements Const {

	 protected String name;

	 protected String type;


	 public GraphicElement(String _name, String _type)
	 {
		  name = _name;
		  type = _type;
	 }


	 public abstract GraphicNode[] getNodes();

	 public abstract void changeDir();
 
	 public abstract boolean isInside(Point p);

	 public abstract void draw(Graphics g);

	 public String getName() {
		  return name;
	 }

	 public String getType() {
		  return type;
	 }


 
}