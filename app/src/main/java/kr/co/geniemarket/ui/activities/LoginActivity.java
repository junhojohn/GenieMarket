package kr.co.geniemarket.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import kr.co.geniemarket.R;
import kr.co.geniemarket.ui.LOGIN_AFTER_REDIR_PAGE_ENUM;
import kr.co.geniemarket.ui.pages.PageMyGenie;


public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";

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
//        callback = SessionCallback.getInstance();

        Button btnKakaoAccountLogin = (Button)findViewById(R.id.btnKakaoAccountLogin);
        btnKakaoAccountLogin.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

//                Session session = Session.getCurrentSession();
//
//                callback.setContext(mContext);
//                callback.setClassObject(nextActivityToMove);
//                session.addCallback(callback);
//
//                session.open(AuthType.KAKAO_TALK, LoginActivity.this);

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            return;
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
