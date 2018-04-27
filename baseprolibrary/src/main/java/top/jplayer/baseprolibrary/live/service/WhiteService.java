package top.jplayer.baseprolibrary.live.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.mvp.model.SampleModel;
import top.jplayer.baseprolibrary.mvp.model.bean.SampleBean;
import top.jplayer.baseprolibrary.net.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SharePreUtil;


/**
 * 正常的系统前台进程，会在系统通知栏显示一个Notification通知图标
 *
 * @author clock
 * @since 2016-04-12
 */
public class WhiteService extends Service {
    private String mUserNos;
    private String mAccessToken;
    private final static String TAG = WhiteService.class.getSimpleName();

    private final static int FOREGROUND_ID = 1000;
    private String[] mNosList;
    private String[] mTokenList;
    private SampleModel mModel;
    private Disposable mDisposable1;
    private Disposable mDisposable2;

    @Override
    public void onCreate() {
        Log.i(TAG, "WhiteService->onCreate");
        mModel = new SampleModel();
        mUserNos = (String) SharePreUtil.getData(this, "userNo", "");
        mAccessToken = (String) SharePreUtil.getData(this, "accessToken", "");
        mNosList = mUserNos.split(",");
        mTokenList = mAccessToken.split(",");
        mDisposable1 = Flowable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    if ((hour >= 10 && hour <= 12) || hour >= 19 && hour <= 23) {
                        long l = aLong + 1;
                        btn_no_common(3, String.format(Locale.CHINA,
                                "已经努力的抢了%s次了", String.valueOf(l)));
                        mModel.requestHBList(mNosList[0], mTokenList[0]).compose(new IoMainSchedule<>())
                                .map(sampleBean -> {
                                    if (TextUtils.equals("0000", sampleBean.errorCode)) {
                                        if (sampleBean.data != null && sampleBean.data.list != null) {
                                            return sampleBean;
                                        } else return null;
                                    }
                                    return null;
                                })
                                .subscribe(sampleBean -> {
                                    if (sampleBean.data.isTwo.hadNum >= 2) {
                                        //已经两次了
                                        btn_no_common(1, "抢红包次数达到最大，明天继续");
                                    } else {
                                        if (sampleBean.data.list.size() > 0) {
                                            autoGrad(sampleBean.data);
                                        }
                                    }
                                }, throwable -> {

                                });
                    }

                });

        mDisposable2 = Flowable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    if ((hour >= 10 && hour <= 12) || hour >= 19 && hour <= 23) {
                        mModel.requestHasHBList(mNosList[0], mTokenList[0]).compose(new IoMainSchedule<>())
                                .map(sampleBean -> {
                                    if (TextUtils.equals("0000", sampleBean.errorCode)) {
                                        if (sampleBean.data != null && sampleBean.data.list != null) {
                                            return sampleBean;
                                        } else return null;
                                    }
                                    return null;
                                })
                                .subscribe(sampleBean -> {
                                    List<SampleBean.DataBean.ListBean> beans = sampleBean.data.list;
                                    if (beans.size() > 0) {
                                        if (beans.get(0).status != 4) {
                                            autoGrad(sampleBean.data);
                                        }
                                    }
                                }, throwable -> {

                                });

                    }
                });
        super.onCreate();
        compositeDisposable.add(mDisposable1);
        compositeDisposable.add(mDisposable2);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "WhiteService->onStartCommand");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon_set);
        builder.setContentTitle("抢红包进行中");
        builder.setContentText("正在拼命抢红包...");
        builder.setWhen(System.currentTimeMillis());
        Intent activityIntent = new Intent("top.jplayer.lianzhong.main.live");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        startForeground(FOREGROUND_ID, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void btn_no_common(int id, String msg) {
        Notification.Builder builder = new Notification.Builder(this);
        Notification notification = builder.setSmallIcon(R.drawable.ic_launcher_round)//设置小图标
                .setTicker(msg)//设置文字
                .setContentTitle("提示信息")
                .setContentText(msg)
                .setWhen(System.currentTimeMillis())//通知的时间
                .setAutoCancel(true)//点击后消失
                .build();//创建通知对象完成
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, notification);//显示通知

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "WhiteService->onDestroy");
        super.onDestroy();
        compositeDisposable.dispose();
        startService(new Intent(this, StartService.class));
        stopSelf();
    }

    /**
     * 自动抢
     *
     * @param data
     */
    private void autoGrad(SampleBean.DataBean data) {
        String sendTime = data.list.get(0).sendTime;
        long stamp = DateUtils.dateToStamp(sendTime);
        long time = new Date().getTime();
        long delay = (stamp - time) - 7000;
        String id = data.list.get(0).id;
        Disposable disposable = Flowable.timer(delay, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> getUserNo(id));
        compositeDisposable.add(disposable);
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void getUserNo(String id) {
        String userNos = (String) SharePreUtil.getData(this, "userNo", "");
        String accessToken = (String) SharePreUtil.getData(this, "accessToken", "");
        LogUtil.e(userNos);
        LogUtil.e(accessToken);
        if (!TextUtils.equals("", userNos)) {
            assert userNos != null;
            assert accessToken != null;
            List<String> userNosList = Arrays.asList(userNos.split(","));
            List<String> accessTokensList = Arrays.asList(accessToken.split(","));
            for (int i = 0; i < userNosList.size(); i++) {
                String userNo = userNosList.get(i);
                String token = accessTokensList.get(i);
                requestGrad(id, userNo, token);
            }
        }
    }

    private void requestGrad(String id, String userNo, String token) {
        mModel.requestGrad(id, userNo, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gradBean -> {
                    if (TextUtils.equals("0000", gradBean.errorCode)) {
                        requestGet(id, userNo, token);
                    } else if (!TextUtils.equals("0009", gradBean.errorCode)) {
                        requestGrad(id, userNo, token);
                    } else if (!TextUtils.equals("0001", gradBean.errorCode)) {
                        btn_no_common(4, "服务器错误");
                    }
                }, throwable -> {
                    startService(new Intent(this, StartService.class));
                    stopSelf();
                });
    }

    public void requestGet(String id, String userNo, String accessToken) {
        mModel.requestGet(id, userNo, accessToken).subscribe(gradBean -> {
            if (TextUtils.equals("0000", gradBean.errorCode)) {
                btn_no_common(2, "红包已入账，恭喜恭喜");
            } else if (!TextUtils.equals("0009", gradBean.errorCode)) {
                requestGet(id, userNo, accessToken);
            } else if (!TextUtils.equals("0001", gradBean.errorCode)) {
                btn_no_common(4, "服务器错误");
            }
        }, throwable -> requestGet(id, userNo, accessToken));
    }

}
