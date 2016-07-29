package com.hang.study.gzinfo.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hang.study.gzinfo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hang on 16/7/24.
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener{
    public int headHeight=0;
    public int startY=-1;
    public View head;
    public View foot;
    public ImageView arrow;
    public TextView state;
    public TextView time;
    public ProgressBar pb;
    final int STATE_PULL_FRUSH=1;
    final int STATE_RELEASE_FRUSH=2;
    final int STATE_FLUSHING=3;
    public int curState=STATE_PULL_FRUSH;
    public RotateAnimation down;
    public RotateAnimation up;
    public int footHeight;
    private Boolean isMore=false;
    public MyListView(Context context) {
        super(context);
        initHead();
        initFoot();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHead();
        initFoot();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHead();
        initFoot();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initHead();
        initFoot();
    }
    private void initHead() {
        initAnim();
        head=View.inflate(getContext(), R.layout.refresh_layout,null);
        arrow= (ImageView) head.findViewById(R.id.arrow);
        state= (TextView) head.findViewById(R.id.state);
        time= (TextView) head.findViewById(R.id.showTime);
        pb= (ProgressBar) head.findViewById(R.id.progress);
        head.measure(0, 0);
        headHeight=head.getMeasuredHeight();
        head.setPadding(0, -headHeight, 0, 0);
        this.addHeaderView(head);
    }
    private void initFoot() {
        foot=View.inflate(getContext(),R.layout.listview_foot,null);
        foot.measure(0, 0);
        footHeight=foot.getMeasuredHeight();
        foot.setPadding(0, -footHeight, 0, 0);
        this.addFooterView(foot);
        this.setOnScrollListener(this);
    }
    public void initAnim() {
        down=new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5F);
        down.setDuration(200);
        down.setFillAfter(true);
        up=new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5F);
        up.setDuration(200);
        up.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY= (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(curState==STATE_FLUSHING) {
                    break;
                }
                if(startY==-1) {
                    startY= (int) ev.getY();
                }
                int endY= (int) ev.getY();
                int dy=endY-startY;
                int curFirst=this.getFirstVisiblePosition();
                int padding=dy-headHeight;
                if(dy>=0&&curFirst==0) {
                    head.setPadding(0,padding,0,0);
                    if(padding>0&&curState!=STATE_RELEASE_FRUSH) {
                        curState = STATE_RELEASE_FRUSH;
                        refreshState();
                    }else if(padding<=0&&curState!=STATE_PULL_FRUSH) {
                        curState=STATE_PULL_FRUSH;
                        refreshState();
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                startY=-1;
                if(curState==STATE_RELEASE_FRUSH) {
                    curState=STATE_FLUSHING;
                    head.setPadding(0,0,0,0);
                    refreshState();
                }else if(curState==STATE_PULL_FRUSH) {
                    head.setPadding(0,-headHeight,0,0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshState() {
        switch (curState) {
            case STATE_PULL_FRUSH:
                arrow.startAnimation(up);
                state.setText("下拉刷新");
                arrow.setVisibility(VISIBLE);
                pb.setVisibility(INVISIBLE);
                break;
            case STATE_RELEASE_FRUSH:
                arrow.startAnimation(down);
                state.setText("松开刷新");
                arrow.setVisibility(VISIBLE);
                pb.setVisibility(INVISIBLE);
                break;
            case STATE_FLUSHING:
                state.setText("正在刷新...");
                arrow.clearAnimation();
                arrow.setVisibility(INVISIBLE);
                pb.setVisibility(VISIBLE);
                reflashListerner.onReflash();
                break;
        }
    }
    public OnReflashListerner reflashListerner;
    public void setOnReflashListener(OnReflashListerner listener) {
        this.reflashListerner=listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE) {
            if(getLastVisiblePosition()==getCount()-1&&!isMore) {
                foot.setPadding(0, 0, 0, 0);
                if(reflashListerner!=null)
                    isMore=true;
                   reflashListerner.onLoad();

            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    public interface OnReflashListerner {
        public void onReflash();
        public void onLoad();
    }
    public void refleshComplete(Boolean bool) {
        if(!isMore) {
            curState = STATE_PULL_FRUSH;
            head.setPadding(0, -headHeight, 0, 0);
            state.setText("下拉刷新");
            arrow.setVisibility(VISIBLE);
            pb.setVisibility(INVISIBLE);
            if (bool) {
                setTime();
            }
        }else {
            System.out.println("===>test");
            foot.setPadding(0,-footHeight,0,0);
            isMore=false;
        }

    }
    public void setTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t=sdf.format(new Date());
        time.setText(t);
    }


}
