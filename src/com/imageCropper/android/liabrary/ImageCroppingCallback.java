package com.imageCropper.android.liabrary;

import android.graphics.Bitmap;

public interface ImageCroppingCallback {
    public void onDone(Status status, Exception exception, Bitmap croppedImage);

}
