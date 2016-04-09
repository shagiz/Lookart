package com.shagi.yandex.lookart.util;

import android.os.AsyncTask;
import android.util.Log;

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
 * Реализует парсинг ссылки на json объект и возвращает список pojo элементов
 * @author shagi
 */
public class JsonHelper extends AsyncTask<Void,Void,List<Artist>>{
    public static final String url = "http://cache-default02f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    /**
     * Конвертирует JSON в JAVA POJO с помощью библиотеки Jackson
     * @return Список pojo объектов json
     * @throws IOException
     */
    public static List<Artist> jsonToJavaObject() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getDataByJavaIo(), new TypeReference<List<Artist>>() {
        });
    }

    /**
     * По URL получает данные в формате String
     * @return Data from url.
     * @throws IOException
     */
    public static String getDataByJavaIo() throws IOException {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = new URL(url).openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            return readData(reader);
        } catch (IOException e) {
            Log.e("JsonHelper", e.getMessage());
            return null;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
