package org.sj.utils.math.symbol;

import java.util.Hashtable;
import org.sj.utils.math.complex.Complex;

/**
 * Valores de variables independientes
 */
public class Context {

	 Hashtable<String, Complex> mapa;

	 public Context()
	 {
		  mapa = new Hashtable<String, Complex>();
	 }

	 public void setValue(String key, double re)
	 {
		  mapa.put(key, new Complex(re, 0));
	 }

	 public void setValue(String key, double re, double im)
	 {
		  mapa.put(key, new Complex(re, im));
	 }

	 public void setValue(String key, Complex z)
	 {
		  mapa.put(key, z);
	 }

	 public Complex getValue(String key) {
	 
		  Complex z = mapa.get(key);
		  if(z == null) {
				return new Complex();
		  }
		  return z;
	 }

	 
}