package org.sj.tools.sycan.circedit;

import java.awt.Panel;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Container;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Vector;


public class ToolBar extends Panel implements Const {

	 //	 protected CircView view;
	 //	 protected CircDoc doc;
	 Controller ctr;
	 
	 Button btnR;
	 Button btnL;
	 Button btnC;
	 Button btnSC; // Short Circuit
	 Button btnVS; // Voltage Source
	 Button btnCS; // Current Source
	 Button btnIdealOA; // Ideal Operational Amplifier
	 Button btnGND; // Ground
	 Button btnDelete; // Borrar
	 Button btnRotate; // Girar
	 //	 Button btnSolve; // Resolver
	 //	 Button btnClear; // Borrar todo

	 Button btnPlot; // Pintar [TO-DO]

	 public ToolBar(Controller c)
	 {
		  ctr = c;
		  //view = vw;
		  //doc = dc;

		  btnR = new Button("R");	
 		  btnL = new Button("L");
		  btnC = new Button("C");
		  btnSC = new Button("Short Circ.");
		  btnVS = new Button("Volt. Src.");
		  btnCS = new Button("Curr. Src.");
		  btnIdealOA = new Button("Ideal O.A.");
		  btnGND = new Button("Ground");	
		  btnDelete = new Button("Delete");
		  btnRotate = new Button("Rotate");
		  //		  btnSolve = new Button("Solve");
		  //		  btnClear = new Button("Clear");

		  btnPlot = new Button("Calc. Node");

		  add(btnR);
 		  add(btnL);
		  add(btnC);
		  add(btnSC);
		  add(btnVS);
		  add(btnCS);	
		  add(btnIdealOA);	
		  add(btnGND);
		  add(btnDelete);
		  add(btnRotate);
		  //		  add(btnSolve);
		  //		  add(btnClear);
		  add(btnPlot);

		  ctr.assign(btnR, C_ACT_DRAW_TL_ELEM_R);
		  ctr.assign(btnL, C_ACT_DRAW_TL_ELEM_L);
		  ctr.assign(btnC, C_ACT_DRAW_TL_ELEM_C);
		  ctr.assign(btnVS, C_ACT_DRAW_TL_ELEM_VS);
		  ctr.assign(btnCS, C_ACT_DRAW_TL_ELEM_CS);
		  ctr.assign(btnSC, C_ACT_DRAW_TL_SHORTCIRC);
		  ctr.assign(btnIdealOA, C_ACT_DRAW_IDEAL_OA);
		  ctr.assign(btnGND, C_ACT_DRAW_GROUND);
		  ctr.assign(btnDelete, C_ACT_DELETE);
		  ctr.assign(btnRotate, C_ACT_ROTATE);
		  //		  ctr.assign(btnSolve, ACT_SOLVE_CIRC);
		  //		  ctr.assign(btnClear, ACT_CLEAR);
		  ctr.assign(btnPlot, C_ACT_PLOT);
	 }



	 

}