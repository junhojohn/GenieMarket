package kr.co.geniemarket.utils;


import android.util.Log;

import kr.co.geniemarket.BuildConfig;
import kr.co.geniemarket.Const;

/**
 * 로그 처리 클래스
 */
public class GMLog {

    private static LogLevel logLevel = LogLevel.Warn;

    /**
     * 로그 표시 설정 Level
     */
    public enum LogLevel {None, Error, Warn, Info, Debug, Verbose}

    /**
     * 로그 표시 설정
     * Admize 로그 수준 지정 : 로그의 상세함 순서는 다음과 같다.
     * LogLevel.None < LogLevel.Error < LogLevel.Warn < LogLevel.Info < LogLevel.Debug < LogLevel.Verbose
     * LogLevel.None 은 모든로그표시 x
     * LogLevel.Verbose 는 모든로그표시
     * @param level 로그 레벨
     */
    public static void setLogLevel(LogLevel level) {
        // 개발모드가 아니면, debug level 이상의 로그는 남기지 않는다.
        if (!BuildConfig.DEBUG && level.ordinal() >= LogLevel.Debug.ordinal())
            logLevel = LogLevel.Info;
        else
            logLevel = level;
    }

    /**
     * 현재 설정된 로그 레벨을 리턴
     *
     * @return 현재의 로그 레벨
     */
    public static LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * It is not allowed to create new instance by user accidentally.
     */
    private GMLog(){}

    /**
     * DEBUG 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void d(String msg) {
        if(logLevel.ordinal() >= LogLevel.Debug.ordinal()){
            Log.d(Const.LOG_TAG, buildLogMsg(msg));
        }
    }

    /**
     * INFO 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void i(String msg) {
        if(logLevel.ordinal() >= LogLevel.Info.ordinal()){
            Log.i(Const.LOG_TAG, buildLogMsg(msg));
        }
    }

    /**
     * WARN 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void w(String msg) {
        if(logLevel.ordinal() >= LogLevel.Warn.ordinal()){
            Log.w(Const.LOG_TAG, buildLogMsg(msg));
        }
    }

    /**
     * ERROR 로그 기록 메소드
     * @param msg 로그 내용
     */
    public static void e(String msg) {
        if(logLevel.ordinal() >= LogLevel.Error.ordinal()){
            Log.e(Const.LOG_TAG, buildLogMsg(msg));
        }
    }

    public static void e(String msg, Exception e){
        if(logLevel.ordinal() >= LogLevel.Error.ordinal()){
            for(StackTraceElement stackTraceElement : e.getStackTrace()){
                Log.e(Const.LOG_TAG, buildLogMsg(stackTraceElement.toString()));
            }
        }
    }

    /**
     * ERROR 로그 기록 메소드
     * @param msg 로그 내용
     * @param t Throwable 객체
     */
    public static void e(String msg, Throwable t) {
        if(logLevel.ordinal() >= LogLevel.Error.ordinal()){
            Log.e(Const.LOG_TAG, buildLogMsg(msg), t);
        }
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