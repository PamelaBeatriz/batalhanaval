package util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {

	public static String getHash(byte[] dados) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(dados));
			String s = hash.toString(16);
			if (s.length() % 2 != 0)
				s = "0" + s;

			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
