package walker.com.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

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
    @BindView(R.id.button6)
    Button bufferknifeButton6;
    @BindView(R.id.button7)
    Button bufferknifeButton7;
    @BindView(R.id.button8)
    Button bufferknifeButton8;
    @BindView(R.id.button9)
    Button bufferknifeButton9;
    private NotificationManager mNotificationManager;
    private int messagenumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8 ,R.id.button9})
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
                notificationWithProgressIndeterminate();
                break;
            case R.id.button6:
                showFloatNotification();
                break;
            case R.id.button7:
                showLockScreenNotification();
                break;

            case R.id.button8:
                showCustomNotification();
                break;
            case R.id.button9:
                mNotificationManager.cancelAll();
                break;
        }
    }

    /**
     * 简单通知
     */
    private void showNormalNotification() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setAutoCancel(true);

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
    private void showExpandNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Event tracker")
                .setContentText("Events received")
                .setAutoCancel(true);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        String[] events = new String[]{"line1", "line2", "line3", "line4", "line5", "line6"};
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setStyle(inboxStyle);
        Intent resultIntent = new Intent(this, SecondActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(2, mBuilder.build());
    }

    /**
     * 根据id更新notification
     *
     * @param notifyID
     */
    private void updateNotification(int notifyID) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Events received")
                .setAutoCancel(true);
        mBuilder.setSubText("You hava a new message");
        mBuilder.setNumber(++messagenumber);
        mNotificationManager.notify(notifyID, mBuilder.build());
    }

    /**
     * 带进度条的通知栏 determinate
     */
    private void notificationWithProgress() {
        //setProgress 方法是android 4.0 之后的方法，如果在.0之前需要实现进度条的通知栏，需要用到带progressbar的自定义布局
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Notifiction With Progress")
                .setContentText("loading")
                .setSmallIcon(R.mipmap.ic_launcher);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress;

                for (progress = 0; progress <= 100; progress += 5) {
                    mBuilder.setProgress(100, progress, false);
                    mNotificationManager.notify(3, mBuilder.build());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mBuilder.setContentText("Download Complete");
                //完成时删除进度栏必须调用 mBuilder.setProgress(0, 0, false);
                mBuilder.setProgress(0, 0, false);
                mNotificationManager.notify(3, mBuilder.build());
            }
        }).start();
    }

    /**
     * 带进度条的通知栏 Indeterminate
     */
    private void notificationWithProgressIndeterminate() {
        //setProgress 方法是android 4.0 之后的方法，如果在.0之前需要实现进度条的通知栏，需要用到带progressbar的自定义布局
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Notifiction With Progress")
                .setContentText("loading")
                .setSmallIcon(R.mipmap.ic_launcher);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress;

                for (progress = 0; progress <= 100; progress += 5) {
                    mBuilder.setProgress(0, 0, true);
                    mNotificationManager.notify(4, mBuilder.build());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mBuilder.setContentText("Download Complete");
                //完成时删除进度栏必须调用 mBuilder.setProgress(0, 0, false);
                mBuilder.setProgress(0, 0, false);
                mNotificationManager.notify(4, mBuilder.build());
            }
        }).start();
    }

    /**
     * 浮动通知栏，需要Android 5.0
     */
    private void showFloatNotification() {
        //浮动通知栏的先决条件
        //1：5.0
        //2：设置铃声或者震动
        //3：一个较高的优先级
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Float Notification")
                .setContentText("You hava a new message")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)//设置默认铃声
                .setPriority(Notification.PRIORITY_HIGH) //设置高优先级
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, SecondActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(5, mBuilder.build());
    }

    /**
     * 锁屏通知
     */
    private void showLockScreenNotification() {
        /**
         * android 5.0
         * 需要手机设置里允许锁屏通知
         *
         */
        final NotificationCompat.Builder mPublicBuild = new NotificationCompat.Builder(this)
                .setContentTitle("Lock Screen Notification")
                .setContentText("You have locked screen")
                .setSmallIcon(R.mipmap.ic_launcher);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Lock Screen Notification in normal")
                .setContentText("In normal")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setNumber(100)
                .setSubText("subtext")
                .setVisibility(Notification.VISIBILITY_SECRET)
                .setPublicVersion(mPublicBuild.build());

        Intent resultIntent = new Intent(this, SecondActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(6, mBuilder.build());
    }

    /**
     * 自定义布局通知
     */
    private void showCustomNotification() {

        final RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notify_custom);
        final Notification noti = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContent(rv)
                .build();
        mNotificationManager.notify(7, noti);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress;

                for (progress = 0; progress <= 100; progress += 5) {
                    rv.setProgressBar(R.id.pb_notify, 100, progress, false);
                    rv.setTextViewText(R.id.tv_progress,progress+"");
                    mNotificationManager.notify(7, noti);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mNotificationManager.cancel(7);

            }
        }).start();

    }


}
