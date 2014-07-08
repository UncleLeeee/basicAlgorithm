package basicAlgorithm;
/**
 * 
 * @ClassName:     Arithmetic.java
 * @Description:   Arithmetic Utils.
 * 
 * @author         UncleLee
 * @version        V1.0  
 * @Date           2014-7-8 обнГ09:09:37
 */
public class Arithmetic {
	/**
	 * 
	 * @Title:        gcd 
	 * @Description:  Calculate the greatest common divider of a and b. 
	 * @param:        @param a
	 * @param:        @param b
	 * @param:        @return    
	 * @return:       int    
	 * @throws
	 */
	public int gcd(int a, int b){
		if(a<b){
			a = a^b;
			b = a^b;
			a = a^b;
		}
		return b==0? a:gcd(b,a%b);
	}
	
	final long MOD = 1000000007;
	/**
	 * 
	 * @Title:        powUnderMOD 
	 * @Description:  Calculate POWER(n,k) under a max value:MOD.
	 * @param:        @param n
	 * @param:        @param k
	 * @param:        @return    
	 * @return:       long    
	 * @throws 
	 */
	public long powUnderMOD(long n, long k){
		long r = 1;
		while(k>0){
			if((k&1)==1)
				r = (r*n)%MOD;
			n = (n*n)%MOD;
			k = k>>1;
		}
		return r;
	}
}
