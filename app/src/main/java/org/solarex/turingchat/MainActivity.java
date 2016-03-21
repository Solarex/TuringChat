package org.solarex.turingchat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.solarex.turingchat.bean.Msg;
import org.solarex.turingchat.component.ContainerListView;
import org.solarex.turingchat.impl.IView;
import org.solarex.turingchat.impl.PresenterImpl;
import org.solarex.turingchat.utils.Logs;

import java.util.ArrayList;


public class MainActivity extends Activity implements IView, View.OnClickListener{
    private static final String TAG = "MainActivity";
    private PresenterImpl mPresenter = null;
    private ContainerListView mListView = null;
    private Button mBtnSend = null;
    private EditText mEdText = null;
    private MsgAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPresenter = new PresenterImpl();
    }

    private void initView() {
        mListView = (ContainerListView)findViewById(R.id.main_lv);
        mEdText = (EditText) findViewById(R.id.main_et_edit);
        mBtnSend = (Button) findViewById(R.id.main_btn_send);
        mBtnSend.setOnClickListener(this);
    }



    @Override
    protected void onStart(){
        super.onStart();
        mPresenter.attachView(this);
        mPresenter.initMsg("你好，我是小冰~");
        //fix this later?
        ArrayList<Msg> msgs = mPresenter.getMsgs();
        if (msgs != null && msgs.size() != 0){
            mAdapter = new MsgAdapter(this.getApplicationContext(), msgs);
        }
        if (mAdapter != null){
            mListView.setAdapter(mAdapter);
        }
    }

    @Override
    public void updateUI(final ArrayList<Msg> msgs) {
        Logs.d(TAG, "updateUI | msgs = " + msgs);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mAdapter.bindData(msgs);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn_send:
                Editable editable = mEdText.getText();
                Logs.d(TAG, "onClick | user input = " + editable);
                if (!TextUtils.isEmpty(editable)){
                    mPresenter.sendMsg(editable.toString());
                    mEdText.setText("");
                    //mEdText.requestFocus();
                } else {
                    mPresenter.showInputError();
                }
                break;
            default:
                Logs.d(TAG, "onClick | unknown component clicked");
                break;
        }
    }

    @Override
    public void notifyInputError(){
        Toast.makeText(this, "Input must not be empty!", Toast.LENGTH_LONG).show();
    }

    // log MotionEvent flow
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        Logs.d(TAG, "dispatchTouchEvent | ev = " + ContainerListView.getAction(ev.getAction()) + ", ret = " + ret);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        Logs.d(TAG, "onTouchEvent | ev = " + ContainerListView.getAction(event.getAction()) + ", ret = " + ret);
        return ret;
    }
}
