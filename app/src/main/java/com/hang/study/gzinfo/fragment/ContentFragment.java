package com.hang.study.gzinfo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.activity.MainActivity;
import com.hang.study.gzinfo.base.BasePager;
import com.hang.study.gzinfo.base.myViewPager;
import com.hang.study.gzinfo.base.pagers.GovPager;
import com.hang.study.gzinfo.base.pagers.HomePager;
import com.hang.study.gzinfo.base.pagers.NewsPager;
import com.hang.study.gzinfo.base.pagers.SettingPager;
import com.hang.study.gzinfo.base.pagers.SmartPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 16/6/5.
 */
public class ContentFragment extends Fragment {
    private myViewPager content_vp;
    private List<BasePager> pages=new ArrayList<>();
    private Activity mActivity;
    private RadioGroup radioGroup;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     //   View view=inflater.inflate(R.layout.content_fragment,container,false);
        View view=inflater.inflate(R.layout.content_fragment,container,false);
        content_vp= (myViewPager) view.findViewById(R.id.content_vp);
        radioGroup= (RadioGroup) view.findViewById(R.id.radios);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home:
                        content_vp.setCurrentItem(0, false);
                        break;
                    case R.id.news:
                        content_vp.setCurrentItem(1, false);
                        break;
                    case R.id.smart:
                        content_vp.setCurrentItem(2, false);
                        break;
                    case R.id.gov:
                        content_vp.setCurrentItem(3, false);
                        break;
                    case R.id.setting:
                        content_vp.setCurrentItem(4, false);
                        break;
                }
            }
        });

        content_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pages.get(position).initData();
                if (position == 0 || position == pages.size() - 1) {
                    setLeftMenu(false);
                } else
                    setLeftMenu(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void setLeftMenu(boolean b) {
        MainActivity mainActivity= (MainActivity) mActivity;
        mainActivity.setLeftMenuClose(b);
    }

    public void initData(){
        pages.add(new HomePager(mActivity));
        pages.add(new NewsPager(mActivity));
        pages.add(new SmartPager(mActivity));
        pages.add(new GovPager(mActivity));
        pages.add(new SettingPager(mActivity));
        pages.get(0).initData();
        content_vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(pages.get(position).mrootView);
                return pages.get(position).mrootView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }
    public NewsPager getNewsPager() {
        return (NewsPager) pages.get(1);
    }
}
