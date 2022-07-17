package org.sj.tools.sycan.circedit;

import java.awt.Canvas;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public abstract class View extends Canvas implements MessageReceiver {
	public static final int MSG_REPAINT = 0;
	public static final int MSG_SET_CAPTION = 1;
	
	private MessageReceiver document;
	private String caption = null;

	
	public View() {
	}
	
	public View(int width, int height) {
		setSize(width, height);
	}
	
	public MessageReceiver getDoc() {
		return document;
	}
	public void setDoc(MessageReceiver o) {
		document = o;
	}
	
	public void paint(Graphics g) {
		Rectangle r = getBounds();
		
		onDraw(g, r);
		g.setColor(Color.BLACK);
		if(caption != null) g.drawString(caption, 10, 10);
		g.drawRect(0, 0, r.width-1, r.height-1);
	}
	
	public final int sendMessage(int msgIndex, Object obj) {
		switch(msgIndex) {
		case MSG_REPAINT:
			repaint();
			break;
		case MSG_SET_CAPTION:
			if(!(obj instanceof String)) return -1;
			caption = (String) obj;
			break;
		default:
			return processMessage(msgIndex, obj);
		}
		return msgIndex;
	}
	
	public void setCaption(String s) {
		caption = s;
	}
	
	public abstract int processMessage(int msgIndex, Object obj);


	public abstract void onDraw(Graphics g, Rectangle bounds);

}