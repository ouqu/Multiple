package com.example.parsexmltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hahaha on 9/2/16.
 */
public class QuestionStateAdapter extends ArrayAdapter<CsdnParser.QuestionState> {
    private int layout_id;

    public QuestionStateAdapter(Context context, int resource, List<CsdnParser.QuestionState> objects) {
        super(context, resource, objects);
        layout_id=resource;
    }

    class ViewHolder{  //控件缓存
        TextView time_usrname;
        TextView question;
        TextView summary;
        TextView state;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CsdnParser.QuestionState questionState=getItem(position);
        ViewHolder viewHolder;
        View view;
        if(convertView!=null){   //已缓存,直接拿
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        else{  //未缓存
            view= LayoutInflater.from(getContext()).inflate(layout_id,null); //载入布局
            viewHolder=new ViewHolder();
            viewHolder.time_usrname= (TextView) view.findViewById(R.id.tv_time_usrname);
            viewHolder.question= (TextView) view.findViewById(R.id.tv_question);
            viewHolder.summary= (TextView) view.findViewById(R.id.tv_summary);
            viewHolder.state= (TextView) view.findViewById(R.id.tv_state);
        }
        viewHolder.time_usrname.setText(questionState.time+" "+questionState.username);
        viewHolder.question.setText(questionState.question);
        viewHolder.summary.setText(questionState.summary);
        viewHolder.state.setText(questionState.tongwen+" "
                                 +questionState.shoucang+" "
                                 +questionState.liulan);
        return view;
    }
}
