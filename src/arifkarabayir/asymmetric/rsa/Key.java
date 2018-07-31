package arifkarabayir.asymmetric.rsa;

import java.math.BigInteger;

public class Key{
	public BigInteger N;
	public BigInteger key;
	
	protected Key(BigInteger N, BigInteger key) {
		this.N = N;
		this.key = key;
	}
}

