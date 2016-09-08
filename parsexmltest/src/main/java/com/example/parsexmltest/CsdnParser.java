package com.example.parsexmltest;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hahaha on 9/1/16.
 */
public class CsdnParser {
    private static final String namespace=null;

    public static class QuestionState{
        public final String time;
        public final String username;
        public final String question;
        public final String summary;
        public final String tongwen;
        public final String shoucang;
        public final String liulan;

        public QuestionState( String time, String username, String question, String summary, String tongwen, String shoucang,String liulan) {
            this.liulan = liulan;
            this.time = time;
            this.username = username;
            this.question = question;
            this.summary = summary;
            this.tongwen = tongwen;
            this.shoucang = shoucang;
        }
    }

    private static class MyData{
        private Map<String,String> data;
        private int n;

        private MyData() {
            data=new HashMap<String, String>();
        }

        public void put(String key,String value){
            data.put(key, value);
            ++n;
        }

        public String get(String key){
            return data.get(key);
        }

        public boolean isFull(){
            if(n==7){
                n=0;
                return true;
            }
            else{
                return false;
            }
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result="";
        if(parser.next()==XmlPullParser.TEXT){
            result=parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if(parser.getEventType()!=XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        String name=null;
        int depth=1;
        while(depth!=0){
            switch(parser.next()){
                case XmlPullParser.END_TAG:
                    name=parser.getName();
                    Log.d("xyz","end "+name);
                    if (!(name.equals("meta")||name.equals("link")))
                            depth--;
                    break;
                case XmlPullParser.START_TAG:
                    name=parser.getName();
                    Log.d("xyz","start "+name);
                    if (!(name.equals("meta")||name.equals("link")))
                            depth++;
                    break;
            }
        }
        name="";
    }

    public List<QuestionState> parse(InputStream in) throws IOException, XmlPullParserException {
        return parse(in,null);
    }

    public List<QuestionState> parse(InputStream in,String inputEncoding) throws XmlPullParserException,IOException {
        try{
            XmlPullParser parser= Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(in,inputEncoding);
            parser.nextTag();
            while(parser.next()!=XmlPullParser.END_TAG){
                if (parser.getEventType()!=XmlPullParser.START_TAG)
                      {continue;}
                String name=parser.getName();
                if (name.equals("body")){
                    return readBody(parser);
                }
                else{
                    skip(parser);
                }
            }
            return null;
        }finally {
            in.close();
        }
    }

    private List<QuestionState> readBody(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"body");
        MyData data=new MyData();
        ArrayList<QuestionState>  arrayQuestion=new ArrayList<QuestionState>();

        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("div")){
                readDiv(parser,data);
                if(data.isFull()){
                    arrayQuestion.add(new QuestionState(data.get("time")
                    ,data.get("usrname")
                    ,data.get("question")
                    ,data.get("summary")
                    ,data.get("tongwen")
                    ,data.get("shoucang")
                    ,data.get("liulan")));
                    if(arrayQuestion.size()==10)
                        break;
                }
            }
            else{
                skip(parser);
            }
        }
        return arrayQuestion;
    }

    private void readDiv(XmlPullParser parser,MyData data) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"div");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("div")){
                readDiv(parser,data);
            }
            else if(name.equals("span")){
                readTime(parser,data);
            }
            else if(name.equals("a")){
                readUsrname(parser,data);
            }
            else if(name.equals("em")){
                readEm(parser,data);
            }
            else if(name.equals("dl")){
                readQ_S(parser,data);
            }
            else{
                skip(parser);
            }
        }
    }

    private void readTime(XmlPullParser parser,MyData data)throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,namespace,"span");
        data.put("time",readText(parser));
        parser.require(XmlPullParser.END_TAG,namespace,"span");
    }

    private void readUsrname(XmlPullParser parser,MyData data)throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,namespace,"a");
        data.put("usrname",readText(parser));

        parser.require(XmlPullParser.END_TAG,namespace,"a");
    }

    private void readEm(XmlPullParser parser,MyData data)throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,namespace,"em");
        String emText=readText(parser);
        if(emText.contains("同问")){
            data.put("tongwen",emText);
        }
        else if(emText.contains("收藏")){
            data.put("shoucang",emText);
        }
        else if (emText.contains("浏览")){
            data.put("liulan",emText);
        }
        parser.require(XmlPullParser.END_TAG,namespace,"em");
    }

    private void readQ_S(XmlPullParser parser,MyData data) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"dl");
        while(parser.next()!=XmlPullParser.END_TAG){
            if (parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("dd")){
                 readSummary(parser,data);
            }
            else if(name.equals("dt")){
                readQuestion(parser,data);
            }
        }
    }

    private void readSummary(XmlPullParser parser,MyData data) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"dd");
        data.put("summary",readText(parser));
        parser.require(XmlPullParser.END_TAG,namespace,"dd");
    }

    private void readQuestion(XmlPullParser parser,MyData data) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"dt");

        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            if(parser.getName().equals("a")){
                data.put("question",readText(parser));
            }
        }

        parser.require(XmlPullParser.END_TAG,namespace,"dt");
    }
}
