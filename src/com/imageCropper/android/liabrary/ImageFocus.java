package com.imageCropper.android.liabrary;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.imageCropper.android.liabrary.animation.SimpleAnim;

public class ImageFocus extends View {
    private static final long ANIM_DURATION = 800L;
    private static final PorterDuffXfermode CLEAR_CIRCLE = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    private DrawLayer mDrawLayer;
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mFocusRadius = 0;
    private int mFocusBorderRadius = mFocusRadius + 2;
    private Paint mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean mIsAnimating = false;
    private PointF mFocusCenter = new PointF();


    /**
     * create a new instance of SmartGuide view which provide a view with circle move with animation to any view on screen.
     *
     * @param context
     */
    public ImageFocus(Context context) {
        super(context);
        if (!isInEditMode()) {
            initView();
        }
    }

    public ImageFocus(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initView();
        }
    }

    public ImageFocus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initView();
        }
    }

    private void initView() {
        mBackgroundPaint.setColor(Color.argb(100, 0, 0, 0));
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setXfermode(CLEAR_CIRCLE);
        mCirclePaint.setAntiAlias(true);

        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(1);
        mBorderPaint.setAntiAlias(true);

        mDrawLayer = new DrawLayer();
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDrawLayer.destroy();
    }

    /**
     *
     */
    public void moveImageFocus() {
        MoveCircleAnim moveAnim = new MoveCircleAnim(100);
        moveAnim.setFillAfter(true);
        startAnimation(moveAnim);
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minIn = Math.min(getMeasuredHeight(), getMeasuredWidth());
        mFocusRadius = minIn / 3;
        mFocusCenter.set(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDrawLayer();
        mDrawLayer.draw(canvas);
        canvas.drawRect(mFocusCenter.x - mFocusBorderRadius, mFocusCenter.y - mFocusBorderRadius,
                mFocusCenter.x + mFocusBorderRadius, mFocusCenter.y + mFocusBorderRadius, mBorderPaint);
        canvas.drawRect(mFocusCenter.x - mFocusRadius, mFocusCenter.y - mFocusRadius,
                mFocusCenter.x + mFocusRadius, mFocusCenter.y + mFocusRadius, mBorderPaint);

    }

    private void initDrawLayer() {
        if (!mDrawLayer.isInitialized()) {
            Rect rect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
            mDrawLayer.init(getMeasuredWidth(), getMeasuredHeight());
            mDrawLayer.getCanvas().drawRect(rect, mBackgroundPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isTouchInside(event)){
            Log.e("Touch :", "Inside");
        }
        mFocusCenter.set(event.getX(), event.getY());
        return true;
    }

    private boolean isTouchInside(MotionEvent event) {
        RectF currentRect = new RectF(mFocusCenter.x - mFocusRadius, mFocusCenter.y - mFocusRadius,
                mFocusCenter.x + mFocusRadius, mFocusCenter.y + mFocusRadius);
        return (currentRect.contains(event.getX(), event.getY()));
    }

    private class MoveCircleAnim extends SimpleAnim {
        private float mRadiusDiff;

        private MoveCircleAnim(float radiusDiff) {
            mRadiusDiff = radiusDiff;
            this.setInterpolator(new AccelerateDecelerateInterpolator());
            this.setDuration(ANIM_DURATION);
        }

        @Override
        public void onAnimationStart(Animation animation) {
            super.onAnimationStart(animation);
            mIsAnimating = true;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            mPathMeasure.getPosTan((mPathMeasure.getLength() / 2) * (interpolatedTime), mCenterOfCircle, null);
//            setCircleRadius((int) (mOldRadius + (mRadiusDiff * interpolatedTime)));
            super.applyTransformation(interpolatedTime, t);
            invalidate();
        }

        @Override
        public void onEnded() {
            cancelAnim();
            invalidate();
        }
    }

    private void cancelAnim() {
        mIsAnimating = false;
        clearAnimation();
        invalidate();
    }

    /**
     * Setter for property 'focusRadius'.
     *
     * @param focusRadius Value to set for property 'focusRadius'.
     */
    public void setFocusRadius(int focusRadius) {
        mFocusRadius = focusRadius;
        mFocusBorderRadius = mFocusRadius + 2;
    }

    public boolean isFocusAnimating() {
        return mIsAnimating;
    }
}
