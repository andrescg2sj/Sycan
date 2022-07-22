package org.sj.tools.sycan.circedit.draw;

import java.awt.Point;
import java.awt.Rectangle;

import org.sj.tools.sycan.circedit.Const;
import java.util.Vector;
import java.util.Hashtable;

public class DrawingFactory implements Const {

	 protected static DrawingFactory instance = null;


	 Hashtable<String, Drawing> drg;

	 protected DrawingFactory()
	 {
 		  drg = new Hashtable<String,Drawing>();

		  drg.put(TYPE_RESISTOR, createResistor());
		  drg.put(TYPE_INDUCTOR, createInductor());
		  drg.put(TYPE_CAPACITOR, createCapacitor());
		  drg.put(TYPE_VOLT_SRC,  createVoltSrc());
		  drg.put(TYPE_CURR_SRC,  createCurrSrc());
		  drg.put(TYPE_GROUND,  createGround());
		  drg.put(TYPE_IDEAL_OA, createIdealOA());
	 }

	 public static DrawingFactory getInstance()
	 {
		  if(instance == null) {
				instance = new DrawingFactory();
		  }

		  return instance;
	 }

	 public Drawing getDrawing(String type) {
		  
		  return drg.get(type);
	 }

	 protected static Drawing createResistor() {
 		  int N = 5;
		  Vector<Drawable> d = new Vector<Drawable>(N*3+4,5);
		  d.add(new Circle(ELEM_SIZE/2, 0,  NODE_RAD));
		  d.add(new Circle(-ELEM_SIZE/2, 0, NODE_RAD));
		  d.add(new Line(ELEM_SIZE/2, 0, (ELEM_SIZE-COMP_SIZE)/2, 0));
		  d.add(new Line(-ELEM_SIZE/2, 0, -(ELEM_SIZE-COMP_SIZE)/2, 0));

		  int L = COMP_SIZE / N;
		  int x, y;

		  y = (int) 0;
		  for(int i = 0; i < N; i++) {
				x = (int) -ELEM_SIZE/2 + (ELEM_SIZE - COMP_SIZE) / 2 +
					 i * COMP_SIZE / N;
				d.add(new Line(x, y, x+L/4, y+GRID_SIZE/2));
				d.add(new Line(x+3*L/4, y-GRID_SIZE/2,	x+L/4, y+GRID_SIZE/2));
				d.add(new Line(x+3*L/4, y-GRID_SIZE/2, x+L, y));
		  }

		  return new Drawing(d);
	 }


	 protected static Drawing createCapacitor() {
		  Vector<Drawable> d = new Vector<Drawable>(8,4);
		  d.add(new Circle(ELEM_SIZE/2, 0,  NODE_RAD));
		  d.add(new Circle(-ELEM_SIZE/2, 0, NODE_RAD));
		  //d.add(new Line(ELEM_SIZE/2, 0, (ELEM_SIZE-COMP_SIZE)/2, 0));
		  //d.add(new Line(-ELEM_SIZE/2, 0, -(ELEM_SIZE-COMP_SIZE)/2, 0));

		  d.add(new Line(ELEM_SIZE/2, 0, ELEM_SIZE/8, 0));
		  d.add(new Line(-ELEM_SIZE/2, 0, -ELEM_SIZE/8, 0));

		  /* placas */
		  d.add(new Line(ELEM_SIZE/8,-ELEM_SIZE/4, ELEM_SIZE/8, ELEM_SIZE/4));
		  d.add(new Line(-ELEM_SIZE/8,-ELEM_SIZE/4, -ELEM_SIZE/8, ELEM_SIZE/4));

		  return new Drawing(d);
	 }


	 protected static Drawing createInductor() {
 		  int N = 3;
		  Vector<Drawable> d = new Vector<Drawable>(8,4);
		  d.add(new Circle(ELEM_SIZE/2, 0,  NODE_RAD));
		  d.add(new Circle(-ELEM_SIZE/2, 0, NODE_RAD));
		  d.add(new Line(ELEM_SIZE/2, 0, (ELEM_SIZE-COMP_SIZE)/2, 0));
		  d.add(new Line(-ELEM_SIZE/2, 0, -(ELEM_SIZE-COMP_SIZE)/2, 0));


		  int L = COMP_SIZE / N;
		  int x, y;

		  y = (int) 0;
		  for(int i = 0; i < N; i++) {
				x = (int) -ELEM_SIZE/2 + (ELEM_SIZE - COMP_SIZE) / 2 +
					 i * COMP_SIZE / N;
				d.add(new Arc(x+L/2, y, L/2, 0, 180));
		  }
 		  

		  return new Drawing(d);
	 }


	 protected static Drawing createVoltSrc() {
		  Vector<Drawable> d = new Vector<Drawable>(8,4);
		  d.add(new Circle(ELEM_SIZE/2, 0,  NODE_RAD));
		  d.add(new Circle(-ELEM_SIZE/2, 0, NODE_RAD));
		  d.add(new Line(ELEM_SIZE/2, 0, (ELEM_SIZE-COMP_SIZE)/2, 0));
		  d.add(new Line(-ELEM_SIZE/2, 0, -(ELEM_SIZE-COMP_SIZE)/2, 0));

		  d.add(new Circle(0, 0, COMP_SIZE/2));

		  d.add(new Line(COMP_SIZE/4, COMP_SIZE/8, COMP_SIZE/4, -COMP_SIZE/8));
		  d.add(new Line(COMP_SIZE/8, 0, 3*COMP_SIZE/8, 0));
		  d.add(new Line(-COMP_SIZE/4, COMP_SIZE/8, -COMP_SIZE/4, -COMP_SIZE/8));

		  return new Drawing(d);
	 }

	 protected static Drawing createCurrSrc() {
		  Vector<Drawable> d = new Vector<Drawable>(8,4);
		  d.add(new Circle(ELEM_SIZE/2, 0,  NODE_RAD));
		  d.add(new Circle(-ELEM_SIZE/2, 0, NODE_RAD));
		  d.add(new Line(ELEM_SIZE/2, 0, (ELEM_SIZE-COMP_SIZE)/2, 0));
		  d.add(new Line(-ELEM_SIZE/2, 0, -(ELEM_SIZE-COMP_SIZE)/2, 0));

		  d.add(new Circle(0, 0, COMP_SIZE/2));

		  /* flecha */
		  d.add(new Line(COMP_SIZE/2, 0, -COMP_SIZE/4, 0));
		  d.add(new Line(-COMP_SIZE/4, 0, -COMP_SIZE/8, COMP_SIZE/8));
		  d.add(new Line(-COMP_SIZE/4, 0, -COMP_SIZE/8, -COMP_SIZE/8));

		  return new Drawing(d);
	 }
	 

	 protected static Drawing createCtrlVoltSrc() {
		  Vector<Drawable> d = new Vector<Drawable>(8,4);
		  d.add(new Circle(ELEM_SIZE/2, 0,  NODE_RAD));
		  d.add(new Circle(-ELEM_SIZE/2, 0, NODE_RAD));
		  d.add(new Line(ELEM_SIZE/2, 0, (ELEM_SIZE-COMP_SIZE)/2, 0));
		  d.add(new Line(-ELEM_SIZE/2, 0, -(ELEM_SIZE-COMP_SIZE)/2, 0));

		  /* rombo */
		  d.add(new Line(ELEM_SIZE/4,0, 0, ELEM_SIZE/4));
		  d.add(new Line(-ELEM_SIZE/4,0, 0, ELEM_SIZE/4));
		  d.add(new Line(-ELEM_SIZE/4,0, 0, -ELEM_SIZE/4));
		  d.add(new Line(ELEM_SIZE/4,0, 0, -ELEM_SIZE/4));
		  

		  d.add(new Line(COMP_SIZE/4, COMP_SIZE/8, COMP_SIZE/4, -COMP_SIZE/8));
		  d.add(new Line(COMP_SIZE/8, 0, 3*COMP_SIZE/8, 0));
		  d.add(new Line(-COMP_SIZE/4, COMP_SIZE/8, -COMP_SIZE/4, -COMP_SIZE/8));

		  return new Drawing(d);
	 }

	 protected static Drawing createCtrlCurrSrc() {
		  Vector<Drawable> d = new Vector<Drawable>(8,4);
		  d.add(new Circle(ELEM_SIZE/2, 0,  NODE_RAD));
		  d.add(new Circle(-ELEM_SIZE/2, 0, NODE_RAD));
		  d.add(new Line(ELEM_SIZE/2, 0, (ELEM_SIZE-COMP_SIZE)/2, 0));
		  d.add(new Line(-ELEM_SIZE/2, 0, -(ELEM_SIZE-COMP_SIZE)/2, 0));

		  /* rombo */
		  d.add(new Line(ELEM_SIZE/4,0, 0, ELEM_SIZE/4));
		  d.add(new Line(-ELEM_SIZE/4,0, 0, ELEM_SIZE/4));
		  d.add(new Line(-ELEM_SIZE/4,0, 0, -ELEM_SIZE/4));
		  d.add(new Line(ELEM_SIZE/4,0, 0, -ELEM_SIZE/4));


		  /* flecha */
		  d.add(new Line(COMP_SIZE/2, 0, -COMP_SIZE/4, 0));
		  d.add(new Line(-COMP_SIZE/4, 0, -COMP_SIZE/8, COMP_SIZE/8));
		  d.add(new Line(-COMP_SIZE/4, 0, -COMP_SIZE/8, -COMP_SIZE/8));

		  return new Drawing(d);
	 }

	 protected static Drawing createGround() {
		  Vector<Drawable> d = new Vector<Drawable>(8,4);
		  d.add(new Circle(0, 0,  NODE_RAD));
		  d.add(new Line(0, 0, 0, COMP_SIZE/2));
		  d.add(new Line(-COMP_SIZE/2, COMP_SIZE/2,
							  COMP_SIZE/2, COMP_SIZE/2));
		  d.add(new Line(-3*COMP_SIZE/8, 5*COMP_SIZE/8,
							  3*COMP_SIZE/8, 5*COMP_SIZE/8));
		  d.add(new Line(-COMP_SIZE/4, 3*COMP_SIZE/4,
							  COMP_SIZE/4, 3*COMP_SIZE/4));
		  return new Drawing(d);
	 }
	 
	 protected static Drawing createIdealOA() {
		 //TODO: add methods to Drawing, instead of creating a separate Vector...
		 Vector<Drawable> d = new Vector<Drawable>(8,4);
		 Vector<Point> p = new Vector<Point>(3);
		 Rectangle bounds = new Rectangle(-ELEM_SIZE/3,-ELEM_SIZE/2, 
				 ELEM_SIZE, 2*ELEM_SIZE/3);
		 
		 /* nodes */
		 d.add(new Circle(-ELEM_SIZE/2, GRID_SIZE,  NODE_RAD));
		 d.add(new Circle(-ELEM_SIZE/2, -GRID_SIZE, NODE_RAD));
		 d.add(new Circle(ELEM_SIZE/2, 0, NODE_RAD));
		 p.add(new Point(-ELEM_SIZE/2, GRID_SIZE));
		 p.add(new Point(-ELEM_SIZE/2, -GRID_SIZE));
		 p.add(new Point(ELEM_SIZE/2, 0));
		 
		 /* connections */
		 d.add(new Line(-ELEM_SIZE/2, GRID_SIZE,  -ELEM_SIZE/3, GRID_SIZE));
		 d.add(new Line(-ELEM_SIZE/2, -GRID_SIZE, -ELEM_SIZE/3, -GRID_SIZE));
		 d.add(new Line(ELEM_SIZE/2, 0, ELEM_SIZE/3,0));
		
		 /* triangle */
		 d.add(new Line(-ELEM_SIZE/3,-ELEM_SIZE/2, ELEM_SIZE/3,0));
		 d.add(new Line(-ELEM_SIZE/3,ELEM_SIZE/2, ELEM_SIZE/3,0));
		 d.add(new Line(-ELEM_SIZE/3,ELEM_SIZE/2, -ELEM_SIZE/3,-ELEM_SIZE/2));
		 
		 /* polarity of inputs */
		 
		 
		 return new Drawing(d, p, bounds);
		 	
	 }
	 
}