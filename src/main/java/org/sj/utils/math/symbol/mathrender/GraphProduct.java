package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Graphics;
//import java.awt.FontMetrics;

import java.util.Vector;

class Dot implements Renderable {

    public Rectangle getBounds() {
	return new Rectangle(7, 7);
    }

    public void render(Graphics g, int x, int y) {
	g.fillOval(x+3,y-5,2, 2);	
    }
}

public class GraphProduct extends GraphMultOp {

    static final Renderable dot = new Dot();
    static final int DEFAULT_PRODUCT_GAP = 1; // pixels

    public GraphProduct(Vector<Renderable> v) {
	super(v, dot, DEFAULT_PRODUCT_GAP);
    }

    public int getPriority() {
	return PRODUCT_PRIORITY;
    }

    
}
