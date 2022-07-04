package com.meiliu.mysortingvisualizer.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.meiliu.mysortingvisualizer.R;
import com.meiliu.mysortingvisualizer.algorithm.SortArrayList;
import com.meiliu.mysortingvisualizer.animation.AlgorithmAnimationListener;
import com.meiliu.mysortingvisualizer.animation.AnimationScenarioItem;
import com.meiliu.mysortingvisualizer.animation.AnimationsCoordinator;
import com.meiliu.mysortingvisualizer.animation.MergeAnimationListener;
import com.meiliu.mysortingvisualizer.animation.MergeAnimationScenarioItem;
import com.meiliu.mysortingvisualizer.animation.MergeAnimationsCoordinator;
import com.meiliu.mysortingvisualizer.constant.Constant;
import com.meiliu.mysortingvisualizer.ui.customview.RectView;
import com.meiliu.mysortingvisualizer.util.Util;

/**
 * Main fragment where sorting visualisation appears
 */

public class SortFragment extends Fragment {
    public static final int minHeight = 80;
    public static final int RECT_MARGIN = 1;//4
    public static SparseIntArray lineHeightArray = new SparseIntArray();
    public static int mRectWidth;
    private LinearLayout viewLine;
    private String TAG = SortFragment.class.getSimpleName();
    private EditText mEtInput;
    private Button mBtnStart;
    private boolean isAnimationRunning;
    private int scenarioItemIndex = 0;
    private LinearLayout mLlContainer;
    private LinearLayout mLlContainerMerge;
    private LinearLayout mLlContainerOriginal;
    private LinearLayout mLlContainerTemp;
    private RelativeLayout mRlContainerParent;
    private AnimationsCoordinator animationsCoordinator;
    private MergeAnimationsCoordinator mergeAnimationsCoordinator;
    private ArrayList<AnimationScenarioItem> animationList;
    private ArrayList<MergeAnimationScenarioItem> mergeAnimationList;
    private ArrayList<Integer> keyList;
    private Spinner algorithmSpinner;
    private int algorithmSelected = Constant.ALGORITHM_BUBBLE;
    private int mWidth;
    private int maxRectHeight;
    private int minRectHeight;
    private View view;
    private StringBuffer stringBuffer;
    private int rectCount = 0;
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String input = mEtInput.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                resetPreviousData();

                ArrayList<Integer> integerList = new ArrayList<>(Util.convertToIntArray(input));
                minRectHeight = integerList.get(0);
                maxRectHeight = integerList.get(0);
                for (int i = 0; i < integerList.size(); i++) {
                    int height = integerList.get(i);

                    if (height > maxRectHeight) {
                        maxRectHeight = height;
                    }
                    if (height < minRectHeight) {
                        minRectHeight = height;
                    }
                }
                rectCount = integerList.size();

                if (algorithmSelected == Constant.ALGORITHM_MERGE) {
                    drawRectsMerge(integerList);
                    mergeAnimationList = new ArrayList<>();
                    sortMerge(integerList, mergeAnimationList);
                    runAnimationIterationMerge();
                } else {
                    drawRects(integerList);
                    animationList = new ArrayList<>();
                    sort(integerList, animationList);
                    runAnimationIteration();
                }
                stringBuffer = new StringBuffer();
                for (int i = 0; i < integerList.size(); i++) {
                    stringBuffer.append(integerList.get(i) + ",");
                }

            } else {
                Toast.makeText(getContext(), R.string.empty_field_warning, Toast.LENGTH_LONG).show();
            }
        }
    };

    private void sortMerge(ArrayList<Integer> unsortedValues, ArrayList<MergeAnimationScenarioItem> mergeAnimationioList) {
        mRlContainerParent.setVisibility(View.GONE);
        mLlContainerMerge.setVisibility(View.VISIBLE);
        SortArrayList.mergeSort(unsortedValues, 0, unsortedValues.size() - 1, mergeAnimationioList);
    }

    private void sort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        mRlContainerParent.setVisibility(View.VISIBLE);
        mLlContainerMerge.setVisibility(View.GONE);
        switch (algorithmSelected) {

            case Constant.ALGORITHM_BUBBLE:
                SortArrayList.bubbleSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_INSERT:
                SortArrayList.insertSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_SELECT:
                SortArrayList.selectSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_QUICK:
                keyList = new ArrayList<>();
                SortArrayList.quickSort(unsortedValues, 0, unsortedValues.size() - 1, animationioList, keyList);
                break;
            case Constant.ALGORITHM_HILL:
                SortArrayList.hillSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_HEAP:
                SortArrayList.heapSort(unsortedValues, unsortedValues.size(), animationioList);
                break;

        }

    }

    private void resetPreviousData() {
        if (isAnimationRunning && animationsCoordinator != null) {
            animationsCoordinator.cancelAllVisualisations();
            isAnimationRunning = false;
        }
        scenarioItemIndex = 0;
        if (viewLine != null) {
            mRlContainerParent.clearAnimation();
            mRlContainerParent.removeView(viewLine);
        }
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sort, container, false);
        mEtInput = view.findViewById(R.id.et_input);
        mBtnStart = view.findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(buttonClickListener);
        algorithmSpinner = view.findViewById(R.id.spinner_algorithm);
        algorithmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "algorithmSpinner:" + parent.getItemAtPosition(position).toString());
                switch (position) {
                    case Constant.ALGORITHM_BUBBLE:
                        algorithmSelected = Constant.ALGORITHM_BUBBLE;
                        break;
                    case Constant.ALGORITHM_INSERT:
                        algorithmSelected = Constant.ALGORITHM_INSERT;
                        break;
                    case Constant.ALGORITHM_SELECT:
                        algorithmSelected = Constant.ALGORITHM_SELECT;
                        break;
                    case Constant.ALGORITHM_QUICK:
                        algorithmSelected = Constant.ALGORITHM_QUICK;
                        break;
                    case Constant.ALGORITHM_MERGE:
                        algorithmSelected = Constant.ALGORITHM_MERGE;
                        break;
                    case Constant.ALGORITHM_HILL:
                        algorithmSelected = Constant.ALGORITHM_HILL;
                        break;
                    case Constant.ALGORITHM_HEAP:
                        algorithmSelected = Constant.ALGORITHM_HEAP;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        algorithmSpinner.setSelection(Constant.ALGORITHM_HILL, true);
        mLlContainer = view.findViewById(R.id.ll_container);
        mRlContainerParent = view.findViewById(R.id.rl_container_parent);
        mLlContainerMerge = view.findViewById(R.id.ll_container_merge);
        mLlContainerOriginal = view.findViewById(R.id.ll_container_original);
        mLlContainerTemp = view.findViewById(R.id.ll_container_temp);
        mWidth = view.getMeasuredWidth();
        animationsCoordinator = new AnimationsCoordinator(mLlContainer);
        animationsCoordinator.addListener(new AlgorithmAnimationListener() {
            @Override
            public void onSwapStepAnimationEnd(int endedPosition) {
                runAnimationIteration();
            }
        });
        mergeAnimationsCoordinator = new MergeAnimationsCoordinator(getActivity(), mLlContainerOriginal, mLlContainerTemp);
        mergeAnimationsCoordinator.addListener(new MergeAnimationListener() {
            @Override
            public void onSwapStepAnimationEnd(int endedPosition) {
                runAnimationIterationMerge();
            }
        });
        return view;
    }

    private void runAnimationIteration() {
        isAnimationRunning = true;
        if (animationList != null && animationList.size() == scenarioItemIndex) {
            animationsCoordinator.showFinish();
            return;
        }
        if (animationList != null && !animationList.isEmpty() && animationList.size() > scenarioItemIndex) {

            AnimationScenarioItem animationStep = animationList.get(scenarioItemIndex);
            scenarioItemIndex++;
            if (keyList != null && !keyList.isEmpty() && keyList.size() > scenarioItemIndex) {
                int height = lineHeightArray.get(keyList.get(scenarioItemIndex));

                TextView tv = viewLine.findViewById(R.id.key_value_text);
                tv.setText("key value: " + keyList.get(scenarioItemIndex));
                int fullHeight = mRlContainerParent.getMeasuredHeight();
                if (height < minHeight) {
                    viewLine.setY(fullHeight - minHeight);
                } else {
                    viewLine.setY(fullHeight - height);
                }
            }
            if (animationStep.isShouldBeSwapped()) {
                animationsCoordinator.showSwapStep(animationStep.getCurPosition(), animationStep.getNextPosition(), animationStep.isFinalPlace());
            } else {
                animationsCoordinator.showNonSwapStep(animationStep.getCurPosition(), animationStep.getNextPosition(), animationStep.isFinalPlace());
            }
        }

    }

    private void runAnimationIterationMerge() {
        isAnimationRunning = true;
        if (mergeAnimationList != null && mergeAnimationList.size() == scenarioItemIndex) {
            mergeAnimationsCoordinator.showFinish();
            return;
        }
        if (mergeAnimationList != null && !mergeAnimationList.isEmpty() && mergeAnimationList.size() > scenarioItemIndex) {

            MergeAnimationScenarioItem mergeAnimationStep = mergeAnimationList.get(scenarioItemIndex);
            scenarioItemIndex++;
            if (mergeAnimationStep.isMerge()) {
                mergeAnimationsCoordinator.mergeOriginalView(mergeAnimationStep.getOriginalPosition(), mergeAnimationStep.getTempPosition(), true);
            } else {
                mergeAnimationsCoordinator.createTempView(mergeAnimationStep.getOriginalPosition(), mergeAnimationStep.getTempPosition(), false);
            }
        }

    }

    private void drawRects(ArrayList<Integer> listToDraw) {
        if (mLlContainer != null) {
            mLlContainer.removeAllViews();
            mLlContainer.clearAnimation();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT); // modified
        int marginInPx = Util.dpToPx(getContext(), RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);
        int pos = 0;
        mWidth = view.getMeasuredWidth();
        mRectWidth = mWidth / rectCount - RECT_MARGIN;
        for (Integer currentIntValue : listToDraw) {
            RectView rectView = new RectView(getContext());

            int rectHeight = view.getMeasuredHeight() * 9 / 10 * (currentIntValue - minRectHeight) / (maxRectHeight - minRectHeight + 1) + 1;
            if (rectHeight < SortFragment.minHeight) {
                rectHeight = SortFragment.minHeight;
            }
            rectView.setRectHeight(rectHeight);
            rectView.setImageBitmap(createCalculatedBitmap(mRectWidth, rectHeight, currentIntValue));

            rectView.setMinimumHeight(minHeight);
            rectView.setNumber(currentIntValue);
            rectView.setId(pos);
            if (mLlContainer != null) {
                mLlContainer.addView(rectView, lp);
            }
            pos++;
        }

        if (algorithmSelected == Constant.ALGORITHM_QUICK) {
            RelativeLayout.LayoutParams lpLine = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lpLine.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lpLine.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            viewLine = (LinearLayout) inflater.inflate(R.layout.view_line, null);
            mRlContainerParent.addView(viewLine, lpLine);
        }
    }

    private void drawRectsMerge(ArrayList<Integer> listToDraw) {
        if (mLlContainerOriginal != null) {
            mLlContainerOriginal.removeAllViews();
            mLlContainerOriginal.clearAnimation();
        }
        if (mLlContainerTemp != null) {
            mLlContainerTemp.removeAllViews();
            mLlContainerTemp.clearAnimation();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int marginInPx = Util.dpToPx(getContext(), RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);
        int pos = 0;
        mWidth = view.getMeasuredWidth();
        mRectWidth = mWidth / rectCount - RECT_MARGIN;
        for (Integer currentIntValue : listToDraw) {
            RectView rectView = new RectView(getContext());

            int rectHeight = view.getMeasuredHeight() * 9 / 20 * (currentIntValue - minRectHeight) / (maxRectHeight - minRectHeight + 1) + 1;
            if (rectHeight < SortFragment.minHeight) {
                rectHeight = SortFragment.minHeight;
            }
            rectView.setRectHeight(rectHeight);
            rectView.setImageBitmap(createCalculatedBitmap(mRectWidth, rectHeight, currentIntValue));

            rectView.setMinimumHeight(minHeight);
            rectView.setNumber(currentIntValue);
            rectView.setId(pos);
            mLlContainerOriginal.addView(rectView, lp);
            pos++;
        }
    }

     private Bitmap createCalculatedBitmap(int mRectWidth, int mRectHeight, Integer currentIntValue) {
        lineHeightArray.put(currentIntValue, mRectHeight);
        return Bitmap.createBitmap(mRectWidth, mRectHeight, Bitmap.Config.ALPHA_8);
    }

}
