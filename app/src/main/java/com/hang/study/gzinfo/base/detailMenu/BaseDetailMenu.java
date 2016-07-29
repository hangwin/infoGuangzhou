package com.hang.study.gzinfo.base.detailMenu;

import android.app.Activity;
import android.view.View;

/**
 * Created by hang on 16/7/18.
 */
public abstract class BaseDetailMenu {
    public Activity mActivity;
    public View rootView;
    public BaseDetailMenu(Activity activity) {
        this.mActivity=activity;
        rootView=initView();
    }
    public abstract View initView() ;
    public void initData() {

    }
}
