package com.hang.study.gzinfo.base.detailMenu;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hang on 16/7/18.
 */
public class DetailInteract extends BaseDetailMenu {
    public DetailInteract(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView text=new TextView(mActivity);
        text.setText("互动详情页");
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
