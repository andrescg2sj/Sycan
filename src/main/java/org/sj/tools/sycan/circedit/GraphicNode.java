package org.sj.tools.sycan.circedit;

import java.awt.Graphics;
import java.awt.Point;

public class GraphicNode implements Const {
	 Point position;

	 /** distingue entre nodos de un mismo elemento */
	 int nodeId;

	 public GraphicNode(Point p, int id) {
		  position = (Point) p.clone();
		  nodeId = id;
	 }

	 public int getNodeId() {
		  return nodeId;
	 }

	 public Point getPosition() {
		  return position;
	 }
}