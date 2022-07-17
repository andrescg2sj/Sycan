package org.sj.utils.math.symbol;

import org.sj.utils.math.complex.Complex;

public abstract class MathNode implements MathObj {


	 /**
	  * devuelve el nmero de subnodos.
	  */
	 public abstract int numNodes();


	 public abstract MathNode getNode(int i);

	 /**
	  * Crea una copia de este MathNode, en la que el nodo i es obj.
	  */
	 public abstract MathNode fork(int i, MathNode obj);
	 

	 /**
	  * Crear una copia de este MathNode. Los subnodos no se clonan,
	  * el clon copia sus referencias.
	  */
	 public abstract MathNode clone();



	 /**
	  * Medida del tamao del rbol
	  */
	 public abstract int weight();

	 // TODO


	 public abstract String _DBG_showTree();
}