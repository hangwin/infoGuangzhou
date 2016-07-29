package com.hang.study.gzinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.utils.SharePrefUtil;


public class SplashActivity extends Activity {
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        relativeLayout= (RelativeLayout) findViewById(R.id.splash);
        RotateAnimation rotate=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);
        ScaleAnimation scale=new ScaleAnimation(0.2f,1,0.2f,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);
        AlphaAnimation alpha=new AlphaAnimation(0,1);
        alpha.setDuration(2000);
        AnimationSet animationSet=new AnimationSet(true);
        animationSet.addAnimation(rotate);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(alpha);
        relativeLayout.setAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               Boolean isFirstUse= SharePrefUtil.getBoolean(SplashActivity.this, "isFirstUse", true);
               Intent intent=null;
               if(isFirstUse) {
                   intent=new Intent(SplashActivity.this,GuideActivity.class);
                   //SharePrefUtil.setBoolean(SplashActivity.this,"isFirstUse",false);
               } else {
                   intent=new Intent(SplashActivity.this,MainActivity.class);
               }
               startActivity(intent);
               finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}
