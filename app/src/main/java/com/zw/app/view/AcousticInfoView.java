package com.zw.app.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.zw.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AcousticInfoView extends View {
    public static final String TAG = "AcousticInfoView";

    private int mWidth = 100;
    private int mHeight = 100;
    private float mRadius = 10;

    private Paint mPaint;

    private final Random mRandom = new Random();

    private final List<Point> mPoints = new ArrayList<>();

    private long mPreRefreshTime = System.currentTimeMillis();

    private long mInterval = 50;

    public AcousticInfoView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public AcousticInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public AcousticInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public AcousticInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private List<Integer> colorsList = new ArrayList<>();

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AcousticInfoView);
        mRadius = ta.getDimension(R.styleable.AcousticInfoView_radius, mRadius);
        mInterval = ta.getInteger(R.styleable.AcousticInfoView_update_interval, 0);

        Log.d(TAG, "init mRadius: " + mRadius + ",mInterval: " + mInterval);

        int resourceId = ta.getResourceId(R.styleable.AcousticInfoView_colors, -1);
        String[] colors = null;
        if (resourceId != -1) {
            colors = getResources().getStringArray(resourceId);
        }

        if (colors != null && colors.length > 0) {
            try {
                for (int i = 0; i < colors.length; i++) {
                    if (!TextUtils.isEmpty(colors[i])) {
                        Log.d(TAG, "colors: " + colors[i]);
                        colorsList.add(Color.parseColor(colors[i]));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ta.recycle();
    }

    public void updateInfo(List<Point> points) {
        boolean needUpdate = true;
        if (mInterval > 0) {
            long curTime = System.currentTimeMillis();
            if (curTime - mPreRefreshTime >= mInterval) {
                needUpdate = true;
                mPreRefreshTime = curTime;
            } else {
                needUpdate = false;
            }
        }

        if (needUpdate) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                mPoints.clear();
                mPoints.addAll(points);
                invalidate();
            } else {
                mPoints.clear();
                mPoints.addAll(points);
                postInvalidate();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int colorsListSize = colorsList.size();
        for (int i = 0; i < mPoints.size(); i++) {
            int color = Color.YELLOW;
            if (colorsListSize > 0) {
                color = colorsList.get(i % colorsListSize);
                mPaint.setColor(color);
            } else {
//                int red = random.nextInt(256);
//                int green = random.nextInt(256);
//                int blue = random.nextInt(256);
//                mPaint.setARGB(255, red, green, blue);
            }

            canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mRadius, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight);
        }
    }
}
