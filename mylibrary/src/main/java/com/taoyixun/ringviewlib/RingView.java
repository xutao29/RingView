package com.taoyixun.ringviewlib;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆环（完成度）
 * Created by Midai_X on 2016-08-26.
 */
public class RingView extends View {

    private final int FINAL_RINGVIEW_DEFAULT_COLOR = 0x00808080;
    private Context mContext;

    private int mShadowWidth; // 阴影宽度
    private int mShadowColor; // 阴影颜色（默认灰色）

    private int mRingMargin; // 主内容圆环与阴影边距
    private int mRingWidth; // 主内容圆环宽度
    private int mBackground = android.R.color.white; // 主内容颜色

    // 主内容圆环
    private int mProgress; // 当前进度
    private String mProgressUnit; // 进度单位
    private int mMax; // 最大进度

    private int mRingStartColor;
    private int mRingEndColor;
    private String mTextString; // 提示内容
    private float mTextSize; // 文本字体大小
    private int mTextColor; // 文本颜色
    private int mTextMargin; // 行边距
    private boolean mIsTextAutoColor; // 文本颜色是否随进度而变化

    private ArgbEvaluator mArgbEvaluator; // 颜色估值器


    private Paint mPaintShadow;
    private Paint mPaintRing;
    private Paint mPaintText;
    private Paint mPaint;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mArgbEvaluator = new ArgbEvaluator();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingView);
        mShadowWidth = (int) typedArray.getDimension(R.styleable.RingView_ringView_shadow_width, 0);
        mShadowColor = typedArray.getColor(R.styleable.RingView_ringView_shadow_color, FINAL_RINGVIEW_DEFAULT_COLOR);
        mRingMargin = (int) typedArray.getDimension(R.styleable.RingView_ringView_ring_margin, 0);
        mRingWidth = (int) typedArray.getDimension(R.styleable.RingView_ringView_ring_width, 0);
        mRingStartColor = typedArray.getColor(R.styleable.RingView_ringView_ring_start_color, FINAL_RINGVIEW_DEFAULT_COLOR);
        mRingEndColor = typedArray.getColor(R.styleable.RingView_ringView_ring_end_color, FINAL_RINGVIEW_DEFAULT_COLOR);
        mBackground = typedArray.getColor(R.styleable.RingView_ringView_background_color, getResources().getColor(android.R.color.white));
        mProgress = typedArray.getInt(R.styleable.RingView_ringView_ring_progress, 0);
        mProgressUnit = typedArray.getString(R.styleable.RingView_ringView_ring_progress_unit);
        mMax = typedArray.getInt(R.styleable.RingView_ringView_ring_max, 100);
        mTextString = typedArray.getString(R.styleable.RingView_ringView_text);
        mTextSize = typedArray.getDimension(R.styleable.RingView_ringView_text_size, 12);
        mTextColor = typedArray.getColor(R.styleable.RingView_ringView_text_color, FINAL_RINGVIEW_DEFAULT_COLOR);
        mTextMargin = (int) typedArray.getDimension(R.styleable.RingView_ringView_text_margin, 4);
        mIsTextAutoColor = typedArray.getBoolean(R.styleable.RingView_ringView_text_auto_color, true);
        typedArray.recycle();

        mPaintShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintShadow.setStyle(Paint.Style.STROKE);
        mPaintShadow.setStrokeWidth(mShadowWidth);
        mPaintShadow.setColor(mShadowColor);

        mPaintRing = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRing.setStyle(Paint.Style.STROKE);
        mPaintRing.setStrokeWidth(mRingWidth);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setTextSize(mTextSize);
        mPaintText.setColor(mTextColor);
        mPaintText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mPaintText.setTextAlign(Paint.Align.CENTER);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBackground);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();
        int shadowWidth = px2dip(mContext, mShadowWidth);

        canvas.drawCircle(width / 2, height / 2, width / 2 - shadowWidth, mPaint);

        if (shadowWidth > 0)
            canvas.drawCircle(width / 2, height / 2, width / 2 - shadowWidth, mPaintShadow);

        SweepGradient sweepGradientRing = new SweepGradient(width / 2, height / 2, new int[]{mRingStartColor, mRingEndColor}, null);
        mPaintRing.setShader(sweepGradientRing);
        int ringMargin = mShadowWidth + mRingMargin + px2dip(mContext, mRingWidth);
        RectF rectF = new RectF(ringMargin, ringMargin, width - ringMargin, height - ringMargin);
        float progress = ((float) 360 / mMax) * mProgress;
        Matrix matrix = new Matrix();
        matrix.preRotate(-90, width / 2, height / 2);
        sweepGradientRing.setLocalMatrix(matrix);
        canvas.drawArc(rectF, -90, progress, false, mPaintRing);

        if (mIsTextAutoColor) {
            float offset = (float) (mProgress / (mMax * 1.0));
            int evaluate = (int) mArgbEvaluator.evaluate(offset, mRingStartColor, mRingEndColor);
            mPaintText.setColor(evaluate);
        }
        canvas.drawText(mTextString, width / 2, height / 2 - mTextSize / 2 - mTextMargin / 2, mPaintText);
        canvas.drawText(mProgress + mProgressUnit, width / 2, height / 2 + mTextSize / 2 + mTextMargin / 2, mPaintText);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void setmBackground(int mBackground) {
        this.mBackground = mBackground;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public void setmRingEndColor(int mRingEndColor) {
        this.mRingEndColor = mRingEndColor;
    }

    public void setmRingMargin(int mRingMargin) {
        this.mRingMargin = mRingMargin;
    }

    public void setmRingStartColor(int mRingStartColor) {
        this.mRingStartColor = mRingStartColor;
    }

    public void setmRingWidth(int mRingWidth) {
        this.mRingWidth = mRingWidth;
    }

    public void setmShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
    }

    public void setmShadowWidth(int mShadowWidth) {
        this.mShadowWidth = mShadowWidth;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }
}
