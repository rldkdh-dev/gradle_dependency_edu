package kr.go.tech.protection.user.global.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.springframework.util.ObjectUtils;

public class DateUtil {

    /***
     * java.util.Date 시간을 Date 형식으로 변경한다.
     * java.util.Date 시간을 DB에 저장 시 Date 형식으로 변환 할 경우 사용 한다.
     *
     * @param date Date
     * @return
     */
    public static java.util.Date getSqlDate(java.util.Date date) {
        return new java.util.Date(date.getTime());
    }

    /***
     * System.currentTimeMillis() 시간을 Timestamp 형식으로 변경한다.
     * System.currentTimeMillis() 시간을 DB에 저장 시 Timestamp 형식으로 변환 할 경우 사용 한다.`
     * @return
     */
    public static Timestamp getSqlTimeStamp(java.util.Date date) {
        return getSqlTimeStamp(new java.util.Date(System.currentTimeMillis()));
    }

    /**
     * 오늘날짜 가져오는 함수(YYYY-MM-DD 포맷)
     *
     * @return YYYY-MM-DD 형태의 오늘 날짜
     * ex> '2020-11-17'
     */
    public static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        java.util.Date currentTime = new java.util.Date();
        String today = sdf.format(currentTime);

        return today;
    }

    /**
     * 오늘날짜 가져오는 함수 -시간까지(YYYY-MM-DD HH24:MI:ss포맷)
     *
     * @return yyyy-MM-dd HH:mm:ss 형태의 오늘 날짜
     * ex> '2020-11-17 23:59:59'
     */
    public static String getTodayTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        java.util.Date currentTime = new java.util.Date();
        String today = sdf.format(currentTime);

        return today;
    }

    /**
     * 오늘날짜 가져오는 함수(timestamp 형태)
     *
     * @return Timestamp 형태의 오늘 날짜
     * ex> 2020-11-17 11:32:54.341
     */
    public static Timestamp getTodayTimeStamp() {
        java.util.Date today = new java.util.Date();  /*오늘날짜*/
        /*java.util.Date 를 Timestamp 로 변환한다.*/
        Timestamp tsTimestamp = new Timestamp(today.getTime());

        return tsTimestamp;
    }

    /**
     * 오늘날짜 가져오는 함수- 시간까지(USER 포맷)
     *
     * @param format : 시간포맷
     * @return 파라미터 형태의 오늘날짜
     * ex> getTodayTime("yyyyMMdd")    >  '20201117'
     * getTodayTime("yyyy-MM-dd")    >  '2020-11-17'
     */
    public static String getTodayTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREAN);
        java.util.Date currentTime = new java.util.Date();
        String today = sdf.format(currentTime);

        return today;
    }

    /**
     * 전월 구해오기
     *
     * @param yyyymm : 년월
     * @return String : 전월
     * ex> getLastMonth('202008')  > '202007'
     */
    public static String getLastMonth(String yyyymm) {

        String lastMonth = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.KOREAN);
            Calendar cal = Calendar.getInstance();
            java.util.Date sMonth = sdf.parse(yyyymm);

            cal.setTime(sMonth);
            cal.add(Calendar.MONTH, -1);

            lastMonth = sdf.format(cal.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lastMonth;
    }

    /**
     * 월의 마지막일자를 구해오는함수
     *
     * @param yyyymm : 년월
     * @return 해당월의 마지막 날짜
     * ex> getLastDayOfMonth("202001")  > "20200131"
     * getLastDayOfMonth("202004")  > "20200430"
     */
    public static String getLastDayOfMonth(String yyyymm) {
        String endOfMonth = "";

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(yyyymm.substring(0, 4)), Integer.valueOf(yyyymm.substring(4, 6)) - 1, 1);
        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        endOfMonth = yyyymm + String.valueOf(endDay);

        return endOfMonth;
    }

    /**
     * 파라미터로 넘기는 수 이후의 월 구해오기
     *
     * @param yyyymm 년월
     * @param month  개월수
     * @return String : yyyymm으로 부터 month가 지난 년월
     * ex> getMonthByDiffMonth("202012", 3) > "202103"
     * getMonthByDiffMonth("202012", -3) > "202009"
     */
    public static String getMonthByDiffMonth(String yyyymm, int month) {
        String lastMonth = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.KOREAN);
            Calendar cal = Calendar.getInstance();
            java.util.Date sMonth = sdf.parse(yyyymm);

            cal.setTime(sMonth);
            cal.add(Calendar.MONTH, month);

            lastMonth = sdf.format(cal.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lastMonth;
    }

    /**
     * 해당일자로 부터 몇일 뒤 또는 몇일 후 를 구하는 함수(yyyyMMdd)
     *
     * @param baseDate 기준일자
     * @param diffDate 일수
     * @return
     * @author swoh1227
     */
    public static String getDateByDiffDate(String baseDate, int diffDate) {
        String result = "";
        java.util.Date d = new java.util.Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            d = sdf.parse(baseDate);
            c.setTime(d);
            c.add(Calendar.DAY_OF_MONTH, diffDate);
            d = c.getTime();
            result = sdf.format(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

/*    public static void startDateEndDateValidation(String startDate, String endDate){
        if(ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(endDate)) {
            throw new GlobalException(ErrorCode.START_DATE_VALIDATION, "Date Validation");
        }

        if(!ObjectUtils.isEmpty(startDate) && ObjectUtils.isEmpty(endDate)) {
            throw new GlobalException(ErrorCode.END_DATE_VALIDATION, "Date Validation");
        }
    }*/

    /*
     * input format: yyyy-MM-dd
     * output format : yyyyMMdd
     * */
    public static String replaceDateHyphen(String date) {
        return ObjectUtils.isEmpty(date) ? null : date.replace("-", "");
    }
}
