package org.sj.tools.sycan.circedit.draw;


import org.sj.tools.sycan.circedit.Const;
import java.awt.Graphics;
import java.util.Vector;


public class Drawing implements Drawable, Const {

	 private Vector<Drawable> elems;

  	 public Drawing() {
		  elems = new Vector<Drawable>();
	 }

  	 public Drawing(Vector<Drawable> d) {
		  elems = d;
	 }

	 public void add(Drawable d) {
		  elems.add(d);
	 }

	 public void draw(Graphics g, Base b) {
		  for(int i = 0; i < elems.size(); i++) {
				elems.get(i).draw(g, b);
		  }
	 }

}