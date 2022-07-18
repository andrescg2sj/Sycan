package org.sj.tools.sycan;

import org.sj.tools.sycan.symbcirc.Circuit;
import org.sj.tools.sycan.symbcirc.CircuitAnalyzer;
import org.sj.tools.sycan.symbcirc.parser.Parser;
import org.sj.utils.math.symbol.Expression;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void usage() {
		System.out.println("App <filename>");
	}
	
    public static void main( String[] args ) 
    {
    	if(args.length == 0) {
    		usage();
    		System.exit(0);
    	}
        String filename = args[0];
        Parser parser = Parser.getInstance();
        Circuit circ;
		try {
			circ = parser.buildCircuit(filename);
			System.out.println("--- Parsed circuit ---");
	        System.out.println(circ.toString());
	        CircuitAnalyzer ca = new CircuitAnalyzer();
	        ca.solve(circ);
	        int numNodes = ca.getNumNodes();
			System.out.println("--- Node voltages ---");
	        for(int i =0; i < numNodes; i++) {
	        	Expression exp = ca.getNodeVoltage(i);
	        	System.out.println("Node "+ i+": "+ exp.toString());	        	
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
