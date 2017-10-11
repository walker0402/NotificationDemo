package walker.com.notificationdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.KeyStore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button bufferknifeButton;
    @BindView(R.id.button2)
    Button bufferknifeButton2;
    @BindView(R.id.button3)
    Button bufferknifeButton3;
    @BindView(R.id.button4)
    Button bufferknifeButton4;
    @BindView(R.id.button5)
    Button bufferknifeButton5;
    private NotificationManager mNotificationManager;
    private int messagenumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                showNormalNotification();
                break;
            case R.id.button2:
                showExpandNotification();
                break;
            case R.id.button3:
                updateNotification(1);
                break;
            case R.id.button4:
                notificationWithProgress();
                break;
            case R.id.button5:
                break;
        }
    }

    /**
     * 简单通知
     */
    private void showNormalNotification(){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setAutoCancel(true)
                        ;

        Intent resultIntent = new Intent(this, SecondActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SecondActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }

    /**
     * 扩展视图的通知
     */
    private void showExpandNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Event tracker")
                .setContentText("Events received")
                .setAutoCancel(true)
                ;

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        String[] events = new String[]{"line1","line2","line3","line4","line5","line6"};
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setStyle(inboxStyle);
        Intent resultIntent = new Intent(this, SecondActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(2,mBuilder.build());
    }

    /**
     * 根据id更新notification
     * @param notifyID
     */
    public void updateNotification(int notifyID){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Events received")
                .setAutoCancel(true)
                ;
        mBuilder.setSubText("You hava a new message");
        mBuilder.setNumber(++messagenumber);
        mNotificationManager.notify(notifyID,mBuilder.build());
    }

    public void notificationWithProgress(){
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Notifiction With Progress")
                .setContentText("loading")
                .setSmallIcon(R.mipmap.ic_launcher);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress;

                for (progress = 0 ; progress <= 100 ; progress+=5) {
                    mBuilder.setProgress(100, progress, false);
                    mNotificationManager.notify(3,mBuilder.build());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mBuilder.setContentText("Download Complete");
                mBuilder.setProgress(0, 0, false);
                mNotificationManager.notify(3,mBuilder.build());
            }
        }).start();
    }

}
