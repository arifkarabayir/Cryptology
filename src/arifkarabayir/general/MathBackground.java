package arifkarabayir.general;

import java.math.BigInteger;
import java.util.Random;

public class MathBackground {
    static public BigInteger modpow(BigInteger numb, BigInteger power, BigInteger mod) {
        BigInteger res = BigInteger.ONE;
        numb = numb.mod(mod);
        power = power.mod(mod);

        while(power.compareTo(BigInteger.ZERO) > 0) {
            res = res.multiply(numb).mod(mod);
            power = power.subtract(BigInteger.ONE);
        }

        return res;
    }


    static public BigInteger MultiplicativeInverse(BigInteger number, BigInteger mod) {
        /* Returns multiplicative inverse of number for the given mod*/
        BigInteger tempMod = mod;
        BigInteger x = BigInteger.ZERO;
        BigInteger inverse = BigInteger.ONE;

        BigInteger i,temp;

        if (mod.compareTo(BigInteger.ONE) == 0) {
            return inverse;
        }

        while(number.compareTo(BigInteger.ONE) > 0) {
            i = number.divide(mod);
            temp = mod;
            mod = number.mod(mod);
            number = temp;

            temp = x;
            x = inverse.subtract(i.multiply(x));
            inverse = temp;
        }

        if (inverse.compareTo(BigInteger.ZERO) < 0) {
            inverse = inverse.add(tempMod);
        }

        return inverse;
    }

    static public BigInteger[] ExtendedEuclideanAlgorithm(BigInteger a, BigInteger b) {
        BigInteger[] arr;

        BigInteger x = BigInteger.ZERO;
        BigInteger oldX = BigInteger.ONE;
        BigInteger y = BigInteger.ONE;
        BigInteger oldY = BigInteger.ZERO;
        BigInteger q;
        BigInteger temp;

        while (b.compareTo(BigInteger.ZERO) != 0) {
            q = a.divide(b);
            temp = x;
            x = oldX.subtract(q.multiply(x));
            oldX = temp;

            temp = y;
            y = oldY.subtract(q.multiply(y));
            oldY = temp;

            temp = b;
            b = a.mod(b);
            a = temp;
        }
        arr = new BigInteger[]{a, oldX, oldY};
        return arr;
    }


    static public BigInteger ChineseRemainderTheorem(BigInteger[] numbers, BigInteger[] mods) {
        BigInteger product = BigInteger.ONE;
        BigInteger sum = BigInteger.ZERO;
        BigInteger M;

        for (int i=0; i<mods.length; i++) {
            product = product.multiply(mods[i]);
        }

        for (int i=0; i<numbers.length; i++) {
            M = product.divide(mods[i]);

            sum = sum.add(numbers[i].multiply(MultiplicativeInverse(M, mods[i]).multiply(M)));
        }

        return sum.mod(product);
    }

    static public boolean PrimalityTesting(BigInteger n) {
        Random rand = new Random();
        BigInteger a;
        BigInteger k = n.subtract(BigInteger.ONE);
        BigInteger res;

        for(int i=0; i<50; i++) {
            a = new BigInteger(String.valueOf(rand.nextLong()));
            a = a.mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
            res = a.modPow(k, n);
            if (res.compareTo(BigInteger.ONE) != 0) {
                return false;
            }

        }
        return true;
    }
    
    static public BigInteger GeneratePrime(BigInteger max) {
    	BigInteger prime;
    	Random rand = new Random();
    	
    	while(true) {
			do {
			    prime = new BigInteger(max.bitLength(), rand);
			} while (prime.compareTo(max) >= 0 || prime.compareTo(BigInteger.TEN) < 0);
			
			if (MathBackground.PrimalityTesting(prime)) {
				break;
			}
		}
    	
    	return prime;
    }

}
