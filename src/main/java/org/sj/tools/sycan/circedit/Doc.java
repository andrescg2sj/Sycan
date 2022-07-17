package org.sj.tools.sycan.circedit;

import java.util.Iterator;

public abstract class Doc implements MessageReceiver {

	private MessageReceiver view;
	
	public MessageReceiver getView() {
		return view;
	}
	
	public void setView(MessageReceiver o) {
		view = o;
	}
	
	public void UpdateView() {
		view.sendMessage(View.MSG_REPAINT, null);
	}
		
	public abstract Iterator<GraphicElement> getIterator();
}