package org.sj.tools.sycan.circedit;

import java.awt.Button;
import java.awt.MenuItem;

public interface Controller {

	 public void assign(Button btn, int action_code);
	 public void assign(MenuItem mi, int action_code);
	 public int getAction(Object obj) throws Exception;

}
	 
