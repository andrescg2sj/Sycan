package org.sj.tools.sycan.circedit;

/**
 * Constantes globales para la interfaz grfica
 */

public interface Const {

	 /* Node index constants */
	 public static final int NO_NODE = -1;
	 public static final int NODE_A = 0;
	 public static final int NODE_B = 1;

	 /* Graphic Constants */
	 public static final int GRID_SIZE = 20;
	 public static final int ELEM_SIZE = GRID_SIZE * 4;
	 public static final int COMP_SIZE = GRID_SIZE * 2;
	 public static final int NODE_RAD = 5;

	 public static final int DIR_RIGHT = 0;
	 public static final int DIR_DOWN  = 1;
	 public static final int DIR_LEFT  = 2;
	 public static final int DIR_UP    = 3;
	 public static final int NUM_DIRS  = 4;

	 public static final int TEXT_OFF_X = 20;
	 public static final int TEXT_OFF_Y = 20;

	 /* Graphic Element types */
	 /* TODO : se puede mejorar? */

	 public static final String TYPE_RESISTOR  = "Resistor";
	 public static final String TYPE_INDUCTOR  = "Inductor";	
	 public static final String TYPE_CAPACITOR = "Capacitor";
	 public static final String TYPE_VOLT_SRC  = "VoltSrc";
	 public static final String TYPE_CURR_SRC  = "CurrSrc";

	 public static final String TYPE_SHORT_CIRC = "ShortCirc";
	 public static final String TYPE_GROUND = "Ground";


	 public static final int SIZE = 80;


	 /** Controller actions */
	 public static final int C_ACT_DRAW_TL_ELEM_R    = 1000;
	 public static final	int C_ACT_DRAW_TL_ELEM_L    = 1001;
	 public static final	int C_ACT_DRAW_TL_ELEM_C    = 1002;
	 public static final	int C_ACT_DRAW_TL_ELEM_VS   = 1003;
	 public static final int C_ACT_DRAW_TL_ELEM_CS   = 1004;
	 public static final int C_ACT_DRAW_TL_SHORTCIRC = 1006;
	 public static final	int C_ACT_DRAW_GROUND       = 1007;
	 public static final	int C_ACT_DELETE            = 1008;
	 public static final	int C_ACT_ROTATE            = 1009;
	 public static final	int C_ACT_PLOT              = 1010;
	 public static final	int C_ACT_CLEAR             = 1011;
	 public static final	int C_ACT_SOLVE_CIRC        = 1012;

	 public static final	int C_ACT_EXIT              = 2001;
	 public static final	int C_ACT_EXPORT_CIRC       = 2002;
	 public static final	int C_ACT_ABOUT             = 10001;

}
