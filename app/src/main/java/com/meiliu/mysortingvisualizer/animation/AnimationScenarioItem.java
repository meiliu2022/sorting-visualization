package com.meiliu.mysortingvisualizer.animation;


public class AnimationScenarioItem {

    private boolean isShouldBeSwapped;
    private int curPosition;
    private int nextPosition;
    private boolean isFinalPlace;

    public AnimationScenarioItem(boolean isShouldBeSwapped, int curPosition, int nextPosition, boolean isFinalPlace) {
        this.isShouldBeSwapped = isShouldBeSwapped;
        this.curPosition = curPosition;
        this.nextPosition = nextPosition;
        this.isFinalPlace = isFinalPlace;
    }

    public boolean isShouldBeSwapped() {
        return isShouldBeSwapped;
    }

    public int getCurPosition() {
        return curPosition;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    public boolean isFinalPlace() {
        return isFinalPlace;
    }

}
