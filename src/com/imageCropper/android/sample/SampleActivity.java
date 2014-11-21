package com.imageCropper.android.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.imageCropper.android.R;
import com.imageCropper.android.liabrary.ImageCropperView;
import com.imageCropper.android.liabrary.ImageCroppingCallback;
import com.imageCropper.android.liabrary.Status;

public class SampleActivity extends Activity implements ImageCroppingCallback {
    private View mContentView = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(this).inflate(R.layout.main, null);
        Bitmap croppingImage = BitmapFactory.decodeResource(getResources(), R.drawable.images);
        ImageCropperView imageCropperView = new ImageCropperView(this, croppingImage, this);
        setContentView(mContentView);
    }

    @Override
    public void onDone(Status status, Exception exception, Bitmap croppedImage) {
        if (croppedImage != null && mContentView != null) {
            ImageView mainImageView = (ImageView) mContentView.findViewById(R.id.img_main_test);
            if (mainImageView != null) {
                mainImageView.setImageBitmap(croppedImage);
            }
        }
    }
}
