package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public class GraphDivision extends GraphNode {

    public final static int SEPARATION = 5;
    public final static int WIDTH_INC = 3;

    GraphNode num;
    GraphNode den;


    public GraphDivision(GraphNode num, GraphNode den) {
	this.num = num;
	this.den = den;
    }

    public Rectangle getBounds() {
	Rectangle numSize = num.getBounds();
	Rectangle denSize = den.getBounds();
	int x = 0;
	int y = - 2*SEPARATION - (int) numSize.getHeight();
	int w = (int) Math.max(numSize.getWidth(), denSize.getWidth())
	    + 2*WIDTH_INC;
	int h = (int) (numSize.getHeight() + denSize.getHeight()
		       + 3*SEPARATION);
	return new Rectangle(x, y, w, h);
    }

    public int getPriority() {
	return DIVISION_PRIORITY;
    }

    public void render(Graphics g, int x, int y) {
	Rectangle numSize = num.getBounds();
	Rectangle denSize = den.getBounds();


	int w = (int) Math.max(numSize.getWidth(), denSize.getWidth())
	    + 2*WIDTH_INC;



	int inc_y = (int) - (numSize.getY() + numSize.getHeight())
			   - 2*SEPARATION;
	int num_x = x + w/2 - (int) numSize.getWidth()/2;
	int num_y = y + inc_y;
	//System.out.println("y1 = " + inc_y);

	inc_y = (int) - denSize.getY();

	//	int den_x = x + WIDTH_INC;
	int den_x = x + w/2 - (int) denSize.getWidth()/2;
	int den_y = (int) y + inc_y;

	/* --- DEBUG --- */
	//num._DBG_drawBounds(g, num_x, num_y, Color.GREEN);
	//den._DBG_drawBounds(g, den_x, den_y, Color.RED);
	/* ------------- */

	num.render(g, num_x, num_y);
	den.render(g, den_x, den_y);
	g.drawLine(x, y - SEPARATION, x+w, y - SEPARATION);


    }

    
}
