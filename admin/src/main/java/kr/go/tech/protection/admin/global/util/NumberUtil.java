package kr.go.tech.protection.admin.global.util;

import java.security.SecureRandom;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtil {

    public static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
     private static final int PASSWORD_LENGTH = 12;

    /**
     * 12자리 임시 비밀번호 생성
     *
     * @return String
     */
    public static String generateTempPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }



    /**
     * Integer 정수를 랜덤으로 생성,
     *
     * @return int
     */
    public static int randomInt() {
        return randomInt(-1);
    }

    /**
     * Integer 정수를 랜덤으로 생성, 난수 범위를 지정 하면 해당 숫자 사이의 임의의 번호 생성
     *
     * @param range
     * @return
     */
    public static int randomInt(int range) {
        Random randomGenerator = new Random();
        int ret = 0;
        ret = (range > -1) ? randomGenerator.nextInt(range) : randomGenerator.nextInt();
        ret = (ret < 0) ? ret * -1 : ret;
        return ret;

    }

    public static long randomLong() {
        Random randomGenerator = new Random();
        long randomLongUniform = randomGenerator.nextLong();

        return randomLongUniform;
    }

    public static double randomDouble() {
        Random randomGenerator = new Random();
        double randomDoubleUniform = randomGenerator.nextDouble();

        return randomDoubleUniform;
    }

    public static float randomFloat() {
        Random randomGenerator = new Random();
        float randomFloatUniform = randomGenerator.nextFloat();

        return randomFloatUniform;
    }

    public static String toStr(int integer) {
        return Integer.toString(integer);
    }

    public static String toStr(long longNumber) {
        return Long.toString(longNumber);
    }

    public static String toStr(double doubleNumber) {
        return Double.toString(doubleNumber);
    }

    public static String toStr(float floatNumber) {
        return Float.toString(floatNumber);
    }

}
