package com.imageCropper.android.liabrary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Holds a drawing buffer for views to save their drawn state and render it onto a canvas.
 * Use it to hold bitmaps that are generated once on view initialization and drawn on view canvas there after.
 */
public class DrawLayer {
    private static final float BITMAP_SCALE = 0.5f;
    private final Paint mPaint = new Paint();
    private final Canvas mCanvas;
    private Matrix mMatrix = new Matrix();
    private Matrix mMatrix2 = new Matrix();
    private Matrix mMatrix3 = new Matrix();
    private Bitmap mBitmap;
    private float mWidth;
    private float mHeight;
    private float mHalfWidth;
    private float mHalfHeight;

    /**
     * Create a new draw layer.
     */
    public DrawLayer() {
        mCanvas = new Canvas();
        mMatrix.preScale(BITMAP_SCALE, BITMAP_SCALE);
        mMatrix2.preScale(1f / BITMAP_SCALE, 1f / BITMAP_SCALE);
        mPaint.setFilterBitmap(true);
    }

    /**
     * Get canvas to draw on this layer.
     * Note : The Canvas may or may not have a backing bitmap if layer is not initialized or {@link #destroy()} has
     * been called. Call {@link #init(int, int)} to ensure a bitmap is created.
     *
     * @return Canvas.
     */
    public Canvas getCanvas() {
        return mCanvas;
    }

    /**
     * Draw this layer onto given canvas.
     *
     * @param canvas canvas to draw layer onto.
     */
    public void draw(Canvas canvas) {
        if (canvas!= null && mBitmap != null && !mBitmap.isRecycled()) {
            canvas.drawBitmap(mBitmap, mMatrix2, mPaint);
        }
    }

    /**
     * Draw the layer centered at given xy coordinates.
     *
     * @param canvas canvas to draw layer onto.
     * @param x      X coordinate.
     * @param y      Y coordinate.
     */
    public void drawCentered(Canvas canvas, float x, float y) {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mMatrix3.set(mMatrix2);
            mMatrix3.postTranslate(x - mHalfWidth, y - mHalfHeight);
            canvas.drawBitmap(mBitmap, mMatrix3, mPaint);
        }
    }

    /**
     * Initialise the layer.
     *
     * @param width  width in pixels, must be > 0.
     * @param height height in pixels, must be > 0.
     * @see #destroy()
     */
    public void init(int width, int height) {
        destroy();
        mWidth = width;
        mHeight = height;
        mHalfWidth = width / 2f;
        mHalfHeight = height / 2f;
        mBitmap = Bitmap.createBitmap((int) (width * BITMAP_SCALE), (int) (height * BITMAP_SCALE), Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        mCanvas.setMatrix(mMatrix);
    }

    /**
     * Releases the backing bitmap.
     * Draw operations will do nothing after this call.
     *
     * @see #init(int, int)
     */
    public void destroy() {
        mWidth = 0;
        mHeight = 0;
        mHalfWidth = 0;
        mHalfHeight = 0;
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
            mCanvas.setBitmap(null);
        }
    }

    /**
     * Check if layer has a bitmap for drawing.
     *
     * @return true if bitmap exists, false otherwise.
     */
    public boolean isInitialized() {
        return mBitmap != null && !mBitmap.isRecycled();
    }

    /**
     * Get layer width.
     *
     * @return width of layer when initialised. 0 is layer not initialised or destroyed.
     */
    public float getWidth() {
        return mWidth;
    }

    /**
     * Get layer height.
     *
     * @return height of layer when initialised. 0 is layer not initialised or destroyed.
     */
    public float getHeight() {
        return mHeight;
    }

    /**
     * Set opacity of layer.
     *
     * @param alpha value from 0.0 (transparent) to 1.0 (opaque).
     */
    public void setAlpha(float alpha) {
        int rgbAlpha = Math.round(255f * alpha);
        rgbAlpha = Math.min(255, rgbAlpha);
        rgbAlpha = Math.max(0, rgbAlpha);
        mPaint.setAlpha(rgbAlpha);
    }


    public void clearLayer(){
        float width = mWidth;
        float height = mHeight;
        destroy();
        mWidth = width;
        mHeight = height;
        mHalfWidth = width / 2f;
        mHalfHeight = height / 2f;
        mBitmap = Bitmap.createBitmap((int) (width * BITMAP_SCALE), (int) (height * BITMAP_SCALE), Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        mCanvas.setMatrix(mMatrix);
    }
}
