package net.clipcodes.myapplication.accounts;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import net.clipcodes.myapplication.models.AdditionalSellerInfo;


public class SessionCallback implements ISessionCallback {

    // Redirection할 클래스
    private Context context;
    private Class classObject;
    private String TAG = "TAG";
    private static volatile SessionCallback instance = null;
    private AdditionalSellerInfo sellerInfo;

    // 로그인에 성공한 상태
    private boolean isLoginSuccess = false;

    private SessionCallback(){
    }

    public static SessionCallback getInstance(){
        if(instance == null){
            synchronized (SessionCallback.class){
                if(instance == null){
                    instance = new SessionCallback();
                }
            }
        }
        return instance;
    }

    @Override

    public void onSessionOpened() {
        if(!isLoginSuccess){
            requestMe();
        }
    }



    // 로그인에 실패한 상태

    @Override

    public void onSessionOpenFailed(KakaoException exception) {

        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());

    }

    public AdditionalSellerInfo getSellerInfo() {
        if(sellerInfo == null){
            sellerInfo = new AdditionalSellerInfo();
        }
        return sellerInfo;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Class getClassObject() {
        return classObject;
    }

    public void setClassObject(Class classObject) {
        this.classObject = classObject;
    }

    // 사용자 정보 요청

    public void requestMe() {

        // 사용자정보 요청 결과에 대한 Callback

        UserManagement.getInstance().me(new MeV2ResponseCallback() {

            // 세션 오픈 실패. 세션이 삭제된 경우,
            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                Log.e(TAG, "requestMe onFailure message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onFailureForUiThread(ErrorResult errorResult) {
                super.onFailureForUiThread(errorResult);
                Log.e(TAG, "requestMe onFailureForUiThread message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "requestMe onSessionClosed message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onDidStart() {
                Log.e("SessionCallback :: ", "onDidStart");
                super.onDidStart();
            }

            @Override
            public void onDidEnd() {
                Log.e("SessionCallback :: ", "onDidEnd");
                super.onDidEnd();
            }

            @Override
            public void onSuccessForUiThread(MeV2Response result) {
                Log.e("SessionCallback :: ", "onSuccessForUiThread");
                super.onSuccessForUiThread(result);
            }

            // 사용자정보 요청에 성공한 경우,

            @Override
            public void onSuccess(MeV2Response userProfile) {

                Log.e("SessionCallback :: ", "onSuccess");

                String nickname = userProfile.getNickname(); // 정준호

                String profileImagePath = userProfile.getProfileImagePath(); // https://k.kakaocdn.net/dn/bm6HH8/btqxyLLaE5g/w3NcqGlQRxzy5DXO56CQK0/profile_110x110c.jpg

                String thumnailPath = userProfile.getThumbnailImagePath(); // https://k.kakaocdn.net/dn/bm6HH8/btqxyLLaE5g/w3NcqGlQRxzy5DXO56CQK0/profile_640x640s.jpg

                String birthday = userProfile.getKakaoAccount().getBirthday(); // 0809
                String age = "";
                if(userProfile.getKakaoAccount().hasAgeRange().getBoolean()){
                    age = userProfile.getKakaoAccount().getAgeRange().getValue(); // 방어처리 필요 10~19, 20~29, 30~39, 40~49
                }

                String gender = "";
                if(userProfile.getKakaoAccount().hasGender().getBoolean()){
                    gender = userProfile.getKakaoAccount().getGender().getValue(); // male, female
                }

                String email = userProfile.getKakaoAccount().getEmail(); // train1990@hanmail.net
                String phoneNum = userProfile.getKakaoAccount().getPhoneNumber(); // null

                long id = userProfile.getId();
//                    user = new UserModel();
//                    user.setNickname(nickname);
//                    user.setThumnailPath(profileImagePath);
//                    user.setBirthday(birthday);
//                    user.setAge(age);
//                    user.setGender(gender);
//                    user.setEmail(email);
//                    user.setPhoneNum(phoneNum);


                Log.e("Profile : ", nickname + "");

                Log.e("Profile : ", email + "");

                Log.e("Profile : ", profileImagePath  + "");

                Log.e("Profile : ", thumnailPath + "");

                Log.e("Profile : ", birthday + "");

                Log.e("Profile : ", id + "");

                Log.e("Profile : ", age + "");

                Log.e("Profile : ", gender + "");

                Log.e("Profile : ", phoneNum + "");


                getSellerInfo().setSellerKakaoAccount(email);
                getSellerInfo().setSellerName(nickname);
                getSellerInfo().setSellerID(nickname);
                getSellerInfo().setSellerPhoneNumber(phoneNum);
                getSellerInfo().setSellerDescription("생년월일: " + birthday + ", 나이 대:" + age);

                if(getContext() != null && getClassObject() != null){
                    Intent mIntent = new Intent(getContext(), getClassObject());
                    mIntent.putExtra("sellerInfo", getSellerInfo());
                    getContext().startActivity(mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    setClassObject(null);
                    setContext(null);
                }
                setLoginSuccess(true);
            }
        });
    }
}