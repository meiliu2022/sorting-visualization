package com.meiliu.mysortingvisualizer.animation;


public interface MergeStepsInterface {

    void createTempView(int originalPosition, int tempPosition, boolean isMerge);

    void mergeOriginalView(int originalPosition, int tempPosition, boolean isMerge);

    void showFinish();

    void cancelAllVisualisations();
}
