package com.hang.study.gzinfo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 16/6/1.
 */
public class GuideActivity extends Activity {
    private ViewPager vp_guide;
    private LinearLayout points;
    private ImageView pointer;
    private int[] imgId={R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private List<ImageView> images=new ArrayList<>();
    private int disX=0;
    private Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide_activity);
        vp_guide= (ViewPager) findViewById(R.id.vp_guide);
        points= (LinearLayout) findViewById(R.id.points);
        pointer= (ImageView) findViewById(R.id.pointer);
        start= (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePrefUtil.setBoolean(GuideActivity.this, "isFirstUse", false);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
        pointer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                disX=points.getChildAt(1).getLeft()-points.getChildAt(0).getLeft();
               // System.out.println("disX===>"+disX);
                pointer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        for (int i=0;i<imgId.length;i++) {
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(imgId[i]);
            images.add(imageView);
        }
        for(int i=0;i<imgId.length;i++) {
            ImageView point=new ImageView(this);
            point.setImageResource(R.drawable.point_gary);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin=10;
            if(i>0) {
                point.setLayoutParams(layoutParams);
            }
            points.addView(point);
        }
        vp_guide.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(images.get(position));
                return images.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView((View) object);
            }
        });
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 System.out.println("left:"+pointer.getLeft());
                 RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                 layoutParams.leftMargin= (int) (disX*positionOffset+position*disX);
                 pointer.setLayoutParams(layoutParams);

            }

            @Override
            public void onPageSelected(int position) {
                 if(position==images.size()-1) {
                     start.setVisibility(View.VISIBLE);
                 }else {
                     start.setVisibility(View.INVISIBLE);
                 }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
