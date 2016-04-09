package com.shagi.yandex.lookart;

import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shagi.yandex.lookart.adapter.TabAdapter;
import com.shagi.yandex.lookart.fragment.RecyclerArtistFragment;
import com.shagi.yandex.lookart.fragment.SelectedArtistFragment;
import com.shagi.yandex.lookart.pojo.Artist;
import com.shagi.yandex.lookart.util.JsonHelper;
import com.shagi.yandex.lookart.util.PreferenceHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = PreferenceHelper.getInstance();
        fragmentManager = getFragmentManager();

        initUniversalImageLoader();

        //TODO : Откуда загружаем данные
        if (hasConnection(this)){
            artists=loadArtistFromJson();
        }else {
            artists=loadArtistFromCache();
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
    protected List<Artist> loadArtistFromJson() {
        try {
            return new JsonHelper().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Получает список обектов Artist из файловой системы
     */
    protected List<Artist> loadArtistFromCache() {
        //TODO
        return null;
    }
}
