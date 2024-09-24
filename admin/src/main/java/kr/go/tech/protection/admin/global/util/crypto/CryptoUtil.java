package kr.go.tech.protection.admin.global.util.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String SECRET_KEY = "123456789123456789!@#$%^&*()1234"; // 32byte
	private static final String IV = SECRET_KEY.substring(0, 16); // initialize vector

	public static String encrypt(String message) {
		try {
			Cipher cipher = getCipherMode(Cipher.ENCRYPT_MODE);

			byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to encrypt", e);
		}
	}

	public static String decrypt(String encryptedMessage) {
		try {
			Cipher cipher = getCipherMode(Cipher.DECRYPT_MODE);

			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to decrypt", e);
		}
	}

	private static Cipher getCipherMode(int encryptMode) throws
		NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
		cipher.init(encryptMode, secretKeySpec, ivParameterSpec);
		return cipher;
	}

}

