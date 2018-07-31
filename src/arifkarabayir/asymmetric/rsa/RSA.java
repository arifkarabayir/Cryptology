package arifkarabayir.asymmetric.rsa;

import java.math.BigInteger;
import java.util.Random;

import arifkarabayir.general.MathBackground;

public class RSA {
	private BigInteger p;
	private BigInteger q;
	private BigInteger phi, e, d;
	private BigInteger N;
	
	public RSA(BigInteger max) {
		this.q = MathBackground.GeneratePrime(max);
		this.p = MathBackground.GeneratePrime(max);
		
		this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		this.N = p.multiply(q);
		
		generateComponents();
	
	}
	
	private void generateComponents() {
		BigInteger e, arr[];
		Random rand = new Random();
		
		do {
		    e = new BigInteger(this.phi.bitLength(), rand);
		    arr = MathBackground.ExtendedEuclideanAlgorithm(e, this.phi);
		    
		} while ( e.compareTo(this.phi) >= 0 || e.compareTo(BigInteger.TEN) < 0 || arr[0].compareTo(BigInteger.ONE) != 0 || arr[1].compareTo(BigInteger.ZERO) < 0 );
		
		this.e = e;
		this.d = arr[1];
	}
	
	public Key getPublicKey() {
		return new Key(this.N, this.e);
	}
	
	public Key getPrivateKey() {
		return new Key(this.N, this.d);
	}
	
	public static BigInteger encrypt(BigInteger message, Key key) {
		return message.modPow(key.key, key.N);
	}
	
	public static BigInteger decrypt(BigInteger cipher, Key key) {
		return cipher.modPow(key.key, key.N);
	}
	
}
