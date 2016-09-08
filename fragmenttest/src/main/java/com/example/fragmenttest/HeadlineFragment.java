package com.example.fragmenttest;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hahaha on 8/20/16.
 */
public class HeadlineFragment extends Fragment {

    //调用了此fragment的Acitivity必须实现此接口
    // 在onHeadSlected方法中显示对应的内容
    public interface OnHeadlineSelectedListener{
        public void onHeadSlected(int position);
    }

    OnHeadlineSelectedListener mHeadlineListener;

    //在attach时把activity保存
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mHeadlineListener= (OnHeadlineSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    +" must impement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.headline_layout,container,false);

        //按钮点击时调用OnHeadlineSelectedListener接口
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.hFragment_display:
                        mHeadlineListener.onHeadSlected(0);
                        break;
                    case R.id.hFragment_btn1:
                        mHeadlineListener.onHeadSlected(1);
                        break;
                    case R.id.hFragment_btn2:
                        mHeadlineListener.onHeadSlected(2);
                        break;
                    case R.id.hFragment_btn3:
                        mHeadlineListener.onHeadSlected(3);
                        break;
                    case R.id.hFragment_refresh:
                        mHeadlineListener.onHeadSlected(4);
                        break;
                }
            }
        };
        ((Button) view.findViewById(R.id.hFragment_display)).setOnClickListener(onClickListener);
        ((Button) view.findViewById(R.id.hFragment_btn1)).setOnClickListener(onClickListener);
        ((Button) view.findViewById(R.id.hFragment_btn2)).setOnClickListener(onClickListener);
        ((Button) view.findViewById(R.id.hFragment_btn3)).setOnClickListener(onClickListener);
        ((Button) view.findViewById(R.id.hFragment_refresh)).setOnClickListener(onClickListener);
        return view;
    }
}
