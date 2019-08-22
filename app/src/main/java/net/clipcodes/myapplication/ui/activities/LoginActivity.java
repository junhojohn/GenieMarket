package net.clipcodes.myapplication.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.AdditionalSellerInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private SessionCallback callback;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onClickLogout();
        callback = new SessionCallback();
        mContext = getApplicationContext();
        Button btnKakaoAccountLogin = (Button)findViewById(R.id.btnKakaoAccountLogin);
        btnKakaoAccountLogin.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Session session = Session.getCurrentSession();

                session.addCallback(callback);

                session.open(AuthType.KAKAO_TALK, LoginActivity.this);

            }

        });

        Log.e(TAG, "토큰큰 : " + Session.getCurrentSession().getTokenInfo().getAccessToken());
        Log.e(TAG, "토큰큰 리프레쉬토큰 : " + Session.getCurrentSession().getTokenInfo().getRefreshToken());
        Log.e(TAG, "토큰큰 파이어데이트 : " + Session.getCurrentSession().getTokenInfo().getRemainingExpireTime());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickLogout(){
        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "카카오 로그아웃 onSessionClosed");
            }

            @Override
            public void onNotSignedUp() {
                Log.e(TAG, "카카오 로그아웃 onNotSignedUp");
            }

            @Override
            public void onSuccess(Long result) {
                Log.e(TAG, "카카오 로그아웃 onSuccess");

//                tvName.setText(null);
//                tvAge.setText(null);
//                tvBirth.setText(null);
//                tvEmail.setText(null);
//                tvGender.setText(null);
//                tvId.setText(null);
//                tvPhone.setText(null);
//                imvThumbnail.setImageResource(R.drawable.kakao_default_profile_image);
            }
        });
    }

    private class SessionCallback implements ISessionCallback {


        // 로그인에 성공한 상태

        @Override

        public void onSessionOpened() {

            requestMe();

        }



        // 로그인에 실패한 상태

        @Override

        public void onSessionOpenFailed(KakaoException exception) {

            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());

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

                    AdditionalSellerInfo sellerInfo = new AdditionalSellerInfo();
                    sellerInfo.setSellerKakaoAccount(email);
                    sellerInfo.setSellerName(nickname);
                    sellerInfo.setSellerID(nickname);
                    sellerInfo.setSellerPhoneNumber(phoneNum);
                    sellerInfo.setSellerDescription("생년월일: " + birthday + ", 나이 대:" + age);

                    Intent mIntent = new Intent(mContext, RegisterActivity.class);
                    mIntent.putExtra("sellerInfo", sellerInfo);
                    mContext.startActivity(mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

//                    tvName.setText(user.getNickname());
//                    tvAge.setText(user.getAge());
//                    tvBirth.setText(user.getBirthday());
//                    tvEmail.setText(user.getEmail());
//                    tvGender.setText(user.getGender());
//                    tvId.setText(String.valueOf(user.getId()));
//                    tvPhone.setText(user.getPhoneNum());


//                    Thread mThread = new Thread(){
//                        @Override
//                        public void run() {
//                            try{
//                                URL url = new URL(user.getThumnailPath());
//                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                                conn.setDoInput(true);
//                                conn.connect();
//
//                                InputStream is = conn.getInputStream();
//                                bitmap = BitmapFactory.decodeStream(is);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//                    };
//
//                    mThread.start();
//
//                    try{
//                        mThread.join();
//                        imvThumbnail.setImageBitmap(bitmap);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }

                }
            });

        }
    }
}
