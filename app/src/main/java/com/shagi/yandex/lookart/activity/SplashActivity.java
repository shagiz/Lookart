package com.shagi.yandex.lookart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.shagi.yandex.lookart.MainActivity;
import com.shagi.yandex.lookart.util.PreferenceHelper;
import com.shagi.yandex.lookart.R;

/**
 * Отображает SplashScreen
 */
public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        PreferenceHelper.getInstance().init(getApplicationContext());

        if (!PreferenceHelper.getInstance().getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {
            setContentView(R.layout.fragment_splash);

            int SPLASH_DISPLAY_LENGTH = 1000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }else {
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        }
    }
}
