package org.sj.tools.sycan.circedit;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

public class NameManager {

	 class NameRegister {
		  String name;
		  int count;

		  public NameRegister(String n) {
				name = n;
				count = 0;
		  }

		  public String getNewName() {
				String rval = name + count;
				count++;
				return rval;
		  }
	 }

	 NameRegister other;

	 HashMap<String, NameRegister> names;

	 public NameManager() {
		  names = new HashMap<String, NameRegister>();
		  other = new NameRegister("_unknown_");
	 }

	 public void register(String key, String name) {
		  NameRegister reg = new NameRegister(name);
		  names.put(key, reg);
		  
	 }

	 public String getName(String key) {
		  NameRegister reg = names.get(key);
		  if(reg == null) {
				return other.getNewName();
		  } else {
				return reg.getNewName();
		  }
	 }

	 public void reset()	{
		  other.count = 0;

		  Collection col = names.values();
		  Iterator it = col.iterator();
		  while(it.hasNext()) {
				NameRegister reg = (NameRegister) it.next();
				reg.count = 0;
		  }
	 }


	 
}