package basicAlgorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IndexedPriorityQueue<T> {
	private int size;
	private Tuple<T>[] pq;
	public Map<Integer, Integer> table;
	private float factor = 0.75f;
	
	class Tuple<E>{
		public int index;
		public E val;
		public Tuple(int i, E t) {
			index = i;
			val = t;
		}

	}
	
	@SuppressWarnings("unchecked")
	public void change(int index, T val){
		int i = table.get(index);
		T old = pq[i].val;
		int res = ((Comparable<? super T>)old).compareTo(val);
		if(res == 0) return;
		pq[i] = new Tuple<T>(index, val);
		if(res<0)
			sink(i);
		else
			swim(i);
	}
	
	public boolean contains(int index){
		return table.containsKey(index);
	}
	
	@SuppressWarnings("unchecked")
	public IndexedPriorityQueue(){
		this.size = 0;
		this.pq =  new Tuple[100];
		this.table = new HashMap<Integer, Integer>();
	}
	
	private void exch(int i, int j){
		Tuple<T> temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
		table.put(pq[i].index, i);
		table.put(pq[j].index, j);
	}
	
	private void grow(){
		int old = pq.length;
		if((float)size/old < factor) return;
		pq = Arrays.copyOf(pq, old*2);
	}
	
	public int Size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size < 1;
	}
	
	public void insert(int index, T item){
		size++;
		Tuple<T> one = new Tuple<T>(index, item);
		pq[size] = one;
		table.put(index, size);
		grow();
		swim(size);
	}
	
	public T delMin(){
		T res = pq[1].val;
		table.remove(pq[1].index);
		pq[1] = pq[size--];
		table.put(pq[1].index, 1);
		sink(1);
		pq[size+1] = null;
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private void sink(int i){
		int now = i;
		int index = i<<1;
		while(index<=size){
			Comparable<? super T> curr = (Comparable<? super T>)pq[now].val;
			Comparable<? super T> t1 = (Comparable<? super T>) pq[index].val;
			int right = index+1;
			if(right<=size && t1.compareTo(pq[right].val)>0)
				index = right;
			if(curr.compareTo(pq[index].val)>0){
				exch(now, index);
				now = index;
				index = index<<1;
			}else break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void swim(int i){
		int now = i;
		int index = i>>1;
		while(index>=1){
			Comparable<? super T> curr = (Comparable<? super T>)pq[now].val;
			if(curr.compareTo(pq[index].val)<0){
				exch(now,index);
				now = index;
				index = index>>1;
			}else break;
		}
	}
}
