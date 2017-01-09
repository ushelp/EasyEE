package cn.easyproject.easyee.sh.base.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * RSA
 * 
 * @author easyproject.cn
 *
 */
@SuppressWarnings("restriction")
public class RSAHelper {

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = (new BASE64Encoder()).encode(keyBytes);
		return s;
	}

	/*public static void main(String[] args) throws Exception {

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 密钥位数
		keyPairGen.initialize(1024);
		// 密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		PublicKey publicKey = keyPair.getPublic();

		// 私钥
		PrivateKey privateKey = keyPair.getPrivate();

		String publicKeyString = getKeyString(publicKey);
		System.out.println("public:\n" + publicKeyString);

		String privateKeyString = getKeyString(privateKey);
		System.out.println("private:\n" + privateKeyString);

		// 加解密类
		Cipher cipher = Cipher.getInstance("RSA");// Cipher.getInstance("RSA/ECB/PKCS1Padding");

		// 明文
		byte[] plainText = "我们都很好！邮件：@sina.com".getBytes();

		// 加密
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] enBytes = cipher.doFinal(plainText);
		System.out.println("#" + new String(enBytes));

		// 通过密钥字符串得到密钥
		publicKey = getPublicKey(publicKeyString);
		privateKey = getPrivateKey(privateKeyString);

		// 解密
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] deBytes = cipher.doFinal(enBytes);

		publicKeyString = getKeyString(publicKey);
		System.out.println("public:\n" + publicKeyString);

		privateKeyString = getKeyString(privateKey);
		System.out.println("private:\n" + privateKeyString);

		String s = new String(deBytes);
		System.out.println(s);

	}*/

}