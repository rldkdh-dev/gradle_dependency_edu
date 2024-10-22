package kr.go.tech.protection.user.global.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;

/**
 * The type Text util.
 */
public class TextUtil {
    /**
     * 문자열을 연결 한다.
     *
     * @param arrStr the arr str
     * @return String string
     */
    public static String concat(String... arrStr) {
        StringBuilder sb = new StringBuilder();

        for (String concatStr : arrStr) {
            sb.append(concatStr);
        }

        return sb.toString();
    }

    /**
     * 문자열이 정규표현식과 일치하면 true
     *
     * @param patternStr 정규식 패턴
     * @param data       정규식 대상 문자열
     * @return boolean boolean
     */
    public static boolean matches(String patternStr, String data) {
        return Pattern.matches(patternStr, data);
    }

    /***
     * 정규 표현식 group 을 지정한 문자열을 찾는다.
     *
     * @param patternStr 정규식 패턴
     * @param data 정규식 대상 문자열
     * @param idx 문자열이 그룹화 된 번호
     * @return String string
     */
    public static String matcherGroup(String patternStr, String data, int idx) {

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            return matcher.group(idx);
        }

        return null;
    }

    /***
     * 정규 표현식 group 을 지정한 문자열을 찾는다.
     *
     * @param patternStr 정규식 패턴
     * @param data 정규식 대상 문자열
     * @param name the name
     * @return String string
     */
    public static String matcherGroup(String patternStr, String data, String name) {

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            return matcher.group(name);
        }

        return null;
    }

    /***
     * 정규 표현식 group으로 지정한 항목들을 모두 출력 한다.
     *
     * @param patternStr 정규식 패턴
     * @param data 정규식 대상 문자열
     * @return String[] string [ ]
     */
    public static String[] matcherGroups(String patternStr, String data) {

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(data);

        String[] arrString = null;

        if (matcher.find() && matcher.groupCount() > 0) {

            int groupCount = matcher.groupCount() + 1;
            arrString = new String[groupCount];

            for (int i = 0; i < groupCount; i++) {
                String value = matcher.group(i);
                if (value != null) {
                    arrString[i] = matcher.group(i).trim();
                }
            }
        }

        return arrString;
    }

    /***
     * Collections 객체를 JSON 문자열로 변환
     *
     * @param o Collections 객체 Map,List,Array
     * @return String string
     */
    public static String stringify(Object o) {

        try {
            return getObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * Gets object mapper.
     *
     * @return the object mapper
     */
    public static ObjectMapper getObjectMapper() {
        ObjectMapper o = new ObjectMapper();
        o.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        o.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        return o;
    }

    /**
     * URL Decoding 기본 charset은 UTF-8 이다.
     *
     * @param s       the s
     * @param charset 인코딩
     * @return String string
     */
    public static String urlDecode(String s, String charset) {
        try {
            return URLDecoder.decode(s, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error in urlDecode.", e);
        }
    }

    /**
     * URL Decoding 기본 charset은 UTF-8 이다.
     *
     * @param s the s
     * @return String string
     */
    public static String urlDecode(String s) {
        return urlDecode(s, "UTF-8");
    }

    /**
     * URL Encoding 기본 charset은 UTF-8 이다.
     *
     * @param s the s
     * @return String string
     */
    public static String urlEncode(String s) {
        return urlEncode(s, "UTF-8");
    }

    /**
     * URL Encoding 기본 charset은 UTF-8 이다.
     *
     * @param s       the s
     * @param charset 인코딩
     * @return String string
     */
    public static String urlEncode(String s, String charset) {
        try {
            return URLEncoder.encode(s, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error in urlDecode.", e);
        }
    }

    /**
     * Convert json to dto t.
     *
     * @param <T>  the type parameter
     * @param json the json
     * @param dto  the dto
     * @return the t
     * @throws Exception the exception
     */
// JSON -> DTO Parsing
    public static <T> T convertJsonToDto(String json, Class<T> dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        return objectMapper.treeToValue(jsonNode, dto);
    }

    /**
     * 알 수 없는 특수문자 제거
     *
     * @param str the str
     * @return String string
     */
    public static String cleanInvalidXmlChar(String str) {
        String xml10pattern = "[^"
                + "\u0009\r\n"
                + "\u0020-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800\udc00-\udbff\udfff"
                + "]";
        return str.replaceAll(xml10pattern, "");
    }


    /**
     * Boolean 문자열 (Y/N)으로 변환
     *
     * @param check check
     * @return string string
     */
    public static String convertBooleanToString(Boolean check) {
        if (check == null) check = false;
        if (check) return "Y";
        else return "N";
    }

    /**
     * 문자열 (Y/N) Boolean 으로 변환
     *
     * @param checkYn checkYn
     * @return boolean boolean
     */
    public static boolean convertStringToBoolean(String checkYn) {
        return !ObjectUtils.isEmpty(checkYn) && checkYn.equals("Y");
    }


    /**
     * 문자열 -> List<String> 으로 변환
     *
     * @param str str
     * @return list list
     */
    public static List<String> convertStringToList(String str) {
        String regex = "[|,;/·ㆍ]";
        Pattern pattern = Pattern.compile(regex);
        return ObjectUtils.isEmpty(str) ?
                Collections.emptyList()
                : pattern.splitAsStream(str).map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * List<String> -> 문자열 로 변환
     *
     * @param list list
     * @return string string
     */
    public static String convertListToString(List<String> list) {
        return ObjectUtils.isEmpty(list) ? "" : String.join("|", list);
    }

    /**
     * 문자열 한글 확인
     *
     * @param word word
     * @return boolean
     */
    public static boolean isKorChar(String word) {
        return Pattern.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*", word);
    }

    /**
     * 전화번호 형식 변환
     *
     * @param phoneNumber phone number
     * @return string
     */
    public static String convertPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 11) {
            return phoneNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        } else if (phoneNumber.length() == 10) {
            return phoneNumber.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "$1-$2-$3");
        } else {
            return phoneNumber.replaceFirst("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");
        }
    }

    /**
     * URL 유효성 확인
     *
     * @param url url
     * @return boolean
     */
    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
