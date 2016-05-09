package com.weiggle.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.weiggle.util.BezierEvaluator;

import java.util.Random;

import github.weiggle.com.bezier.R;

/**
 * Created by wei.li on 2016/5/8.
 */
public class BazierWidget extends FrameLayout {

    private int width,height;
    private int drawableWidth,drawableHeight;
    private Drawable orange,green,blue,cyan,gray;
    private Drawable[] mDrawables = new Drawable[5];
    private LayoutParams mParams;
    private Random mRandom;


    public BazierWidget(Context context) {
        this(context,null);
    }

    public BazierWidget(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BazierWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDrawable();
    }


    private void initDrawable(){
        orange = getResources().getDrawable(R.drawable.orange);
        green = getResources().getDrawable(R.drawable.green);
        blue = getResources().getDrawable(R.drawable.blue);
        cyan = getResources().getDrawable(R.drawable.cyan);
        gray = getResources().getDrawable(R.drawable.gray);

        mDrawables[0] = orange;
        mDrawables[1] = green;
        mDrawables[2] = blue;
        mDrawables[3] = cyan;
        mDrawables[4] = gray;

        drawableWidth = orange.getIntrinsicWidth();
        drawableHeight = orange.getIntrinsicHeight();
        mRandom = new Random();
        mParams = new LayoutParams(drawableWidth,drawableHeight);
        mParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;

    }


    public void addImageView(){
        ImageView view = new ImageView(getContext());
        view.setImageDrawable(mDrawables[mRandom.nextInt(mDrawables.length-1)]);
        view.setLayoutParams(mParams);
        addView(view,mParams);


        AnimatorSet set = new AnimatorSet();
        set.playSequentially(initAnimator(view),bezierAnimator(view));
        set.setTarget(view);
        set.start();

    }

    /**
     * initAnimator
     * @param view
     */
    private AnimatorSet initAnimator(ImageView view){

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",0.2f,1.2f,1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",0.2f,1.2f,1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view,"alpha",0.2f,1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX,scaleY,alpha);
        set.setDuration(500);
        set.setTarget(view);

        return set;

    }


    /**
     * bazier Animator
     * @param view
     * @return
     */
    private ValueAnimator bezierAnimator(final ImageView view){

        PointF start = new PointF((width-drawableHeight)/2,height-drawableHeight);
        PointF control1 = getPointF(1);
        PointF control2 = getPointF(2);
        PointF end = new PointF(mRandom.nextInt(width),0);

        BezierEvaluator evaluator = new BezierEvaluator(start,end);
        ValueAnimator animator = ValueAnimator.ofObject(evaluator,control1,control2);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF f = (PointF) animation.getAnimatedValue();
                view.setX(f.x);
                view.setY(f.y);
                view.setAlpha(1-animation.getAnimatedFraction());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(view);
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(5000);
        animator.setTarget(view);

        return animator;

    }

    private PointF getPointF(int dex){
        PointF f = new PointF();

        f.x = mRandom.nextInt(width-drawableWidth);
        f.y = mRandom.nextInt(height/2)+(dex == 1?height/2:0);

        return f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
