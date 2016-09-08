package com.example.fragmenttest;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements HeadlineFragment.OnHeadlineSelectedListener{


    private boolean display=false;
    private ViewPager viewPager;
    private ArticleFragment articleFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onHeadSlected(int position) {
        switch (position){
            case 0: //按下display按钮
                if(!display)
                {
                    articleFragment=new ArticleFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.afragment_container,articleFragment).commit();
                    display=true;

                }
                break;
            case 1:
                if (display)
                {articleFragment.setViewPagerItem(0);}
                else{
                    Toast.makeText(MainActivity.this,"please click display",Toast.LENGTH_SHORT).show();
                }

                break;
            case 2:
                if (display)
                {articleFragment.setViewPagerItem(1);}
                else{
                    Toast.makeText(MainActivity.this,"please click display",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if (display)
                {articleFragment.setViewPagerItem(2);}
                else{
                    Toast.makeText(MainActivity.this,"please click display",Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                articleFragment.update(1,"fresh 1");
                break;
        }
    }
}

