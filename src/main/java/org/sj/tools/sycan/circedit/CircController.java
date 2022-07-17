package org.sj.tools.sycan.circedit;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.Dialog;
import java.awt.Button;
import java.awt.MenuItem;
import java.awt.Point;

import org.sj.utils.math.symbol.Expression;
import org.sj.utils.math.symbol.Context;
import org.sj.utils.math.complex.Complex;
import org.sj.tools.sycan.symbcirc.CircuitAnalyzer;

/**
 * Controler in the Model-View-Controller design pattern.
 */

public class CircController extends ControllerImpl
	 implements ActionListener, MouseListener, MouseMotionListener, Const {


	 /* posibles acciones */
	 public static final int ACT_NONE    =   0;
	 public static final int ACT_DELETE  =  11;
	 public static final int ACT_ROTATE  =  12;
	 public static final int ACT_DRAW_TL_ELEM  = 100;
	 public static final int ACT_DRAW_SHORTCIRC  = 101;
	 public static final int ACT_DRAW_GROUND  = 102;
	 public static final int ACT_PLOT = 103;

	 public static final int DRAW_SHORTCIRC_NUM_STATES = 3;


	 /** Para dibujar cortocircuitos */
	 Point A, B;
	 boolean sc_draw_up = false;


	 /** accion a realizar */
	 private int action;

	 /** estado de la accion */
	 private int state;

	 /** tipo de objeto a crear */
	 private String create_type = TYPE_RESISTOR;


	 public CircController(DocViewWindow cvw) {
		  super(cvw);
		  view.addMouseListener(this);
		  view.addMouseMotionListener(this);

		  action = ACT_DRAW_TL_ELEM;
		  create_type = TYPE_RESISTOR;

	 }


	 public void setAction(int act) {
		  action = act;
	 }

	 public void setCreateType(String type) {
		  create_type = type;
	 }




	 public void actionPerformed(ActionEvent ae) {
		  Object obj = ae.getSource();
		  int act = -1;
		  try {
				act = getAction(obj);
		  } catch (Exception e) {
				System.out.println("Excepcion obteniendo accion: "
										 + e.getMessage());
				return;
		  }

		  CircView cview = (CircView) this.view;
		  CircDoc cdoc = (CircDoc) this.doc;

		  switch(act) {
		  case C_ACT_DRAW_TL_ELEM_R:
				setAction(ACT_DRAW_TL_ELEM);
				setCreateType(TYPE_RESISTOR);
				break;
		  case C_ACT_DRAW_TL_ELEM_L:
 				setAction(ACT_DRAW_TL_ELEM);
				setCreateType(TYPE_INDUCTOR);
				break;
		  case C_ACT_DRAW_TL_ELEM_C:
 				setAction(ACT_DRAW_TL_ELEM);
				setCreateType(TYPE_CAPACITOR);
				break;
		  case C_ACT_DRAW_TL_SHORTCIRC:
				setAction(ACT_DRAW_SHORTCIRC);
				break;
		  case C_ACT_DRAW_TL_ELEM_VS:
				setAction(ACT_DRAW_TL_ELEM);
				setCreateType(TYPE_VOLT_SRC);
				break;
		  case C_ACT_DRAW_TL_ELEM_CS:
				setAction(ACT_DRAW_TL_ELEM);
				setCreateType(TYPE_CURR_SRC);
				break;
		  case C_ACT_DRAW_GROUND:
				setAction(ACT_DRAW_GROUND);
				//setCreateType(TYPE_CURR_SRC);
				break;
		  case C_ACT_DELETE:
				setAction(ACT_DELETE);
				break;
		  case C_ACT_ROTATE:
				setAction(ACT_ROTATE);
				break;

		  case C_ACT_PLOT:
				setAction(ACT_PLOT);
				//				String names[] = {"abc", "def", "ghi"};
				//				Dialog dlg = new ParamDlg(win, names);
				//				dlg.setVisible(true);
				//PlotWindow w = new PlotWindow();
				//				w.setVisible(true);
				break;
		  case C_ACT_SOLVE_CIRC:
				try {
					 cdoc.solve();
				} catch(Exception exc) {
					 System.out.println("Excepcion: " +exc.getMessage());
				}
				break;
		  case C_ACT_EXPORT_CIRC:
				// TO-DO:
				break;

		  case C_ACT_CLEAR:
				cdoc.clear();
				break;

		  case C_ACT_ABOUT:
		      showAboutInfo();
		      break;


		  case C_ACT_EXIT:
				win.setVisible(false);
				win.dispose();
				System.exit(0);
				break;

		  default:
				;
		  }
		  
	 }

    public void showAboutInfo() {
	AboutWindow awin = new AboutWindow();
	awin.setVisible(true);
    }



	 /**
	  * Busca el nodo que está sobre el punto p y representa
	  * la función de su tensión en función de la frecuencia
	  *
	  * TO-DO: acabar
	  */
	 public void plotNode(Point p) {

		  CircDoc cdoc = (CircDoc) doc;
		  Expression exp = cdoc.getNodeExp(p);
		  
		  /* --- TEST --- */
// 		  for(int f = 10; f < 10000; f*=10) {
// 				ctx.setValue("s", 0, 2*Math.PI*f);
// 				Complex z = exp.eval(ctx);
// 				double v = z.module();
// 				System.out.println("f = "+f+"; v = " + v);
// 		  }

		  /* plot */
		  FormulaWindow fw = new FormulaWindow("Formula", exp, cdoc);
		  fw.setVisible(true);
		  
	 }


	 /* --- Atención a eventos de ratón --- */

	 public void mouseClicked(MouseEvent e) {
		  CircView cview = (CircView) view;
		  CircDoc cdoc = (CircDoc) doc;

		  Point mouse = e.getPoint();
		  Point fixed = cview.snapToGrid(mouse);


		  switch(action) {
		  case ACT_DRAW_TL_ELEM:
				cdoc.createTLElement(fixed, create_type);
				break;
		  case ACT_DRAW_SHORTCIRC:
				/* TODO: mejorar? */
				state++;
				switch(state) {
				case 1:
					 A = fixed;
					 cview.beginSCPreview(A);
					 break;
				case 2:
					 B = fixed;
					 if(B.getY() == A.getY()) {
						  cdoc.createShortCirc(A, B, false);
						  state = 0;
					 }
					 break;

				case 3:
					 /**  crear Cortocircuito */
					 cdoc.createShortCirc(A, B, sc_draw_up);
					 state = 0;
					 break;

				default:
					 state = 0;
				}
				 
				break;

		  case ACT_DRAW_GROUND:
				/* TODO : mejorar? */
				cdoc.createGround(fixed);
				break;

		  case ACT_DELETE:
				cdoc.seekAndDestroy(mouse);
				break;
		  case ACT_ROTATE:
				cdoc.seekAndRotate(mouse);
				break;
		  case ACT_PLOT:
				plotNode(fixed);
				break;
			
		  }
	 }


	 /* --- interfaz MouseMotionListener --- */
	 public void mouseMoved(MouseEvent e) {
		  
		  if(action == ACT_DRAW_SHORTCIRC) {
				CircView cview = (CircView) view;
				Point mouse = e.getPoint();
				switch(state) {
				case 1:
					 Point fixed = cview.snapToGrid(mouse);
					 cview.drawSCPreview(A, fixed, sc_draw_up);
					 break;
				case 2:
					 sc_draw_up = calcDrawUp(A, B, mouse);
					 cview.drawSCPreview(A, B, sc_draw_up);
					 break;
				}
		  }
		  //public static final int DRAW_SHORTCIRC_NUM_STATES = 2;
		  
	 }

	 static boolean calcDrawUp(Point A, Point B, Point mouse) {
		  int dx = (int) (mouse.getX() - A.getX());
		  int dy = (int) (mouse.getY() - A.getY());
		  int ny = (int)- (B.getX() - A.getX());
		  int nx = (int) (B.getY() - A.getY());
		  if(ny > 0) {
				ny = -ny;
				nx = - nx;
		  }
		  int h = nx*dx + ny*dy;
		  return (h > 0);
	 }

	 public void mouseDragged(MouseEvent e) {
		  
	 }

	 
	 /* --- interfaz MouseListener --- */


	 public void mouseEntered(MouseEvent e) {
	 }

	 public void mouseExited(MouseEvent e) {
	 }

	 public void mousePressed(MouseEvent e) {
	 }

	 public void mouseReleased(MouseEvent e) {
	 }


}
