package org.solarex.turingchat.component;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import org.solarex.turingchat.utils.Logs;

//log MotionEvent flow
public class ContainerListView extends ListView {
    private static final String TAG = "ContainerListView";

    public ContainerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContainerListView(Context context) {
        super(context);
    }

    public ContainerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContainerListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        Logs.d(TAG, "dispatchTouchEvent | ev = " + getAction(ev.getAction()) + ",ret = " + ret);
        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        Logs.d(TAG, "onInterceptTouchEvent | ev = " + getAction(ev.getAction()) + ",ret = " + ret);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean ret = super.onTouchEvent(ev);
        Logs.d(TAG, "onTouchEvent | ev = " + getAction(ev.getAction()) + ",ret = " + ret);
        return ret;
    }

    public static String getAction(int action){
        String actionName = "";
        switch (action){
            case 0:
                actionName = "DOWN";
                break;
            case 1:
                actionName = "UP";
                break;
            case 2:
                actionName = "MOVE";
                break;
            default:
                actionName = "" + action;
                break;
        }
        return actionName;
    }
}
