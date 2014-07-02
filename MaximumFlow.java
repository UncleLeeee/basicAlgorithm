package basicAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @ClassName:     MaximumFlow.java
 * @Description:   Implement Ford-Fulkerson algorithm for contest purpose;
 * 
 * @author         UncleLee
 * @version        V1.0  
 * @Date           2014-7-1 ÏÂÎç08:11:05
 */
public class MaximumFlow {
	class FlowEdge{
		public int v;
		public int w;
		public int flow;
		public int capacity;
		public int cost;
		
		public FlowEdge(int v, int w, int f, int c) {
			this.v = v;
			this.w = w;
			this.flow = f;
			this.capacity = c;
			this.cost = 0;
		}
		
		public FlowEdge(int v, int w, int f, int c, int co) {
			this.v = v;
			this.w = w;
			this.flow = f;
			this.capacity = c;
			this.cost = co;
		}
		
		public int other(int x){
			return x == v?w:v;
		}
		public int residualTo(int x){
			if(x == v)
				return flow;
			else
				return capacity - flow;
		}
		public void addResidualTo(int x, int delta){
			if(x == v)
				flow -= delta;
			else 
				flow += delta;
		}
	}
	
	public int N;
	public int M;
	public int S;
	public int T;
	public ArrayList<FlowEdge>[] adjList;
	public FlowEdge[] edgeTo;
	
	/**
	 * 
	 * @Title:        readGraph 
	 * @Description:  Construct the graph. 
	 * @param:        @throws Exception    
	 * @return:       void    
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public void readGraph() throws Exception{
		Reader.init(System.in);
		N = Reader.nextInt();
		M = Reader.nextInt();
		this.adjList = new ArrayList[N];
		for(int i=0;i<N;i++)
			this.adjList[i] = new ArrayList<FlowEdge>();
		this.edgeTo = new FlowEdge[N];
		for(int i=0;i<M;i++){
			FlowEdge curr = new FlowEdge(Reader.nextInt(),Reader.nextInt(),0,Reader.nextInt());
			this.adjList[curr.v].add(curr);
			this.adjList[curr.w].add(curr);
		}
	}
	
	/**
	 * 
	 * @Title:        hasResidualPath 
	 * @Description:  Check that if the network still has residual path. 
	 * @param:        @return    
	 * @return:       boolean    
	 * @throws 
	 */
	public boolean hasResidualPath(){
		boolean[] marked = new boolean[N];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(S);
		while(!q.isEmpty()){
			int curr = q.poll();
			for(FlowEdge e:adjList[curr]){
				int other = e.other(curr);
				if(marked[other])
					continue;
				if(e.residualTo(other)>0){
					marked[other] = true;
					q.add(other);
					edgeTo[other] = e;
				}
			}
		}
		if(marked[T])
			return true;
		return false;
	}
	
	public int maxFlows;
	/**
	 * 
	 * @Title:        FordFulkerson 
	 * @Description:  Run the algorithm to calculate maximum flow. 
	 * @param:        @param S
	 * @param:        @param T    
	 * @return:       void    
	 * @throws 
	 */
	public void FordFulkerson(int S, int T){
		this.S = S;
		this.T = T;
		maxFlows = 0;
		while(hasResidualPath()){
			int bottle = Integer.MAX_VALUE;
			for(int p=T;p!=S;p=edgeTo[p].other(p)){
				FlowEdge curr = edgeTo[p];
				int f = curr.residualTo(p);
				if(f<bottle)
					bottle = f;
			}
			for(int p=T;p!=S;p=edgeTo[p].other(p)){
				FlowEdge curr = edgeTo[p];
				curr.addResidualTo(p, bottle);
			}
			maxFlows += bottle;
		}
	}
	
	public int minCosts;
	public int[] costTo;
	public boolean hasResidualMinCostPath(){
		boolean[] marked = new boolean[N];
		Arrays.fill(costTo, Integer.MAX_VALUE);
		costTo[S] = 0;
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(S);
		while(!q.isEmpty()){
			int curr = q.poll();
			marked[curr] = false;
			for(FlowEdge e:adjList[curr]){
				int other = e.other(curr);
				if(e.residualTo(other)>0&&(costTo[other]>costTo[curr]+e.cost)){
					costTo[other] = costTo[curr]+e.cost;
					edgeTo[other] = e;
					if(marked[other])
						continue;
					marked[other] = true;
					q.offer(other);
				}
			}
		}
		if(costTo[T]<Integer.MAX_VALUE)
			return true;
		return false;
	}
	
	public void MinCostMaxFlow(int S, int T){
		this.S = S;
		this.T = T;
		maxFlows = 0;
		minCosts = 0;
		while(hasResidualMinCostPath()){
			int bottle = Integer.MAX_VALUE;
			for(int p=T;p!=S;p=edgeTo[p].other(p)){
				FlowEdge curr = edgeTo[p];
				int f = curr.residualTo(p);
				if(f<bottle)
					bottle = f;
			}
			for(int p=T;p!=S;p=edgeTo[p].other(p)){
				FlowEdge curr = edgeTo[p];
				curr.addResidualTo(p, bottle);
			}
			maxFlows += bottle;
			minCosts += bottle*costTo[T];
		}
	}
}
