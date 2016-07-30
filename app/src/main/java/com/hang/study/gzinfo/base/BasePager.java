package com.hang.study.gzinfo.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hang.study.gzinfo.R;


/**
 * Created by hang on 16/6/5.
 */
public class BasePager {
    public Activity mActivity;
    public TextView title;
    public ImageView grid;
    public ImageView img_menu;
    public FrameLayout frameContent;
    public View mrootView;
    public BasePager(Activity activity){
        mActivity=activity;
        mrootView=initView();
    }
    public View initView() {
        View view= View.inflate(mActivity, R.layout.pager_layout,null);
        title= (TextView) view.findViewById(R.id.title);
        img_menu= (ImageView) view.findViewById(R.id.img_menu);
        frameContent= (FrameLayout) view.findViewById(R.id.frameContent);
        grid= (ImageView) view.findViewById(R.id.icon_list);
        return view;
    }
    public void initData() {

    }
 }
