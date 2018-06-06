package palmy.com.loadingimageview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

/**
 * loading
 *
 * @author palmy
 * @date 2018/5/25
 */

public class LoadingImageView extends android.support.v7.widget.AppCompatImageView {
    public static final int DEFAULT_SHADOW_COLOR = 0x60000000;
    private boolean mIsLoading = false;
    private Bitmap mBitmapLoad;
    private Bitmap mBitmapBg;
    private int mDegree = 0;
    private ValueAnimator mValueAnimator;
    private Matrix mMatrix;

    public LoadingImageView(Context context) {
        super(context);
        init();
    }

    public LoadingImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsLoading) {
            canvas.drawBitmap(getBitmapBg(), 0, 0, null);
            mMatrix.setRotate(mDegree, (float) mBitmapLoad.getWidth() / 2, (float) mBitmapLoad.getHeight() / 2);
            mMatrix.postTranslate(getWidth() / 2 - mBitmapLoad.getWidth() / 2, getHeight() / 2 - mBitmapLoad.getHeight() / 2);
            canvas.drawBitmap(mBitmapLoad, mMatrix, null);
        }
    }

    private void init() {
        mMatrix = new Matrix();
        mBitmapLoad = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
    }

    private Bitmap getBitmapBg() {
        if(mBitmapBg == null){
            mBitmapBg = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(mBitmapBg);
            tempCanvas.drawColor(DEFAULT_SHADOW_COLOR);
        }
        return mBitmapBg;
    }

    private void startAnim() {
        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofInt(0, 360);
            mValueAnimator.setDuration(2000);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mDegree = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    invalidate();
                }
            });
        }
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.start();
        }
    }

    private void stopAnim() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }
    }

    public void setLoadingStatus(boolean isLoading) {
        this.mIsLoading = isLoading;
        if (mIsLoading) {
            startAnim();
        } else {
            stopAnim();
        }
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("destroy", "destroy");
        if(mBitmapBg != null){
            mBitmapBg.recycle();
        }
        if(mBitmapLoad != null){
            mBitmapLoad.recycle();
        }
    }
}
