package com.example.instagram.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewAnimator;

import com.example.instagram.R;

public class SendCommentButton extends ViewAnimator implements View.OnClickListener {
    public final static int STATE_SEND = 0;
    public final static int STATE_DONE = 1;

    private static final long RESET_STATE_DELAY_MILIS = 2000;

    private OnSendClickListener mOnClickListener;
    private int currentState;

    private Runnable revertStateRunnable = new Runnable() {
        @Override
        public void run() {
            setCurrentState(STATE_SEND);
        }
    };


    public SendCommentButton(Context context) {
        super(context);
        init();
    }

    public SendCommentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onSendClickListener(v);
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_send_comment_button, this, true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        currentState = STATE_SEND;
        super.setOnClickListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(revertStateRunnable);
        super.onDetachedFromWindow();
    }

    public void setCurrentState(int state) {
        if (state == currentState) {
            return;
        }

        currentState = state;
        if (state == STATE_DONE) {
            setEnabled(false);
            postDelayed(revertStateRunnable, RESET_STATE_DELAY_MILIS);
            setInAnimation(getContext(), R.anim.slide_in_done);
            setOutAnimation(getContext(), R.anim.slide_out_send);
        } else if (state == STATE_SEND) {
            setEnabled(true);
            setInAnimation(getContext(), R.anim.slide_in_send);
            setOutAnimation(getContext(), R.anim.sliede_out_done);
        }
        showNext();
    }

    public void setOnSendClickListener(OnSendClickListener onSendClickListener) {
        this.mOnClickListener = onSendClickListener;
    }

    public interface OnSendClickListener{
        void onSendClickListener(View v);
    }
}
