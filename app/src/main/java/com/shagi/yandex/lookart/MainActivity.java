package com.shagi.yandex.lookart;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.shagi.yandex.lookart.adaptor.TabAdaptor;
import com.shagi.yandex.lookart.fragment.ArtistFragment;
import com.shagi.yandex.lookart.fragment.RecyclerArtistFragment;
import com.shagi.yandex.lookart.fragment.SelectedArtistFragment;

public class MainActivity extends AppCompatActivity implements RecyclerArtistFragment.OnArtistSelectedListener{

    FragmentManager fragmentManager;
    PreferenceHelper preferenceHelper;
    TabAdaptor tabAdaptor;
    TabLayout tabLayout;

    ArtistFragment artistFragment;
    RecyclerArtistFragment recyclerArtistFragment;
    SelectedArtistFragment selectedArtistFragment;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceHelper = PreferenceHelper.getInstance();
        fragmentManager = getFragmentManager();

        setUI();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        tabAdaptor = new TabAdaptor(fragmentManager, 2);

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

        selectedArtistFragment = (SelectedArtistFragment) tabAdaptor.getItem(TabAdaptor.SELECTED_ARTIST_POSITION);
    }

    @Override
    public void onArtistSelected(int position, boolean openInfoTab) {
        tabLayout.getTabAt(1).setText(selectedArtistFragment.getArtists().get(position).getName());
        selectedArtistFragment.changeInfo(position);
        if (openInfoTab) {
            viewPager.setCurrentItem(1);
        }
    }
}
