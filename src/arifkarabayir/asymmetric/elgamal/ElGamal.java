package arifkarabayir.asymmetric.elgamal;

import java.math.BigInteger;
import java.util.Random;

import arifkarabayir.general.MathBackground;

public class ElGamal {
	
	private BigInteger prime;
	private BigInteger g; // must be primitive element of prime
	private BigInteger priviteKey;
	private BigInteger publicKey;
	private BigInteger rand_k;
	
	public ElGamal(BigInteger max) {		
		Random rand = new Random();
		this.prime = MathBackground.GeneratePrime(max);
		this.g = generatePirimitiveOfPrime(this.prime);
		this.priviteKey = generatePrivateKey(this.prime);
		this.publicKey = computePublicKey();
		
		do {
		    this.rand_k = new BigInteger(this.prime.bitLength(), rand);
		} while ( this.rand_k.compareTo(this.prime) >= 0 || this.rand_k.compareTo(BigInteger.TEN) < 0);
	}
	
	private BigInteger generatePirimitiveOfPrime(BigInteger prime) {
		BigInteger g, arr[];
		Random rand = new Random();
		
		do {
		    g = new BigInteger(prime.bitLength(), rand);
		    arr = MathBackground.ExtendedEuclideanAlgorithm(g, prime);
		    
		} while ( g.compareTo(prime) >= 0 || g.compareTo(BigInteger.TEN) < 0 || arr[0].compareTo(BigInteger.ONE) != 0 );
		
		return g;
	}
	
	private BigInteger generatePrivateKey(BigInteger prime) {
		BigInteger key;
		Random rand = new Random();
		
		do {
		    key = new BigInteger(prime.bitLength(), rand);
		} while ( key.compareTo(prime) >= 0 || key.compareTo(BigInteger.TEN) < 0);
		
		return key;
	}
	
	private BigInteger computePublicKey() {
		return this.g.modPow(this.priviteKey, this.prime);
	}
	
	public static Cipher encrypt(BigInteger message, Key publicKey) {
		Cipher cipher = new Cipher();
		BigInteger temp, temp2;
		
		temp = publicKey.key.modPow(publicKey.k, publicKey.prime);
		temp2 = temp.multiply(message);
		temp2 = temp2.mod(publicKey.prime);
		
		cipher.C1 = publicKey.g.modPow(publicKey.k, publicKey.prime);
		cipher.C2 = temp2;
		
		return cipher;
	}
	
	public static BigInteger decrypt(Cipher cipher, Key privateKey) {
		BigInteger message;
		BigInteger temp;
		
		temp = cipher.C1.modPow(privateKey.key, privateKey.prime);
		message = temp.modInverse(privateKey.prime).multiply(cipher.C2).mod(privateKey.prime);
		return message;
	}
	
	public Key getPublicKey() {
		Key key = new Key();
		key.key = publicKey;
		key.k = this.rand_k;
		key.prime = this.prime;
		key.g = this.g;
		return key;
	}
	
	public Key getPrivateKey() {
		Key key = new Key();
		key.prime = this.prime;
		key.key = this.priviteKey;
		key.k = this.rand_k;
		
		return key;
	}
	
}
