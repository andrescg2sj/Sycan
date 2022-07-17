package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public abstract class GraphNode implements Renderable, Priority {


    public abstract int getPriority();

    void _DBG_drawBounds(Graphics g, int x, int y) {
	_DBG_drawBounds(g, x, y, Color.RED);
    }

    void _DBG_drawBounds(Graphics g, int x, int y, Color clr) {

	g.setColor(clr);
	Rectangle rect = getBounds();
   	g.drawRect(x + (int) rect.getX(), y + (int) rect.getY(),
		   (int) rect.getWidth(), (int) rect.getHeight());
	g.setColor(Color.BLACK);
	
    }
}
