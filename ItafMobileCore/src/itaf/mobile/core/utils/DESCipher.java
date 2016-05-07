package itaf.mobile.core.utils;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.annotation.SuppressLint;
import android.util.Base64;

public class DESCipher {
	private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";
	private static final byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * DES算法，加密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException
	 *             异常
	 */
	@SuppressLint("TrulyRandom")
	public static String encrypt(String data, String key) {
		if (data == null) {
			return null;
		}
		byte[] result = null;
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			AlgorithmParameterSpec paramSpec = ivParameterSpec;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			result = cipher.doFinal(data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Base64.encodeToString(result, 0);
	}

	/**
	 * DES算法，解密
	 * 
	 * @param data
	 *            待解密字符串
	 * @param key
	 *            解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception
	 *             异常
	 */
	public static String decrypt(String data, String key) {
		byte[] result = null;
		try {
			byte[] dataByte = Base64.decode(data, 0);
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			AlgorithmParameterSpec paramSpec = ivParameterSpec;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			result = cipher.doFinal(dataByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(result);
	}

}