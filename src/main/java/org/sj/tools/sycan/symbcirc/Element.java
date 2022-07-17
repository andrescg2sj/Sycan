package org.sj.tools.sycan.symbcirc;


/**
 * Representa un elemento concentrado:
 * R, L, C, generador de corriente, etc.
 *
 *    o     + (A)
 *    |
 *    |
 *   | |    |
 *   | |    |
 *   | |    |  i
 *   | |   \|/ 
 *    |     V
 *    |
 *    o     - (B)
 */
public abstract class Element {
	 /** identificador del nodo positivo */
	 protected int A;

	 /** nodo negativo */
	 protected int B;

	 /** nombre */
	 protected String name;

	 public Element(int a, int b, String n)
	 {
		  A = a;
		  B = b;
		  name = n;
	 }

	 public Element(int a, int b)
	 {
		  A = a;
		  B = b;
		  name = defaultName();
	 }

	 public int getNodeA()
	 {
		  return A;
	 }

	 public int getNodeB()
	 {
		  return B;
	 }
	 

	 public void setNodeA(int a)
	 {
		  A = a;
	 }

	 public void setNodeB(int b)
	 {
		  B = b;
	 }

	 public String getName() {
		  return name;
	 }

	 protected abstract String defaultName();

	 public abstract Object clone();

}