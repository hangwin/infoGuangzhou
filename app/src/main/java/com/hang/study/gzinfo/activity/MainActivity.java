package com.hang.study.gzinfo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.fragment.ContentFragment;
import com.hang.study.gzinfo.fragment.LeftMenuFragment;


/**
 * Created by hang on 16/6/1.
 */
public class MainActivity extends FragmentActivity {
    private DrawerLayout left_menu;

    public DrawerLayout getLeft_menu() {
        return left_menu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        left_menu= (DrawerLayout) findViewById(R.id.drawerLayout);
        getSupportFragmentManager().beginTransaction().replace(R.id.leftmenu,new LeftMenuFragment(),"leftMenu").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container,new ContentFragment(),"content").commit();

    }

    public void setLeftMenuClose(boolean tag) {

        if(!tag) {
            //System.out.println("关闭手势滑动");
            left_menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else {
            //System.out.println("打开手势滑动");
            left_menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public LeftMenuFragment getLeftMenu() {
        LeftMenuFragment leftMenu= (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag("leftMenu");
        return leftMenu;
    }

    public ContentFragment getContent() {
        ContentFragment contentFragment= (ContentFragment) getSupportFragmentManager().findFragmentByTag("content");
        return  contentFragment;
    }

}
