package com.shagi.yandex.lookart;

import android.os.AsyncTask;

import com.shagi.yandex.lookart.pojo.Artist;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Shagi on 06.04.2016.
 */
public class JsonHelper extends AsyncTask<Void,Void,List<Artist>>{
    public static final String url = "http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";


    public static List<Artist> jsonToJavaObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getDataByJavaIo(), new TypeReference<List<Artist>>() {
        });
    }

    public static String getDataByJavaIo() throws Exception {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = new URL(url).openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            return readData(reader);
        } catch (Exception e) {
            throw e;
        } finally {
            inputStream.close();
            reader.close();
        }
    }

    public static String readData(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }


    @Override
    protected List<Artist> doInBackground(Void... params) {
        try {
            return jsonToJavaObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
