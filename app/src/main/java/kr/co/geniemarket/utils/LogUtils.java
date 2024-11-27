package kr.co.geniemarket.utils;


import android.util.Log;

import kr.co.geniemarket.BuildConfig;
import kr.co.geniemarket.Const;

/**
 * 로그 처리 클래스
 */
public class LogUtils {

    /**
     * It is not allowed to create new instance by user accidentally.
     */
    private LogUtils(){}

    /**
     * DEBUG 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(Const.LOG_TAG, buildLogMsg(msg));
        }
    }

    /**
     * INFO 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void i(String msg) {
//        if (BuildConfig.DEBUG) {
            Log.i(Const.LOG_TAG, buildLogMsg(msg));
//        }
    }

    /**
     * WARN 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(Const.LOG_TAG, buildLogMsg(msg));
        }
    }

    /**
     * ERROR 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void e(String msg) {
        Log.e(Const.LOG_TAG, buildLogMsg(msg));
    }

    public static void e(String msg, Exception e){
        Log.e(Const.LOG_TAG, buildLogMsg(msg), e);
    }

    /**
     * ERROR 로그 기록 메소드
     * @param msg 로그 내용
     * @param t Throwable 객체
     */
    public static void e(String msg, Throwable t) {
        Log.e(Const.LOG_TAG, buildLogMsg(msg), t);
    }

    private static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(Const.BLANK);
        sb.append("@");
        sb.append(ste.getClassName());
        sb.append(".");
        sb.append(ste.getMethodName());
        sb.append(Const.BLANK);
        sb.append("in");
        sb.append(Const.BLANK);
        sb.append(ste.getLineNumber());
        sb.append(Const.BLANK);
        sb.append("line.");


        return sb.toString();
    }
}