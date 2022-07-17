package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Graphics;

public class GraphParenthesis extends GraphNode {


    static final int GAP = 5;
    Renderable node;

    public GraphParenthesis(Renderable node) {
	this.node = node;
    }

    public Rectangle getBounds() {
	Rectangle size = node.getBounds();
	size.grow(GAP, GAP);
	size.translate(GAP, 0);
	return size;
    }



    public int getPriority() {
	return PARENTHESIS_PRIORITY;
    }

    public void render(Graphics g, int x, int y) {
	node.render(g, x+GAP, y);
	Rectangle size = node.getBounds();
	int x0 = (int) size.getX() + x;
	int y0 = (int) size.getY() - GAP + y;
	g.drawArc(x0, y0, GAP, (int)size.getHeight() + 2*GAP,
		 90, 180);
	x0 = (int) size.getX()+(int) size.getWidth() + x + GAP;
	//y0 = (int) size.getY() - GAP + y;
	g.drawArc(x0, y0, GAP, (int)size.getHeight() + 2*GAP,
		 270, 180);
		
    }
}

