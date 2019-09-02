package net.clipcodes.myapplication.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.accounts.SessionCallback;
import net.clipcodes.myapplication.ui.LOGIN_AFTER_REDIR_PAGE_ENUM;
import net.clipcodes.myapplication.ui.pages.PageMyGenie;

import java.io.Serializable;


public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private SessionCallback callback;
    private Context mContext;
    private Class nextActivityToMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent previousPage = getIntent();
//        nextActivityToMove = previousPage.getSerializableExtra("nextActivityToMove");
        String param = previousPage.getStringExtra("nextActivityToMove");
        if(param.equals(LOGIN_AFTER_REDIR_PAGE_ENUM.REGISTER_ACTIVITY.getActivityName())){
            nextActivityToMove = RegisterActivity.class;
        }else if(param.equals(LOGIN_AFTER_REDIR_PAGE_ENUM.PURCHASE_ACTIVITY.getActivityName())){
            nextActivityToMove = PurchaseActivity.class;
        }else if(param.equals(LOGIN_AFTER_REDIR_PAGE_ENUM.PAGE_MY_GENIE.getActivityName())){
            nextActivityToMove = PageMyGenie.class;
        }else if(param.equals(LOGIN_AFTER_REDIR_PAGE_ENUM.MAIN_ACTIVITY.getActivityName())){
            nextActivityToMove = MainActivity.class;
        }

        mContext = getApplicationContext();
        callback = SessionCallback.getInstance();

        Button btnKakaoAccountLogin = (Button)findViewById(R.id.btnKakaoAccountLogin);
        btnKakaoAccountLogin.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Session session = Session.getCurrentSession();

                callback.setContext(mContext);
                callback.setClassObject(nextActivityToMove);
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

}
