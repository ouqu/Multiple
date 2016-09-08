package com.example.fragmenttest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hahaha on 8/20/16.
 */
public class ArticleFragment extends Fragment {
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;

    public void setViewPagerItem(int current){
        if(viewPager!=null)
        {viewPager.setCurrentItem(current);}
    }

    //text为需要更新的数据
    public void update(int position,String text){
        if(myPagerAdapter!=null){
             myPagerAdapter.update(position,text);
            myPagerAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.article_layout,container,false);
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.aFragment_viewpager);
        this.viewPager=viewPager;
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getFragmentManager());
        this.myPagerAdapter=myPagerAdapter;
        viewPager.setAdapter(myPagerAdapter);

        return view;
    }
}
