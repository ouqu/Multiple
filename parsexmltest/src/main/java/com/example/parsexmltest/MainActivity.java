package com.example.parsexmltest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView listView;

    private class ParseXml extends AsyncTask<String,Void,List<CsdnParser.QuestionState>>{
        @Override
        protected List<CsdnParser.QuestionState> doInBackground(String... strings) {
            List<CsdnParser.QuestionState> list=null;
            try{
                InputStream inputStream=new MyNetConnect().getStreamFromURL(strings[0]);
                list = new CsdnParser().parse(inputStream);
            }
            catch (Exception e){
                e.printStackTrace();

            }
            return list;
        }

        @Override
        protected void onPostExecute(List<CsdnParser.QuestionState> list) {
            //关闭进度圈
            progressBar.setVisibility(View.GONE);
            if (list==null){
                Toast.makeText(MainActivity.this,"parse failed",Toast.LENGTH_SHORT).show();
            }
            else {
                listView.setAdapter(new QuestionStateAdapter(MainActivity.this,
                                                                 R.layout.layout_item,
                                                                 list));
            }

        }

        @Override
        protected void onPreExecute() {
            //显示进度圈
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listView);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);

        Button button= (Button) findViewById(R.id.btn_load);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyNetConnect.isConnected()){   //如果有网,开始解析
                    new ParseXml().execute("http://192.168.56.1/wenda2.html");
                }
                else{  //如果没网,弹出提示
                    Toast.makeText(MainActivity.this,"network doesn't connect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
