package com.lxs.notificationreply;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String ID = "id";
    private static final String NOTIFICATION_GROUP = "GroupId";
    private static final String KEY_TEXT_REPLY = "text_reply";
    //定义顶部summary Notification的固定ID，以便后期进行更新消息数字等操作
    private static final int NOTIFICATION_GROUP_SUMMARY_ID = -1;
    private int id = 1;
    private TextView tv;
    NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        tv = (TextView) findViewById(R.id.main_txt);

        findViewById(R.id.main_btn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
//                RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY).setLabel("回复点什么呢？").build();
//                Intent intent = new Intent();
//                intent.putExtra(ID, NOTIFICATION_GROUP_SUMMARY_ID);
//                intent.setClass(MainActivity.this, MainActivity.class);
//                PendingIntent replyPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//                Notification.Action action = new Notification.Action
//                        .Builder(R.mipmap.ic_launcher, "点击此处回复内容！", replyPendingIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();
//                final Notification group = new Notification.Builder(MainActivity.this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("分组标题")
//                        .setGroup(GROUP_ID)
//                        .setAutoCancel(true)
//                        .setStyle(new Notification.InboxStyle())
//                        .setGroupSummary(true)//如果想要折叠消息，必须设置为true，否则同组消息不会折叠
//                        .setContentText("分组内容")
//                        .addAction(action)
//                        .build();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                notificationManager.notify(NOTIFICATION_GROUP_SUMMARY_ID, group);

                RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel("回复此消息")
                        .build();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(ID, NOTIFICATION_GROUP_SUMMARY_ID);
                intent.putExtras(bundle);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action action = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send, "回复此消息", pendingIntent)
                        .addRemoteInput(remoteInput)
                        .setAllowGeneratedReplies(true)
                        .build();
                final StatusBarNotification[] activeNotifications = mNotificationManager.getActiveNotifications();   //获取活动的Notification组，该API只有在6.0之后才可使用
                int numberOfNotifications = activeNotifications.length;
                String notificationContent = "There are " + numberOfNotifications + "ActivieNotifications";
                final NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("分组标题")
                        .setContentText("分组内容")
                        .setColor(android.graphics.Color.RED)
                        .addAction(action)
                        .setStyle(new NotificationCompat.BigTextStyle().setSummaryText(notificationContent))
                        .setGroup(NOTIFICATION_GROUP)  //设置关联组
                        .setGroupSummary(true);       //如果想要折叠消息，必须设置为true，否则同组消息不会折叠
                final Notification notification = builder.build();
                mNotificationManager.notify(NOTIFICATION_GROUP_SUMMARY_ID, notification);       //NOTIFICATION_GROUP_SUMMARY_ID为顶部summary Notification的固定ID
            }
        });

        findViewById(R.id.main_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //远程的输入控件构造一个
//                RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY).setLabel("回复点什么呢？").build();
//                Intent intent = new Intent();
//                intent.putExtra(ID, id);
//                intent.setClass(MainActivity.this, MainActivity.class);
//                PendingIntent replyPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//                Notification.Action action = new Notification.Action
//                        .Builder(R.mipmap.ic_launcher, "点击此处回复内容！", replyPendingIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();
//
//                Notification newMessageNotification = new Notification.Builder(MainActivity.this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("通知栏具体消息Title")
//                        .setGroup(GROUP_ID)
//                        .setAutoCancel(true)
//                        .setStyle(new Notification.InboxStyle())
//                        .setContentText("通知栏具体消息")
//                        .addAction(action)
//                        .build();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                notificationManager.notify(id++, newMessageNotification);

                RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel("回复此消息")
                        .build();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(ID, id);
                intent.putExtras(bundle);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action action = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send, "回复此消息", pendingIntent)
                        .addRemoteInput(remoteInput)
                        .setAllowGeneratedReplies(true)
                        .build();
                //构造底部Notification消息的内容
                final NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .addAction(action)
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.msg) + id)
                        .setAutoCancel(true)
                        .setGroup(NOTIFICATION_GROUP);   //必须与上面的summary Notification的NOTIFICATION_GROUP保持一致，且组内的Notification也要保持一致
                final Notification notification = builder.build();
                mNotificationManager.notify(id++, notification);  //getNewNotificationId()返回一个int类型的数字，作为组内每个Notification的唯一ID
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        //接收回复的内容
        if (intent != null) {
            Bundle idBundle = intent.getExtras();
            int notificationId = 0;
            if (idBundle != null) {
                notificationId = idBundle.getInt(ID);
            }
            Bundle bundle = RemoteInput.getResultsFromIntent(intent);
            if (bundle != null) {
                String content = (String) bundle.getCharSequence(KEY_TEXT_REPLY);
                if (notificationId > 0) {
                    tv.setText("消息ID为" + notificationId + "的记录回复的内容：" + content);
                } else {
                    tv.setText("未知记录回复的内容：" + content);
                }
                mNotificationManager.cancel(notificationId);
            }
        }
    }
}
