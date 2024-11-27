package kr.co.geniemarket.ui.activities;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import kr.co.geniemarket.BuildConfig;
import kr.co.geniemarket.R;
import kr.co.geniemarket.models.AdditionalProductInfo;
import lombok.Getter;

import java.util.ArrayList;

public class SplashscreenActivity extends AppCompatActivity {
    private String TAG = "SplashscreenActivity";

    private ArrayList<AdditionalProductInfo> productItemList  = null;

    @Getter
    private ViewModelProvider viewModelProvider;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Log.i(TAG, "GenieMarket version: " + BuildConfig.GENIEMARKET_VERSION);
        viewModelProvider =
                new ViewModelProvider(
                        (ViewModelStoreOwner) this,
                        new ViewModelProvider.NewInstanceFactory());

        checkKakaoLoginSession();
        getData();
    }

    private void checkKakaoLoginSession(){
//        if(Session.getCurrentSession().checkAndImplicitOpen()){
//            Session.getCurrentSession().addCallback(SessionCallback.getInstance());
//            SessionCallback.getInstance().requestMe();
//        }
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_splash_alpha_color);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashscreenActivity.this,
                            MainActivity.class);

                    intent.putExtra("productItemList", productItemList);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashscreenActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashscreenActivity.this.finish();
                }

            }
        };
        splashTread.start();

    }

    public void getData() {

        productItemList = new ArrayList<>();
        AdditionalProductInfo additionalProductInfo01 = new AdditionalProductInfo();
        additionalProductInfo01.setName("꽃다발팝니다");
        additionalProductInfo01.setDescription("시장에서 산 꽃 저렵하게 팝니다. 네고는 힘들고 강남역 직거래만 가능합니다.");
        additionalProductInfo01.setSellerName("victory-124");
        ArrayList<String> imageUrlPathList01 = new ArrayList<>();
        imageUrlPathList01.add("https://thumbs2.imgbox.com/2d/a9/t6uRgQeF_t.jpeg");
        imageUrlPathList01.add("https://thumbs2.imgbox.com/57/45/76vqnGHh_t.jpeg");
        additionalProductInfo01.setImageURLPathList(imageUrlPathList01);
        additionalProductInfo01.setPrice(9900);
        additionalProductInfo01.setBigCategory(getString(R.string.title_cheap_product));
        additionalProductInfo01.setMidCategory(getString(R.string.title_category_accessaries));
        additionalProductInfo01.setSmallCategory(getString(R.string.title_category_etc_accessaries));
        additionalProductInfo01.setItemCount(1);

        AdditionalProductInfo additionalProductInfo02 = new AdditionalProductInfo();
        additionalProductInfo02.setName("레고 10278 미개봉팝니다.");
        additionalProductInfo02.setDescription("레고 10278 미개봉팝니다. 택배거래 착불로 가능하나, 직거래 선호합니다. 직거래시 교통비 정도 네고 가능.");
        additionalProductInfo02.setSellerName("james10");
        ArrayList<String> imageUrlPathList02 = new ArrayList<>();
        imageUrlPathList02.add("https://thumbs2.imgbox.com/e1/b1/nZAYgCjf_t.jpeg");
        additionalProductInfo02.setImageURLPathList(imageUrlPathList02);
        additionalProductInfo02.setPrice(285000);
        additionalProductInfo02.setBigCategory(getString(R.string.title_best_product));
        additionalProductInfo02.setMidCategory(getString(R.string.title_category_toys));
        additionalProductInfo02.setSmallCategory(getString(R.string.title_category_blocks));
        additionalProductInfo02.setItemCount(1);

        AdditionalProductInfo additionalProductInfo03 = new AdditionalProductInfo();
        additionalProductInfo03.setName("레고 10234 미개봉팝니다.");
        additionalProductInfo03.setDescription("레고 10234 미개봉팝니다. 택배거래 착불로 가능하나, 직거래 선호합니다. 직거래시 교통비 정도 네고 가능.");
        additionalProductInfo03.setSellerName("RnqelW413");
        ArrayList<String> imageUrlPathList03 = new ArrayList<>();
        imageUrlPathList03.add("https://thumbs2.imgbox.com/af/d6/5arkrdiR_t.jpeg");
        imageUrlPathList03.add("https://thumbs2.imgbox.com/01/4c/jMI8ZiVM_t.jpeg");
        imageUrlPathList03.add("https://thumbs2.imgbox.com/f9/db/nMlj521H_t.jpeg");
        additionalProductInfo03.setImageURLPathList(imageUrlPathList03);
        additionalProductInfo03.setPrice(360000);
        additionalProductInfo03.setBigCategory(getString(R.string.title_best_product));
        additionalProductInfo03.setMidCategory(getString(R.string.title_category_toys));
        additionalProductInfo03.setSmallCategory(getString(R.string.title_category_blocks));
        additionalProductInfo03.setItemCount(1);

        AdditionalProductInfo additionalProductInfo04 = new AdditionalProductInfo();
        additionalProductInfo04.setName("레고 71042 미개봉팝니다.");
        additionalProductInfo04.setDescription("레고 71042 미개봉팝니다. 택배거래 착불로 가능하나, 직거래 선호합니다. 직거래시 교통비 정도 네고 가능.");
        additionalProductInfo04.setSellerName("RkfRmal3");
        ArrayList<String> imageUrlPathList04 = new ArrayList<>();
        imageUrlPathList04.add("https://thumbs2.imgbox.com/b1/70/yXOQOEy1_t.jpeg");
        imageUrlPathList04.add("https://thumbs2.imgbox.com/36/7f/jIqI9lEr_t.jpeg");
        imageUrlPathList04.add("https://thumbs2.imgbox.com/28/00/Yo6epppB_t.jpeg");
        additionalProductInfo04.setImageURLPathList(imageUrlPathList04);
        additionalProductInfo04.setPrice(400000);
        additionalProductInfo04.setBigCategory(getString(R.string.title_best_product));
        additionalProductInfo04.setMidCategory(getString(R.string.title_category_toys));
        additionalProductInfo04.setSmallCategory(getString(R.string.title_category_blocks));
        additionalProductInfo04.setItemCount(1);

        AdditionalProductInfo additionalProductInfo05 = new AdditionalProductInfo();
        additionalProductInfo05.setName("아이썬 블루투스 선글라스 중고팝니다.");
        additionalProductInfo05.setDescription("홈쇼핑에서 올해 초에 구매했습니다. 착용 1~2번 했고 변색 적용도 가능합니다. UV-200까지 자외서 차단됩니다.");
        additionalProductInfo05.setSellerName("bmk0130");
        ArrayList<String> imageUrlPathList05 = new ArrayList<>();
        imageUrlPathList05.add("https://thumbs2.imgbox.com/8d/b7/qP3Tfo9M_t.jpeg");
        additionalProductInfo05.setImageURLPathList(imageUrlPathList05);
        additionalProductInfo05.setPrice(400000);
        additionalProductInfo05.setBigCategory(getString(R.string.title_best_product));
        additionalProductInfo05.setMidCategory(getString(R.string.title_category_electronics));
        additionalProductInfo05.setSmallCategory(getString(R.string.title_category_cellphone_accessaries));
        additionalProductInfo05.setItemCount(1);

        AdditionalProductInfo additionalProductInfo06 = new AdditionalProductInfo();
        additionalProductInfo06.setName("에코백 중고 팝니다.");
        additionalProductInfo06.setDescription("집에서만 사용해서 상태 깨끗합니다. 문고리 거래합니다.");
        additionalProductInfo06.setSellerName("karias104");
        ArrayList<String> imageUrlPathList06 = new ArrayList<>();
        imageUrlPathList06.add("https://thumbs2.imgbox.com/c9/39/x4zain2n_t.jpeg");
        additionalProductInfo06.setImageURLPathList(imageUrlPathList06);
        additionalProductInfo06.setPrice(1000);
        additionalProductInfo06.setBigCategory(getString(R.string.title_cheap_product));
        additionalProductInfo06.setMidCategory(getString(R.string.title_category_merchandise));
        additionalProductInfo06.setSmallCategory(getString(R.string.title_category_backpack));
        additionalProductInfo06.setItemCount(1);

        AdditionalProductInfo additionalProductInfo07 = new AdditionalProductInfo();
        additionalProductInfo07.setName("캘리그래피달력 팝니다.");
        additionalProductInfo07.setDescription("1~12월까지 직접 그린 캘리그래피달력 팝니다. 문고리 거래합니다.");
        additionalProductInfo07.setSellerName("diane");
        ArrayList<String> imageUrlPathList07 = new ArrayList<>();
        imageUrlPathList07.add("https://thumbs2.imgbox.com/6d/72/3GuQ0ja7_t.jpeg");
        additionalProductInfo07.setImageURLPathList(imageUrlPathList07);
        additionalProductInfo07.setPrice(1000);
        additionalProductInfo07.setBigCategory(getString(R.string.title_cheap_product));
        additionalProductInfo07.setMidCategory(getString(R.string.title_category_accessaries));
        additionalProductInfo07.setSmallCategory(getString(R.string.title_category_etc_accessaries));
        additionalProductInfo07.setItemCount(1);

        productItemList.add(additionalProductInfo01);
        productItemList.add(additionalProductInfo02);
        productItemList.add(additionalProductInfo03);
        productItemList.add(additionalProductInfo04);
        productItemList.add(additionalProductInfo05);
        productItemList.add(additionalProductInfo06);
        productItemList.add(additionalProductInfo07);

        StartAnimations();

//        try {
//            getViewModelProvider().get(GenieMarketViewModel.class)
//                    .requestProductInfo(this, 10000)
//                    .observe((LifecycleOwner) this, newResProductInfo -> {
//                        if (newResProductInfo != null &&
//                                newResProductInfo.getAdditionalProductInfo() != null) {
//                            productItemList.addAll(newResProductInfo.getAdditionalProductInfo());
//
//                            for (AdditionalProductInfo additionalProductInfo : productItemList) {
//                                String productImg = additionalProductInfo.getProductImg();
//                                getProductImages(
//                                        additionalProductInfo,
//                                        "https://images.app.goo.gl/gF3LtnWA2ypG99oE9",
//                                        this.getCacheDir().toString(),
//                                        productImg);
//                            }
//                        }else{
//                            productItemList = new ArrayList<>();
//                            AdditionalProductInfo additionalProductInfo01 = new AdditionalProductInfo();
//                            additionalProductInfo01.setName("Test Name01");
//                            additionalProductInfo01.setDescription("Test Desc01");
//                            additionalProductInfo01.setSellerName("Test Seller01");
//                            additionalProductInfo01.setProductImg("productimg01.jpg");
//                            additionalProductInfo01.setPrice(12345);
//                            additionalProductInfo01.setBigCategory(getString(R.string.title_best_product));
//                            additionalProductInfo01.setMidCategory(getString(R.string.title_category_clothes));
//                            additionalProductInfo01.setSmallCategory(getString(R.string.title_category_shirt));
//                            additionalProductInfo01.setItemCount(1);
//
//                            AdditionalProductInfo additionalProductInfo02 = new AdditionalProductInfo();
//                            additionalProductInfo02.setName("Test Name02");
//                            additionalProductInfo02.setDescription("Test Desc02");
//                            additionalProductInfo02.setSellerName("Test Seller02");
//                            additionalProductInfo02.setProductImg("productimg02.jpg");
//                            additionalProductInfo02.setPrice(7890);
//                            additionalProductInfo02.setBigCategory(getString(R.string.title_cheap_product));
//                            additionalProductInfo02.setMidCategory(getString(R.string.title_category_toys));
//                            additionalProductInfo02.setSmallCategory(getString(R.string.title_category_blocks));
//                            additionalProductInfo02.setItemCount(2);
//
//                            productItemList.add(additionalProductInfo01);
//                            productItemList.add(additionalProductInfo02);
//
//                            for (AdditionalProductInfo additionalProductInfo : productItemList) {
//                                String productImg = additionalProductInfo.getProductImg();
////                                Bitmap bitmap = getProductImages(ConnectionConst.IMAGE_DOWNLOAD_SERVER_URL + productImg);
//                                getProductImages(
//                                        additionalProductInfo,
//                                        "https://images.app.goo.gl/gF3LtnWA2ypG99oE9",
//                                        this.getCacheDir().toString(),
//                                        productImg);
//
//                            }
//
//                        }
//                        StartAnimations();
//                    });
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

}