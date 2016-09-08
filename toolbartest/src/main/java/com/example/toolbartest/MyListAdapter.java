package com.example.toolbartest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by hahaha on 9/4/16.
 */
public class MyListAdapter extends ArrayAdapter<Integer> {

    private int id;
    public MyListAdapter(Context context, int resource, List<Integer> objects) {
        super(context, resource, objects);
        id=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Integer data=getItem(position);
        View view=null;
        view=LayoutInflater.from(getContext()).inflate(id,null);
        TextView textView= (TextView) view.findViewById(R.id.listview_tv);
        textView.setText(data.toString());
        return view;

    }
}
