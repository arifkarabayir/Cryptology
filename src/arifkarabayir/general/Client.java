package arifkarabayir.general;

import java.math.BigInteger;
import java.util.Random;

import arifkarabayir.asymmetric.elgamal.Cipher;
import arifkarabayir.asymmetric.elgamal.ElGamal;
import arifkarabayir.asymmetric.rsa.Key;
import arifkarabayir.asymmetric.rsa.RSA;

public class Client {
	private BigInteger a;
	private BigInteger publicKey;
	public BigInteger sharedKey;
	private BigInteger targetPublicKey;
	private Key targetRSAPublicKey;
	private arifkarabayir.asymmetric.elgamal.Key targetElGamalPublicKey;
	private arifkarabayir.asymmetric.elgamal.Key ElGamalPrivateKey;
	private RSA rsa;
	private ElGamal elgamal;
	
	public Client(String method) {
		if(method.equals("symmetric")) {
			BigInteger r;
			BigInteger n = new BigInteger("10000000");
			Random rand = new Random();		
	
			do {
			    r = new BigInteger(n.bitLength(), rand);
			} while (r.compareTo(n) >= 0 || r.compareTo(BigInteger.TEN) < 0);
			
			this.a = r;
		}
		
		if(method.equals("asymmetric")) {
			
		}
	}
	
	public void generateDHPublicKey(DHKeyExchange dh) {
		this.publicKey = dh.generateKey(this.a);
	}
	
	public BigInteger getPublicKey() {
		return this.publicKey;
	}
	
	public void receivePublicKey(Client cl) {
		this.targetPublicKey = cl.getPublicKey();
	}
	
	public void calculateDHSharedKey(DHKeyExchange dh) {
		this.sharedKey = dh.calculateSharedKey(this.targetPublicKey, this.a);
	}
	
	
	public void generateRSAKeys(BigInteger max) {
		this.rsa = new RSA(max);	
	}
	
	public void getPublicKey(Client cl) {
		this.targetRSAPublicKey = cl.rsa.getPublicKey();
	}
	
	public BigInteger encryptRSA(String message) {
		return RSA.encrypt(new BigInteger(message.getBytes()), this.targetRSAPublicKey);
	}
	
	public String decryptRSA(BigInteger cipher) {
		BigInteger m = RSA.decrypt(cipher, this.rsa.getPrivateKey());
		return new String(m.toByteArray());
		
	}
	
	
	public void generateElGamalKeys(BigInteger max) {
		this.elgamal = new ElGamal(max);	
	}
	
	public void getElGamalPublicKey(Client cl) {
		this.targetElGamalPublicKey = cl.elgamal.getPublicKey();
	}
	
	public Cipher encryptElGamal(String message) {
		return ElGamal.encrypt(new BigInteger(message.getBytes()), this.targetElGamalPublicKey);
	}
	
	public String decryptElGamal(Cipher cipher) {
		BigInteger m = ElGamal.decrypt(cipher, this.elgamal.getPrivateKey());
		return new String(m.toByteArray());
		
	}
	
}
