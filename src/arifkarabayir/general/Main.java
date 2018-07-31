package arifkarabayir.general;

import arifkarabayir.asymmetric.elgamal.Cipher;
import arifkarabayir.asymmetric.elgamal.ElGamal;
import arifkarabayir.asymmetric.rsa.Key;
import arifkarabayir.asymmetric.rsa.RSA;
import arifkarabayir.symmetric.DES;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Scanner;


public class Main {
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		int n;
		System.out.println("Select:");
		System.out.println("1-) Symmetric\n2-) Asymmetric");
		while(true){
			n = reader.nextInt();
			
			if (n == 1) {
				symmetric();
				break;
			}
			else if (n == 2) {
				asymmetric();
				break;
			}
			else {
				System.out.println("Invalid Input");
			}
		}
		
		System.out.println("End");
	}
	
	public static void symmetric() {
		Scanner reader = new Scanner(System.in);
		DES des = new DES();
		DHKeyExchange dh = new DHKeyExchange();
		String str = "symmetric";
		String message = null;
		byte[] cipher;
		
		Client alice = new Client(str);
		Client bob = new Client(str);
		
		alice.generateDHPublicKey(dh);
		bob.generateDHPublicKey(dh);
		
		alice.receivePublicKey(bob);
		bob.receivePublicKey(alice);
		
		alice.calculateDHSharedKey(dh);
		bob.calculateDHSharedKey(dh);
		
		while(true){
			System.out.println("Enter a message to encrypt:");
			message = reader.nextLine();
			cipher = des.crypt(message.getBytes(), alice.sharedKey.toByteArray(), "encrypt");
			System.out.println("CIPHER TEXT:");
			des.printBytes(cipher);
			byte[] result = des.crypt(cipher, bob.sharedKey.toByteArray(), "decrypt");
			System.out.println("DECRYPTED TEXT:");
			des.printBytes(result);
			System.out.println(new String(result));
		}
		
		
	}
	
	public static void asymmetric() {
		BigInteger cipher;
		Client alice = new Client("asymmetric");
		Client bob = new Client("asymmetric");
		
		Scanner reader = new Scanner(System.in);
		
		System.out.println("Select:");
		System.out.println("1-) RSA\n2-) ElGamal");
		int choice = reader.nextInt();
		if(choice == 1) {
			System.out.println("Enter one word message:");
			String message = reader.next();
			String decrypted;
			System.out.println("Alice is generating keys...");
			alice.generateRSAKeys(new BigInteger(message.getBytes()));
			System.out.println("Alice generated keys");
			bob.getPublicKey(alice);
			System.out.println("Bob recieved Alice's public key");
			cipher = bob.encryptRSA(message);
			System.out.println("Bob encrpted the message");
			System.out.println("The Cipher is: " + cipher);

			decrypted = alice.decryptRSA(cipher);
			System.out.println("Alice decrypted the message");
			System.out.println("Decrypted message: " + decrypted);
			
		}
		
		if(choice == 2) {
			System.out.println("Enter number to encrypt:");
			String message = reader.next();
			BigInteger decrypted = null;
//			
			System.out.println("Alice is generating keys...");
			ElGamal el = new ElGamal(new BigInteger("1000000"));
			System.out.println("Alice generated keys");
			arifkarabayir.asymmetric.elgamal.Key key = el.getPublicKey();
			arifkarabayir.asymmetric.elgamal.Key key2 = el.getPrivateKey();
			System.out.println("Bob recieved Alice's public key");
			Cipher c = ElGamal.encrypt(new BigInteger(message), key);
			System.out.println("Bob encrpted the message");
		
			decrypted = ElGamal.decrypt(c, key2);
			System.out.println("Alice decrypted the message");
			System.out.println("Decrypted message: " + decrypted);
		}
	}
}
