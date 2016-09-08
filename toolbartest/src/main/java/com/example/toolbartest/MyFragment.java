package com.example.toolbartest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hahaha on 9/4/16.
 */
public class MyFragment extends Fragment {
    public static final String ARG="object";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_myfragment,container,false);
        Bundle args=getArguments();
        ((TextView)view.findViewById(R.id.fragment_tv)).setText(
                Integer.toString(args.getInt(ARG))
        );
        return view;
    }
}
