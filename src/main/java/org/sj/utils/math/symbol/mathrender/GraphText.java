package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

public class GraphText extends GraphNode {

    String text;
    FontMetrics fm;
    //Font font;

    Rectangle size;

    public GraphText(String txt, FontMetrics fm) {
	this.text = txt;
	this.fm = fm;
    }

    public int getPriority() {
	return TEXT_PRIORITY;
    }

    public Rectangle getBounds() {
	if(size == null) {

	    int x = 0;
	    int y = - fm.getMaxAscent();
	    int w = fm.stringWidth(text);
	    int h = fm.getMaxAscent() + fm.getMaxDescent();
	    size = new Rectangle(x, y, w, h);
	}
	return size;
    }


    public void render(Graphics g, int x, int y) {
	g.drawString(text, x, y);



	//DBG_drawBounds(g, x, y);
    }

    public String toString() {
	return text;
    }

}
