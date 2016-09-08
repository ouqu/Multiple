package com.example.parsexmltest;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hahaha on 9/1/16.
 */
public class ExampleParser {
    private static final String namespace=null;

    public static class Entry{
        public final String title;  //the data you need
        public final String link;
        public final String summary;

        public Entry(String link, String title, String summary) {
            this.link = link;
            this.title = title;
            this.summary = summary;
        }
    }

    public List parse(InputStream in) throws XmlPullParserException,IOException{
        try{
            XmlPullParser parser= Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(in,null);
            parser.nextTag();
            return readFeed(parser);
        }finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser)throws XmlPullParserException,IOException{

        List entries=new ArrayList();

        parser.require(XmlPullParser.START_TAG,namespace,"feed");//check if the state is right
        while (parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("entry")){
                entries.add(readEntry(parser));
            }
            else{
                skip(parser);
            }
        }
        return entries;
    }

    private Entry readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"entry");
        String title=null;  //the data you need
        String link=null;
        String summary=null;
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("title")){
                title=readTitle(parser);
            }
            else if(name.equals("summary")){
                summary=readSummary(parser);
            }
            else if(name.equals("link")){
                link=readLink(parser);
            }
            else{
                skip(parser);
            }
        }
        return new Entry(link,title,summary);
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"title");
        String title=readText(parser);
        parser.require(XmlPullParser.END_TAG,namespace,"title");
        return title;
    }

    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,namespace,"summary");
        String summary=readText(parser);
        parser.require(XmlPullParser.END_TAG,namespace,"summary");
        return summary;
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Sample:
        //<link rel="alternate" href="http://............" />
        String link="";

        parser.require(XmlPullParser.START_TAG,namespace,"link");
        String tag=parser.getName();
        String relType=parser.getAttributeValue(null,"rel");  //get a param
        if(tag.equals("link")){
            if(relType.equals("alternate")){
                link=parser.getAttributeValue(null,"hreg");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG,namespace,"link");
        return link;
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
        int depth=1;
        while(depth!=0){
            switch(parser.next()){
                case XmlPullParser.END_TAG:
                    depth--;   break;
                case XmlPullParser.START_TAG:
                    depth++;   break;
            }
        }
    }

}
