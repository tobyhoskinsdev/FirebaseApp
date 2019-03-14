package examples.aaronhoskins.com.firebaseapp;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN_RECEIVED", "onNewToken: New Token = " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        displayPushNotification(remoteMessage);
    }

    public void displayPushNotification(RemoteMessage remoteMessage){
        //Base intent to start the activity
        Intent intent = new Intent(this, MainActivity.class);
        //Pending intent so the intent stays around pending an action
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT);
        //Get the user selected ring tone for notifications
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //build the notification
        NotificationCompat.Builder notifactionBuilder = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_disabled)
                .setSound(soundUri);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        notifactionBuilder.setChannelId("0");
        //update user notifications
        notificationManager.notify(0, notifactionBuilder.build());

    }
}
