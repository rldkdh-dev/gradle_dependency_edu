package kr.go.tech.protection.global.config.crypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {


	@Test
	void jasyptEncryption() {

		String key = "d57154f4-448f-4002-b8e4-f63411f7402a";

		String encryptUrl = jasyptEnc(key);
		String decryptUrl = jasyptDec("MTaJnrW/MG0I+MP8jY916a6CYCShtVJ2tQjDAvp9HqXdcyOYHMkHc+ZCSw0cBV0/");

		System.out.println("encryptUrl = " + encryptUrl);
		System.out.println("decryptUrl = " + decryptUrl);

	}

	public String jasyptEnc(String value) {
		PooledPBEStringEncryptor encryptor = getPbeStringEncryptor();
		return encryptor.encrypt(value);
	}

	public String jasyptDec(String value) {
		PooledPBEStringEncryptor encryptor = getPbeStringEncryptor();
		return encryptor.decrypt(value);
	}

	private PooledPBEStringEncryptor getPbeStringEncryptor() {
		String key = "techProtection-password";
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();

		config.setPassword(key);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations(1000);
		config.setPoolSize(1);
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");

		encryptor.setConfig(config);
		return encryptor;
	}

}
