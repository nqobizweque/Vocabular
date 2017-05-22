package nmmu.wrap302.Task01;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


/**
 * Created by s214108503 on 2017/05/21.
 */
public class Notification_reciever extends BroadcastReceiver{


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, Browser.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.appicon)
                .setContentTitle("{TERM}")
                .setContentText("{Description}")
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}
