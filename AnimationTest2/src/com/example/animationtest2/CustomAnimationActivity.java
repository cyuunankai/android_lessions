package com.example.animationtest2;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CustomAnimationActivity extends Activity {

	private final static String TAG = "CustomAnimationActivity";
	private ViewGroup mRootView;
    private FrameLayout mFloatLayout;
    private ImageView mHoverView = null;
    private final static int MARGIN = 48;
    
    private ObjectAnimator mInAnimator = null;
    private ObjectAnimator mOutAnimator = null;

    private HoverAnimationListener mHoverListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animation);
        Log.i(TAG, "step1");
        mRootView = ((ViewGroup)((Activity)this).getWindow().getDecorView());
        mFloatLayout = new FrameLayout(this);
        Log.i(TAG, "step2");
        
        mHoverView = new ImageView(this);
        mHoverView.setImageResource(android.R.drawable.ic_partial_secure);
        
        int gravity = Gravity.BOTTOM|Gravity.CENTER;
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        frameParams.gravity = gravity;
        mFloatLayout.setLayoutParams(frameParams);

        FrameLayout.LayoutParams wrapParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        wrapParams.gravity = gravity;
        wrapParams.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
        Log.i(TAG, "step3");
        mFloatLayout.addView(mHoverView, wrapParams);
        mFloatLayout.setBackgroundColor(Color.argb(0, 255, 255, 255));
        mRootView.addView(mFloatLayout);
        Log.i(TAG, "step4");
        mHoverListener = new HoverAnimationListener(false);
        if (mInAnimator == null) {
            mInAnimator = ObjectAnimator.ofPropertyValuesHolder(mHoverView, fromLeftAnimation());
            mInAnimator.setDuration(1000);
            mInAnimator.setInterpolator(new AnticipateOvershootInterpolator());
            mInAnimator.addListener(mHoverListener);
        }
        Log.i(TAG, "step5");
        if (mOutAnimator == null) {
            mOutAnimator = ObjectAnimator.ofPropertyValuesHolder(mHoverView, toRightAnimation());
            mOutAnimator.setDuration(1000);
            mOutAnimator.setInterpolator(new AnticipateOvershootInterpolator());
            mOutAnimator.addListener(mHoverListener);
        }
        Log.i(TAG, "step6");
        findViewById(R.id.switch_hover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchHover();
            }
        });
    }
    
    private PropertyValuesHolder[] fromLeftAnimation() {

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat( "translationX", -getWidth(), 0f );
        PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);
        PropertyValuesHolder[] holders = {holderX, holderR};
        return holders;
    }

    private PropertyValuesHolder[] toRightAnimation() {

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat( "translationX", 0f, getWidth() );
        PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);
        PropertyValuesHolder[] holders = {holderX, holderR};
        return holders;
    }

    private float getWidth() {
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Log.i("size", " " + size);
        Log.i("getWidth", " " + ((size.x/5)*3));
        return ((size.x/5)*3);
    }
    
    public void switchHover() {
        if (mHoverListener.isLocked()) {
            return;
        }
        if (isSetCustomAnimation()) {
            if (mHoverListener.isShown()) {
                customSlideOut();
            } else {
                customSlideIn();
            }
        }
    }
    
    private boolean isSetCustomAnimation() {
        if (mInAnimator != null && mOutAnimator != null) {
            return true;
        }
        Log.i("", "Not set custom animation.");
        return false;
    }
    
    private void customSlideIn() {
        if (mInAnimator == null) {
            Log.w("", "Not set slide in animation");
            return;
        }
        mInAnimator.start();
        mHoverListener.setShowStatus(true);
    }

    private void customSlideOut() {
        if (mOutAnimator == null) {
            Log.w("", "Not set slide out animation");
            return;
        }
        mOutAnimator.start();
        mHoverListener.setShowStatus(false);
    }
}
