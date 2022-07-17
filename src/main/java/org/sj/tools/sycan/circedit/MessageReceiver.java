package org.sj.tools.sycan.circedit;

public interface MessageReceiver {
	public int sendMessage(int msgIndex, Object obj);
	public Object requestObject(int objType, int objIndex);
}