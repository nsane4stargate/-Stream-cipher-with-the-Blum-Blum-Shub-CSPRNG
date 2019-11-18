import java.math.BigInteger;
import java.util.List;
import java.io.*;

public class driver {

	public static void main(String[] args) throws IOException {
		
		// Get user plain text input
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter plaintext: ");
		
		String plaintext_bits = read.readLine();
		
		
		/* Number of seeds to be generated will be the 
		 * length of the incoming data from the user */
		
		int num_of_seeds = plaintext_bits.length();
		
		// Strings to store large numbers for the encryption
		String p = "24672462467892469787";
		String q = "396736894567834589803";
		
		// Convert String p and q to BigInteger dataTypes
		BigInteger big_p = new BigInteger(p);
		BigInteger big_q = new BigInteger(q);
		
		// Make a BBS class object
		BBSprng bbs = new BBSprng(big_p,big_q,plaintext_bits);
		
		bbs.makeSeeds(num_of_seeds);
		
		// Variable to hold object's list of generated seeds
		List<String> list = bbs.getSeeds();
		
		// Print off list of seeds
		int j = 0;
		for(String seed: list) {
			System.out.print("x"+j+" = ");
			System.out.println(seed);	
			j++; 
		}
		
		/* Find the least significant bit for each 
		 * seed to generate the cipher text bits */
		bbs.findLeastSigBits(bbs.getSeeds(), num_of_seeds - 1);
		
		bbs.createCipherText();
		
		// Loop through bits and print in pairs of 4
		int count = 0;
		System.out.print("CipherText Bits: ");
		char[] string = bbs.getCipherTextBits().toCharArray();
		for(char bit : string) {
			if(count > 3) {
				System.out.print(" ");
				count = 0;
			}
			System.out.print(bit);
			count ++;
		}
	} // End of main
}
