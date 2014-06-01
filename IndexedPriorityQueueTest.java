package basicAlgorithm;

import static org.junit.Assert.*;

import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class IndexedPriorityQueueTest {
	IndexedPriorityQueue<Integer> instance;
	@Before
	public void setUp() throws Exception {
		this.instance = new IndexedPriorityQueue<Integer>();
	}

	@Test
	public void testInsert() {
		int size = 10000;
		int range = 10000;
		Random r = new Random();
		int[] test = new int[size];
		for(int i=0;i<size;i++)
			test[i] = r.nextInt(range);
		long start = System.currentTimeMillis();
		for(int i=0;i<size;i++)
			instance.insert(i, test[i]);
		long end = System.currentTimeMillis();
		System.out.println(String.format("(Indexed priority queue)Inserting cost : %d ms", end-start));
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		start = System.currentTimeMillis();
		for(int i=0;i<size;i++)
			queue.offer(test[i]);
		end = System.currentTimeMillis();
		System.out.println(String.format("(Priority queue)Inserting cost : %d ms", end-start));
		int[] test2 = new int[size];
		for(int i=0;i<size;i++)
			test2[i] = r.nextInt(range);
		start = System.currentTimeMillis();
		for(int i=0;i<size;i++)
			instance.change(i, test[2]);
		end = System.currentTimeMillis();
		System.out.println(String.format("(Indexed priority queue)Changing cost : %d ms", end-start));
		int[] res = new int[size];
		start = System.currentTimeMillis();
		for(int i=0;i<size;i++)
			res[i] = instance.delMin();
		end = System.currentTimeMillis();
		System.out.println(String.format("(Indexed priority queue)Deleting cost : %d ms", end-start));
		for(int i=1;i<size;i++)
			if(res[i]<res[i-1]) System.out.println(String.format("Wrong Info: i-1: %d, i: %d", res[i-1],res[i]));
	}
}
