package arifkarabayir.asymmetric.elgamal;

import java.math.BigInteger;

public class Cipher {
	public BigInteger C1;
	public BigInteger C2;
	
	public Cipher(BigInteger C1, BigInteger C2) {
		this.C1 = C1;
		this.C2 = C2;
	}
	
	public Cipher() {
		
	}
}
