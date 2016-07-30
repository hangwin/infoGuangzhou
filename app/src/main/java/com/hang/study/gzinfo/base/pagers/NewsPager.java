package com.hang.study.gzinfo.base.pagers;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hang.study.gzinfo.activity.MainActivity;
import com.hang.study.gzinfo.base.BasePager;
import com.hang.study.gzinfo.base.detailMenu.BaseDetailMenu;
import com.hang.study.gzinfo.base.detailMenu.DetailInteract;
import com.hang.study.gzinfo.base.detailMenu.DetailNews;
import com.hang.study.gzinfo.base.detailMenu.DetailPhotos;
import com.hang.study.gzinfo.base.detailMenu.DetailTopic;
import com.hang.study.gzinfo.bean.NewsMenu;
import com.hang.study.gzinfo.fragment.LeftMenuFragment;
import com.hang.study.gzinfo.utils.Global;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by hang on 16/6/5.
 */
public class NewsPager extends BasePager {
    public NewsMenu newsMenu;
    public NewsPager(Activity activity) {
        super(activity);
    }
    public ArrayList<BaseDetailMenu> detailMenus;
    @Override
    public void initData() {
       /* TextView tv=new TextView(mActivity);
        tv.setText("新闻");
        title.setText("新闻中心");
        tv.setGravity(Gravity.CENTER);
        frameContent.addView(tv);
        */

        getDataFromServer();
        img_menu.setVisibility(View.VISIBLE);
    }

    public void getDataFromServer() {
        HttpUtils hutils=new HttpUtils();
        hutils.send(HttpRequest.HttpMethod.GET, Global.categoruUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
              //  System.out.println("服务器返回结果：" + result);
                processData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void processData(String data) {
        Gson gson=new Gson();
        newsMenu=gson.fromJson(data, NewsMenu.class);
        MainActivity mainActivity= (MainActivity) mActivity;
        final LeftMenuFragment leftMenu=mainActivity.getLeftMenu();
        if(leftMenu!=null)
        leftMenu.fillData(newsMenu.data);
        detailMenus=new ArrayList<>();
        detailMenus.add(new DetailNews(mActivity,newsMenu.data.get(0).children));
        detailMenus.add(new DetailTopic(mActivity));
        if(grid==null) {
            System.out.println("grid---->null");
        }
        detailMenus.add(new DetailPhotos(mActivity,grid));
        detailMenus.add(new DetailInteract(mActivity));
        changeDetailPage(0);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftMenu.toggle();
            }
        });
    }
    public void changeDetailPage(int position) {
        View view=detailMenus.get(position).rootView;
        detailMenus.get(position).initData();
        frameContent.removeAllViews();
        frameContent.addView(view);
        title.setText(newsMenu.data.get(position).title);

    }
}
