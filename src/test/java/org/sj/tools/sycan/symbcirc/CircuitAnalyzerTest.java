package org.sj.tools.sycan.symbcirc;

import java.util.Vector;

import org.sj.tools.sycan.AppTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class CircuitAnalyzerTest extends TestCase {
	
	public CircuitAnalyzerTest(String testName) {
		super(testName);
	}
	
    public static Test suite()
    {
        return new TestSuite( CircuitAnalyzerTest.class );
    }

	
	public void testingAddsUnknownCurr() {
		UnknownCurrSrc ucs = new UnknownCurrSrc(1,0,"UCS");
		assertTrue(CircuitAnalyzer.addsUnknownCurrent(ucs));
	}
	
	public void testingAddsVoltageConstr() {
		VirtualShortCirc vsc = new VirtualShortCirc(1,0);
		assertTrue(CircuitAnalyzer.addsVoltageConstraint(vsc));
		VoltSrc vs = new VoltSrc(2,3,1.0f);
		assertTrue(CircuitAnalyzer.addsVoltageConstraint(vs));

	}

	
	public void testingCountUnknownCurr() {
		Vector<Element> elems = new Vector<Element>();
		VoltSrc vs = new VoltSrc(2,3,1.0f);
		UnknownCurrSrc ucs = new UnknownCurrSrc(1,0,"UCS");
		elems.add(vs);
		elems.add(ucs);
		Circuit c = new Circuit(elems);
		
		assertEquals("Count Unk. Curr", 2, CircuitAnalyzer.countUnknownCurr(c));
		
	}

	public void testingCountVoltageConstr() {
		Vector<Element> elems = new Vector<Element>();
		VoltSrc vs = new VoltSrc(2,3,1.0f);
		VirtualShortCirc vsc = new VirtualShortCirc(1,0);
		elems.add(vs);
		elems.add(vsc);
		Circuit c = new Circuit(elems);
		
		assertEquals("Count Volt. Constr.", 2, CircuitAnalyzer.countVoltageConstr(c));
		
	}

	

}
