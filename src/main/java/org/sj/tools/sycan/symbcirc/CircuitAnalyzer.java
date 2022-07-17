package org.sj.tools.sycan.symbcirc;

//import java.util.Vector;

/* TODO: comprobar transformaciones: defrag, delSC, defrag.
*/

import org.sj.utils.math.complex.Complex;
import org.sj.utils.math.symbol.Constant;
import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.SymbolMatrix;

public class CircuitAnalyzer {


	 int numVSrc = -1;
	 int numNodes = -1;

	 /** suma del número de nodos y el de fuentes de tensión */
	 int N;


	 SymbolMatrix M; 

	 /* índices precalculados o dependientes del estado */
	 int A, B;
	 int j_A, j_B, i_A, i_B;

	 /** índice de la fuente de tensión actual */
	 int c_vgen=0;

	 TrCompound trans;

	 public void solve(Circuit circ) throws Exception
	 {
		  /* preprocesar lista de elementos */
		  /* - (1) eliminar cortocircuitos y (2) defragmentar */
		  //System.out.println("Paso 1. Eliminar cortocircuitos.");
		  //System.out.println("Paso 2. Defragmentar.");
		  TrDeleteSC delSC = new TrDeleteSC();
		  TrDefrag defrag1 = new TrDefrag();
		  TrDefrag defrag2 = new TrDefrag(); 

		  trans = new TrCompound(defrag1);
		  //System.out.println(" . 1");
		  trans.add(delSC);
		  trans.add(defrag2); 

		  //System.out.println(" . 2");
		  trans.calc(circ);
		  //System.out.println(" . ok");

		  //delSC.calc(circ);
		  //Circuit noSC = delSC.getResult();
		  //defrag.calc(noSC);
		  // Circuit compact = defrag.getResult();
		  Circuit compact = trans.getResult();

		  /* comprobar que no queda ningún nodo suelto */

		  if(compact.checkUnbound()) {
				//System.out.println(compact.toString());
				throw new Exception("Hay nodos sueltos en el circuito!");
		  }


		  /* TODO: comprobar  otras patologías...*/

		  //System.out.println("Circuito final:");
		  //System.out.println(compact.toString());
		  
		  //System.out.println("Paso 3. Calcular numero total de nodos.");

		  /* N <- contar nodos distintos de 0 */

		  // Este algoritmo asume que los nodos tienen asignados
		  // índices consecutivos. Esto es necesario para el funcionamiento
		  // del algoritmo.
		  //   El algoritmo consiste simplemente en buscar el máximo índice
		  // de nodo.
		  numNodes = compact.getMax();

		  if(numNodes <= 0) {
				throw new Exception("El circuito no contiene nodos.");
		  }

		  /* contar fuentes de tensión */
		  /* ya que introducen una incógnita cada uno */

		  numVSrc = 0;
		  for(int i = 0; i < compact.numElements(); i++) {
				Element elem = compact.get(i);
				if((elem instanceof VoltSrc) ||
					(elem instanceof VCVS) ||
					(elem instanceof CCVS)) {
					 numVSrc++;
				}
		  }

		  /* número total de incógnitas */
		  N = numNodes + numVSrc;
		  //System.out.println(" - numNodes: "+numNodes);
		  //System.out.println(" - numVsrc : "+numVSrc);
		  //System.out.println(" - N       : "+N);

		  /* crear la matriz del sistema, Nx(N+1) */
		  M = new SymbolMatrix(N, N+1);

		  // Convenio:

		  //  i_entrantes - i_salientes = i_sal_gen - i_ent_gen
		  //
		  //  \-----------+-----------/   \---------+---------/
		  //      elementos pasivos         gen. corriente
        //       y  gen. tensión

		  /* índice de generador de tensión */
		  // va contanto los generadores de tensión que me encuentro,
		  // para asignar a cada uno la columna correspondiente a su
		  // orden en el circuito. Es decir, si numeramos las fuentes
		  // de tensión, a la número k (k = 0, ..., numVSrc) se le
		  // asigna la corriente i_k, que corresponde a la fila k del
		  // vector de incógnitas, y por lo tanto sus coeficientes
		  // están en la columna k de la matriz ampliada del sistema.
		  //System.out.println("Paso 4. Construir matriz.");

		  for(int i = 0; i < compact.numElements(); i++) {
				//System.out.println("Nodo: "+i+".");
				Element em = compact.get(i);
				A = em.getNodeA() - 1;
				B = em.getNodeB() - 1;

				j_A = A + numVSrc;
				j_B = B + numVSrc;
				i_A = A + numNodes;
				i_B = B + numNodes;

				if(em instanceof Admitance) {
					 Admitance Y = (Admitance) em;
					 //System.out.println(" * Y = "+ Y.getY());
					 addAdmitance(Y);
					 
				} else if(em instanceof VoltSrc) {
					 VoltSrc VS = (VoltSrc) em;
					 //System.out.println(" * VS = "+ VS.getV());
					 addVoltSrc(VS);
 
				} else if(em instanceof CurrSrc) {
					 CurrSrc I = (CurrSrc) em;
					 //System.out.println(" * CS = "+ I.getI());
					 addCurrSrc(I);
				 
				} else if(em instanceof VCCS) {
					 VCCS cs = (VCCS) em;
					 //System.out.println(" * VCCS = "+ cs.getFactor());
					 addVCCS(cs);
				} else if(em instanceof VCVS) {
					 VCVS vs = (VCVS) em;
					 //System.out.println(" * VCVS = "+ vs.getFactor());
					 addVCVS(vs);
				}
		  }


		  //System.out.println(" Matriz: ----------------------- ");
		  //System.out.println(M.toString());

		  /* resolver */
		  SymbolMatrix R = M.reducidaGaussJordan();


		  //System.out.println(" Resultado: ----------------------- ");
		  //System.out.println(R.toString());

		  M = R;
	 }


	 private void addVCVS(VCVS vcvs) {
		  Expression v;
		  int X = vcvs.getNodeX() - 1;
		  int Y = vcvs.getNodeY() - 1;

		  int j_X = X + numVSrc;
		  int j_Y = Y + numVSrc;

		  int i_vgen = c_vgen + numNodes;

		  Expression factor = vcvs.getFactor();

		  if(A > -1) {
				v = Expression.sum(M.getValue(A, c_vgen),
										 new Expression(new Constant(-1)));
				M.setValue(A, c_vgen, v);

				v = Expression.sum(M.getValue(i_vgen, j_A),
										 new Expression(new Constant(1)));
				M.setValue(i_vgen, j_A, v); 
		  }

		  if(B > -1) {
				v = Expression.sum(M.getValue(B, c_vgen),
										 new Expression(new Constant(1)));
				M.setValue(B, c_vgen, v);

				v = Expression.sum(M.getValue(i_vgen, j_B),
										 new Expression(new Constant(-1)));
				M.setValue(i_vgen, j_B, v);
		  }

		  if(X > -1) {
				v = Expression.sum(M.getValue(i_vgen, j_X), factor.getOpposite());
				M.setValue(i_vgen, j_X, v);
		  }
		  
		  if(Y > -1) {
				v = Expression.sum(M.getValue(i_vgen, j_Y), factor);
				M.setValue(i_vgen, j_Y, v);
		  }
		  

		  c_vgen++;

	 }


	 private void addVCCS(VCCS vccs) {
		  Expression v;
		  int X = vccs.getNodeX() - 1;
		  int Y = vccs.getNodeY() - 1;

		  int j_X = X + numVSrc;
		  int j_Y = Y + numVSrc;

		  Expression factor = vccs.getFactor();


		  if(A > -1) {

				if(X > -1) {
					 v = Expression.sum(M.getValue(A, j_X), factor.getOpposite());
					 M.setValue(A, j_X, v);
				}

				if(Y > -1) {
					 v = Expression.sum(M.getValue(A, j_Y), factor);
					 M.setValue(A, j_Y, v);
				}
		  }

		  if(B > -1) {
				if(X > -1) {
					 v = Expression.sum(M.getValue(B, j_X), factor);
					 M.setValue(B, j_X, v);
				}

				if(Y > -1) {
					 v = Expression.sum(M.getValue(B, j_Y), factor.getOpposite());
					 M.setValue(B, j_Y, v);
				}
		  }
	 }


	 private void addCurrSrc(CurrSrc I) {
		  Expression v;
		  int i_vgen = c_vgen + numNodes;
		  
		  if(A > -1) {
				v = Expression.sum(M.getValue(A, N), I.getI());
				M.setValue(A, N, v);
		  }

		  if(B > -1) {
				v = Expression.sum(M.getValue(B, N), I.getI().getOpposite());
				M.setValue(B, N, v);
		  }
	 }

	 private void addVoltSrc(VoltSrc VS) {
		  Expression v;
		  int i_vgen = c_vgen + numNodes;

		  if(A > -1) {
				/* submatriz de corrientes */
				//ASSERT(M.getValue(A, c_vgen) != null, "null en matriz");
				v = Expression.sum(M.getValue(A, c_vgen),
										 new Expression(-1));
				M.setValue(A, c_vgen, v);

				/* submatriz de tensiones */
				v = Expression.sum(M.getValue(i_vgen, j_A),
										 new Expression(1));
				M.setValue(i_vgen, j_A, v);
		  }

		  if(B > -1) {
				/* submatriz de corrientes */
				v = Expression.sum(M.getValue(B, c_vgen),
										 new Expression(1));
				M.setValue(B, c_vgen, v);

				/* submatriz de tensiones */
				v = Expression.sum(M.getValue(i_vgen, j_B),
										 new Expression(-1));
				M.setValue(i_vgen, j_B, v);
		  }

		  /* término independiente para ecuaciones de tensión */
		  v = Expression.sum(M.getValue(i_vgen, N), VS.getV());
		  M.setValue(i_vgen, N, v);

		  c_vgen++;

	 }

	 private void addAdmitance(Admitance Y) {
		  Expression v;

		  if(A > -1) {
				v = Expression.sum(M.getValue(A, j_A), Y.getY().getOpposite());
				M.setValue(A, j_A, v);

				if(B > -1) {
					 v = Expression.sum(M.getValue(A, j_B), Y.getY());
					 M.setValue(A, j_B, v);

					 v = Expression.sum(M.getValue(B, j_A), Y.getY());
					 M.setValue(B, j_A, v);
				}
		  }

		  if(B > -1) {
				v = Expression.sum(M.getValue(B, j_B), Y.getY().getOpposite());
				M.setValue(B, j_B, v);
		  }
		  
	 }

	 /** 
	  * maps a node index from the source circuit to its homologue
	  * in the transformed one (after TrCompound is applied).
	  */
	 public int getTransNode(int id) {
		  return trans.transNode(id);
	 }

	 public int getNumVoltSrc() {
		  return numVSrc;
	 }

	 public int getNumNodes() {
		  return numNodes;
	 }


	 /**
	  * Get the Expression of the voltage in node i.
	  * valid indices are: 1, ... , numNodes.
	  */
	 public Expression getNodeVoltage(int i) {
		  if(i <= 0 || i > numNodes) {
				return new Expression(new Complex());
		  }

		  int j = numVSrc + i - 1;
		  //System.out.println("    getNodeVoltage("+i+"): ["+j +", "+N+"]: "+M.getValue(j,N).toString());
		  return M.getValue(j, N);
		  		  		  
	 }

	 public void _DBG_showAll() {
		  //System.out.println("C.A.: - SHOW ALL");
		  for(int i = 0; i < N; i++) {
				for(int j = 0; j < N+1; j++) {
					 Expression e = M.getValue(i, j);
					 //System.out.println("("+i+","+j+"): "+ e.toString());
				}
		  }
		  
	 }

	 


	 public void ASSERT(boolean condition, String errMsg) throws Exception {
		  if(!condition) {
				throw new Exception(errMsg);
		  }
	 }

	 
}

