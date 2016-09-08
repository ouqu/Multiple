package com.example.fragmenttest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

/**
 * Created by hahaha on 9/8/16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private boolean updateFlag=false;
    private int freshPosition;
    private String freshText;

    public void update(int freshPosition,String freshText){
        updateFlag=true;
        this.freshPosition=freshPosition;
        this.freshText=freshText;
    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager=fm;
    }

    @Override
    public int getCount() {
        return 3;
    }

    //此方法只在第一次生成fragment时调用
    @Override
    public Fragment getItem(int position) {
          Fragment fragment=new ArticleDisplayFragment();
        Bundle args=new Bundle();
        args.putString(ArticleDisplayFragment.ARG,"init "+(position+1));
        fragment.setArguments(args);
        return fragment;
    }

    //返回POSITION_NONE表示可以更新
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ArticleDisplayFragment fragment=(ArticleDisplayFragment)super.instantiateItem(container, position);
        //如果当前fragment需要更更新
        if(updateFlag && freshPosition==position){
            String fragmentTag=fragment.getTag();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            //移除当前fragment
            ft.remove(fragment);

            //新建一个fragment
            fragment=new ArticleDisplayFragment();
            Bundle args=new Bundle();
            args.putString(ArticleDisplayFragment.ARG,freshText);
            fragment.setArguments(args);

            //提交fragment
            ft.add(container.getId(),fragment,fragmentTag);
            ft.attach(fragment);
            ft.commit();

            //回复标志位
            updateFlag=false;
        }
        return fragment;
    }
}
