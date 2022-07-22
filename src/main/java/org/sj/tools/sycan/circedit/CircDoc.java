package org.sj.tools.sycan.circedit;

import java.util.Vector;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Point;

import org.sj.tools.sycan.circedit.builder.CircuitBuilder;
import org.sj.tools.sycan.symbcirc.Element;
import org.sj.tools.sycan.symbcirc.ShortCirc;
import org.sj.tools.sycan.symbcirc.Circuit;
import org.sj.tools.sycan.symbcirc.CircuitAnalyzer;

import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Context;
import org.sj.utils.math.complex.Complex;

public class CircDoc extends Doc implements Const {

	 LinkedList<GraphicPart> elements;

	 NameManager nameMan;

	 /** ltimo nodo creado */
	 int lastNode;

	 /** Admitancia por defecto */
	 float def_Y = 1;
	 float def_V = 1;
	 float def_I = 1;



	 /** Construye el circuito y guarda datos del proceso */
	 CircuitBuilder circBuilder;

	 /** circuito construdo */
	 Circuit circ;

	 /** Indica si el documento ha sido modificado desde la ltima
		  ver que se construy el circuito */
	 boolean modified;

	 public CircDoc() {
		  elements = new LinkedList<GraphicPart>();
		  lastNode = 0;
		  nameMan = new NameManager();
		  nameMan.register(TYPE_RESISTOR, "R");
		  nameMan.register(TYPE_INDUCTOR, "L");
		  nameMan.register(TYPE_CAPACITOR, "C");
		  nameMan.register(TYPE_VOLT_SRC, "VS");
		  nameMan.register(TYPE_CURR_SRC, "CS");
		  nameMan.register(TYPE_SHORT_CIRC, "SC");
		  nameMan.register(TYPE_IDEAL_OA, "IOA");

		  modified = false;
	 }


	 public void clear() {
		  elements.clear();
		  nameMan.reset();
		  getCircView().updateView();
		  setModifiedFlag();
	 }

	 

	 /* --- Mtodos heredados --- */

	 public Iterator<GraphicPart> getIterator()
	 {
		  return elements.iterator();
	 }

	 public Object requestObject(int objType, int objIndex)
	 {
		  return null;
	 }


	 public int sendMessage(int msgIndex, Object obj)
	 {
		  return 0;
	 }

	 /* --- Otros mtodos --- */

	 public CircView getCircView() {
		  return (CircView) getView();
	 }

	 protected String generateName(String type) {
		  return nameMan.getName(type);
	 }


	 public void createGround(Point center) {
		  GraphicPart E =	new GraphicGround(center);
		  elements.add(E);
		  getCircView().updateView();
		  setModifiedFlag();
	 }


	 public void createShortCirc(Point A,  Point B, boolean draw_up) {
		  GraphicPart E =	new GraphicShortCirc(generateName(TYPE_SHORT_CIRC),
																A, B, draw_up);
		  elements.add(E);
		  getCircView().updateView();
		  setModifiedFlag();
		  
	 }

	 public void createTLElement(Point p, String type) {
		  
		  
		  GraphicPart E =	new GraphicTLElement(generateName(type), type, p,
																DIR_UP);

		  elements.add(E);
		  getCircView().updateView();
		  setModifiedFlag();
	 }



	 /*
	 public void createResistor(Point p) {
		  GraphicElement R =	new GraphicTLElement("R", TYPE_RESISTOR, p,
																DIR_UP);
		  elements.add(R);
		  getCircView().updateView();
	 }

	 public void createVoltSrc(Point p) {

		  GraphicElement V =	new GraphicTLElement("V", TYPE_VOLT_SRC, p,
																DIR_UP);

		  elements.add(V);
		  getCircView().updateView();
	 }

	 public void createCurrSrc(Point p) {
		  GraphicElement I =	new GraphicTLElement("I", TYPE_CURR_SRC, p,
																DIR_UP);
		  elements.add(I);
		  getCircView().updateView();
	 }
	 */


	 /**
	  * Busca el objeto que tenga el punto p dentro
	  */
	 int seek(Point p) throws Exception {
		  Iterator<GraphicPart> it = elements.iterator();
		  int i = 0;
		  while(it.hasNext()) {
				GraphicPart ge = it.next();
				if(ge.isInside(p)) {
					 return i;
				}

				i++;
		  }
		  throw new Exception("Not found");
	 }


	 void seekAndDestroy(Point p) {
		  try {
				int i = seek(p);
				elements.remove(i);
				setModifiedFlag();
				getCircView().updateView();
		  } catch(Exception e) {
				return;
		  }
		  
	 }

	 void seekAndRotate(Point p) {
		  try {
				int i = seek(p);
				GraphicPart ge = elements.get(i);
				ge.changeDir();
				setModifiedFlag();
				getCircView().updateView();
		  } catch(Exception e) {
				return;
		  }
		  
	 }

	 protected void setModifiedFlag()
	 {
		  modified = true;
	 }

	 protected void resetModifiedFlag()
	 {
		  modified = false;
	 }

	 public boolean isModified()
	 {
		  return modified;
	 }



	 /**
	  * Obtener la expressin de la tensin de un nodo del circuito
	  * @param p Point con la posicin donde debe buscarse el nodo
	  */
	 public Expression getNodeExp(Point p)
	 {
		  try {
				Circuit c = getCircuit();
				if(c == null) {
					 throw new Exception("No circuit.");
				}
		  
				System.out.println(" Circuit: -----------------------");
				System.out.println(c.toString());
				System.out.println(" Solve: -------------------------");
				CircuitAnalyzer can = new CircuitAnalyzer();
				can.solve(c);
				System.out.println("---------------------------------");
				int id = NO_NODE;
				try {
					 int id1 = circBuilder.getNodeId(p);
					 id = can.getTransNode(id1);
					 System.out.println("id'="+id1+", id="+id);
				} catch(Exception e) {
					 throw new Exception("Excepcion ("+
												e.getMessage()+") obteniedo ID de nodo");
				}
				if(id == NO_NODE) {
					 System.err.println("No node in position " + p.toString());
					 return null;
				}

				return can.getNodeVoltage(id);
		  } catch(Exception e) {
				System.err.println("Exception: "+e.getMessage());
				return null;
		  }
	 }


	 /**
	  * Obtener los nombres de las variables del circuito
	  */
	 public String[] getCircVarNames()
	 {
		  Circuit c = getCircuit();
		  
		  /* get variables */
		  Vector<String> vars = new Vector<String>();
		  for(int i = 0; i < circ.numElements(); i++) {
				Element e = c.get(i);
				if(!(e instanceof ShortCirc)) {
					 vars.add(e.getName());
				}
		  }

		  System.out.println("to String...");
		  /* ... to String */
		  String names[] = new String[vars.size()];
		  for(int i = 0; i < vars.size(); i++) {
				names[i] = vars.get(i);
		  }
		  return names;
	 }

	 protected void buildCircuit() throws Exception
	 {
		  Vector<GraphicPart> elems = new Vector<GraphicPart>();
		  elems.addAll(elements);
		  System.out.println(" Build: ..-----------------------");
		  circBuilder = new CircuitBuilder(elems);
		  circ = circBuilder.build();
		  resetModifiedFlag();
		  System.out.println("  - Done  -----------------------");
	 }

	 /**
	  * Obtener el circuito, ya construdo, listo para ser analizado, etc.
	  */
	 public Circuit getCircuit()
	 {
		  if(isModified() || (circ == null)) {
				try {
					 buildCircuit();
				} catch(Exception e) {
				}
		  }

		  return circ;
		  
	 }

	 void solve() throws Exception
	 {
		  Circuit circ = getCircuit();
		  System.out.println(" Circuit: -----------------------");
		  System.out.println(circ.toString());
		  System.out.println(" Solve: -------------------------");
		  CircuitAnalyzer can = new CircuitAnalyzer();
		  can.solve(circ);
		  System.out.println("---------------------------------");

		  //		  int id = can.getNumNodes();
		  //		  Expression exp = can.getNodeVoltage(id);
	 }


	public void createIdealOA(Point p) {
		// TODO Auto-generated method stub
		  GraphicPart E = new GraphicIdealOA(generateName(TYPE_IDEAL_OA), p);

		  elements.add(E);
		  getCircView().updateView();
		  setModifiedFlag();

	}


}