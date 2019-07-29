package net.clipcodes.myapplication.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
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

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.Picture;
import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.adapters.GalleryItemAdapter;
import net.clipcodes.myapplication.utils.ConstantDataManager;
import net.clipcodes.myapplication.utils.Libraries;

import java.util.ArrayList;

public class ChooseImagesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Picture> pictures;
    GalleryItemAdapter adapter;
    Handler handler;

    private ImageView imageViewButtonSend;
    private TextView textViewSelectedCount;
    private ConstraintLayout constraintLayout;
    private AdditionalProductInfo productInfo;
    Toolbar toolbar;
    Button btnFinish;
    Button btnBack;

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
        adapter = new GalleryItemAdapter(this, pictures, new GalleryItemAdapter.ItemSelectedChangeListener() {
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
                    for(Picture picture : pictures){
                        Log.e("RESULT", "picuture: " + picture.getPath() + ", "  + picture.getPosition() + ", " + picture.getSelectCount());
                    }

                    break;
                case R.id.btn_back2:
                    finish();
                    break;
            }


        }
    };

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
