package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.HashMap;
import java.util.Map;

public class ImageRepository {

    private String imageUrl;
    private static boolean isInitialized = false;

    public ImageRepository(Context context) {
        if (!isInitialized) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "dmtsek7j7");
            config.put("api_key", "264587117157862");
            config.put("api_secret", "sIfXmNyf87WSxkgIiWk3uhjLJNg");
            MediaManager.init(context, config);
            isInitialized = true;
        }
    }

 public void uploadImage(Uri imagePath, ImageUploadCallback callback){



     MediaManager.get().upload(imagePath).callback(new UploadCallback() {

         @Override
         public void onStart(String requestId) {
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "1 - COMENZÓ A CARGAR LA IMAGEN");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
         }

         @Override
         public void onProgress(String requestId, long bytes, long totalBytes) {
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "2 - CARGANDO LA IMAGEN");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
         }

         @Override
         public void onSuccess(String requestId, Map resultData) {
             imageUrl = (String) resultData.get("url");
             callback.onSuccess(imageUrl);
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "3 - CARGA DE IMAGEN EXITOSA - URL: " + imageUrl);
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
         }

         @Override
         public void onError(String requestId, ErrorInfo error) {

             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "ERROR AL CARGAR LA IMAGEN");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
         }

         @Override
         public void onReschedule(String requestId, ErrorInfo error) {
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "ON RESCHEDULE");
             Log.d("LOG", "---------------------------");
             Log.d("LOG", "---------------------------");
         }
     }).dispatch();


 }

    public interface ImageUploadCallback {
        void onSuccess(String imageUrl);

    }





}
