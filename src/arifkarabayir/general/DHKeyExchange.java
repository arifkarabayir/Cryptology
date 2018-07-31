package arifkarabayir.general;

import java.math.BigInteger;
import java.util.Random;

public class DHKeyExchange {
	
	private BigInteger p, g;
	
	public DHKeyExchange() {
		generateParameters();
	}
	
	public void generatePrime() {
		BigInteger r;
		BigInteger n = new BigInteger("10000000");
				
		Random rand = new Random();
		
		while(true) {
			do {
			    r = new BigInteger(n.bitLength(), rand);
			} while (r.compareTo(n) >= 0 || r.compareTo(BigInteger.TEN) < 0);
			
			if (MathBackground.PrimalityTesting(r) && r.compareTo(new BigInteger("100")) > 0) {
				break;
			}
		}
		this.p = r;
	}
	
	public BigInteger generateKey(BigInteger a) {		
		return MathBackground.modpow(this.g, a, this.p);
	}
	
	public void generateParameters() {
		BigInteger r;
		BigInteger n;
		Random rand = new Random();
		
		generatePrime();
		n = this.p;
		
		while(true) {
			do {
			    r = new BigInteger(n.bitLength(), rand);
			} while (r.compareTo(n) >= 0 || r.compareTo(BigInteger.TEN) < 0);
			
			if (MathBackground.PrimalityTesting(r)) {
				break;
			}
		}
		
		this.g = r;
	}
	
	public BigInteger calculateSharedKey(BigInteger publicKey, BigInteger a) {
		return MathBackground.modpow(publicKey, a, p);
	}
}
