package kr.co.geniemarket.repositories;

import android.annotation.SuppressLint;

import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kr.co.geniemarket.BuildConfig;
import kr.co.geniemarket.Const;
import kr.co.geniemarket.models.ProductInfo;
import kr.co.geniemarket.utils.GMLog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenieMarketRemoteClient {

    private static StringBuffer resultMs = new StringBuffer();
    private static final Pattern pattern = Pattern.compile("([0-9]+ms)");;
    private static StringBuffer responseMessage = new StringBuffer();
    public static Converter errorResponseConverter;

    private GenieMarketRemoteClient(){}

    public static GenieMarketRemoteService createRequest(long timeoutSec) throws Exception {
        return getInstance(timeoutSec).create(GenieMarketRemoteService.class);
    }

    private static Retrofit getInstance(long timeoutSec) throws Exception {
        Retrofit.Builder builder = new Retrofit.Builder();

        // 1. Converter 세팅
        builder.addConverterFactory(GsonConverterFactory.create());

        // 2. RxJava Call Converter 세팅
        builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create());

        // 3. 인증 우회여부 세팅
        OkHttpClient.Builder okHttpClient = getOkHttpClient(true, timeoutSec);
        if(okHttpClient == null){
            return null;
        }

        // 4. Retrofit 객체 생성
        Retrofit retrofit = builder
                .baseUrl(
                        BuildConfig.GENIEMARKET_SERVER_PROTOCOL +
                                Const.SEMI_COLON +
                                Const.SLASH +
                                Const.SLASH +
                                BuildConfig.GENIEMARKET_SERVER_DOMAIN)
                .client(okHttpClient.build())
                .build();

        // 5. Error Response Body 파싱을 위한 컨버터 생성
        errorResponseConverter = retrofit.
                responseBodyConverter(
                        ProductInfo.class,
                        ProductInfo.class.getAnnotations());

        return retrofit;
    }

    /**
     * 클라이언트에서 HTTPS(Security) REQUEST URL를 요청할 때 서버로부터 정상 응답을 받는 2가지 방법이 있다.                                     <br>
     * 1. 요청하려는 서버에 Security 인증서(Certification)가 없을 경우, 강제 우회(웹페이지 계속 탐색)하면서 HTTPS REQUEST URL 요청               <br>
     * 2. 정상적인 Security 인증서를 가지고 SSL을 이용한 HTTPS REQUEST URL 요청                                                                <br>
     * <code>getUnsafeOkHttpClient()</code> 메서드는 1번 방법을 위한 것이다.(인증서가 없으면 우회하는 것이니, 안전한 요청인지는 보장할 수 없음)   <br>
     * <code>getUnsafeOkHttpClient()</code>에서 OkHttpClient 라이브러리를 사용하는 이유는,                                                     <br>
     * Retrofit2에서 OkHttp3 라이브러리를 래핑하기 때문에, SSL 우회 접속이 가능하도록 OkHttpClient 객체를 만듬.                                  <br>
     * @return SSL 우회 접속 가능하도록 설정된 OkHttpClient 객체. Retrofit 객체를 빌더패턴으로 <code>build()</code> 할 때 리턴된 OkHttpClient를 set(혹은 add)해주면 된다. <br>
     */
    private static OkHttpClient.Builder getOkHttpClient(boolean useSSL, long timeoutSec) throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if(!useSSL){
            setDefaultSSL(builder);
        }

        // 5. Timeout 설정
        if(timeoutSec <= Const.DEFAULT_ZERO){
            builder.connectTimeout(Const.DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS);
            builder.readTimeout(Const.DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS);
        }else{
            builder.connectTimeout(timeoutSec, TimeUnit.SECONDS);
            builder.readTimeout(timeoutSec, TimeUnit.SECONDS);
        }

        // 6. Http Body Logger 설정
//        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        builder.addInterceptor(new HttpLoggingInterceptor(bodyLogMessage -> {
            GMLog.i(bodyLogMessage);
            responseMessage.append(bodyLogMessage);
            Matcher m = pattern.matcher(bodyLogMessage);
            while(m.find()) {
                resultMs.append(m.group());
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));

        return builder;
    }

    /**
     * NewRetrofitClientUtil 과 OldHttpUrlConnectionClientUtil 공통인듯하지만 아님. 인자가 다름.
     */
    private static void setDefaultSSL(OkHttpClient.Builder builder) throws Exception {
        TrustManager[] trustAllCerts        = createBypassTrustManager();
        SSLSocketFactory sslSocketFactory   = createSSLSocketFactory(trustAllCerts);
        // 4. OKHttpClient 빌더에 SSLSocket 설정 및 도메인 필터링.
        builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier((domain, sslSession) -> domain.equalsIgnoreCase(BuildConfig.GENIEMARKET_SERVER_DOMAIN));

    }

    /**
     * 공통) NewRetrofitClientUtil 과 OldHttpUrlConnectionClientUtil
     * @return TrustManager[] TrustManager 배열
     */
    private static TrustManager[] createBypassTrustManager() {
        // 1. 인증서가 없으므로, HTTPS를 바이패스한다.
        return new TrustManager[]{
                new X509TrustManager() {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        // TODO Nothing
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        // TODO Nothing
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    /**
     * 공통) NewRetrofitClientUtil 과 OldHttpUrlConnectionClientUtil
     * @return SSLSocketFactory SSLSocketFactory
     */
    private static SSLSocketFactory createSSLSocketFactory(TrustManager[] trustAllCerts) {
        try{
            // 2. SSLContext 생성
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // 3. SSLSocket 생성
            return sslContext.getSocketFactory();

        }catch (Exception e){
            GMLog.e(e.getMessage(), e);
            return null;
        }
    }

    public static void clearResponseMessage(){
        GenieMarketRemoteClient.responseMessage.delete(0, GenieMarketRemoteClient.responseMessage.length());
    }

    public static void clearResultMs(){
        GenieMarketRemoteClient.resultMs.delete(0, GenieMarketRemoteClient.resultMs.length());
    }

    public static String getResponseMessage() {
        String msg = GenieMarketRemoteClient.responseMessage.toString();
        return msg;
    }

    public static String getResultMs() {
        String resultMs = GenieMarketRemoteClient.resultMs.toString();
        GenieMarketRemoteClient.resultMs.delete(0, GenieMarketRemoteClient.resultMs.length());
        return resultMs;
    }
}
