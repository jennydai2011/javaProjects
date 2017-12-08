package datastructure.linkedlist;

import org.junit.Test;

public class LinkedListTest {
	
	@Test
	public void testAdd() {
		LinkedList linklist1 = new LinkedList();
		linklist1.append(12);
		linklist1.append(22);
		linklist1.append(32);
		linklist1.prepend(122);
		
		linklist1.tranverse();
	}
}
