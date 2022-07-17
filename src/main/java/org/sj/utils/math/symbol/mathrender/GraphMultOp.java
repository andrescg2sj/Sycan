package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.FontMetrics;

import java.util.Vector;

public abstract class GraphMultOp extends GraphNode {

    Vector<Renderable> operand;
    

    /** separación entre operandos y operador */
    int gap = 0;

    Renderable operator;

    public GraphMultOp(Vector<Renderable> v, Renderable operator) {
	operand = v;
	this.operator = operator;
    }

    public GraphMultOp(Vector<Renderable> v, Renderable operator,  int gap) {
	operand = v;
	this.gap = gap;
	this.operator = operator;
    }

    public Rectangle getBounds() {
	int optrWidth = (int) operator.getBounds().getWidth();

	int x = 0;
	int y = 0;
	int w = (2*gap + optrWidth) * (operand.size() - 1);
	int h = 0;

	for(int i = 0; i < operand.size(); i++) {
	    Renderable op = operand.get(i);
	    w += (int) op.getBounds().getWidth();
	    int op_h = (int) op.getBounds().getHeight();
	    int op_y = (int) op.getBounds().getY();
	    if(op_h > h) h = op_h;
	    if(op_y < y) y = op_y;
	}

	return new Rectangle(x, y, w, h);
    }


    public void render(Graphics g, int x, int y) {
	int cx = x;
	Renderable op = operand.get(0);
	int inc_w = (int) op.getBounds().getWidth(); 
	operand.get(0).render(g, x, y);

	int optrWidth = (int) operator.getBounds().getWidth();

	//System.out.println("m_op: "+operand.size());
	cx += inc_w;

	for(int i = 1; i < operand.size(); i++) {
	    cx += gap;
	    operator.render(g, cx, y);
	    cx += optrWidth + gap;

	    op = operand.get(i);
	    inc_w = (int) op.getBounds().getWidth(); 
	    //System.out.println("   "+i+": " + op.toString() + " " + inc_w);
	    op.render(g, cx, y);
	    cx += inc_w;
	}

	//DBG_drawBounds(g, x, y);
    }
}

