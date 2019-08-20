package net.clipcodes.myapplication.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import net.clipcodes.myapplication.BuildConfig;
import net.clipcodes.myapplication.ui.ConnectionConst;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Libraries {
    //permission
    public static void requestPermissionStorage(final Activity context) {
        //lay hinh tu dien thoai
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //the second show request permission
            AlertDialog.Builder builderExplain = new AlertDialog.Builder(context);
            builderExplain.setCancelable(false);
            builderExplain.setMessage("This function requires access to storage");
            builderExplain.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE);

                }
            });
            builderExplain.show();
        } else {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE);
        }
    }

    public static void requestPermissionStorageDeny(final Activity context) {
        //deny
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //press not allow
            Toast.makeText(context, "You must allow usage of storage access to use this function", Toast.LENGTH_SHORT).show();
        } else {
            //press never show again

            AlertDialog.Builder builderExplain = new AlertDialog.Builder(context);
            builderExplain.setCancelable(false);
            builderExplain.setMessage("This function requires access to storage\n\nDo you want to enable storage access?");
            builderExplain.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));

                }
            });
            builderExplain.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builderExplain.show();
        }
    }

    public static String getStringFromBitmap(Bitmap bitmapPicture) {
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public static byte[] convertToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public static Bitmap convertToBitmap(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public static int uploadFile(String sourceFileUri, String fileRenamed, String copyPath) {


        int serverResponseCode = -1;
//        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        File sourceFile = new File(sourceFileUri);
        Bitmap bitmap = Libraries.getBitmapFromFile(sourceFile);
        Log.i("비트맵 사이즈(줄이기 전):", + bitmap.getHeight() * bitmap.getWidth() * 4 + " Byte");
        sourceFile = Libraries.saveBitmapToJpeg(bitmap,  copyPath, Libraries.getFileName(sourceFileUri));
        bitmap = Libraries.getBitmapFromFile(sourceFile);
        Log.i("비트맵 사이즈(줄인 후):", bitmap.getHeight() * bitmap.getWidth() * 4 + " Byte");
        if (!sourceFile.isFile()) {
            return 0;
        }else{
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(ConnectionConst.IMAGE_UPLOAD_SERVER_URL);
                // Open a HTTP  connection to  the URL

                conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true); // Allow Inputs

                conn.setDoOutput(true); // Allow Outputs

                conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Connection", "Keep-Alive");

                conn.setRequestProperty("ENCTYPE", "multipart/form-data");

                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                conn.setRequestProperty("uploaded_file", sourceFileUri);


                dos = new DataOutputStream(conn.getOutputStream());



                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""

                        + fileRenamed + "\"" + lineEnd);



                dos.writeBytes(lineEnd);



                // create a buffer of  maximum size

                bytesAvailable = fileInputStream.available();



                bufferSize = Math.min(bytesAvailable, maxBufferSize);

                buffer = new byte[bufferSize];



                // read file and write it into form...

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);



                while (bytesRead > 0) {



                    dos.write(buffer, 0, bufferSize);

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);



                }



                // send multipart form data necesssary after file data...

                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);



                // Responses from the server (code and message)

                serverResponseCode = conn.getResponseCode();

                String serverResponseMessage = conn.getResponseMessage();



                Log.i("uploadFile", "HTTP Response is : "

                        + serverResponseMessage + ": " + serverResponseCode);



                if(serverResponseCode == 200){

                }



                //close the streams //

                fileInputStream.close();

                dos.flush();

                dos.close();



            } catch (MalformedURLException ex) {
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

            } catch (Exception e) {
                Log.e("Upload Exception", "Exception : "

                        + e.getMessage(), e);

            }
            return serverResponseCode;
        } // End else block

    }

    public static String getFileExtensions(String fileFullPath){
        String ext = null;
        if(fileFullPath == null || fileFullPath.isEmpty()){
            return ext;
        }

        File file = new File(fileFullPath);
        int startIdx = file.getPath().indexOf(".");
        int endIdx = file.getPath().length();
        ext = file.getPath().substring(startIdx, endIdx);
        return ext;

    }

    public static String getFileRootPath(String fileFullPath){
        if(fileFullPath == null || fileFullPath.isEmpty()){
            return null;
        }

        int startIdx = 0;
        int endIdx = fileFullPath.indexOf(getFileName(fileFullPath));
        fileFullPath = fileFullPath.substring(startIdx, endIdx);
        return fileFullPath;
    }

    public static String getFileName(String fileFullPath){
        if(fileFullPath == null || fileFullPath.isEmpty()){
            return null;
        }

        File file = new File(fileFullPath);
        return file.getName();
    }

    public Bitmap readImageWithSampling(String imagePath, int targetWidth, int targetHeight) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);

        int photoWidth  = bmOptions.outWidth;
        int photoHeight = bmOptions.outHeight;

        if (targetHeight <= 0) {
            targetHeight = (targetWidth * photoHeight) / photoWidth;
        }

        // Determine how much to scale down the image
        int scaleFactor = 1;
        if (photoWidth > targetWidth) {
            scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight);
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }

    public static List<Bitmap> getBitmapsFromCacheDir(String cacheDir, List<String> imagePathURLList){
        List<Bitmap> bitmapList = new ArrayList<Bitmap>();
        List<File> jpegFlieList = getJpegsFromCacheDir(cacheDir, imagePathURLList);
        for(File file : jpegFlieList){
            bitmapList.add(getBitmapFromFile(file));
        }

        return bitmapList;
    }

    public static List<File> getJpegsFromCacheDir(String cacheDir, List<String> imagePathURLList){
        List<File> jpegFlieList = new ArrayList<File>();
        for(String key : imagePathURLList){
            File file = new File(key);
            file = getJpegFromCacheDir(cacheDir, file.getName());
            if(file != null){
                jpegFlieList.add(file);
            }
        }
        return jpegFlieList;
    }

    public static File getJpegFromCacheDir(String cacheDir, String fileName){
        File file = new File(cacheDir);
        File[] files = file.listFiles();
        for(File tempFile : files){
            if(tempFile.getName().equals(fileName)){
                return tempFile;
            }
        }
        return null;
    }
    public static File saveBitmapToJpeg(Bitmap bitmap, String cacheDir, String fileName) {
        File tempFile = null;
        boolean result = false;


        //storage 에 파일 인스턴스를 생성합니다.
        tempFile = new File(cacheDir, fileName);
        if(tempFile.exists()){
           return tempFile;
        }

        try {

            // 자동으로 빈 파일을 생성합니다.
            tempFile.canWrite();
            result =  tempFile.createNewFile();
            Log.i("result: ", result+"");
            // 파일을 쓸 수 있는 스트림을 준비합니다.
            FileOutputStream out = new FileOutputStream(tempFile);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap = bitmap.createScaledBitmap(bitmap, 500, 500, true);
            result = bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out);

            Log.i("result: ", result+"");
            // 스트림 사용후 닫아줍니다.
            out.close();

        } catch (FileNotFoundException e) {
            Log.e("MyTag","FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            Log.e("MyTag","IOException : " + e.getMessage());
        }
        return tempFile;
    }

    public static Bitmap getBitmapFromFile(File imgFile){
        if(imgFile.exists()){
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return null;
    }

    // 프로젝트의 해시키를 반환

    @Nullable

    public static String getHashKey(Context context) {

        final String TAG = "KeyHash";

        String keyHash = null;

        try {

            PackageInfo info =

                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);



            for (Signature signature : info.signatures) {

                MessageDigest md;

                md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());

                keyHash = new String(Base64.encode(md.digest(), 0));

                Log.d(TAG, keyHash);

            }

        } catch (Exception e) {

            Log.e("name not found", e.toString());

        }



        if (keyHash != null) {

            return keyHash;

        } else {

            return null;

        }

    }
}