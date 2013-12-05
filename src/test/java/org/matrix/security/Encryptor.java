package org.matrix.security;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.codec.Hex;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013-12-5 上午9:35:18
 */
public class Encryptor {
	private static final Log log = LogFactory.getLog(Encryptor.class);
	private static final String DES = "DES";
	private static final String UTF_8 = "UTF-8";
	private static final String password = "Encryptor";

	private static final Encryptor ENCRYPTOR = new Encryptor();

	private Encryptor() {
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			Key key = genEncryptorKey(Encryptor.password.getBytes(Encryptor.UTF_8));
			encryptor = Cipher.getInstance(DES);
			encryptor.init(Cipher.ENCRYPT_MODE, key);
			decryptor = Cipher.getInstance(DES);
			decryptor.init(Cipher.DECRYPT_MODE, key);
			log.error("Encryptor.init success.");
		} catch (Exception e) {
			log.error("Encryptor.init error.", e);
			encryptor = null;
			decryptor = null;
		}
	}

	private Key genEncryptorKey(byte[] bytes) throws Exception {
		byte[] encryptorKey = new byte[8];
		int length = bytes.length >= encryptorKey.length ? encryptorKey.length : bytes.length;
		System.arraycopy(bytes, 0, encryptorKey, 0, length);
		return new javax.crypto.spec.SecretKeySpec(encryptorKey, DES);
	}

	public static Encryptor getInstance() {
		return Encryptor.ENCRYPTOR;
	}

	private Cipher encryptor = null;
	private Cipher decryptor = null;

	public String encrypt(String text) throws Exception {
		return String.copyValueOf(Hex.encode(encryptor.doFinal(text.getBytes(UTF_8))));
	}

	public String decrypt(String encryptedText) throws Exception {
		return new String(decryptor.doFinal(Hex.decode(encryptedText)), UTF_8);
	}
	
	public static void main(String[] args) throws Exception {
		Encryptor encryptor = getInstance();
		String msg = "刘飞你好！！aS12`~!@#$%^&*()_+=-/*-+|\\}{[];'\":/.,<>?";
		String encryptedString = encryptor.encrypt(msg);
		System.out.println("encryptedString : " + encryptedString);
		String decryptString = encryptor.decrypt(encryptedString);
		System.out.println("decryptString : " + decryptString);
	}
}