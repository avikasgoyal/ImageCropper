package com.imageCropper.android.liabrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.view.*;
import android.widget.ImageView;
import com.imageCropper.android.R;

/**
 * ImageCropper provide a view for image cropping
 * user can give a bitmap to view image cropper manages its cropping
 * and provide a cropped image in @link{ImageCroppingCallback} interface
 * <p/>
 * Created by Avi Goyal on 11/21/2014.
 */
public class ImageCropperView implements View.OnClickListener {
    private final WindowManager mWindowManager;
    private ImageCroppingCallback mCallbackListener;
    private Bitmap mOriginalImage = null;
    private Context mContext;
    private View mView;


    public ImageCropperView(Context context, Bitmap croppingImage, ImageCroppingCallback callback) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mOriginalImage = croppingImage.copy(Bitmap.Config.ALPHA_8, true);
        mContext = context;
        mCallbackListener = callback;
        initView();
    }

    private void initView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, PixelFormat.TRANSPARENT);

        mView = LayoutInflater.from(mContext).inflate(R.layout.image_cropper_layout, null);
        ImageView imageView = (ImageView) mView.findViewById(R.id.img_image_cropper_cropper);
        if (imageView != null) {
            imageView.setImageBitmap(mOriginalImage);
        }
        View okButton = mView.findViewById(R.id.btn_image_cropper_ok);
        if (okButton != null) {
            okButton.setOnClickListener(this);
        }
        View cancelButton = mView.findViewById(R.id.btn_image_cropper_cancel);
        if (cancelButton != null) {
            cancelButton.setOnClickListener(this);
        }
        mWindowManager.addView(mView, params);
    }

    public void removeView() {
        mWindowManager.removeView(mView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image_cropper_cancel:
                removeView();
                break;
            case R.id.btn_image_cropper_ok:
                removeView();
                break;
        }
    }
}


