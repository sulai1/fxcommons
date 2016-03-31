package algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultimapTest {

	@Test
	public void test() {
		Multimap<Integer, String> map = new Multimap<Integer, String>(s -> (int)s.charAt(0)	); 	// key is the first char of the string cast to int
		
		//add three items with different keys 
		map.put("aa");
		map.put("bb");
		map.put("cc");
		
		//add three items all with key s
		map.put("sa");
		map.put("sb");
		map.put("sc");

		int key = (int)'s';
		
		//check data
		assertEquals(3,map.get(key).getValues().size());
		assertEquals(6,map.size());
		assertEquals(true,map.contains("sa"));
		
		//test remove by value
		String val = "sa";
		SetNode<Integer, String> remove = map.remove(val);
		assertEquals(2,remove.getValues().size());
		assertEquals(5,map.size());
		assertEquals(false,map.contains(val));
		assertEquals(null,map.remove(val));
		
		//test remove by key
		assertEquals(true, map.removeKey(key)!=null);
		assertEquals(false, map.contains("sb"));
		assertEquals(3, map.size());
		
	}
}
