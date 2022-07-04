package com.meiliu.mysortingvisualizer.algorithm;

import android.util.Log;

import java.util.ArrayList;

import com.meiliu.mysortingvisualizer.animation.AnimationScenarioItem;
import com.meiliu.mysortingvisualizer.animation.MergeAnimationScenarioItem;

public class SortArrayList {

//    private static String TAG = SortArrayList.class.getSimpleName();


    public static void bubbleSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Integer temp;
        boolean isLastInLoop;
        int size = unsortedValues.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                isLastInLoop = (j == unsortedValues.size() - i - 2);
                if (unsortedValues.get(j + 1) < unsortedValues.get(j)) {
                    temp = unsortedValues.get(j);
                    unsortedValues.set(j, unsortedValues.get(j + 1));
                    unsortedValues.set(j + 1, temp);
                    animationioList.add(new AnimationScenarioItem(true, j, j + 1, isLastInLoop));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, j, j + 1, isLastInLoop));
                }
            }
        }
    }


    public static void insertSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        for (int i = 1; i < unsortedValues.size(); i++) {
            int temp = unsortedValues.get(i);
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (unsortedValues.get(j) > temp) {
                    unsortedValues.set(j + 1, unsortedValues.get(j));
                    animationioList.add(new AnimationScenarioItem(true, j, j + 1, false));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, j, j + 1, false));
                    break;

                }
            }
            unsortedValues.set(j + 1, temp);
        }
    }


    public static void selectSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        int min;
        int tmp;
        for (int i = 0; i < unsortedValues.size(); i++) {
            min = unsortedValues.get(i);
            for (int j = i + 1; j < unsortedValues.size(); j++) {
                if (unsortedValues.get(j) < min) {
                    min = unsortedValues.get(j);
                    tmp = unsortedValues.get(i);
                    unsortedValues.set(i, min);
                    unsortedValues.set(j, tmp);
                    animationioList.add(new AnimationScenarioItem(true, i, j, false));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, i, j, false));
                }
            }
            animationioList.add(new AnimationScenarioItem(false, i, i, true));
        }
    }

    public static void quickSort(ArrayList<Integer> unsortedValues, int low, int high, ArrayList<AnimationScenarioItem> animationioList, ArrayList<Integer> keyList) {

        int start = low;
        int end = high;
        int key = unsortedValues.get(start);
        while (end > start) {

            while (end > start && unsortedValues.get(end) >= key) {
                end--;
                animationioList.add(new AnimationScenarioItem(false, start, end, false));
                keyList.add(key);
            }
            if (unsortedValues.get(end) <= key) {
                int temp = unsortedValues.get(end);
                unsortedValues.set(end, unsortedValues.get(start));
                unsortedValues.set(start, temp);
                animationioList.add(new AnimationScenarioItem(true, start, end, false));
                keyList.add(key);
            }

            while (end > start && unsortedValues.get(start) <= key) {
                start++;
                animationioList.add(new AnimationScenarioItem(false, start, end, false));
                keyList.add(key);
            }
            if (unsortedValues.get(start) >= key) {
                int temp = unsortedValues.get(start);
                unsortedValues.set(start, unsortedValues.get(end));
                unsortedValues.set(end, temp);
                animationioList.add(new AnimationScenarioItem(true, start, end, false));
                keyList.add(key);
            }

        }

        if (start > low) {
            quickSort(unsortedValues, low, start - 1, animationioList, keyList);
        }
        if (end < high) {
            quickSort(unsortedValues, end + 1, high, animationioList, keyList);
        }
    }


    public static void mergeSort(ArrayList<Integer> unsortedValues, int low, int high, ArrayList<MergeAnimationScenarioItem> mergeAnimationioList) {

        int mid = (low + high) / 2;
        if (low < high) {
            mergeSort(unsortedValues, low, mid, mergeAnimationioList);
            mergeSort(unsortedValues, mid + 1, high, mergeAnimationioList);
            merge(unsortedValues, low, mid, high, mergeAnimationioList);
        }
    }

    private static void merge(ArrayList<Integer> unsortedValues, int low, int mid, int high, ArrayList<MergeAnimationScenarioItem> mergeAnimationioList) {
        ArrayList<Integer> temp = new ArrayList<>();
        int i = low;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= high) {
            if (unsortedValues.get(i) < unsortedValues.get(j)) {
                mergeAnimationioList.add(new MergeAnimationScenarioItem(i, k, false));
                temp.add(k++, unsortedValues.get(i++));
            } else {
                mergeAnimationioList.add(new MergeAnimationScenarioItem(j, k, false));
                temp.add(k++, unsortedValues.get(j++));
            }
        }
        while (i <= mid) {
            mergeAnimationioList.add(new MergeAnimationScenarioItem(i, k, false));
            temp.add(k++, unsortedValues.get(i++));
        }
        while (j <= high) {
            mergeAnimationioList.add(new MergeAnimationScenarioItem(j, k, false));
            temp.add(k++, unsortedValues.get(j++));
        }
        for (int x = 0; x < temp.size(); x++) {
            unsortedValues.set(x + low, temp.get(x));
            mergeAnimationioList.add(new MergeAnimationScenarioItem(x + low, x, true));
        }
    }

    public static void hillSortTrue(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        System.out.println("希尔排序 new");
        int i, j, r, tmp;
        for (r = unsortedValues.size() / 2; r >= 1; r = r / 2) {
            for (i = r; i < unsortedValues.size(); i++) {
                tmp = unsortedValues.get(i);
                j = i - r;
                boolean isSwaped = false;
                while (j >= 0 && tmp < unsortedValues.get(j)) {
                    unsortedValues.set(j + r, unsortedValues.get(j));
                    animationioList.add(new AnimationScenarioItem(true, j, j + r, false));
                    j -= r;
                    isSwaped = true;
                }
                unsortedValues.set(j + r, tmp);
                if (!isSwaped) {
                    animationioList.add(new AnimationScenarioItem(false, j, j + r, false));
                }
            }

        }
    }

    @Deprecated
    public static void hillSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        int d = unsortedValues.size() / 2;
        if ((unsortedValues.size() & 1) == 1) {
            d++;
        }
        while (true) {
            for (int i = 0; i < d; i++) {
                for (int j = i; j + d < unsortedValues.size(); j += d) {
                    int temp;
                    if (unsortedValues.get(j) > unsortedValues.get(j + d)) {
                        temp = unsortedValues.get(j);
                        unsortedValues.set(j, unsortedValues.get(j + d));
                        unsortedValues.set(j + d, temp);
                        animationioList.add(new AnimationScenarioItem(true, j, j + d, false));

                    } else {
                        animationioList.add(new AnimationScenarioItem(false, j, j + d, false));
                    }
                }
            }
            if (d < 1) {
                break;
            }
            d--;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < unsortedValues.size(); i++) {
            stringBuffer.append(unsortedValues.get(i) + ",");
        }
    }

    public static void heapSort(ArrayList<Integer> unsortedValues, int n, ArrayList<AnimationScenarioItem> animationioList) {
        int i, tmp;
        for (i = n / 2 - 1; i >= 0; i--) {
            maxHeapDown(unsortedValues, i, n - 1, animationioList);
        }
        for (i = n - 1; i > 0; i--) {
            tmp = unsortedValues.get(0);
            unsortedValues.set(0, unsortedValues.get(i));
            unsortedValues.set(i, tmp);
            animationioList.add(new AnimationScenarioItem(true, 0, i, true));
            maxHeapDown(unsortedValues, 0, i - 1, animationioList);
        }
    }

    private static void maxHeapDown(ArrayList<Integer> unsortedValues, int start, int end, ArrayList<AnimationScenarioItem> animationioList) {
        int c = start;
        int l = 2 * c + 1;
        int tmp = unsortedValues.get(c);
        for (; l <= end; c = l, l = 2 * l + 1) {
            if (l < end && unsortedValues.get(l) < unsortedValues.get(l + 1))
                l++;
            if (tmp >= unsortedValues.get(l))
                break;
            else {
                unsortedValues.set(c, unsortedValues.get(l));
                unsortedValues.set(l, tmp);
                animationioList.add(new AnimationScenarioItem(true, c, l, false));
            }
        }
    }

    public static void logList(ArrayList<Integer> unsortedValues) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < unsortedValues.size(); i++) {
            stringBuffer.append(unsortedValues.get(i) + ",");
        }
    }
}
