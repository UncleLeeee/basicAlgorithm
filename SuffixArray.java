package basicAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 
 * @ClassName:     SuffixArray.java
 * @Description:   Implement SuffixArray using normal sort algorithms, I'll using radix sorting later.
 * 
 * @author         UncleLee
 * @version        V1.0  
 * @Date           2014-7-9 ÏÂÎç09:26:21
 */
public class SuffixArray {
	public int N;
	public String[] suffix;
	
	public SuffixArray(String text) {
		this.N = text.length();
		this.suffix = new String[N];
		for(int i=0;i<N;i++)
			this.suffix[i] = text.substring(i);
		Arrays.sort(suffix);
	}
	/**
	 * 
	 * @Title:        index 
	 * @Description:  get the substring index of the selected string.  
	 * @param:        @param i
	 * @param:        @return    
	 * @return:       int    
	 * @throws 
	 */
	public int index(int i){
		return N - suffix[i].length();
	}
	/**
	 * 
	 * @Title:        select 
	 * @Description:  get the ith string of the sorted suffix array. 
	 * @param:        @param i
	 * @param:        @return    
	 * @return:       String    
	 * @throws 
	 */
	public String select(int i){
		return suffix[i];
	}
	/**
	 * 
	 * @Title:        lcp 
	 * @Description:  get the length of the lcp of ith & (i-1)th substring.
	 * @param:        @param i
	 * @param:        @return    
	 * @return:       int    
	 * @throws 
	 */
	public int lcp(int i){
		return lcp(suffix[i], suffix[i-1]);
	}
	
	private int lcp(String s1, String s2){
		int length = 0;
		int i = 0;
		int j = 0;
		while(i<s1.length()&&j<s2.length()){
			if(s1.charAt(i)!=s2.charAt(j)) return length;
			i++;
			j++;
			length++;
		}
		return length;
	}
	/**
	 * 
	 * @Title:        rank 
	 * @Description:  get the number of string which is less than the key string. 
	 * @param:        @param key
	 * @param:        @return    
	 * @return:       int    
	 * @throws 
	 */
	public int rank(String key){
		int lo = 0;
		int hi = N - 1;
		while(lo<=hi){
			int mid = (lo+hi)/2;
			if(key.compareTo(suffix[mid])<0)
				hi = mid-1;
			else if(key.compareTo(suffix[mid])>0)
				lo = mid+1;
			else return mid;
		}
		return lo;
	}
	
	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\UncleLee\\Downloads\\tinyTale.txt";
		File input = new File(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
		StringBuilder sb = new StringBuilder();
		char[] buffer = new char[1024];
		int curr = 0;
		while((curr = reader.read(buffer))!=-1){
			sb.append(buffer, 0, curr);
		}
		String in = sb.toString().trim();
		System.out.println(in.length());
		System.out.println(in);
		SuffixArray sa = new SuffixArray(in);
		String res = "";
		int maxLength = 0;
		for(int i=1;i<in.length();i++){
			int l = sa.lcp(i);
			if(l>maxLength){
				maxLength = l;
				res = sa.select(i).substring(0,l);
			}
		}
		System.out.println(res);
	}
}
