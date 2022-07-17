package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;
import java.util.Vector;
import java.util.Iterator;

public class Expression implements MathObj {

    /** nodo raz del rbol */
    MathNode node;



    /* Constructores ============================================ */

    public Expression(MathNode x) {
	node = x;
	simplify();
    }

    public Expression(Complex z) {
	node = new Constant(z);
    }

    public Expression(double x) {
	node = new Constant(new Complex(x, 0));
    }

    /* Componer con operaciones ================================== */




    public Expression getOpposite() {
	return new Expression(new Product(new Constant(-1), node));
    }

    public Expression getInverse() {
	return new Expression(new Division(new Constant(1), node));
    }


    /* Operaciones =============================================== */

    public void add(Expression exp) {
	if(exp.isZero()) {
	    return;
	} else if(isZero()) {
	    node = exp.node;
	} else {
	    node = new Sum(node, exp.node);
	}
	simplify();
    }

    public void add(Complex z) {
	if(z.isZero()) {
	    return;
	} else if(isZero()) {
	    node = new Constant(z);
	} else {
	    node = new Sum(node, new Constant(z));
	}
	simplify();
    }

    public void mult(Expression exp) {
	if(isZero()) {
	    return;
	} else if(exp.isZero()) {
	    node = new Constant(0);
	} else if(is(1, 0)) {
	    node = exp.node;
	} else if(exp.is(1, 0)) {
	    return;
	} else {
	    node = new Product(node, exp.node);
	}
	simplify();
    }

    public void mult(Complex z) {
	mult(new Expression(z));
    }

    public void div(Expression exp) {
	node = new Division(node, exp.node);
	simplify();
    }

    /* Otros ===== */

    public MathNode getNode() {
	return node;
    }


    /* Operaciones estticas ===================================== */


    public static Expression sum(Expression e1, Expression e2) {
	if(e1.isZero()) {
	    return e2;
	} else if(e2.isZero()) {
	    return e1;
	}
	return new Expression(new Sum(e1.node, e2.node));
    }

    public static Expression prod(Expression e1, Expression e2) {
	if(e1.isZero() || e2.isZero()) {
	    return new Expression(0);
	} else if(e1.is(1, 0)) {
	    return e2;
	} else if(e2.is(1, 0)) {
	    return e1;
	} 
	return new Expression(new Product(e1.node, e2.node));
    }

    public static Expression div(Expression e1, Expression e2) {
	if(e1.isZero()) {
	    if(e2.isZero()) {
		// TODO  : ???
	    } else {
		return new Expression(0);
	    }
	}else if(e2.isZero()) {
	    // TODO : ???
	}

	return new Expression(new Division(e1.node, e2.node));
    }


    /* Modificacin del rbol =================================== */

    /**
     * Modifica la expresin, cambiando un nodo al final de la ruta
     *
     * Modifica la expresin actual, creando una copia modificada del
     * rbol, donde node sustituye al nodo final de la
     * ruta indicada por path.
     *   Este mtodo es necesario cada vez que se quiere modificar la
     * expresin, pues no es seguro modificar los nodos de la expresin,
     * ya que podran estar referenciados por otras.
     *
     * @return MathNode con la raz del rbol original
     */
    public MathNode setNode(NodePath path, MathNode mnod) {
	MathNode prev = node;
	node = path.replaceTail(mnod);
	return prev;
    }

    /**
     * Simplificar la expresin
     *
     * @return true si se pudo simplificar, false si no se alter
     */
    public boolean simplify() {
	NodePath path = new NodePath(node);
	boolean rval = simplify(path);
	return rval;
    }


    /**
     * Simplificar el nodo apuntado por la ruta path (el ltimo de la lista)
     * y todos sus subnodos. Los subnodos se simplifican en primer lugar.
     *
     * @return true si se realiz algn cambio en algn nodo.
     */
    protected boolean simplify(NodePath path) {

	/* simplificar primero los subnodos */
	boolean modified = simplifyNodes(path);

	/* simplificar este nodo */
	while(simplifyTail(path)) {
	    modified = true;
	}

	return modified;
    }


    /**
     * Simplificar el nodo apuntado por la ruta path (el ltimo de la lista)
     *
     * @return true si se realiz algn cambio.
     */
    protected boolean simplifyTail(NodePath path) {
	MathNode tail = path.getTail();


	////System.out.println("simplifyTail: E(" + this + ")");


	if(tail instanceof Division) {
	    ////System.out.println("        -> div");
	    return simplifyDivision(path);
	} else if(tail instanceof Product) {
	    ////System.out.println("        -> prod");
	    return simplifyProduct(path);
	} else if(tail instanceof Sum) {
	    ////System.out.println("        -> sum");
	    return simplifySum(path);
	}

	return false;
    }

    /**
     * Recorre todos los subnodos del ltimo de una ruta, y los
     * simplifica
     */
    protected boolean simplifyNodes(NodePath path) {
	boolean modified = false;
	int N = path.numTailNodes();
	for(int i = 0; i < N; i++) {
	    // El path cambia a lo largo del bucle.
	    path.advance(i);

	    while(simplify(path)) {
		modified = true;
	    }

	    // A la salida de la funcin, el path queda inalterado.
	    path.stepBack();
	}

	return modified;
    }

    /**
     * Simplifica
     *
     * Observacin: Cada uno de los bloques if debe realizar una transformacin
     * que haga falsa la condicin de entrada, de lo contrario se entrar
     * en un bucle infinito de llamadas a esta funcin (bucle infinito de
     * llamadas, no recursividad infinita).
     *    Ms concretamente, los mtodos simplifyXXX son de la forma:
     *
     *   if( c1 ) {
     t1
     return true
     }

     if( c2 ) {
     t2
     return true
     }

     ...
     return false

     Y debe cumplirse, para todo i, que para cualquier Expression que
     cumpla c_i,	al aplicar la transformacin t_i un nmero finito de
     veces, la condicin c_i se hace falsa.

     Adems, deben ordenarse de tal manera que, para todo par i,j;
     si j es mayor que i, debe existir e tal que c_i(e) = F y c_j(e) = T.
     De lo contrario, nunca se producira la transformacin t_j

     @return Si se modific el arbol.
     *
     */
    protected boolean simplifyDivision(NodePath path) {
	Division div = (Division) path.getTail();

	MathNode num = div.getNumerator();
	MathNode den = div.getDenominator();

	if(den instanceof Constant) {
	    Constant c = (Constant) den;
	    if(c.value.is(1,0)) {
		setNode(path, num);
		return true;
	    }
	}

	if((num instanceof Constant) && (den instanceof Constant)) {
	    Complex n = ((Constant) num).value;
	    Complex d = ((Constant) den).value;
	    Complex res = Complex.div(n, d);
	    setNode(path, new Constant(res));
	    return true;
	}

	if(num instanceof Division) {
	    Division dn = (Division) num;
	    MathNode dnNum = dn.getNumerator();
	    MathNode dnDen = dn.getDenominator();
	    setNode(path, new Division(dnNum, new Product(den,dnDen)));
	    return true;
	}

	if(den instanceof Division) {
	    Division dd = (Division) den;
	    MathNode ddNum = dd.getNumerator();
	    MathNode ddDen = dd.getDenominator();
	    setNode(path, new Division(new Product(num, ddDen), ddNum));
	    return true;
	}


	if((num instanceof Product) && (den instanceof Product)) {
	    Product p = (Product) num;
	    Product q = (Product) den;

	    int M = p.operand.size();
	    int N = q.operand.size();

	    /* cuenta los factores borrados */
	    int deleted = 0;

	    /* ndice del denominador */
	    int min_j = 0;
	    for(int i = 0; i < M; i++) {
		MathNode pn = p.operand.get(i);

		for(int j = 0; j < N; j++) {
		    MathNode qn = q.operand.get(j);

		    if(pn.equals(qn)) {
			Vector<MathNode> newNum = new Vector<MathNode>(M,1);
			Vector<MathNode> newDen = new Vector<MathNode>(N,1);
			newNum.addAll(p.operand);
			newDen.addAll(q.operand);
			newNum.remove(i);
			newDen.remove(j);
			MathNode n, d;
			if(newNum.size() == 0) {
			    n = new Constant(1);
			} else if(newNum.size() == 1) {
			    n = newNum.firstElement();
			} else {
			    n = new Product(newNum);
			}

			if(newDen.size() == 0) {
			    setNode(path, n);
			    return true;
			} else if(newNum.size() == 1) {
			    d = newDen.firstElement();
			} else {
			    d = new Product(newDen);
			}
			setNode(path, new Division(n, d));
			return true;
		    }
		}
	    }
	} 



	return false;

    }


    protected boolean simplifySum(NodePath path) {
	Sum s = (Sum) path.getTail();

	////System.out.println("sS: E("+this+") s:("+s+")");

	if(s.operand.size() == 1) {
	    MathNode n = s.operand.firstElement();
	    setNode(path, n);
	    return true;
	}

	if(s.containsSame()) {
	    setNode(path, s.getExpanded());
	    return true;
	}

	int numConst = countConst(s);

	if(numConst > 1) {
	    ////System.out.println("<<<>>>");
	    /* OBJETIVO: sumar todas las constantes en una */
	    Complex v = new Complex(0, 0);
	    Vector<MathNode> others = new Vector<MathNode>(s.operand.size(), 1);
	    Iterator<MathNode> it = s.operand.iterator();
	    while(it.hasNext()) {
		MathNode obj = it.next();
		if(obj instanceof Constant) {
		    Constant cte = (Constant) obj;
		    v.add(cte.value);
		} else {
		    others.add(obj);
		}
	    }

	    if((!v.is(0, 0)) || (others.size() == 0)) {
		others.insertElementAt(new Constant(v), 0);
	    }
	    setNode(path, new Product(others));
	    return true;
	}



	try {

	    if(isDivisionSum(s)) {
		Vector<MathNode> denFactors = new Vector<MathNode>(s.numNodes(), 1);
		Vector<MathNode> numAddends = new Vector<MathNode>(s.numNodes(), 1);

		////System.out.println("Denominadores:");
		for(int i = 0; i < s.numNodes(); i++) {
		    Division div = (Division) s.getNode(i);
		    MathNode den = div.getDenominator();
		    denFactors.add(den);
		}


		////System.out.println("Numeradores:");

		for(int i = 0; i < s.numNodes(); i++) {
		    ////System.out.println("  i=" + i);
		    Division div = (Division) s.getNode(i);
		    MathNode num = div.getNumerator();
		    MathNode den = div.getDenominator();
		    Vector<MathNode> factor = new Vector<MathNode>(denFactors);
		    factor.remove(den);
		    factor.add(num);
		    MathNode addend = new Product(factor);
		    ////System.out.println("  addend:" + addend);
		    numAddends.add(addend);
		}

		Product den = new Product(denFactors);
		Sum num = new Sum(numAddends);
		Division div = new Division(num, den);
		setNode(path, div);
		////System.out.println("  div:"+ div);
		return true;
	    }

	} catch(Exception e) {
	    throw new Error("Error durante la simplificacin");
	}



	  try {
	      /* TODO _  */

	      if(hasAnyDivision(s)) {
		  Vector<MathNode> denFactors = new Vector<MathNode>(s.numNodes(), 1);
		  Vector<MathNode> numAddends = new Vector<MathNode>(s.numNodes(), 1);

		  ////System.out.println("Denominadores:");
		  for(int i = 0; i < s.numNodes(); i++) {
		      MathNode n = s.getNode(i);
		      if(n instanceof Division) {
			  Division div = (Division) n;
			  MathNode den = div.getDenominator();
			  denFactors.add(den);
		      }
		  }


		  ////System.out.println("Numeradores:");

		  for(int i = 0; i < s.numNodes(); i++) {
		      ////System.out.println("  i=" + i);
		      MathNode n = s.getNode(i);
		      if(n instanceof Division) {

			  Division div = (Division) s.getNode(i);
			  Vector<MathNode> factor = new Vector<MathNode>(denFactors);
			  MathNode num = div.getNumerator();
			  MathNode den = div.getDenominator();

			  factor.remove(den);
			  factor.add(num);
			  MathNode addend = new Product(factor);
			  ////System.out.println("  addend:" + addend);
			  numAddends.add(addend);
		      } else {
			  Vector<MathNode> factor = new Vector<MathNode>(denFactors);
			  factor.add(n);
			  MathNode addend = new Product(factor);
			  numAddends.add(addend);
		      }
		  }

		  Product den = new Product(denFactors);
		  Sum num = new Sum(numAddends);
		  Division div = new Division(num, den);
		  setNode(path, div);
		  ////System.out.println("  div:"+ div);
		  return true;
	      }

	  } catch(Exception e) {
	      throw new Error("Error durante la simplificacin");
	  }



	  {
	      /* Sacar factor comn, si se puede */
	      ProductSum ps = new ProductSum(s);
	      MathNode cf = ps.extractCommonFactor();
	      if(cf != null) {
		  setNode(path, cf);
		  ////System.out.println("    E("+this+")");
		  return true;
	      }
	  }

	// TO-DO:
	////System.out.println("    E("+this+")");
	return false;
    }




    public static boolean isDivisionSum(Sum s) {
	if(s.numNodes() < 2) {
	    return false;
	}

	for(int i = 0; i < s.numNodes(); i++) {
	    MathNode n = s.getNode(i);
	    if( !(n instanceof Division)) {
		return false;
	    }
	}
	return true;
    }


    public static boolean hasAnyDivision(Sum s) {
	if(s.numNodes() < 2) {
	    return false;
	}

	for(int i = 0; i < s.numNodes(); i++) {
	    MathNode n = s.getNode(i);
	    if(n instanceof Division) {
		return true;
	    }
	}
	return false;
    }


    public boolean simplifyProduct(NodePath path) {
	Product p = (Product) path.getTail();

	////System.out.println("simplifyProduct E:"+node._DBG_showTree());
	////System.out.println("                P:"+path);

	if(p.operand.size() == 1) {
	    ////System.out.println("  p.operand.size() == 1");
	    ////System.out.println("  p:" + p);
	    MathNode n = p.operand.firstElement();
	    ////System.out.println("  n: " + n);
	    setNode(path, n);
	    ////System.out.println("  E:"+node._DBG_showTree());
	    return true;
	}

	if(p.containsSame()) {
	    ////System.out.println("  p.containsSame() == true");
	    ////System.out.println("  p = "+ p._DBG_showTree());
	    MathNode n = p.getExpanded();
	    setNode(path, n);
	    ////System.out.println("  n = "+ n._DBG_showTree());
	    return true;
	}


	int numConst = countConst(p);

	if(numConst > 1) {
	    ////System.out.println(" A");

	    /* OBJETIVO: multiplicar todas las constantes en una */
	    Complex v = new Complex(1, 0);
	    Vector<MathNode> others = new Vector<MathNode>(p.operand.size(), 1);
	    Iterator<MathNode> it = p.operand.iterator();
	    while(it.hasNext()) {
		MathNode obj = it.next();
		if(obj instanceof Constant) {
		    Constant cte = (Constant) obj;
		    v.mult(cte.value);
		} else {
		    others.add(obj);
		}
	    }
	    if(v.isZero()) {
		setNode(path, new Constant(v));
	    }else {

		if((!v.is(1, 0)) || (others.size() == 0)) {
		    others.insertElementAt(new Constant(v), 0);
		}
		setNode(path, new Product(others));
	    }
	    return true;
	}

	if((numConst == 1) && (p.operand.size() > 1)) {
	    ////System.out.println(" B");

	    for(int i = 0; i < p.operand.size(); i++) {
		if(p.operand.get(i) instanceof Constant) {
		    Constant cte = (Constant) p.operand.get(i);
		    if(cte.value.is(1, 0)) {
			Vector<MathNode> rest = new Vector<MathNode>();
			rest.addAll(p.operand);
			rest.remove(i);
			Product q = new Product(rest);
			setNode(path, q);
			return true;
		    } else {
			break;
		    }
		}
	    }
	}

	if(countDiv(p) >= 1) {
	    ////System.out.println(" C");
	    Vector<MathNode> num = new Vector<MathNode>(p.operand.size(), 1);
	    Vector<MathNode> den = new Vector<MathNode>(p.operand.size(), 1);
	    Iterator<MathNode> it = p.operand.iterator();
	    while(it.hasNext()) {
		MathNode obj = it.next();
		if(obj instanceof Division) {
		    Division div = (Division) obj;
		    num.add(div.A);
		    den.add(div.B);
		} else {
		    num.add(obj);
		}
	    }
	    MathNode n, d;
	    if(num.size() > 1) {
		n = new Product(num);
	    } else {
		n = num.firstElement();
	    }
	    if(den.size() > 1) {
		d = new Product(den);
	    } else {
		d = den.firstElement();
	    }
	    setNode(path, new Division(n, d));

	    return true;
	}


	////System.out.println("   <- false");


	// TO-DO:
	return false;
    }




    /**
     * Cuenta el nmero de constantes 
     */
    public static int countConst(MultipleOp node) {
	int numConst = 0; // nmero de constantes
	for(int i = 0; i < node.operand.size(); i++) {
	    if(node.operand.get(i) instanceof Constant) {
		numConst++;
	    }
	}
	return numConst;
    }

    /**
     * Cuenta el nmero de divisiones
     */
    public static int countDiv(MultipleOp node) {
	int numDiv = 0; // nmero de constantes
	for(int i = 0; i < node.operand.size(); i++) {
	    if(node.operand.get(i) instanceof Division) {
		numDiv++;
	    }
	}
	return numDiv;
    }


    public boolean isZero() {
	if(node instanceof Constant) {
	    return ((Constant) node).isZero();
	}

	return false;
    }

    public static boolean isZero(MathNode mobj) {
	if(mobj instanceof Constant) {
	    return ((Constant) mobj).isZero();
	}

	return false;
    }

    public boolean is(double r, double i) {
	return (node instanceof Constant) &&
	    (((Constant) node).value.is(r,i));
    }


    public static boolean is(MathNode mobj, double r, double i) {
	return (mobj instanceof Constant) &&
	    (((Constant) mobj).value.is(r,i));
    }

    /* Implementacin de interfaces ============================= */

    public Complex eval(Context ctx) {
	return node.eval(ctx);		  
    }

    public Expression clone() {
	return new Expression(node);
    }


    public String toString() {
	return node.toString();
    }


}
