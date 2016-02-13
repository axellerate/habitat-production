package com.habitatapi.habit_project.cars;

import android.util.Log;
import android.util.Xml;

import com.habitatapi.habit_project.cars.Car;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeep on 2015-03-27.
 */
public class VehicleIdParser {
    public VehicleIdParser()
    {

    }

    public List<Car> parse(String in) throws XmlPullParserException, URISyntaxException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new InputStreamReader(getUrlData(in)));
        parser.nextTag();
        return readFeed(parser);
    }

    private List<Car> readFeed(XmlPullParser parser)throws XmlPullParserException, URISyntaxException, IOException {
        List<Car> entries = new ArrayList();
        parser.require(XmlPullParser.START_TAG, null, "menuItems");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            Log.i("XML PULL 1", name);
            if (name.equals("menuItem")) {
                entries.add(getMenu(parser));
            } else {
                skip(parser);
            }
        }
        return entries;

    }

    private Car getMenu(XmlPullParser parser) throws XmlPullParserException, IOException, URISyntaxException {
        parser.require(XmlPullParser.START_TAG, null, "menuItem");
        String title = null;
        int id = 0;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            Log.i("XML PULL 2", name);
            if (name.equals("value")) {
                id = getId(parser);
            }
            else if(name.equals("text"))
            {
                title = getName(parser);
            }
            else {
                skip(parser);
            }
        }

        return new Car(id, title);
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String getName(XmlPullParser parser) throws XmlPullParserException, URISyntaxException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "text");
        String title = readText(parser);

        Log.i("XML PULL 3", title);
        parser.require(XmlPullParser.END_TAG, null, "text");
        return title;
    }
    private int getId(XmlPullParser parser) throws XmlPullParserException, URISyntaxException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "value");
        int title = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, null, "value");
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();

            Log.i("XML PULL 4", result);
            parser.nextTag();
        }
        return result;
    }

    private InputStream getUrlData(String url) throws URISyntaxException, ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(new URI(url));
        HttpResponse res = client.execute(method);
        return  res.getEntity().getContent();
    }
}
