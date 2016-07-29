package com.hang.study.gzinfo.base.detailMenu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.bean.NewsMenu;
import com.viewpagerindicator.TabPageIndicator;


import java.util.ArrayList;

/**
 * Created by hang on 16/7/18.
 */
public class DetailNews extends BaseDetailMenu implements View.OnClickListener {
    public ViewPager vp;
    public ArrayList<TabNewsPager> tabNewsPagers;
    public TabPageIndicator tabPagerIndicator;
    public ArrayList<NewsMenu.children> children;
    public ImageButton next;
    public DetailNews(Activity activity, ArrayList<NewsMenu.children> children) {
        super(activity);
        this.children=children;
    }

    @Override
    public View initView() {
        /*TextView text=new TextView(mActivity);
        text.setText("新闻详情页");
        text.setGravity(Gravity.CENTER); */
        View view=LayoutInflater.from(mActivity).inflate(R.layout.detail_news,null);
        vp= (ViewPager) view.findViewById(R.id.detail_news_vp);
        tabPagerIndicator= (TabPageIndicator) view.findViewById(R.id.tabTitle);
        next= (ImageButton) view.findViewById(R.id.next);
        next.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        tabNewsPagers=new ArrayList<>();
        for(int i=0;i<children.size();i++) {
            tabNewsPagers.add(new TabNewsPager(mActivity,children.get(i)));
        }
        vp.setAdapter(new myAdapter());
        tabPagerIndicator.setViewPager(vp);
    }

    @Override
    public void onClick(View v) {
        int cur=vp.getCurrentItem();
        vp.setCurrentItem(++cur);

    }

    class myAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
           // System.out.println("===>"+tabNewsPagers.get(position).children.title);
            return tabNewsPagers.get(position).children.title;
        }

        @Override
        public int getCount() {
            return tabNewsPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabNewsPager pager=tabNewsPagers.get(position);
            View view=pager.rootView;
            pager.initData();
            container.addView(view);
            return view;
        }
    }
}
