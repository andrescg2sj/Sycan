package org.sj.tools.sycan.symbcirc.parser;

import org.sj.tools.sycan.symbcirc.Circuit;
import org.sj.tools.sycan.symbcirc.Element;
import org.sj.tools.sycan.symbcirc.Resistor;
import org.sj.tools.sycan.symbcirc.Capacitor;
import org.sj.tools.sycan.symbcirc.Inductor;
import org.sj.tools.sycan.symbcirc.CurrSrc;
import org.sj.tools.sycan.symbcirc.VoltSrc;
import org.sj.tools.sycan.symbcirc.VCCS;
import org.sj.tools.sycan.symbcirc.VCVS;
import org.sj.tools.sycan.symbcirc.CCVS;
import org.sj.tools.sycan.symbcirc.CCCS;
import org.sj.tools.sycan.symbcirc.ShortCirc;

import org.sj.utils.math.complex.Complex;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Vector;
import java.util.NoSuchElementException;

public class Parser {

	 private static Parser instance = null;

	 protected Parser() {
		  ;
	 }

	 public static Parser getInstance() {
		  if(instance == null) {
				instance = new Parser();
		  }
		  return instance;
	 }


	 
	 public Circuit buildCircuit(String filename) throws Exception {

		  FileReader fr = new FileReader(filename);
		  BufferedReader br = new BufferedReader(fr);

		  Vector<Element> elems = new Vector<Element>();

		  int numLine = 0;
		  while(br.ready()) {
				String line = br.readLine().trim();
				numLine++;

				if((line.length() == 0) || line.startsWith("#")) {
					 continue;
				}

			  

				String[] tokens = line.split("\\s+");

				/* tipo de elemento */
				String type = null, name = null;

				/* nodos */
				int A=-1, B=-1; 

				if(tokens.length < 3) {
					 throw new Exception(filename+":"+numLine+
												": Faltan parmetros.");
				}

				if(tokens.length > 6) {
					 throw new Exception(filename+":"+numLine+
												": Demasiados parmetros");
				}

				type = tokens[0];
				A = Integer.parseInt(tokens[1]);
				B = Integer.parseInt(tokens[2]);

				if((A < 0) || (B < 0)) {
					 throw new Exception(filename+":"+numLine+
												": ndices de nodo invlidos");
				}
				

				if(tokens.length >= 4) {
					 name = tokens[3];
				} 

				float defValue = 1; 
				if(tokens.length >= 5) {
					//TODO
					 //defValue = (float) Double.parseDouble(tokens[4]);
				} 

				float defArg = 0; 
				if(tokens.length == 6) {
					//TODO
					 //defArg = (float) Double.parseDouble(tokens[5]);
				} 

				Complex z = Complex.polar(defValue, defArg);

				Element elem;
				if(type.equals("R")) {
					 if(tokens.length < 4) {
						  elem = new Resistor(A, B, defValue);
					 } else {
						  name = tokens[3];
						  elem = new Resistor(A, B, defValue, name);
					 }
				} else if(type.equals("L")) {
					 if(tokens.length < 4) {
						  elem = new Inductor(A, B, defValue);
					 } else {
						  name = tokens[3];
						  elem = new Inductor(A, B, defValue, name);
					 }
				} else if(type.equals("C")) {
					 if(tokens.length < 4) {
						  elem = new Capacitor(A, B, defValue);
					 } else {
						  name = tokens[3];
						  elem = new Capacitor(A, B, defValue, name);
					 }
				} else if(type.equals("VS")) {
					 if(tokens.length < 4) {
						  elem = new VoltSrc(A, B, z);
					 } else {
						  name = tokens[3];
						  elem = new VoltSrc(A, B, z, name);
					 }
				} else if(type.equals("CS")) {
					 if(tokens.length < 4) {
						  elem = new CurrSrc(A, B, z);
					 } else {
						  name = tokens[3];
						  elem = new CurrSrc(A, B, z, name);
					 }
				} else if(type.equals("SC")) {
					 elem = new ShortCirc(A, B);
				} else if(type.equals("VCCS")) {
					 // VCCS A B X Y name
					 int X = Integer.parseInt(tokens[3]);
					 int Y = Integer.parseInt(tokens[4]);
					 name = tokens[5];
					 elem = new VCCS(A, B, name, X, Y);
				} else if(type.equals("VCVS")) {
					 // VCVS A B X Y name
					 int X = Integer.parseInt(tokens[3]);
					 int Y = Integer.parseInt(tokens[4]);
					 name = tokens[5];
					 elem = new VCVS(A, B, name, X, Y);
				} else if(type.equals("CCCS")) {
					 // VCCS A B X Y name
					 int branch = Integer.parseInt(tokens[3]);
 					 name = tokens[4];
					 elem = new CCCS(A, B, name, branch);
				} else if(type.equals("CCVS")) {
					 // VCCS A B X Y name
					 int branch = Integer.parseInt(tokens[3]);
 					 name = tokens[4];
					 elem = new CCVS(A, B, name, branch);
				} else {
					 throw new Exception(filename+":"+numLine+
												": Tipo no vlido: '"+ type+"'");
				}
				if(elem != null) {
					 elems.add(elem);
				}
		  }

		  return new Circuit(elems);
	 }
}
