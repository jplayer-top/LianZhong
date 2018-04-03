package top.jplayer.baseprolibrary.live.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.utils.SharePreUtil;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;


/**
 * 正常的系统前台进程，会在系统通知栏显示一个Notification通知图标
 *
 * @author clock
 * @since 2016-04-12
 */
public class StartService extends Service {


    private Disposable mSubscribe;

    @Override
    public void onCreate() {
        Log.i(TAG, "WhiteService->onCreate");
        mSubscribe = Observable.interval(10, TimeUnit.SECONDS).subscribe(aLong -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int saveDay = (int) SharePreUtil.getData(this, "day", 0);
            if (hour >= 9 && saveDay == day) {
                startService(new Intent(this, WhiteService.class));
                stopSelf();
            }
        });
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "WhiteService->onStartCommand");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(top.jplayer.baseprolibrary.R.drawable.icon_set);
        builder.setContentTitle("时间监听");
        builder.setContentText("正在监听当前时间...");
        builder.setWhen(System.currentTimeMillis());
        Intent activityIntent = new Intent("top.jplayer.lianzhong.main.live");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        startForeground(1001, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "WhiteService->onDestroy");
        super.onDestroy();
        mSubscribe.dispose();
    }

}
