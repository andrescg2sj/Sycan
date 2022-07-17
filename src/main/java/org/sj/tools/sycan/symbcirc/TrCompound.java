package org.sj.tools.sycan.symbcirc;

import java.util.Vector;

/**
 * Las transformaciones deben haber sido calculadas previamente
 */
public class TrCompound extends NodeTransform {

	 /** transformaciones individuales */
	 Vector<NodeTransform> steps;

	 public TrCompound(NodeTransform t)
	 {
		  steps = new Vector<NodeTransform>();
		  steps.add(t);
	 }

	 public void add(NodeTransform t)
	 {
		  steps.add(t);
	 }

	 public void calc(Circuit c) {
		  circ = c;

		  branch_trans = new int[circ.numElements()];
		  node_trans = new int[circ.getMax() + 1];
		  for(int i = 0; i < branch_trans.length; i++) {
				branch_trans[i] = i;
		  }
		  for(int i = 0; i < node_trans.length; i++) {
				node_trans[i] = i;
		  }

		  Circuit cr = circ;
		  for(int j = 0; j < steps.size(); j++) {
				//System.out.println("Iteracion: "+ j + " b: "+branch_trans.length+
				//", n:"+node_trans.length+";-----------");

				NodeTransform t = steps.get(j);
				t.calc(cr);
				cr = t.getResult();

				/* aplicar transformaciones acumulativas */
				for(int i = 0; i < branch_trans.length; i++) {
					 ////System.out.println("branch[" + i+"] = "+branch_trans[i]);;
					 if(branch_trans[i] != -1) {
						  branch_trans[i] = t.transBranch(branch_trans[i]);
						  ////System.out.println("  -> "+branch_trans[i]);
					 }
				}
				for(int i = 0; i < node_trans.length; i++) {
					 ////System.out.println("node[" + i+"] = "+node_trans[i]);
					 if(node_trans[i] != -1) {
						  node_trans[i] = t.transNode(node_trans[i]);
						  ////System.out.println("  -> "+node_trans[i]);
					 }
				}

		  }

		  result = cr;
	 }

	 
}