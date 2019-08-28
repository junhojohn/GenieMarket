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
import net.clipcodes.myapplication.accounts.SessionCallback;
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
//        onClickLogout();
        mContext = getApplicationContext();
        callback = SessionCallback.getInstance();

        Button btnKakaoAccountLogin = (Button)findViewById(R.id.btnKakaoAccountLogin);
        btnKakaoAccountLogin.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Session session = Session.getCurrentSession();

                callback.setContext(mContext);
                callback.setClassObject(RegisterActivity.class);
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
}
