package fx.view;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableTest {

	@Test
	public void test() {

		SimpleTable<String> t = new SimpleTable<String>();
		t.addColumn("a", s->""+s.charAt(0));
		t.addColumn("b", s->""+s.charAt(1));
		t.addColumn("c", s->""+s.charAt(2));
		t.addRow("123");
		t.addRow("456");
		t.addRow("789");
		
		t.store(System.out);
		assertTrue(true);
	}

}
