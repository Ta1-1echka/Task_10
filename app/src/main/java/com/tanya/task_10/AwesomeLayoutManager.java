package com.tanya.task_10;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;

import static android.support.v7.widget.RecyclerView.LayoutManager;


public class AwesomeLayoutManager extends LayoutManager implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Recycler recycler;
    private SparseArray<View> viewCache = new SparseArray<>();
    int position = -1;

    public AwesomeLayoutManager(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    int top;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.recycler = recycler;
        top = 0;
        detachAndScrapAttachedViews(recycler);
        fillDown(recycler);
    }

    private void fillDown(RecyclerView.Recycler recycler) {
        int itemCount = getItemCount();
        for (int pos = 0; pos < itemCount; pos++) {
            View view = recycler.getViewForPosition(pos);
            view.setTag(pos);
            addView(view);
            view.setOnClickListener(this);
            measureChildWithMargins(view, 0, 0);
            layoutDecorated(view, 0, top, getWidth(), top + 200);
            top += 150;
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.dy += dy;
        this.state = state;
        System.out.println("" + dy);
        int delta = scrollVerticallyInternal(dy);
        offsetChildrenVertical(-delta);
        return delta;
    }

    int dy;
    RecyclerView.State state;

    private int scrollVerticallyInternal(int dy) {
        int childCount = getChildCount();
        int itemCount = getItemCount();
        if (childCount == 0) {
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
        if (dy < 0) {
            View firstView = getChildAt(0);
            int firstViewAdapterPos = getPosition(firstView);
            if (firstViewAdapterPos > 0) { //если верхняя вюшка не самая первая в адаптере
                delta = dy;
            } else { //если верхняя вьюшка самая первая в адаптере и выше вьюшек больше быть не может
                int viewTop = getDecoratedTop(firstView);
                delta = Math.max(viewTop, dy);
            }
        } else if (dy > 0) { //если контент уезжает вверх
            View lastView = getChildAt(childCount - 1);
            int lastViewAdapterPos = getPosition(lastView);
            if (lastViewAdapterPos < itemCount - 1) { //если нижняя вюшка не самая последняя в адаптере
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
    public void onClick(View v) {
        repaint((Integer) v.getTag(), v.getTop() + v.getMeasuredHeight() + 50);
    }

    private void repaint(int posRepaint, int topRepaint) {
        int itemCount = getItemCount();
        detachAndScrapAttachedViews(recycler);
        top = 0;
        System.out.println("*****"+position);
        if(position!=posRepaint) {
            View view = recycler.getViewForPosition(posRepaint);
            view.setTag(posRepaint);
            addView(view);
            view.setOnClickListener(this);
            measureChildWithMargins(view, 0, 0);
            layoutDecorated(view, 0, top, getWidth(), top + view.getMeasuredHeight());
            top = (10 + view.getMeasuredHeight());
            position = posRepaint;
        }
        else {
            position = -1;
            posRepaint = -1;
        }
        for (int pos = 0; pos < itemCount; pos++) {
            if (pos != posRepaint) {
            View view = recycler.getViewForPosition(pos);
            view.setTag(pos);
            addView(view);
            view.setOnClickListener(this);
            measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, 0, top, getWidth(), top + 200);
                top += 150;
            }

        }

        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, getItemCount() - 1);


    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }

    public class Animations {
        public Animation fromAtoB(float fromX, float fromY, float toX, float toY, int speed) {


            Animation fromAtoB = new TranslateAnimation(
                    Animation.ABSOLUTE, //from xType
                    fromX,
                    Animation.ABSOLUTE, //to xType
                    toX,
                    Animation.ABSOLUTE, //from yType
                    fromY,
                    Animation.ABSOLUTE, //to yType
                    toY
            );

            fromAtoB.setDuration(speed);
            fromAtoB.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
            return fromAtoB;
        }
    }
}
