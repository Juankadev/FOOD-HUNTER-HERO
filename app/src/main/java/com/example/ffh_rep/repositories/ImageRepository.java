package com.example.ffh_rep.repositories;

import android.content.Context;
import android.net.Uri;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.callback.UploadResult;
import com.cloudinary.android.callback.UploadStatus;
import com.cloudinary.utils.ObjectUtils;

import java.util.Map;

public class ImageRepository {

    private Cloudinary cloudinary;
    public ImageRepository(Context context) {
        MediaManager.init(context);

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dmtsek7j7",
                "api_key", "264587117157862",
                "api_secret", "sIfXmNyf87WSxkgIiWk3uhjLJNg"));
    }


    public void uploadImage(Uri imagePath, ImageUploadCallback callback) {
        MediaManager.get().upload(imagePath)
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {

                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                })
                .dispatch();
    }

    public interface ImageUploadCallback {
        void onSuccess(String imageUrl);
        void onError(String errorMessage);
    }
}
