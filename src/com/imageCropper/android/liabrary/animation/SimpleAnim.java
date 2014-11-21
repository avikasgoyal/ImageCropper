package com.imageCropper.android.liabrary.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Simple implementation of {@link android.view.animation.Animation} and
 * {@link android.view.animation.Animation.AnimationListener}class, with most lifecycle methods implemented as stubs.
 * Also includes a {@link #onEnded()} method which is always called properly when animation ends/finishes.
 */
public abstract class SimpleAnim extends Animation implements Animation.AnimationListener {
    private final AtomicBoolean mFlag = new AtomicBoolean();

    /**
     * Create a new Instance.
     */
    protected SimpleAnim() {
        setAnimationListener(this);
    }

    /**
     * {@inheritDoc}
     * Note: Sub classes must always call super method, this will ensure {@link #onEnded()} is invoked properly.
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime >= 1f && mFlag.compareAndSet(false, true)) {
            onEnded();
        }
    }

    /**
     * Invoked once when one of the following happens (whichever happens first):
     * <ul>
     * <li>Animation's interpolated time reaches 1.</li>
     * <li>{@link android.view.animation.Animation.AnimationListener#onAnimationEnd(android.view.animation.Animation)}
     * is called.</li>
     * </ul>
     * This method is there to fix buggy Animation events that are some times called after a long delay.
     */
    public void onEnded() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAnimationEnd(Animation animation) {
        if (mFlag.compareAndSet(false, true)) {
            onEnded();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
