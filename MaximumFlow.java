package basicAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MaximumFlow {
	class FlowEdge{
		public int v;
		public int w;
		public int flow;
		public int capacity;
		
		public FlowEdge(int v, int w, int f, int c) {
			this.v = v;
			this.w = w;
			this.flow = f;
			this.capacity = c;
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
	
	@SuppressWarnings("unchecked")
	public void readGraph() throws Exception{
		Reader.init(System.in);
		N = Reader.nextInt();
		M = Reader.nextInt();
		S = Reader.nextInt();
		T = Reader.nextInt();
		this.adjList = new ArrayList[N];
		for(int i=0;i<N;i++)
			this.adjList[i] = new ArrayList<FlowEdge>();
		this.edgeTo = new FlowEdge[N];
		this.edgeTo[S] = new FlowEdge(-1,S,0,0);
		for(int i=0;i<M;i++){
			FlowEdge curr = new FlowEdge(Reader.nextInt(),Reader.nextInt(),0,Reader.nextInt());
			this.adjList[curr.v].add(curr);
			this.adjList[curr.w].add(curr);
		}
	}
	
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
	public void FordFulkerson(){
		maxFlows = 0;
		while(hasResidualPath()){
			int bottle = Integer.MAX_VALUE;
			for(int p=T;p!=-1;p=edgeTo[p].other(p)){
				FlowEdge curr = edgeTo[p];
				int f = curr.residualTo(p);
				if(f<bottle)
					bottle = f;
			}
			for(int p=T;p!=-1;p=edgeTo[p].other(p)){
				FlowEdge curr = edgeTo[p];
				curr.addResidualTo(p, bottle);
			}
			maxFlows += bottle;
		}
	}
	
}
