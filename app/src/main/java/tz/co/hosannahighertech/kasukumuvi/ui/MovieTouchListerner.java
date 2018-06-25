package tz.co.hosannahighertech.kasukumuvi.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import tz.co.hosannahighertech.kasukumuvi.ui.interfaces.ClickListener;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.ui
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 23/06/2018 13:59.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MovieTouchListerner extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetector mGestureDetector;
    ClickListener mClickListener;

    public MovieTouchListerner(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

        mClickListener = clickListener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
            mClickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

}
