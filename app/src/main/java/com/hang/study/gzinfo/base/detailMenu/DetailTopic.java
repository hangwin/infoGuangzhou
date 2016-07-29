package com.hang.study.gzinfo.base.detailMenu;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hang on 16/7/18.
 */
public class DetailTopic extends BaseDetailMenu {
    public DetailTopic(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView text=new TextView(mActivity);
        text.setText("专题详情页");
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
