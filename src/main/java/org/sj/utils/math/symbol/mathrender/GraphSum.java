package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Graphics;
//import java.awt.FontMetrics;

import java.util.Vector;

class Plus implements Renderable {

    public Rectangle getBounds() {
	return new Rectangle(7, 7);
    }


    public void render(Graphics g, int x, int y) {
	g.drawLine(x+1, y-4, x+5, y-4);
	g.drawLine(x+3, y-6, x+3, y-1);
    }
}


public class GraphSum extends GraphMultOp {

    static final Renderable plus = new Plus();
    static final int DEFAULT_SUM_GAP = 5; // pixels

    public GraphSum(Vector<Renderable> v) {
	super(v, plus, DEFAULT_SUM_GAP);
    }

    public int getPriority() 
    {
	return SUM_PRIORITY;
    }

    
}
