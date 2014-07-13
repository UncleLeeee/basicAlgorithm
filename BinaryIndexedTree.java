package basicAlgorithm;
/**
 * 
 * @ClassName:     BinaryIndexedTree.java
 * @Description:   Implement basic BIT data structure.
 * 
 * @author         UncleLee
 * @version        V1.0  
 * @Date           2014-7-13 ÏÂÎç09:42:57
 */
public class BinaryIndexedTree {
	private int[] treeArray;
	private int N;
	
	private int lowbit(int x){
		return x&(-x);
	}
	/**
	 * @Description:   Index from 1 to N.
	 * @param:         N
	 */
	public BinaryIndexedTree(int N) {
		this.N = N;
		this.treeArray = new int[N+1];
	}
	/**
	 * 
	 * @Title:        sum 
	 * @Description:  Get sum from 1 to x. 
	 * @param:        @param x
	 * @param:        @return    
	 * @return:       int    
	 * @throws 
	 */
	public int sum(int x){
		int res = 0;
		while(x>0){
			res += treeArray[x];
			x -= lowbit(x);
		}
		return res;
	}
	/**
	 * 
	 * @Title:        add 
	 * @Description:  Add val to index x. 
	 * @param:        @param x
	 * @param:        @param val    
	 * @return:       void    
	 * @throws 
	 */
	public void add(int x, int val){
		while(x<=N){
			treeArray[x] += val;
			x += lowbit(x);
		}
	}
}
