package com.hang.study.gzinfo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.activity.MainActivity;
import com.hang.study.gzinfo.base.pagers.NewsPager;
import com.hang.study.gzinfo.bean.NewsMenu;

import java.util.ArrayList;

/**
 * Created by hang on 16/6/5.
 */
public class LeftMenuFragment extends Fragment {
    public ListView listView;
    public  MyAdapter myAdapter;
    public ArrayList<NewsMenu.leftTab> myData;
    public int cur=0;
    public Context mActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.left_menu_fragment,container,false);
        listView= (ListView) rootView.findViewById(R.id.leftMenuList);
        mActivity=getActivity();
        return rootView;
    }

    public void fillData(ArrayList<NewsMenu.leftTab> data) {
        cur=0;
        myData=data;
        myAdapter=new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cur=position;
                myAdapter.notifyDataSetChanged();
                toggle();
                changeDetailPager(position);
            }
        });
    }

   public void changeDetailPager(int position) {
       MainActivity mainActivity= (MainActivity) mActivity;
       ContentFragment contentFragment=mainActivity.getContent();
       NewsPager newsPager=contentFragment.getNewsPager();
       newsPager.changeDetailPage(position);

   }
   public void toggle() {
       MainActivity mainActivity= (MainActivity) mActivity;
       DrawerLayout drawerLayout=mainActivity.getLeft_menu();
       if(drawerLayout.isDrawerOpen(GravityCompat.START))
           drawerLayout.closeDrawer(GravityCompat.START);
       else
           drawerLayout.openDrawer(GravityCompat.START);
   }
   public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return myData.size();
        }

        @Override
        public Object getItem(int position) {
            return myData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=LayoutInflater.from(LeftMenuFragment.this.getContext()).inflate(R.layout.left_menu_list_item,null);
            TextView textView= (TextView) view.findViewById(R.id.menutext);
            textView.setText(myData.get(position).title);
            if(position==cur)
                textView.setEnabled(true);
            return view;
        }
    }
}
