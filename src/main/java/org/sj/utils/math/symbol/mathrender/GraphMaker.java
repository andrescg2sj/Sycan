package org.sj.utils.math.symbol.mathrender;


import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.MathNode;
import org.sj.utils.math.symbol.Sum;
import org.sj.utils.math.symbol.Leaf;
import org.sj.utils.math.symbol.Product;
import org.sj.utils.math.symbol.Division;

import java.util.Vector;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.FontMetrics;
import java.awt.Graphics;


public class GraphMaker implements Priority {

    Graphics graph;

    public GraphMaker(Graphics g) {
	graph = g;
    }

    public GraphNode makeGraph(Expression exp) throws Exception {
 	return makeGraph(exp.getNode());
     }

    public GraphNode makeGraph(MathNode n) throws Exception {
	FontMetrics fm = graph.getFontMetrics();

	if(n instanceof Leaf) {
	    Leaf l = (Leaf) n;
	    return new GraphText(l.toString(), fm);

	} else if (n instanceof Sum) {
	    Sum s = (Sum) n;
	    Vector<Renderable> addends =
		new Vector<Renderable>(s.numNodes());

	    for(int i = 0; i < s.numNodes(); i++) {
		GraphNode a = makeGraph(s.getNode(i));
		if(a.getPriority() < getPriority(n)) {
		    a = new GraphParenthesis(a);
		}
		addends.add(a);
	    }

	    return new GraphSum(addends);   

	} else if (n instanceof Product) {
	    Product p = (Product) n;
	    Vector<Renderable> factors =
		new Vector<Renderable>(p.numNodes());

	    for(int i = 0; i < p.numNodes(); i++) {
		GraphNode f = makeGraph(p.getNode(i)); 
		if(f.getPriority() < getPriority(n)) {
		    f = new GraphParenthesis(f);
		}
		factors.add(f);
	    }

	    return new GraphProduct(factors);   
	} else if (n instanceof Division) {
	    Division d = (Division) n;

	    GraphNode num = makeGraph(d.getNumerator());
	    GraphNode den = makeGraph(d.getDenominator());

	    return new GraphDivision(num, den);
	}

	throw new Exception("Tipo de nodo desconicido");
    }
	
    public int getPriority(MathNode n) {
	if(n instanceof Leaf) {
	    return TEXT_PRIORITY;

	} else if (n instanceof Sum) {
	    return SUM_PRIORITY;

	} else if (n instanceof Product) {
	    return PRODUCT_PRIORITY;
	} else if (n instanceof Division) {

	    return DIVISION_PRIORITY;
	}

	return DEFAULT_PRIORITY;
	
    }

    
}
