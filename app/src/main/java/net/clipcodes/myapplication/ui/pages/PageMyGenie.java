package net.clipcodes.myapplication.ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.accounts.SessionCallback;
import net.clipcodes.myapplication.models.AdditionalSellerInfo;
import net.clipcodes.myapplication.ui.LOGIN_AFTER_REDIR_PAGE_ENUM;
import net.clipcodes.myapplication.ui.activities.LoginActivity;
import net.clipcodes.myapplication.ui.activities.RegisterActivity;

import java.io.Serializable;

public class PageMyGenie extends Fragment {
    private String TAG = "PageMyGenie";
    private ImageView iv_userProfileLoggedOut;
    private TextView tv_nameLoggedOut;
    private TextView tv_idLoggedOut;
    private TextView tv_ageRangeLoggedOut;
    private TextView tv_birthLoggedOut;
    private TextView tv_emailLoggedOut;
    private TextView tv_genderLoggedOut;
    private TextView tv_phoneNumLoggedOut;
    private Button btnKakaoAccountLoginFromMyPage;

    private ImageView iv_userProfileLoggedIn;
    private TextView tv_nameLoggedIn;
    private TextView tv_idLoggedIn;
    private TextView tv_ageRangeLoggedIn;
    private TextView tv_birthLoggedIn;
    private TextView tv_emailLoggedIn;
    private TextView tv_genderLoggedIn;
    private TextView tv_phoneNumLoggedIn;
    private Button kakaoLogoutFromMyPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if(Session.getCurrentSession().checkAndImplicitOpen()){
            View fragment_myGenieLoggedInView = inflater.inflate(R.layout.fragment_mygenie_loggedin, container, false);
            initLoggedInView(fragment_myGenieLoggedInView);
            return fragment_myGenieLoggedInView;
        }else{
            View fragment_myGenieLoggedOutView = inflater.inflate(R.layout.fragment_mygenie_loggedout, container, false);
            initLoggedOutView(fragment_myGenieLoggedOutView);
            return fragment_myGenieLoggedOutView;

        }
    }


    public void initLoggedOutView(View v){
        iv_userProfileLoggedOut    = (ImageView)v.findViewById(R.id.user_profile_logged_out);
        tv_nameLoggedOut           = (TextView)v.findViewById(R.id.textView_Name_logged_out);
        tv_idLoggedOut             = (TextView)v.findViewById(R.id.textView_Id_logged_out);
        tv_ageRangeLoggedOut       = (TextView)v.findViewById(R.id.textView_AgenRange_logged_out);
        tv_birthLoggedOut          = (TextView)v.findViewById(R.id.textView_Birth_logged_out);
        tv_emailLoggedOut          = (TextView)v.findViewById(R.id.textView_Email_logged_out);
        tv_genderLoggedOut         = (TextView)v.findViewById(R.id.textView_Gender_logged_out);
        tv_phoneNumLoggedOut       = (TextView)v.findViewById(R.id.textView_PhoneNum_logged_out);
        btnKakaoAccountLoginFromMyPage = (Button)v.findViewById(R.id.btnKakaoAccountLoginFromMyPage);

        tv_nameLoggedOut.setText("null");
        tv_idLoggedOut.setText("null");
        tv_phoneNumLoggedOut.setText("null");
        tv_emailLoggedOut.setText("null");

        btnKakaoAccountLoginFromMyPage.setOnClickListener(clik);
    }


    public void initLoggedInView(View v){
        iv_userProfileLoggedIn    = (ImageView)v.findViewById(R.id.user_profile_logged_in);
        tv_nameLoggedIn           = (TextView)v.findViewById(R.id.textView_Name_logged_in);
        tv_idLoggedIn             = (TextView)v.findViewById(R.id.textView_Id_logged_in);
        tv_ageRangeLoggedIn       = (TextView)v.findViewById(R.id.textView_AgenRange_logged_in);
        tv_birthLoggedIn          = (TextView)v.findViewById(R.id.textView_Birth_logged_in);
        tv_emailLoggedIn          = (TextView)v.findViewById(R.id.textView_Email_logged_in);
        tv_genderLoggedIn         = (TextView)v.findViewById(R.id.textView_Gender_logged_in);
        tv_phoneNumLoggedIn       = (TextView)v.findViewById(R.id.textView_PhoneNum_logged_in);
        kakaoLogoutFromMyPage     = (Button)v.findViewById(R.id.kakaoLogoutFromMyPage);

        AdditionalSellerInfo sellerInfo = SessionCallback.getInstance().getSellerInfo();
        sellerInfo.getSellerName();
        sellerInfo.getSellerID();
        sellerInfo.getSellerDescription();
        sellerInfo.getSellerKakaoAccount();
        sellerInfo.getSellerPhoneNumber();

        tv_nameLoggedIn.setText(sellerInfo.getSellerName());
        tv_idLoggedIn.setText(sellerInfo.getSellerID());
        tv_phoneNumLoggedIn.setText(sellerInfo.getSellerPhoneNumber());
        tv_emailLoggedIn.setText(sellerInfo.getSellerDescription());

        kakaoLogoutFromMyPage.setOnClickListener(clik);


    }


    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnKakaoAccountLoginFromMyPage:
                    Intent loginIntent = new Intent(view.getContext(), LoginActivity.class);
                    loginIntent.putExtra("nextActivityToMove", LOGIN_AFTER_REDIR_PAGE_ENUM.PAGE_MY_GENIE.getActivityName());
                    startActivity(loginIntent);
                    break;
                case R.id.kakaoLogoutFromMyPage:
                    onClickLogout();
                    break;
            }
        }
    };

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
                    SessionCallback.getInstance().setLoginSuccess(false);
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
