package com.shagi.yandex.lookart.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.shagi.yandex.lookart.R;


/**
 * Created by shagi on 22.07.16.
 */
public class HeadPhoneReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "LookArt";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    cancelNotification(context);
                    break;
                case 1:
                    showNotification(context);
                    break;
                default:
                    Log.d(LOG_TAG, "I have no idea what the headset state is");
            }
        }
    }

    private void showNotification(Context context) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_play_circle_outline_white_36dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("LookArt");


        Intent musicIntent = context.getPackageManager().getLaunchIntentForPackage("ru.yandex.music");

        if (musicIntent != null) {
            PendingIntent pendingIntentMusic = PendingIntent.getActivity(context, 0, musicIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.addAction(R.drawable.ic_library_music_white_24dp, "Яндекс.Музыка", pendingIntentMusic);
        }

        Intent radioIntent = context.getPackageManager().getLaunchIntentForPackage("ru.yandex.radio");

        if (radioIntent != null) {
            PendingIntent pendingIntentRadio = PendingIntent.getActivity(context, 0, radioIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.addAction(R.drawable.ic_library_music_white_24dp,  "Яндекс.Радио", pendingIntentRadio);
        }

        if (radioIntent == null && musicIntent == null){
            Intent playMarketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=ru.yandex.radio"));
            PendingIntent pendingIntentPlayMarket = PendingIntent.getActivity(context, 0, playMarketIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.addAction(R.drawable.ic_library_music_white_24dp, "Скачать Яндекс.Радио", pendingIntentPlayMarket);
        }
        ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(500, notificationBuilder.build());
    }

    private void cancelNotification(Context context) {
        ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE))
                .cancel(500);
    }
}