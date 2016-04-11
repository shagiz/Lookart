package com.shagi.yandex.lookart;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shagi.yandex.lookart.adapter.TabAdapter;
import com.shagi.yandex.lookart.fragment.RecyclerArtistFragment;
import com.shagi.yandex.lookart.fragment.SelectedArtistFragment;
import com.shagi.yandex.lookart.pojo.Artist;
import com.shagi.yandex.lookart.util.CacheHelper;
import com.shagi.yandex.lookart.util.JsonHelper;
import com.shagi.yandex.lookart.util.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author shagi
 */
public class MainActivity extends AppCompatActivity implements RecyclerArtistFragment.OnArtistSelectedListener {

    private FragmentManager fragmentManager;
    /**
     * Вспогательное поле класса настроек приложения
     *
     * @see PreferenceHelper
     */
    private PreferenceHelper preferenceHelper;

    private TabLayout tabLayout;

    private SelectedArtistFragment selectedArtistFragment;

    private ViewPager viewPager;

    private List<Artist> artists;

    private static final String LOG_TAG = "LookArt";
    /**
     * Вспогательное поле класса для работы с файловой системой
     * @see CacheHelper
     */
    private CacheHelper cacheHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = PreferenceHelper.getInstance();
        fragmentManager = getFragmentManager();

        initUniversalImageLoader();

        cacheHelper = CacheHelper.getInstance(this);

        if (hasConnection(this)) {
            artists = loadArtistFromJson();

            if (!preferenceHelper.getBoolean(PreferenceHelper.DO_NOT_ASC_AGAIN)) {
                saveCacheAlertDialog();
            }

            // Сохранять данные при наличии интернета
            if (preferenceHelper.getBoolean(PreferenceHelper.CACHE_DOWNLOAD_ACCEPTED)) {
                downloadCache();
            }
        } else {
            artists = loadArtistFromCache();
        }

        setUI();
    }


    /**
     * Инициализирует объект ImageLoader из библиотеки UniversalImageLoader
     * <a href="https://github.com/nostra13/Android-Universal-Image-Loader">UIL</a>
     * для кэширования изображений
     */
    private void initUniversalImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_loading)
                .showImageForEmptyUri(R.drawable.img_not_found)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);
    }

    /**
     * Проверяем наличие интернет соединения
     */
    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        } else {
            // not connected to the internet
            return false;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));

        MenuItem ask_cache = menu.findItem(R.id.do_not_ask_again);
        ask_cache.setChecked(preferenceHelper.getBoolean(PreferenceHelper.DO_NOT_ASC_AGAIN));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_splash) {
            item.setChecked(!item.isChecked());
            preferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked());
            return true;
        }

        if (id == R.id.do_not_ask_again) {
            item.setChecked(!item.isChecked());
            preferenceHelper.putBoolean(PreferenceHelper.DO_NOT_ASC_AGAIN, item.isChecked());
        }

        if (id == R.id.clean_cache) {
            preferenceHelper.putBoolean(PreferenceHelper.DO_NOT_ASC_AGAIN, false);
            preferenceHelper.putBoolean(PreferenceHelper.CACHE_DOWNLOAD_ACCEPTED, false);
            cacheHelper.clean();
        }

        return super.onOptionsItemSelected(item);
    }


    public void setUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            setSupportActionBar(toolbar);
        }

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.artists_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.selected_artist_tab));

        viewPager = (ViewPager) findViewById(R.id.pager);
        TabAdapter tabAdaptor = new TabAdapter(fragmentManager, 2);

        viewPager.setAdapter(tabAdaptor);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        selectedArtistFragment = (SelectedArtistFragment) tabAdaptor.getItem(TabAdapter.SELECTED_ARTIST_POSITION);
    }


    /**
     * @see RecyclerArtistFragment.OnArtistSelectedListener
     */
    @Override
    public void onArtistSelected(int position, boolean openInfoTab) {
        String name = getArtists().get(position).getName();

        tabLayout.getTabAt(1).setText(name);

        selectedArtistFragment.changeInfo(position);
        if (openInfoTab) {
            viewPager.setCurrentItem(1);
        }
    }


    public List<Artist> getArtists() {
        return artists;
    }

    /**
     * Получает список обектов Artist из JSON по URL
     */
    private List<Artist> loadArtistFromJson() {
        try {
            return new JsonHelper().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * Получает список обектов Artist из файловой системы
     */
    private List<Artist> loadArtistFromCache() {
        return cacheHelper.upload();
    }

    /**
     * Скачивает данные в файловую систему
     */
    private void downloadCache() {
        try {
            cacheHelper.download();
        } catch (ExecutionException | InterruptedException e) {
            Log.e(LOG_TAG, e.getMessage(),e);
        }
    }


    /**
     * Показывает AlertDialog с выбором сохрания кэша
     */
    public void saveCacheAlertDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("Первый запуск");
        ad.setMessage("Сохранять данные при наличии интернета?\n" +
                "Для экономии трафика и места изображения сохранены не будут."); // сообщение
        ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                preferenceHelper.putBoolean(PreferenceHelper.CACHE_DOWNLOAD_ACCEPTED, true);
                preferenceHelper.putBoolean(PreferenceHelper.DO_NOT_ASC_AGAIN, true);
                try {
                    cacheHelper.download();
                } catch (ExecutionException | InterruptedException e) {
                    Log.e(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                preferenceHelper.putBoolean(PreferenceHelper.DO_NOT_ASC_AGAIN, true);
                cacheHelper.clean();
            }
        });
        ad.setCancelable(false);
        AlertDialog alertDialog = ad.create();
        alertDialog.show();
    }
}
