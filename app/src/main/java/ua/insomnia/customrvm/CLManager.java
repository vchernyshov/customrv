package ua.insomnia.customrvm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CLManager extends RecyclerView.LayoutManager {

    private static final String TAG = "CLManager";

    public static final int OFFSET = 50;

    public static final int VISIBLE_ITEMS = 3;

    private boolean isExpanded = false;

    RecyclerView.Recycler r;

    public CLManager(){}


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        detachAndScrapAttachedViews(recycler);

        int itemCount = getItemCount();

        for (int i = 0; i < itemCount; i++) {
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);

            int decoratedWidth = getDecoratedMeasuredWidth(child);
            int decoratedHeight = getDecoratedMeasuredHeight(child);

            int offset;

            if (!isExpanded) {
                // we adding visible items
                if (i > (itemCount - VISIBLE_ITEMS)) {
                    offset = (itemCount - i - 1) * OFFSET;
                }
                // we adding invisible items
                else {
                    offset = (VISIBLE_ITEMS -1) * OFFSET;
                }
            } else {
                offset = (itemCount - i - 1) * decoratedHeight;

            }

            int decoratedBottom = getHeight() - offset;
            int decoratedTop = decoratedBottom - decoratedHeight;
            layoutDecorated(child, 0, decoratedTop, decoratedWidth, decoratedBottom);

        }
    }

    @Override
    public boolean canScrollVertically() {
        return isExpanded() ? true  : false;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = scrollVerticallyInternal(dy);
        offsetChildrenVertical(-delta);
        return delta;
    }

    private int scrollVerticallyInternal(int dy) {
        int childCount = getChildCount();
        int itemCount = getItemCount();
        if (childCount == 0){
            return 0;
        }

        final View topView = getChildAt(0);
        final View bottomView = getChildAt(childCount - 1);

        //Случай, когда все вьюшки поместились на экране
        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if (viewSpan <= getHeight()) {
            return 0;
        }

        int delta = 0;
        //если контент уезжает вниз
        if (dy < 0){
            View firstView = getChildAt(0);
            int firstViewAdapterPos = getPosition(firstView);
            if (firstViewAdapterPos > 0){ //если верхняя вюшка не самая первая в адаптере
                delta = dy;
            } else { //если верхняя вьюшка самая первая в адаптере и выше вьюшек больше быть не может
                int viewTop = getDecoratedTop(firstView);
                delta = Math.max(viewTop, dy);
            }
        } else if (dy > 0){ //если контент уезжает вверх
            View lastView = getChildAt(childCount - 1);
            int lastViewAdapterPos = getPosition(lastView);
            if (lastViewAdapterPos < itemCount - 1){ //если нижняя вюшка не самая последняя в адаптере
                delta = dy;
            } else { //если нижняя вьюшка самая последняя в адаптере и ниже вьюшек больше быть не может
                int viewBottom = getDecoratedBottom(lastView);
                int parentBottom = getHeight();
                delta = Math.min(viewBottom - parentBottom, dy);
            }
        }
        return delta;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        Log.d(TAG, "onItemsMoved() called with: from = [" + from + "], to = [" + to + "], itemCount = [" + itemCount + "]");
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
        requestLayout();
    }
}
