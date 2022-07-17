package org.sj.utils.math.symbol;

public class SymbolMatrix {

	 protected Expression valor[][];
	 protected int m, n;
	
	 public SymbolMatrix(int _m, int _n) {
		  m = _m;
		  n = _n;
		  valor = new Expression[m][n];

		  for(int i = 0; i < m; i++) {
				for(int j = 0; j < n; j++) {
					 valor[i][j] = new Expression(0);
				}
		  }

	 }
	
	 public int getM() {
		  return m;
	 }
	
	 public int getN() {
		  return n;
	 }
	
	 public int numFilas() {
		  return m;
	 }
	
	 public int numColumnas() {
		  return n;
	 }

	
	 public Expression getValue(int a, int b) {
		  return valor[a][b];
	 }

	 public void setValue(int a, int b, Expression v) {
		  valor[a][b] = v;
	 }
	
	 /**
	  * Devuelve el producto M*A siendo M la matriz apuntada por this
	  * @param A matriz a multiplicar
	  * @returns producto de matrices
	  * @throws IllegalArgumentException si la matriz A no es vlida
	  */
	 public SymbolMatrix multDcha(SymbolMatrix A) throws IllegalArgumentException {
// 		  if(A.getM() != n) throw new IllegalArgumentException();

		  /* TODO - */
	
// 		  SymbolMatrix B = new SymbolMatrix(m, A.getN());
// 		  for(int j=0; j<A.getN(); j++) {
// 				for(int i=0; i<A.getM(); i++) {
// 					 double dato = 0;
// 					 for(int k=0; k<n; k++) {
// 						  dato += valor[i][k]*A.valor[k][j];
// 					 }
// 					 B.valor[i][j]=dato;
// 				}
// 		  }
// 		  return B;

		  return null; /* TODO */
	 }
	
	 public SymbolMatrix traspuesta() {
		  SymbolMatrix B = new SymbolMatrix(n, m);
		  for(int i=0; i<n; i++) {
				for(int j=0; j<m; j++) {
					 B.valor[i][j] = valor[j][i]; 
				}
		  }
		
		  return B;
	 }
	
	 public SymbolMatrix getFila(int i) {
		  SymbolMatrix f = new SymbolMatrix(1, n);
		  for(int j=0; j<m; j++) {
				f.valor[0][j] = valor[i][j];
		  }
		  return f;
	 }
	
	 public SymbolMatrix getColumna(int j) {
		  SymbolMatrix c = new SymbolMatrix(m, 1);
		  for(int i=0; i<n; i++) {
				c.valor[i][0] = valor[i][j];
		  }
		  return c;
	 }
	
	 /*
	  * Crea una matriz unitaria
	  * @param m tamao de la matriz
	  * @returns una matriz unitaria
	  */	
	 public static SymbolMatrix matrizUnidad(int m) {
		  SymbolMatrix I = new SymbolMatrix(m, m);
		  for(int i=0; i<m; i++) {
				I.valor[i][i] = new Expression(1);
		  }
		  return I;
	 }

	 /**
	  * Multiplica una fila por e
	  */
	 public void multFila(int i, Expression e)
	 {
		  for(int j=0; j<n; j++) {
				valor[i][j].mult(e);
		  }
	 }


	 /**
	  * Multiplica por e todos los elementos con columna j &gt; k en la fila i
	  */
	 public void multFila(int i, int k,  Expression e)
	 {
		  //System.out.println(" - multFila {");
		  for(int j=k+1; j<n; j++) {
				//System.out.println(" ("+i+","+j+"):"+valor[i][j]);
				valor[i][j].mult(e);
				//System.out.println("      :"+valor[i][j]);
		  }
		  //System.out.println("   }");

	 }

	 /**
	  * Multiplica una columna por x
	  */
	 public void multColumna(int j, Expression e)
	 {
		  for(int i=0; i<m; i++) {
				valor[i][j].mult(e);
		  }
	 }
	
	 /**
	  * suma a la fila i una combinacin lineal de las filas de la matriz
	  * @param i fila 
	  * @param vector Matriz con coeficientes de la combinacin lineal
	  * throws IllegalArgumentException si el vector no es de tamao 1xm
	  */
	 /* TODO - */
	 public void sumCombFilas(int i, SymbolMatrix vector) throws IllegalArgumentException
	 {

		  /* TODO */
// 		  if(vector.m != 1 || vector.n != m) throw new IllegalArgumentException();
// 		  SymbolMatrix fila = getFila(i);
// 		  for(int k=0; k<m; k++) {
// 				for(int h=0; h<n; h++) {
// 					 fila.valor[1][h] += valor[k][h]*vector.valor[1][k];
// 				}
// 		  }
// 		  for(int h=0; h<n; h++) {
// 				valor[i][h] = fila.valor[1][h];
// 		  }
	 }

	 /**
	  * suma a la fila i la fila j multiplicada por a
	  * @param i fila que va a modificarse
	  * @param j fila que se va a multiplicar por a para sumarla a i
	  * 
	  */
	 public void sumFila(int i, int j, Expression e) 
	 {
		  for(int k=0; k<n; k++) {
				valor[i][k].add(Expression.prod(valor[j][k], e));
		  }
	 }


	 /**
	  * Suma a la fila i la fila j multiplicada por e.
	  * Slo se alteran los elementos con ndice de columna mayor que k.
	  *
	  * @param i fila que va a modificarse
	  * @param j fila que se va a multiplicar por e para sumarla a i
	  * 
	  */
	 public void sumFila(int i, int j, int k, Expression e) 
	 {
		  for(int r=k+1; r<n; r++) {
				valor[i][r].add(Expression.prod(valor[j][r], e));
		  }
	 }

	 public void sumElement(int i, int j, Expression e)
	 {
		  valor[i][j] = Expression.sum(valor[i][j], e);
	 }

	 /**
	  * Calcula la matriz reducida de gauss
	  */
	 public SymbolMatrix reducidaGauss()
	 {
		  SymbolMatrix r = (SymbolMatrix) clone();

		  /* TO-DO */

		  return r;
	 }
	
	 /**
	  * Calcula la matriz reducida de Gauss-Jordan
	  */

	 public SymbolMatrix reducidaGaussJordan()
	 {
		  SymbolMatrix r = (SymbolMatrix) clone();


		  int j = 0; // columna j
		  int i = 0; // fila inicial
		  for(j = 0; (j < r.numColumnas()) && (i < r.numFilas()); j++) {
				int k = i; // fila i

				/* TODO - FIXME: los ficheros de pruebas amp2.txt, y amp3.txt
				   muestran una advertencia de columna vaca. Debera
				   empezar k desde 0? */

				//System.out.println("Iteracion: " + j + " ===========*");

				/* buscar el primer elemento no nulo de la columna j,
				 empezando por i_ini */
				while((k < r.numFilas()) && (r.getValue(k, j).isZero())) {
					 k++;
				}
				if(k == r.numFilas()) {
					 /* la columna j tiene todos sus elementos nulos:
					    saltar a la siguiente columna */
					 //System.out.println("  W!: La columna "+j+" es nula.");
					 continue;
				}

				// El elemento (k,j) es no-nulo.
				//System.out.println("  D: i="+i+"; j="+j+"; k="+k);
				if(k > i) {
					 //System.out.println("  D: cambiar filas " +i+" y "+k);
					 r.cambiarFilas(k, i);
					 
				}
				//i++; // FIXME: i_ini++  i_ini = j ?

				//System.out.println("Paso 1;");
				//System.out.println(r.toString()+"\r\n");

				Expression inv = r.valor[i][j].getInverse(); // FIXME: j,i  i,j?
				//System.out.println("v="+r.valor[i][j].toString()
				// +", inv="+inv.toString());

				//System.out.println("  Multiplicando fila:");
				/* dividir todos los elementos de la fila por el inverso
					del elemento (i,j), de forma que este quede a 1 */
				r.multFila(i, j, inv);
				//System.out.println(r.toString()+"\r\n");
				r.valor[i][j] = new Expression(1);

				/* hacer ceros los elementos encima de (i,j) */
				for(k = 0; k < i; k++) {
					 if(!r.getValue(k, j).isZero()) {
						  r.sumFila(k, i, j, r.valor[k][j].getOpposite());
						  r.valor[k][j] = new Expression(0); // cero
					 }
								
				}
				//System.out.println("Paso 2;");
				//System.out.println(r.toString()+"\r\n");

				/* hacer ceros los elementos debajo de (i,j) */
				for(k = i+1; k < r.numFilas(); k++) {
					 if(!r.getValue(k, j).isZero()) {
						  r.sumFila(k, i, j, r.valor[k][j].getOpposite());
						  r.valor[k][j] = new Expression(0); // cero
					 }
				}
				//System.out.println("Paso 3;");
				//System.out.println(r.toString()+"\r\n");
				i++;
		  }
		  r.simplify();
		  return r;
	 }

  /*---------------------------------------*/

	 public void simplify() {
		  for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					 valor[i][j].simplify();
				}

		  }
	 }

	 public void cambiarFilas(int i, int j) {
		  for(int k = 0; k < n; k++) {
				Expression s = valor[i][k];
				valor[i][k] = valor[j][k];
				valor[j][k] = s;
		  }
	 }
	
	 public Object clone() {
		  SymbolMatrix r = new SymbolMatrix(m, n);
		  for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					 r.valor[i][j] = valor[i][j].clone();
				}

		  }

		  return r;
	 }

	 public String toString()
	 {
		  String str = "";
		  for(int i=0; i<m; i++) {
				str += "["+i+"]: ";
				for(int j=0; j<n; j++) {
					 str = str + valor[i][j] + "; ";
				}
				str += "\r\n";
		  }
		  return str;
	 }
}