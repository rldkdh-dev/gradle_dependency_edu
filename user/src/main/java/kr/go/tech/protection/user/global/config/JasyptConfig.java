package kr.go.tech.protection.user.global.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class JasyptConfig {
	// magic number 및 무의미한 값을 field에 상수로 적용
	public static final String ALGORITHM = "PBEWithMD5AndDES";
	public static final int KEY_ITERATIONS = 1000;
	public static final int POOL_SIZE = 1;
	public static final String RANDOM_SALT_GENERATOR = "org.jasypt.salt.RandomSaltGenerator";
	public static final String OUTPUT_TYPE = "base64";

	@Bean("jasyptStringEncryptor")
	public StringEncryptor getEncryptor() {
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(getPrivatePassword());
		config.setAlgorithm(ALGORITHM);
		config.setKeyObtentionIterations(KEY_ITERATIONS);
		config.setPoolSize(POOL_SIZE);
		config.setSaltGeneratorClassName(RANDOM_SALT_GENERATOR);
		config.setStringOutputType(OUTPUT_TYPE);

		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setConfig(config);
		return encryptor;
	}

	// 지정한 secret key를 해당 method에서 사용.
	// 추후 ClassPathResource에 파일 경로로 지정해서 해당 내용을 읽어들일 수 있음.
	private String getPrivatePassword() {
		try {
			ClassPathResource resource = new ClassPathResource("data/password");
			return String.join("", Files.readAllLines(Paths.get(resource.getURI())));
		} catch (IOException e) {
			throw new RuntimeException("Not Found database.properties file And read error  ", e);
		}
	}
}
