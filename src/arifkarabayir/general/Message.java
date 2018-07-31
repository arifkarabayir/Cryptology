package arifkarabayir.general;

import java.math.BigInteger;

public class Message {
	String binary;
	
	public Message(String str) {
		String result = "";
	    char[] messChar = str.toCharArray();

	    for (int i = 0; i < messChar.length; i++) {
	        result += Integer.toBinaryString(messChar[i]) + "";
	    }

	    this.binary = result;
	}
	
	public BigInteger toBigInteger() {
		return new BigInteger(this.binary);
	}
	
	
}
