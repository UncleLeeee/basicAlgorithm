package basicAlgorithm;

import java.util.Arrays;
/**
 * 
 * @ClassName:     SegmentTree.java
 * @Description:   Implement segment tree.
 * 
 * @author         UncleLee
 * @version        V1.0  
 * @Date           2014-7-6 ÏÂÎç08:02:00
 */
public class SegmentTree {
	public int N;
	public int R;
	public int[] rangeMin;
	public int[] rangeMax;
	public int[] rangeSum;
	public int[] addV;
	
	/**
	 * 
	 * @param n
	 */
	public SegmentTree(int n) {
		int i=0;
		while((1<<i)<n)
			i++;
		this.N = (1<<i);
		this.R = (1<<(i+1))-1;
		this.rangeMin = new int[this.R];
		this.rangeMax = new int[this.R];
		this.rangeSum = new int[this.R];
		this.addV = new int[this.R];
		Arrays.fill(rangeMin, Integer.MAX_VALUE);
		Arrays.fill(rangeMax, Integer.MIN_VALUE);
	}
	
	/**
	 * 
	 * @Title:        buildTree 
	 * @Description:  Build the segment tree through an input array. 
	 * @param:        @param a    
	 * @return:       void    
	 * @throws 
	 */
	public void buildTree(int[] a){
		buildTree(0, 1, N, a);
	}
	
	private void buildTree(int o, int L, int R, int[] a){
		if(L>a.length)
			return;
		if(L==R){
			rangeMin[o] = a[L-1];
			return;
		}
		int M = (L+R)/2;
		buildTree(o*2+1, L, M, a);
		buildTree(o*2+2, M+1, R, a);
		rangeMin[o] = Math.min(rangeMin[o*2+1], rangeMin[o*2+2]);
	}
	/**
	 * 
	 * @Title:        queryMin 
	 * @Description:  Query the min value in [L,R], index begins with 1. 
	 * @param:        @param L
	 * @param:        @param R
	 * @param:        @return    
	 * @return:       int    
	 * @throws 
	 */
	public int queryMin(int L, int R){
		return queryMin(0, 1, N, L, R);
	}
	
	private int queryMin(int o, int L, int R, int QL, int QR){
		int M = (L+R)/2;
		if(QL<=L&&QR>=R)
			return rangeMin[o];
		int min = Integer.MAX_VALUE;
		if(QL<=M)
			min = Math.min(min, queryMin(o*2+1, L, M, QL, QR));
		if(QR>=M+1)
			min = Math.min(min, queryMin(o*2+2, M+1, R, QL, QR));
		return min;
	}
	/**
	 * 
	 * @Title:        update 
	 * @Description:  Modify the value of the specified index, index begins with 1.  
	 * @param:        @param index
	 * @param:        @param val    
	 * @return:       void    
	 * @throws 
	 */
	public void update(int index, int val){
		update(0, 1, N, index, val);
	}
	
	private void update(int o, int L, int R, int index, int val){
		if(L == R){
			rangeMin[o] = val;
			return;
		}
		int M = (L+R)/2;
		if(index<=M)
			update(o*2+1, L, M, index, val);
		else
			update(o*2+2, M+1, R, index, val);
		rangeMin[o] = Math.min(rangeMin[o*2+1], rangeMin[o*2+2]);
	}
	
	public static void main(String[] args) {
		SegmentTree st = new SegmentTree(7);
		int[] test = {1,2,-3,3,9,2,7};
		st.buildTree(test);
		System.out.println(st.queryMin(4, 4));
		st.update(3, 10);
		System.out.println(st.queryMin(1, 3));
	}
}
