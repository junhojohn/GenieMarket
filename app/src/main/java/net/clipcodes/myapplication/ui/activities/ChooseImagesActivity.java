package net.clipcodes.myapplication.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.clipcodes.myapplication.R;
import net.clipcodes.myapplication.models.Picture;
import net.clipcodes.myapplication.models.AdditionalProductInfo;
import net.clipcodes.myapplication.ui.adapters.LocalGalleryImageListAdapter;
import net.clipcodes.myapplication.utils.ConstantDataManager;
import net.clipcodes.myapplication.utils.Libraries;
import net.clipcodes.myapplication.utils.RegisterRequest;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    private String upLoadServerUri = null;
    final String uploadFilePath = "storage/emulated/0/DCIM/새 폴더 (6)/";//경로를 모르겠으면, 갤러리 어플리케이션 가서 메뉴->상세 정보

    final String uploadFileName = "독일출장_왕복항공권_기안_와이페이모어.PNG"; //전송하고자하는 파일 이름


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
//                    Log.e("RESULT", "productInfo : " + productInfo.getName() + ", "  + productInfo.getPrice() + ", " + productInfo.getSellerName() + ", " + productInfo.getItemCount() + ", " + productInfo.getDescription());
//                    final ArrayList<Picture> selectedPictures = adapter.getAllPictureSelected();
//                    for(Picture picture : selectedPictures){
//                        Log.e("RESULT", "picuture: " + picture.getPath() + ", "  + picture.getPosition() + ", " + picture.getSelectCount());
//                    }

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
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

                    RegisterRequest registerRequest = new RegisterRequest(productInfo.getName(), productInfo.getPrice(), productInfo.getItemCount(), productInfo.getDescription(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ChooseImagesActivity.this);
                    queue.add(registerRequest);

                    break;
                case R.id.btn_back2:
                    finish();
                    break;
            }


        }
    };
}


//package net.clipcodes.myapplication.ui.activities;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Looper;
//import android.support.annotation.NonNull;
//import android.support.constraint.ConstraintLayout;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import net.clipcodes.myapplication.R;
//import net.clipcodes.myapplication.models.Picture;
//import net.clipcodes.myapplication.models.AdditionalProductInfo;
//import net.clipcodes.myapplication.ui.adapters.LocalGalleryImageListAdapter;
//import net.clipcodes.myapplication.utils.ConstantDataManager;
//import net.clipcodes.myapplication.utils.Libraries;
//
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//
//public class ChooseImagesActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private ArrayList<Picture> pictures;
//    LocalGalleryImageListAdapter adapter;
//    Handler handler;
//
//    private ImageView imageViewButtonSend;
//    private TextView textViewSelectedCount;
//    private ConstraintLayout constraintLayout;
//    private AdditionalProductInfo productInfo;
//    Toolbar toolbar;
//    Button btnFinish;
//    Button btnBack;
//    private int serverResponseCode = 0;
//    private ProgressDialog dialog = null;
//    private String upLoadServerUri = null;
//    final String uploadFilePath = "storage/emulated/0/DCIM/새 폴더 (6)/";//경로를 모르겠으면, 갤러리 어플리케이션 가서 메뉴->상세 정보
//
//    final String uploadFileName = "독일출장_왕복항공권_기안_와이페이모어.PNG"; //전송하고자하는 파일 이름
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        productInfo = (AdditionalProductInfo)getIntent().getSerializableExtra("productInfo");
//        Log.e("RESULT", "productInfo : " + productInfo.getName() + ", "  + productInfo.getPrice() + ", " + productInfo.getSellerName() + ", " + productInfo.getItemCount() + ", " + productInfo.getDescription());
//        setContentView(R.layout.activity_choose_images);
//
//        toolbar = findViewById(R.id.choose_image_toolbar);
//        setSupportActionBar(toolbar);
//        setTheme(R.style.AppTheme_Cursor);
//
//        btnFinish = findViewById(R.id.btn_finish);
//        btnBack = findViewById(R.id.btn_back2);
//
//        btnFinish.setOnClickListener(clik);
//        btnBack.setOnClickListener(clik);
//
//        imageViewButtonSend = findViewById(R.id.button_send);
//        textViewSelectedCount = findViewById(R.id.textViewSeletedCount);
//        constraintLayout = findViewById(R.id.layoutSend);
//
//        imageViewButtonSend.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        pictures = new ArrayList<Picture>();
//        recyclerView = findViewById(R.id.recyclerViewGallery);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        adapter = new LocalGalleryImageListAdapter(this, pictures, new LocalGalleryImageListAdapter.ItemSelectedChangeListener() {
//            @Override
//            public void onItemSelectedChange(int number) {
//                if(number > 0)     {
//                    constraintLayout.setVisibility(View.VISIBLE);
//                    textViewSelectedCount.setText(number+"");
//                }else{
//                    constraintLayout.setVisibility(View.GONE);
//                }
//            }
//        });
//        recyclerView.setAdapter(adapter);
//
//        handler = new Handler();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(ChooseImagesActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            // request permission runtime
//            Libraries.requestPermissionStorage(ChooseImagesActivity.this);
//        }else{
//            // permission allow
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    Looper.prepare();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            pictures.clear();
//                            pictures.addAll(Picture.getGalleryPhotos(ChooseImagesActivity.this));
//                            adapter.notifyDataSetChanged();
//                        }
//                    });
//                    Looper.loop();
//                }
//            }).start();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE){
//            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Looper.prepare();
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                pictures.clear();
//                                pictures.addAll(Picture.getGalleryPhotos(ChooseImagesActivity.this));
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                        Looper.loop();
//                    }
//                }).start();
//            }else{
//                Libraries.requestPermissionStorageDeny(ChooseImagesActivity.this);
//            }
//        }
//    }
//
//    public View.OnClickListener clik = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.btn_finish:
//                    Log.e("RESULT", "productInfo : " + productInfo.getName() + ", "  + productInfo.getPrice() + ", " + productInfo.getSellerName() + ", " + productInfo.getItemCount() + ", " + productInfo.getDescription());
//                    final ArrayList<Picture> selectedPictures = adapter.getAllPictureSelected();
//                    for(Picture picture : selectedPictures){
//                        Log.e("RESULT", "picuture: " + picture.getPath() + ", "  + picture.getPosition() + ", " + picture.getSelectCount());
//                    }
//
////                    upLoadServerUri = "http://10.0.2.2:8080/UploadToServer.php";//서버컴퓨터의 ip주소
////                    upLoadServerUri = "http://192.168.200.52/UploadToServer.php";//서버컴퓨터의 ip주소
//                    upLoadServerUri = "http://10.0.2.2/UploadToServer.php";//서버컴퓨터의 ip주소
//
//                    dialog = ProgressDialog.show(ChooseImagesActivity.this, "", "Uploading file...", true);
//                    new Thread(new Runnable() {
//
//                        public void run() {
//
////                            runOnUiThread(new Runnable() {
////                                public void run() {
////                                    messageText.setText("uploading started.....");
////                                }
////                            });
//                            for(Picture picture : selectedPictures){
//                                Log.e("RESULT", "picuture: " + picture.getPath() + ", "  + picture.getPosition() + ", " + picture.getSelectCount());
//                                uploadFile(uploadFilePath + "" + uploadFileName);
//                            }
//
//                        }
//                    }).start();
//                    break;
//                case R.id.btn_back2:
//                    finish();
//                    break;
//            }
//
//
//        }
//    };
//
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        if(item.getItemId() == android.R.id.home){
////            finish();
////        }
////        return super.onOptionsItemSelected(item);
////    }
//
//    public int uploadFile(String sourceFileUri) {
//
//
//
//        String fileName = sourceFileUri;
//
//
//
//        HttpURLConnection conn = null;
//
//        DataOutputStream dos = null;
//
//        String lineEnd = "\r\n";
//
//        String twoHyphens = "--";
//
//        String boundary = "*****";
//
//        int bytesRead, bytesAvailable, bufferSize;
//
//        byte[] buffer;
//
//        int maxBufferSize = 1 * 1024 * 1024;
//
//        File sourceFile = new File(sourceFileUri);
//
//
//
//        if (!sourceFile.isFile()) {
//
//
//
//            dialog.dismiss();
//
//
//
//            Log.e("uploadFile", "Source File not exist :"
//
//                    +uploadFilePath + "" + uploadFileName);
//
//
//
//            runOnUiThread(new Runnable() {
//
//                public void run() {
//                    Toast.makeText(ChooseImagesActivity.this, "Source File not exist :"
//
//                                    +uploadFilePath + "" + uploadFileName,
//
//                            Toast.LENGTH_SHORT).show();
//
//                }
//
//            });
//
//
//
//            return 0;
//
//
//
//        }
//
//        else
//
//        {
//
//            try {
//
//
//
//                // open a URL connection to the Servlet
//
//                FileInputStream fileInputStream = new FileInputStream(sourceFile);
//
//                URL url = new URL(upLoadServerUri);
//
//
//
//                // Open a HTTP  connection to  the URL
//
//                conn = (HttpURLConnection) url.openConnection();
//
//                conn.setDoInput(true); // Allow Inputs
//
//                conn.setDoOutput(true); // Allow Outputs
//
//                conn.setUseCaches(false); // Don't use a Cached Copy
//
//                conn.setRequestMethod("POST");
//
//                conn.setRequestProperty("Connection", "Keep-Alive");
//
//                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//
//                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//                conn.setRequestProperty("uploaded_file", fileName);
//
//
//
//                dos = new DataOutputStream(conn.getOutputStream());
//
//
//
//                dos.writeBytes(twoHyphens + boundary + lineEnd);
//
//                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
//
//                        + fileName + "\"" + lineEnd);
//
//
//
//                dos.writeBytes(lineEnd);
//
//
//
//                // create a buffer of  maximum size
//
//                bytesAvailable = fileInputStream.available();
//
//
//
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//
//                buffer = new byte[bufferSize];
//
//
//
//                // read file and write it into form...
//
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//
//
//                while (bytesRead > 0) {
//
//
//
//                    dos.write(buffer, 0, bufferSize);
//
//                    bytesAvailable = fileInputStream.available();
//
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//
//
//                }
//
//
//
//                // send multipart form data necesssary after file data...
//
//                dos.writeBytes(lineEnd);
//
//                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//
//
//                // Responses from the server (code and message)
//
//                serverResponseCode = conn.getResponseCode();
//
//                String serverResponseMessage = conn.getResponseMessage();
//
//
//
//                Log.i("uploadFile", "HTTP Response is : "
//
//                        + serverResponseMessage + ": " + serverResponseCode);
//
//
//
//                if(serverResponseCode == 200){
//
//
//
//                    runOnUiThread(new Runnable() {
//
//                        public void run() {
//
//
//
//                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
//
//                                    +uploadFileName;
//
//                            Toast.makeText(ChooseImagesActivity.this, "File Upload Complete." + msg,
//
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    });
//
//                }
//
//
//
//                //close the streams //
//
//                fileInputStream.close();
//
//                dos.flush();
//
//                dos.close();
//
//
//
//            } catch (MalformedURLException ex) {
//
//
//
//                dialog.dismiss();
//
//                ex.printStackTrace();
//
//
//
//                runOnUiThread(new Runnable() {
//
//                    public void run() {
//
////                        messageText.setText("MalformedURLException Exception : check script url.");
//
//                        Toast.makeText(ChooseImagesActivity.this, "MalformedURLException",
//
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//
//                });
//
//
//
//                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
//
//            } catch (Exception e) {
//
//
//
//                dialog.dismiss();
//
//                e.printStackTrace();
//
//
//
//                runOnUiThread(new Runnable() {
//
//                    public void run() {
//
////                        messageText.setText("Got Exception : see logcat ");
//
//                        Toast.makeText(ChooseImagesActivity.this, "Got Exception : see logcat ",
//
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//
//                });
//
//                Log.e("Upload Exception", "Exception : "
//
//                        + e.getMessage(), e);
//
//            }
//
//            dialog.dismiss();
//
//            return serverResponseCode;
//
//
//
//        } // End else block
//
//    }
//
//}
