import java.util.*;
import java.math.BigInteger;

public class BBSprng {
	
	private BigInteger p,q,n;
	private List<String> seeds = new ArrayList<String>();
	private String key_bits = "";
	private String plaintext_bits = "";
	private String ciphertext_bits = "";
	
	public BBSprng(BigInteger p0, BigInteger q0, String plaintext_bits) {
		this.plaintext_bits = plaintext_bits;
		p = p0; 
		q = q0;
		if(IsPrime(p,q) == true) {
			n = p.multiply(q);
		}
	}
	
	/********************************
	 *			Void functions 		*
	 ********************************/
	
	void findLeastSigBits(List<String>seeds, int index) {
		if(index < 0) {
			return;
		}
		BigInteger current_seed = new BigInteger(seeds.get(index));
		BigInteger divisor = new BigInteger("2");
		BigInteger even = new BigInteger("0");
		
		findLeastSigBits(seeds, index - 1);
		
		// Determine if the value of the seed is odd or even
		if(current_seed.mod(divisor).equals(even)) {
			setKeyBits(0);
		}else {
			setKeyBits(1);
		}
	}

	
	void createCipherText() {
		char[] key_bits = getKeyBits().toCharArray();
		char[] plaintext_bits = getPlainTextBits().toCharArray();
		
		int i;
		
		/* Loop through key_bit array and XOR with bits from
		 * the plaintext_bits array. Store result in the 
		 * private variable ciphertext_bits */
		
		for(i = 0; i < key_bits.length; i ++){
			int xor_bit = Integer.parseInt(String.valueOf(key_bits[i]))^
						Integer.parseInt(String.valueOf(plaintext_bits[i]));
			setCipherTextBits(xor_bit);
		}	
	}
	
	
	void makeSeeds(int num_of_seeds) {	
		// Choose a "x" that is relative prime to q*p 
		BigInteger x = new BigInteger("873245647888478349013");
				
		// Get the gcd(n,x)
		BigInteger relative_prime = gcd(getN(), x);
		
		// If the gcd(n,x) = 1, find remaining seeds
		if(isARelativePrime(relative_prime)) {
			setSeeds(num_of_seeds - 1, x);
		}else {
				System.out.println("x is not a relative prime of n = p*q");
		}
	}
	
	
	void setSeeds(int index, BigInteger seed){
		if(index < 0) {
			return;
		}
		// Calculate next seed
		BigInteger x = (seed.multiply(seed)).mod(getN());
		getSeeds().add(x.toString(10));
		setSeeds(index - 1,x);
	}
	
	
	void setKeyBits(int leastSigBit) {
		key_bits += String.valueOf(leastSigBit);
	}

	
	void setCipherTextBits(int result) {
		ciphertext_bits += result;
	}
	
	/********************************
	 * Functions with return values *
	 ********************************/
	
	BigInteger getQ(){
		return q;
	}

	
	BigInteger getP(){
		return p;
	}

	
	BigInteger getN(){
		return n;
	}

	
	BigInteger gcd(BigInteger num1, BigInteger num2) {
		BigInteger n = new BigInteger("0");
		if(num2.equals(n)) {
			return num1;
		}
		return gcd(num2,num1.mod(num2));
	}
	
	
	List<String> getSeeds(){
		return seeds;
	}
	
	
	boolean isARelativePrime(BigInteger result) {
		BigInteger n = new BigInteger("1");
		if(result.equals(n)) {
			return true;
		}
		return false;
	}
	
	
	boolean IsPrime(BigInteger p, BigInteger q){
		BigInteger m = new BigInteger("4");
		BigInteger n = new BigInteger("3");
		if ((p.mod(m)).compareTo(n) == 0) {
			if((q.mod(m)).equals(n)){
				return true;
			}
		}
		return false;
	}
	
	
	String getKeyBits() {
		return key_bits;
	}
	
	
	String getPlainTextBits() {
		return plaintext_bits;
	}
	
	
	String getCipherTextBits() {
		return ciphertext_bits;
	}
}
