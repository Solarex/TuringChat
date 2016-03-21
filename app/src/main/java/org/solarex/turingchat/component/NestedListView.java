package org.solarex.turingchat.component;

import android.annotation.TargetApi;
import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import org.solarex.turingchat.utils.Logs;


public class NestedListView extends ListView {

    private static final String TAG = "NestedListView";
    public NestedListView(Context context) {
        super(context);
    }

    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NestedListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        Logs.d(TAG, "dispatchTouchEvent | ev = " + ContainerListView.getAction(ev.getAction()) + ", ret = " + ret);
        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        Logs.d(TAG, "onInterceptTouchEvent | ev = " + ContainerListView.getAction(ev.getAction()) + ", ret = " + ret);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean originRet = super.onTouchEvent(ev);
        Logs.d(TAG, "onTouchEvent | ev = " + ContainerListView.getAction(ev.getAction()) + ",originRet = " + originRet);
        Logs.d(TAG, "=====onTouchEvent,handle event here======");
        if (shouldChildHandle(ev)){
            getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
            return originRet;
        }
    }

    private boolean shouldChildHandle(MotionEvent event){
        boolean ret = false;
        View firstChild = getChildAt(getFirstVisiblePosition());
        View lastChild = getChildAt(getLastVisiblePosition());

        if (event.getY() < firstChild.getTop() || event.getY() > lastChild.getBottom() ){
            ret = false;
        } else {
            ret = true;
        }
        Logs.d(TAG, "=====shouldChildHandle dump info begin=====");
        Logs.d(TAG, "first_visible_child{top = " + firstChild.getTop() + ",bottom = " + firstChild.getBottom() + ",left = " + firstChild.getLeft() + ",right = " + firstChild.getRight() + "}");
        Logs.d(TAG, "last_visible_child{top = " + lastChild.getTop() + ",bottom = " + lastChild.getBottom() + ",left = " + lastChild.getLeft() + ", right = " + lastChild.getRight() + "}");
        Logs.d(TAG, "event{X = " + event.getX() + ", Y = " + event.getY() + ",name = " + ContainerListView.getAction(event.getAction()) + "}");
        Logs.d(TAG, "should handle ret = " + ret);
        Logs.d(TAG, "=====shouldChildHandle dump info end=====");
        return ret;
    }
}
