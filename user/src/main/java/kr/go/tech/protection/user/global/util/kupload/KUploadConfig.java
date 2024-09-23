package kr.go.tech.protection.user.global.util.kupload;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KUploadConfig {

    @Value("${license.product_key}")
    private String productKey;

    @Value("${license.license_key}")
    private String licenseKey;

    @Value("${setting.runtimes}")
    private String runtimes;

    @Value("${setting.width}")
    private String width;

    @Value("${setting.height}")
    private String height;

    @Value("${setting.skin_name}")
    private String skinName;

    @Value("${setting.lang}")
    private String lang;

    @Value("${setting.mode}")
    private String mode;

    @Value("${uploader_setting.develop_language}")
    private String developLanguage;

    @Bean
    public RaonKUploadProperties raonKUploadProperties() {
        RaonKUploadProperties properties = new RaonKUploadProperties();
        properties.setProductKey(productKey);
        properties.setLicenseKey(licenseKey);
        properties.setRuntimes(runtimes);
        properties.setWidth(width);
        properties.setHeight(height);
        properties.setSkinName(skinName);
        properties.setLang(lang);
        properties.setMode(mode);
        properties.setDevelopLanguage(developLanguage);

        return properties;
    }

    @Data
    public static class RaonKUploadProperties {
        private String productKey;
        private String licenseKey;
        private String runtimes;
        private String width;
        private String height;
        private String skinName;
        private String lang;
        private String mode;
        private String developLanguage;
    }
}
