package com.meiliu.mysortingvisualizer.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import com.meiliu.mysortingvisualizer.R;
import com.meiliu.mysortingvisualizer.ui.customview.RectView;



public class AnimationsCoordinator implements AlgorithmStepsInterface {

    private ViewGroup container;
    private ArrayList<AlgorithmAnimationListener> listeners;
    private ValueAnimator blinkAnimation;

    public AnimationsCoordinator(ViewGroup container) {
//        Log.e(TAG, "AnimationsCoordinator");
        this.container = container;
    }

    @Override
    public void showSwapStep(final int curPosition, final int nextPosition, final boolean isOnFinalPlace) {
//        Log.e(TAG, "showSwapStep");
        if (container != null && container.getChildCount() > 0 && container.getChildCount() > nextPosition) {
//            Log.e(TAG, "curPosition:" + curPosition + ",nextPosition:" + nextPosition);
            final RectView curRectView = (RectView) container.getChildAt(curPosition);
            final RectView nextRectView = (RectView) container.getChildAt(nextPosition);

            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 5);
            blinkAnimation.setDuration(1000);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    int value = (Integer) animation.getAnimatedValue();
                    if (value % 2 == 0) {
                        curRectView.setSelected(false);
                        nextRectView.setSelected(false);
                    } else {
                        curRectView.setSelected(true);
                        nextRectView.setSelected(true);
                    }
                }
            });


            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    curRectView.setSelected(false);
                    curRectView.setIsOnFinalPlace(isOnFinalPlace);
                    nextRectView.setSelected(false);
                    container.removeView(curRectView);
                    container.addView(curRectView, nextPosition);
                    container.removeView(nextRectView);
                    container.addView(nextRectView, curPosition);
                    notifySwapStepAnimationEnd(curPosition);
                }
            });

            blinkAnimation.start();
        }
    }

    @Override
    public void showNonSwapStep(final int curPosition, final int nextPosition, final boolean isOnFinalPlace) {
//        Log.e(TAG, "showNonSwapStep");
        if (container != null && container.getChildCount() > 0 && container.getChildCount() > nextPosition) {
            final RectView curRectView = (RectView) container.getChildAt(curPosition);
            final RectView nextRectView = (RectView) container.getChildAt(nextPosition);

            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 6);
            blinkAnimation.setDuration(1200);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    if (value % 2 == 0) {
                        curRectView.setSelected(false);
                        nextRectView.setSelected(false);
                    } else {
                        curRectView.setSelected(true);
                        nextRectView.setSelected(true);
                    }
                }
            });

            blinkAnimation.start();
            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    curRectView.setSelected(false);
                    nextRectView.setSelected(false);
                    nextRectView.setIsOnFinalPlace(isOnFinalPlace);
                    notifySwapStepAnimationEnd(curPosition);
                }
            });
        }
    }

    @Override
    public void showFinish() {
//        Log.e(TAG, "showFinish");
        if (container != null && container.getChildCount() > 0) {
//            ((RectView) container.getChildAt(0)).setIsOnFinalPlace(true);//?????????????????????
            for (int i = 0; i < container.getChildCount(); i++) {
                ((RectView) container.getChildAt(i)).setIsOnFinalPlace(true);//?????????????????????????????????????????????
            }
        }
        Toast.makeText(container.getContext(), R.string.sort_finish, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelAllVisualisations() {
//        Log.e(TAG, "cancelAllVisualisations");
        if (blinkAnimation != null) {
            blinkAnimation.removeAllListeners();
            blinkAnimation.cancel();
            container.clearAnimation();
        }
    }

    private void notifySwapStepAnimationEnd(int position) {
//        Log.e(TAG, "notifySwapStepAnimationEnd");
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; ++i) {
                listeners.get(i).onSwapStepAnimationEnd(position);
            }
        }
    }

    public void addListener(AlgorithmAnimationListener listener) {
//        Log.e(TAG, "addListener");
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void removeListener(Animator.AnimatorListener listener) {
//        Log.e(TAG, "removeListener");
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        if (listeners.size() == 0) {
            listeners = null;
        }
    }
}
