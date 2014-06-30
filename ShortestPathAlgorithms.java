package basicAlgorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 * 
 * @ClassName:     ShortestPathAlgorithms.java
 * @Description:   Implements 3 shortest path algorithms for contest purpose.
 * 				   (Dijkstra, Bellmen-Ford and Floyd)
 * @author         UncleLee
 * @version        V1.0  
 * @Date           2014-6-30 ÉÏÎç08:22:11
 */
public class ShortestPathAlgorithms {
	
	private int[] first;
	private int[] u;
	private int[] v;
	private int[] w;
	private int[] next;
	
	private int[] pathTo;
	private int[] distTo;
	
	/**
	 * 
	 * @Title:        readGraph 
	 * @Description:  Construct the graph.
	 * @param:        @throws Exception    
	 * @return:       void    
	 * @throws 
	 * @author        UncleLee
	 * @Date          2014-6-30 ÉÏÎç08:23:32
	 */
	public void readGraph() throws Exception{
		Reader.init(System.in);
		int n = Reader.nextInt();
		int m = Reader.nextInt();
		
		this.first = new int[n];
		this.distTo = new int[n];
		
		this.u = new int[m];
		this.v = new int[m];
		this.w = new int[m];
		this.next = new int[m];
		this.pathTo = new int[m];
		for(int i=0;i<n;i++)
			first[i] = -1;
		for(int i=0;i<m;i++){
			u[i] = Reader.nextInt();
			v[i] = Reader.nextInt();
			w[i] = Reader.nextInt();
			next[i] = first[u[i]];
			first[u[i]] = i;
		}
	}
	
	class Pair implements Comparable<Pair>{
		public int no;
		public int val;
		
		public Pair(int n, int v){
			no = n;
			val = v;
		}

		public int compareTo(Pair o) {
			return val<o.val?-1:1;
		}
	}
	/**
	 * 
	 * @Title:        Dijkstra 
	 * @Description:  Dijkstra algorithm code block. 
	 * @param:            
	 * @return:       void    
	 * @throws 
	 */
	public void Dijkstra(){
		int n = first.length;
		boolean[] marked = new boolean[n];
		for(int i=0;i<n;i++)
			distTo[i] = (i == 0?0 : Integer.MAX_VALUE);
		Queue<Pair> queue = new PriorityQueue<Pair>();
		queue.offer(new Pair(0,0));
		while(!queue.isEmpty()){
			Pair curr = queue.poll();
			if(marked[curr.no])
				continue;
			for(int e=first[curr.no];e!=-1;e=next[e]){
				if(distTo[v[e]]>distTo[curr.no] + w[e]){
					pathTo[v[e]] = curr.no;
					distTo[v[e]] = distTo[curr.no] + w[e];
					queue.offer(new Pair(v[e],distTo[v[e]]));
				}
			}
		}
	}
	/**
	 * 
	 * @Title:        BellmenFordI 
	 * @Description:  BellmenFord algorithm code block. 
	 * @param:            
	 * @return:       void    
	 * @throws 
	 */
	public void BellmenFordI(){
		int n = first.length;
		int m = u.length;
		
		for(int i=0;i<n;i++)
			distTo[i] = (i == 0? 0 : Integer.MAX_VALUE);
		for(int k=0;k<n-1;k++){
			for(int i=0;i<m;i++){
				if(distTo[u[i]]<Integer.MAX_VALUE && distTo[v[i]]>distTo[u[i]]+w[i]){
					distTo[v[i]] = distTo[u[i]]+w[i];
					pathTo[v[i]] = u[i];
				}
			}
		}
	}
	/**
	 * 
	 * @Title:        BellmenFordII 
	 * @Description:  Uses queue to implement BellmenFord algorithm. 
	 * @param:            
	 * @return:       void    
	 * @throws 
	 */
	public void BellmenFordII(){
		int n = first.length;
		boolean[] marked = new boolean[n];
		
		for(int i=0;i<n;i++)
			distTo[i] = (i == 0? 0 : Integer.MAX_VALUE);
		
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(0);
		while(!queue.isEmpty()){
			int curr = queue.poll();
			marked[curr] = false;
			for(int e=first[curr];e!=-1;e=next[e]){
				if(distTo[v[e]]>distTo[curr]+w[e]){
					distTo[v[e]] = distTo[curr]+w[e];
					if(!marked[v[e]]){
						marked[v[e]] = true;
						queue.offer(v[e]);
					}
				}
			}
		}
	}
	
	int[][] fd;
	/**
	 * 
	 * @Title:        Floyd 
	 * @Description:  Floyd algorithm code block. 
	 * @param:            
	 * @return:       void    
	 * @throws 
	 * @author        UncleLee
	 * @Date          2014-6-30 ÉÏÎç08:42:04
	 */
	public void Floyd(){
		int n = first.length;
		int m = u.length;
		this.fd = new int[n][n];
		for(int i=0;i<n;i++)
			Arrays.fill(fd[i], Integer.MAX_VALUE);
		for(int i=0;i<n;i++)
			fd[i][i] = 0;
		for(int i=0;i<m;i++)
			fd[u[i]][v[i]] = w[i];
		for(int k=0;k<n;k++){
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(fd[i][k]<Integer.MAX_VALUE && fd[k][j]<Integer.MAX_VALUE && fd[i][j] < fd[i][k] + fd[k][j])
						fd[i][j] = fd[i][k] + fd[k][j];
				}
			}
		}
	}
}
