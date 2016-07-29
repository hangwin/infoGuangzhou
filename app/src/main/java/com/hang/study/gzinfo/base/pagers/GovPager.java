package com.hang.study.gzinfo.base.pagers;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hang.study.gzinfo.base.BasePager;


/**
 * Created by hang on 16/6/5.
 */
public class GovPager extends BasePager {
    public GovPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView tv=new TextView(mActivity);
        tv.setText("政务");
        title.setText("政务");
        tv.setGravity(Gravity.CENTER);
        frameContent.addView(tv);
        img_menu.setVisibility(View.VISIBLE);

    }
}
