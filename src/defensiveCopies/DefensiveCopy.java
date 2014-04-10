package defensiveCopies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DataStruct {
	List<String> ls = new ArrayList<String>();

	public void addS(String inString) {
		if (inString.isEmpty() == false)
			ls.add(inString);
	}

	public List<String> getList() {
		return Collections.unmodifiableList(ls);
	} // read only access
}

public class DefensiveCopy {

	// data structure must give its data up, but such that the reference will
	// allow access to the original data
	public static void main(String... strings) {
		List<String> ls = new ArrayList<String>();
		DataStruct ds = new DataStruct();

		ls = ds.getList();

		try {
			ls.add("test");
		} catch (UnsupportedOperationException e) {
			System.out.println("Can't change readonly list");
		}
		
		ds.addS("But can add from here");
		System.out.println(ds.ls);
	}
}
