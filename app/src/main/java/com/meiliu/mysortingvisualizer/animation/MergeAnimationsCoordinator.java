package com.meiliu.mysortingvisualizer.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import com.meiliu.mysortingvisualizer.R;
import com.meiliu.mysortingvisualizer.ui.customview.RectView;
import com.meiliu.mysortingvisualizer.ui.fragment.SortFragment;
import com.meiliu.mysortingvisualizer.util.Util;



public class MergeAnimationsCoordinator implements MergeStepsInterface {

    private String TAG = MergeAnimationsCoordinator.class.getSimpleName();
    private ViewGroup originalContainer;
    private ViewGroup tempContainer;
    private ArrayList<MergeAnimationListener> listeners;
    private ValueAnimator blinkAnimation;
    private LinearLayout.LayoutParams lp;
    private Context context;

    public MergeAnimationsCoordinator(Context context, ViewGroup originalContainer, ViewGroup tempContainer) {
        Log.e(TAG, "MergeAnimationsCoordinator");
        this.context = context;
        this.originalContainer = originalContainer;
        this.tempContainer = tempContainer;
    }

    @Override
    public void createTempView(final int originalPosition, final int tempPosition, final boolean isMerge) {

        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int marginInPx = Util.dpToPx(context, SortFragment.RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);

        if (originalContainer != null && originalContainer.getChildCount() > 0 && originalContainer.getChildCount() > tempPosition) {
            final RectView originalView = (RectView) originalContainer.getChildAt(originalPosition);
            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 5);
            blinkAnimation.setDuration(1000);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    int value = (Integer) animation.getAnimatedValue();
                    if (value % 2 == 0) {
                        originalView.setSelected(false);
                    } else {
                        originalView.setSelected(true);
                    }
                }
            });


            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    originalView.setSelected(false);
                    originalView.setIsOnFinalPlace(isMerge);
                    originalContainer.removeView(originalView);
                    int tempNumber = originalView.getNumber();
                    originalView.setMinimumHeight(1);
                    originalView.setImageBitmap(createSpaceBitmap(SortFragment.mRectWidth));
                    originalView.setNumber(1);
                    originalView.setHidden(true); // set customized hidden flag
                    originalContainer.addView(originalView, originalPosition, lp);

                    RectView tempRectView = new RectView(context);
                    int rectHeight = SortFragment.lineHeightArray.get(tempNumber);
                    tempRectView.setRectHeight(rectHeight);
                    tempRectView.setImageBitmap(createCalculatedBitmap(SortFragment.mRectWidth, rectHeight));

                    tempRectView.setNumber(tempNumber);
                    tempContainer.addView(tempRectView, tempPosition, lp);
                    notifySwapStepAnimationEnd(originalPosition);
                }
            });

            blinkAnimation.start();
        }
    }

    @Override
    public void mergeOriginalView(final int originalPosition, final int tempPosition, final boolean isMerge) {


        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int marginInPx = Util.dpToPx(context, SortFragment.RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);
        if (originalContainer != null && originalContainer.getChildCount() > 0 && originalContainer.getChildCount() > tempPosition) {
            final RectView originalView = (RectView) originalContainer.getChildAt(originalPosition);
            final RectView tempRectView = (RectView) tempContainer.getChildAt(tempPosition);
            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 6);
            blinkAnimation.setDuration(1200);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    if (value % 2 == 0) {
                        originalView.setSelected(false);
                        tempRectView.setSelected(false);
                    } else {
                        originalView.setSelected(true);
                        tempRectView.setSelected(true);
                    }
                }
            });

            blinkAnimation.start();
            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    originalView.setSelected(false);
                    tempRectView.setSelected(false);
                    tempRectView.setIsOnFinalPlace(isMerge);
                    tempContainer.removeView(tempRectView);
                    int tempNumber = tempRectView.getNumber();

                    tempRectView.setMinimumHeight(1);
                    tempRectView.setImageBitmap(createSpaceBitmap(SortFragment.mRectWidth));
                    tempRectView.setNumber(1);
                    tempRectView.setHidden(true); // set customized hidden flag
                    tempContainer.addView(tempRectView, tempPosition, lp);


                    originalContainer.removeView(originalView);
                    RectView originalView = new RectView(context);
                    int rectHeight = SortFragment.lineHeightArray.get(tempNumber);
                    originalView.setRectHeight(rectHeight);
                    originalView.setImageBitmap(createCalculatedBitmap(SortFragment.mRectWidth, rectHeight));

                    originalView.setNumber(tempNumber);
                    originalContainer.addView(originalView, originalPosition, lp);
                    notifySwapStepAnimationEnd(originalPosition);
                }
            });
        }
    }

    @Override
    public void showFinish() {
        Log.e(TAG, "showFinish");
        if (originalContainer != null && originalContainer.getChildCount() > 0) {
            for (int i = 0; i < originalContainer.getChildCount(); i++) {
                ((RectView) originalContainer.getChildAt(i)).setIsOnFinalPlace(true);
            }
        }
        Toast.makeText(originalContainer.getContext(), R.string.sort_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelAllVisualisations() {
        Log.e(TAG, "cancelAllVisualisations");
        if (blinkAnimation != null) {
            blinkAnimation.removeAllListeners();
            blinkAnimation.cancel();
            originalContainer.clearAnimation();
        }
    }

    private void notifySwapStepAnimationEnd(int position) {
        Log.e(TAG, "notifySwapStepAnimationEnd");
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; ++i) {
                listeners.get(i).onSwapStepAnimationEnd(position);
            }
        }
    }

    public void addListener(MergeAnimationListener listener) {
        Log.e(TAG, "addListener");
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void removeListener(Animator.AnimatorListener listener) {
        Log.e(TAG, "removeListener");
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        if (listeners.size() == 0) {
            listeners = null;
        }
    }

    private Bitmap createCalculatedBitmap(int mRectWidth, int mRectHeight) {
        return Bitmap.createBitmap(mRectWidth, mRectHeight, Bitmap.Config.ALPHA_8);
    }


    private Bitmap createSpaceBitmap(int mRectWidth) {
        return Bitmap.createBitmap(mRectWidth, 1, Bitmap.Config.ALPHA_8);
    }
}
