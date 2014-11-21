package com.imageCropper.android.liabrary;

import android.graphics.Bitmap;

public interface CropperCallback {
    public void onDone(Status status, Exception exception, Bitmap croppedImage);

}
