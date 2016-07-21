package com.shagi.yandex.lookart.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.shagi.yandex.lookart.MainActivity;
import com.shagi.yandex.lookart.pojo.Artist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Обеспечивает работу с файловой системой
 * @author Shagi
 */
public class CacheHelper {

    private Context context;

    private static final String FILENAME = "lookart_cache";

    private static final String LOG_TAG = "LookArt";

    private static CacheHelper instance;

    private CacheHelper(Context context) {
        this.context = context;
    }

    public static CacheHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CacheHelper(context);
        }
        return instance;
    }

    public boolean download() throws ExecutionException, InterruptedException {
        return new CacheWriter().execute().get();
    }

    public List<Artist> upload() {
        return new CacheReader().readFile();
    }

    /**
     * Удаляет файл кэша
     */
    public void clean() {
        File dir = context.getFilesDir();
        File file = new File(dir, FILENAME);
        boolean deleted = file.delete();
       // Log.d(LOG_TAG, "Файл удален");
    }

    /**
     * Сохраняет данные из интернета
     */
    class CacheWriter extends AsyncTask<Void, Void, Boolean> {

        private boolean writeFile() {
            try {
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream((context.openFileOutput(FILENAME, Context.MODE_PRIVATE)));
                objectOutputStream.writeObject(((MainActivity) context).getArtists());
                objectOutputStream.flush();
                objectOutputStream.close();
                //Log.d(LOG_TAG, "Файл записан");
                return true;
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(),e);
            }
            return false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return writeFile();
        }
    }

    /**
     * Загружает данные при отсутсвии интернета
     */
    class CacheReader {
        private List<Artist> readFile() {
            try {
                // открываем поток для чтения
                final ObjectInputStream objectInputStream = new ObjectInputStream((context.openFileInput(FILENAME)));
                List<Artist> artists = (List<Artist>) objectInputStream.readObject();
                objectInputStream.close();
                return artists;
            } catch (IOException | ClassNotFoundException e) {
                Log.e(LOG_TAG, e.getMessage(),e);
            }
            return new ArrayList<>();
        }
    }
}
