package org.sj.utils.math.complex;

public class ComplexMatrix {
	 protected Complex valor[][];
	 /** filas */
	 protected int m;

	 /** columnas */
	 protected int n;
	
	 public ComplexMatrix(int _m, int _n) {
		  m = _m;
		  n = _n;
		  valor = new Complex[m][n];

		  for(int i = 0; i < m; i++) {
				for(int j = 0; j < n; j++) {
					 valor[i][j] = new Complex();
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

	
	 public Complex getValue(int a, int b) {
		  return valor[a][b];
	 }

	 public void setValue(int a, int b, Complex v) {
		  valor[a][b] = v;
	 }

	 public void setValue(int a, int b, double re, double im) {
		  valor[a][b] = new Complex(re, im);
	 }
	
	 /**
	  * Devuelve el producto M*A siendo M la matriz apuntada por this
	  * @param A Matriz a multiplicar
	  * @returns producto de matrices
	  * @throws IllegalArgumentException si la matriz A no es vlida
	  */
	 public ComplexMatrix multDcha(ComplexMatrix A) throws IllegalArgumentException {
		  if(A.getM() != n) throw new IllegalArgumentException();
	
		  ComplexMatrix B = new ComplexMatrix(m, A.getN());
		  for(int j=0; j<A.getN(); j++) {
				for(int i=0; i<A.getM(); i++) {
					 Complex dato = new Complex();
					 for(int k=0; k<n; k++) {
						  dato.add(Complex.mult(valor[i][k], A.valor[k][j]));
					 }
					 B.valor[i][j] = dato;
				}
		  }
		  return B;
	 }
	
	 public ComplexMatrix traspuesta() {
		  ComplexMatrix B = new ComplexMatrix(n, m);
		  for(int i=0; i<n; i++) {
				for(int j=0; j<m; j++) {
					 B.valor[i][j] = valor[j][i]; 
				}
		  }
		
		  return B;
	 }
	
	 public ComplexMatrix getFila(int i) {
		  ComplexMatrix f = new ComplexMatrix(1, n);
		  for(int j=0; j<m; j++) {
				f.valor[0][j] = valor[i][j];
		  }
		  return f;
	 }
	
	 public ComplexMatrix getColumna(int j) {
		  ComplexMatrix c = new ComplexMatrix(m, 1);
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
	 public static ComplexMatrix matrizUnidad(int m) {
		  ComplexMatrix I = new ComplexMatrix(m, m);

		  for(int i=0; i<m; i++) {
				I.valor[i][i] = new Complex(1,0);
		  }
		  return I;
	 }

	 /**
	  * Multiplica una fila por z
	  */
	 public void multFila(int i, Complex z)
	 {
		  for(int j=0; j<n; j++)
				{
					 valor[i][j].mult(z);
				}
	 }

	 /**
	  * Multiplica una columna por z
	  */
	 public void multColumna(int j, Complex z)
	 {
		  for(int i=0; i<m; i++) {
				valor[i][j].mult(z);
		  }
	 }
	
	 /**
	  * suma a la fila i una combinacin lineal de las filas de la matriz
	  * @param i fila 
	  * @param vector ComplexMatrix con coeficientes de la combinacin lineal
	  * throws IllegalArgumentException si el vector no es de tamao 1xm
	  */
	
	 public void sumCombFilas(int i, ComplexMatrix vector) throws IllegalArgumentException
	 {
		  if(vector.m != 1 || vector.n != m) throw new IllegalArgumentException();
		  ComplexMatrix fila = getFila(i);
		  for(int k=0; k<m; k++) {
				for(int h=0; h<n; h++) {
					 fila.valor[1][h].add(Complex.mult(valor[k][h],
																  vector.valor[1][k]));
				}
		  }
		  for(int h=0; h<n; h++) {
				valor[i][h] = fila.valor[1][h];
		  }
	 }

	 /**
	  * suma a la fila i la fila j multiplicada por a
	  * @param i fila que va a modificarse
	  * @param j fila que se va a multiplicar por a para sumarla a i
	  * 
	  */
	
	 public void sumFila(int i, int j, Complex a) 
	 {
		  for(int k=0; k<n; k++) {
				valor[i][k].add(Complex.mult(valor[j][k], a));
		  }
	 }


	 /**
	  * Calcula la matriz reducida de gauss
	  */
	 public ComplexMatrix reducidaGauss()
	 {
		  ComplexMatrix r = (ComplexMatrix) clone();

		  /* TODO - */

		  return r;
	 }
	
	 /**
	  * Calcula la matriz reducida de Gauss-Jordan
	  */

	 public ComplexMatrix reducidaGaussJordan()
	 {
		  ComplexMatrix r = (ComplexMatrix) clone();


		  int j = 0; // columna j
		  int i_ini = 0; // fila inicial
		  for(j = 0; (j < r.numColumnas()) && (i_ini < r.numFilas()); j++) {
				int i = i_ini; // fila i
				//System.out.println("Iteracion: " + j + " ============");
				while((i < r.numFilas()) && (r.getValue(i, j).isZero())) {
					 i++;
				}
				if(i == r.numFilas()) {
					 // la columna j tiene todos sus elementos nulos
					 //System.out.println("  W!: La columna "+j+" tiene es nula.");
					 continue;
				}
				
				//System.out.println("  D: i = "+i+"; j = "+j);
				if(i > j) {
					 //System.out.println("  D: cambiar filas " +i+" y "+j);
					 r.cambiarFilas(i, j);
					 
				}
				i_ini++;

				//System.out.println("Paso 1;");
				//System.out.println(r.toString()+"\r\n");

				Complex inv = r.valor[j][i].getInverse();
				//System.out.println("v="+r.valor[j][i].toString()
				// +", inv="+inv.toString());

				r.multFila(j, inv);
				r.valor[j][j] = new Complex(1, 0);
				for(i = 0; i < j; i++) {
					 if(!r.getValue(i, j).isZero()) {
						  r.sumFila(i, j, r.valor[i][j].getOpposite());
						  r.valor[i][j] = new Complex(); // cero
					 }
								
				}
				//System.out.println("Paso 2;");
				//System.out.println(r.toString()+"\r\n");

				for(i = j+1; i < r.numFilas(); i++) {
					 if(!r.getValue(i, j).isZero()) {
						  r.sumFila(i, j, r.valor[i][j].getOpposite());
						  r.valor[i][j] = new Complex(); // cero
					 }
				}
				//System.out.println("Paso 3;");
				//System.out.println(r.toString()+"\r\n");
		  }
		  return r;
	 }

	 public void cambiarFilas(int i, int j) {
		  for(int k = 0; k < n; k++) {
				Complex s = valor[i][k];
				valor[i][k] = valor[j][k];
				valor[j][k] = s;
		  }
	 }
	
	 public Object clone() {
		  ComplexMatrix r = new ComplexMatrix(m, n);
		  for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					 r.valor[i][j] = valor[i][j];
				}

		  }

		  return r;
	 }

	 public String toString()
	 {
		  String str = "";
		  for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					 str = str + valor[i][j] + " ";
				}
				str += "\n\r";
		  }
		  return str;
	 }
}