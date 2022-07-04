package com.meiliu.mysortingvisualizer.animation;


public interface AlgorithmStepsInterface {

    void showSwapStep(int curPosition, int nextPosition, boolean isOnFinalPlace);

    void showNonSwapStep(int curPosition, int nextPosition, boolean isOnFinalPlace);

    void showFinish();

    void cancelAllVisualisations();
}
