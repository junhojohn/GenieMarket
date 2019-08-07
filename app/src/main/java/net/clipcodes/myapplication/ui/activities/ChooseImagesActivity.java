package net.clipcodes.myapplication.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.Picture;
import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.ConnectionConst;
import net.clipcodes.myapplication.ui.adapters.LocalGalleryImageListAdapter;
import net.clipcodes.myapplication.utils.ConstantDataManager;
import net.clipcodes.myapplication.utils.Libraries;
import net.clipcodes.myapplication.utils.RegisterRequest;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;

public class ChooseImagesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Picture> pictures;
    LocalGalleryImageListAdapter adapter;
    Handler handler;

    private ImageView imageViewButtonSend;
    private TextView textViewSelectedCount;
    private ConstraintLayout constraintLayout;
    private AdditionalProductInfo productInfo;
    Toolbar toolbar;
    Button btnFinish;
    Button btnBack;
    private String productImage1 = null;
    private String fileRenamed = null;
    private boolean isProductRegisterSuccess = false;
    private int serverReturnCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productInfo = (AdditionalProductInfo)getIntent().getSerializableExtra("productInfo");
        Log.e("RESULT", "productInfo : " + productInfo.getName() + ", "  + productInfo.getPrice() + ", " + productInfo.getSellerName() + ", " + productInfo.getItemCount() + ", " + productInfo.getDescription());
        setContentView(R.layout.activity_choose_images);

        toolbar = findViewById(R.id.choose_image_toolbar);
        setSupportActionBar(toolbar);
        setTheme(R.style.AppTheme_Cursor);

        btnFinish = findViewById(R.id.btn_finish);
        btnBack = findViewById(R.id.btn_back2);

        btnFinish.setOnClickListener(clik);
        btnBack.setOnClickListener(clik);

        imageViewButtonSend = findViewById(R.id.button_send);
        textViewSelectedCount = findViewById(R.id.textViewSeletedCount);
        constraintLayout = findViewById(R.id.layoutSend);

        imageViewButtonSend.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

        pictures = new ArrayList<Picture>();
        recyclerView = findViewById(R.id.recyclerViewGallery);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new LocalGalleryImageListAdapter(this, pictures, new LocalGalleryImageListAdapter.ItemSelectedChangeListener() {
            @Override
            public void onItemSelectedChange(int number) {
                if(number > 0)     {
                    constraintLayout.setVisibility(View.VISIBLE);
                    textViewSelectedCount.setText(number+"");
                }else{
                    constraintLayout.setVisibility(View.GONE);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        handler = new Handler();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(ChooseImagesActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // request permission runtime
            Libraries.requestPermissionStorage(ChooseImagesActivity.this);
        }else{
            // permission allow
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pictures.clear();
                            pictures.addAll(Picture.getGalleryPhotos(ChooseImagesActivity.this));
                            adapter.notifyDataSetChanged();
                        }
                    });
                    Looper.loop();
                }
            }).start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Looper.prepare();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                pictures.clear();
                                pictures.addAll(Picture.getGalleryPhotos(ChooseImagesActivity.this));
                                adapter.notifyDataSetChanged();
                            }
                        });
                        Looper.loop();
                    }
                }).start();
            }else{
                Libraries.requestPermissionStorageDeny(ChooseImagesActivity.this);
            }
        }
    }

    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_finish:
                    Log.e("RESULT", "productInfo : " + productInfo.getName() + ", "  + productInfo.getPrice() + ", " + productInfo.getSellerName() + ", " + productInfo.getItemCount() + ", " + productInfo.getDescription());
                    final ArrayList<Picture> selectedPictures = adapter.getAllPictureSelected();
                    for(Picture picture : selectedPictures){
                        Log.e("RESULT", "picuture: " + picture.getPath() + ", "  + picture.getPosition() + ", " + picture.getSelectCount());
                        try{
                            productImage1 = picture.getPath();
                        }catch (Exception e){
                            Log.e("error:", e.getMessage());
                        }
                    }

                    final  Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                isProductRegisterSuccess = jsonResponse.getBoolean(ConnectionConst.RETURN_JSON_IS_PRODUCT_REGISTER_SUCCESS);

                                if(serverReturnCode == 200 && isProductRegisterSuccess){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChooseImagesActivity.this);
                                    builder.setMessage("상품등록에 성공했습니다.").setPositiveButton("확인", null).create().show();
                                    Intent intent = new Intent(ChooseImagesActivity.this, MainActivity.class);
                                    ChooseImagesActivity.this.startActivity(intent);
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChooseImagesActivity.this);
                                    builder.setMessage("상품등록에 실패했습니다.").setNegativeButton("다시 시도", null).create().show();
                                }
                            }catch (Exception e){
                                Log.d("http protocol:", e.getMessage());
                            }
                        }
                    };

                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            fileRenamed = productInfo.getSellerName() + "_" + System.currentTimeMillis() + Libraries.getFileExtensions(productImage1);
                            serverReturnCode = Libraries.uploadFile(productImage1, fileRenamed);
                            RegisterRequest productRegisterRequest = new RegisterRequest(productInfo.getName(), productInfo.getPrice(), productInfo.getItemCount(), productInfo.getDescription(), productInfo.getSellerName(), fileRenamed, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ChooseImagesActivity.this);
                            queue.add(productRegisterRequest);
                        }
                    };
                    thread.start();
                    break;
                case R.id.btn_back2:
                    finish();
                    break;
            }
        }



    };
}