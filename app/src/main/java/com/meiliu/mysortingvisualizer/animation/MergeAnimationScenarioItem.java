package com.meiliu.mysortingvisualizer.animation;


public class MergeAnimationScenarioItem {


    private int originalPosition;
    private int tempPosition;
    private boolean isMerge;


    public MergeAnimationScenarioItem(int originalPosition, int tempPosition, boolean isMerge) {

        this.originalPosition = originalPosition;
        this.tempPosition = tempPosition;
        this.isMerge = isMerge;
    }


    public int getOriginalPosition() {
        return originalPosition;
    }

    public int getTempPosition() {
        return tempPosition;
    }

    public boolean isMerge() {
        return isMerge;
    }
}
