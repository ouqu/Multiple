package com.example.fragmenttest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hahaha on 9/8/16.
 */
public class ArticleDisplayFragment extends Fragment{
    public static final String ARG="object";
    //private String text;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article_display_layout,container,false);
        Bundle args=getArguments();
        TextView textView= (TextView) view.findViewById(R.id.adFragment_tv);
        mTextView=textView;
        textView.setText(args.getString(ARG));

        return view;
    }



}
