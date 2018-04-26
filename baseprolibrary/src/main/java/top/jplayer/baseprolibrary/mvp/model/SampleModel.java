package top.jplayer.baseprolibrary.mvp.model;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.mvp.model.bean.GradBean;
import top.jplayer.baseprolibrary.mvp.model.bean.LoginBean;
import top.jplayer.baseprolibrary.mvp.model.bean.MoneyBean;
import top.jplayer.baseprolibrary.mvp.model.bean.SampleBean;
import top.jplayer.baseprolibrary.mvp.model.bean.WZBean;
import top.jplayer.baseprolibrary.net.ApiService;
import top.jplayer.baseprolibrary.net.IoMainSchedule;
import top.jplayer.baseprolibrary.net.JsonRefixInterceptor;
import top.jplayer.baseprolibrary.net.RetrofitManager;

/**
 * Created by Obl on 2018/1/13.
 * top.jplayer.baseprolibrary.mvp.model
 */

public class SampleModel {
    public Observable<SampleBean> requestHBList(String no, String token) {
        String time = String.valueOf(new Date().getTime());
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getSampleBean(String.format(Locale.CHINA, "{\"information\":\"bd_web_api\",\"command\":\"redhallwill\",\"userno\":\"%s\"," +
                                "\"accessToken\":\"%s\",\"token\":\"Ar3H8JuWQAULEgJhTr3tfjWCa-CNNQkKGVUroCy5JpKJ\"," +
                                "\"platform\":\"html\",\"version\":\"5.2.40\",\"productName\":\"lzcp\"}", no, token), time,
                        String.format(Locale.CHINA, "Zepto%s", time))
                .compose(new IoMainSchedule<>());
    }

    public Observable<SampleBean> requestHasHBList(String no, String token) {
        String time = String.valueOf(new Date().getTime());
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getSampleHasBean(String.format(Locale.CHINA, "{\"information\":\"bd_web_api\",\"command\":\"redhalling\",\"userno\":\"%s\"," +
                                "\"accessToken\":\"%s\",\"token\":\"Ar3H8JuWQAULEgJhTr3tfjWCa-CNNQkKGVUroCy5JpKJ\"," +
                                "\"imei\":\"864341034978208\",\"platform\":\"html\",\"version\":\"5.2.40\"," +
                                "\"productName\":\"lzcp\"}", no, token), time,
                        String.format(Locale.CHINA, "Zepto%s", time))
                .compose(new IoMainSchedule<>());
    }

    public Observable<GradBean> requestHBY(String userNo) {
        String time = String.valueOf(new Date().getTime());
        String parameter = String.format(Locale.CHINA, "{\"command\":\"submitJoinGames\",\"userNo\":\"%s\",\"money\":\"157\",\"channel\":\"1030020\",\"productName\":\"yccp\"}", userNo);
        return RetrofitManager.init().reset("https://m.lechuangmingcai.com/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getHBYBean(parameter, time, String.format(Locale.CHINA, "jQuery21407126036777626723_%s", time))
                .compose(new IoMainSchedule<>());
    }

    public Flowable<GradBean> requestGrad(String id, String userNo, String accessToken) {
        String time = String.valueOf(new Date().getTime());
        String parameter = String.format(Locale.CHINA, "{\"information\":\"bd_web_api\",\"command\":\"grab\",\"userno\":\"%s\",\"id\":\"%s\",\"accessToken\":\"%s\",\"token\":\"Ar3H8JuWQAULEgJhTr3tfjWCa-CNNQkKGVUroCy5JpKJ\",\"platform\":\"html\",\"version\":\"5.2.36\",\"productName\":\"lzcp\"}", userNo, id, accessToken);
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getGradBean(parameter, time, String.format(Locale.CHINA, "Zepto%s", time))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GradBean> requestWZ(String str, String userNo) {
        long l = new Date().getTime();
        long l1 = l / 10000 * 10000 + 10000;
        String time = String.valueOf(l);
        String parameter = String.format(Locale.CHINA, "{\"command\":\"drawhongbao\",\"token\":\"%s\",\"userno\":\"%s\",\"activityId\":\"1518172027989\",\"productName\":\"lzcp\",\"channel\":\"780\",\"timeStamp\":%s}", str, userNo, l1 + "");
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getWZBean(parameter, time, String.format(Locale.CHINA, "jQuery111003890031433469199_%s", time))
                .compose(new IoMainSchedule<>());
    }

    public Observable<WZBean> requestStr() {
        long l = new Date().getTime();
        long l1 = l / 10000 * 10000 + 10000;
        String time = String.valueOf(l);
        String parameter = String.format(Locale.CHINA, "{\"command\":\"queryTheme\",\"activityId\":\"1518172027989\",\"productName\":\"lzcp\",\"channel\":\"780\",\"timeStamp\":%s}", l1 + "");
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getStrBean(parameter, time, String.format(Locale.CHINA, "jQuery111003212321644024907_%s", time))
                .compose(new IoMainSchedule<>());
    }

    public Flowable<GradBean> requestGet(String id, String userNo, String accessToken) {
        String time = String.valueOf(new Date().getTime());
        String parameter = String.format(Locale.CHINA,
                "{\"information\":\"bd_web_api\",\"command\":\"reddetail\",\"userno\":\"%s\",\"id\":\"%s\",\"accessToken\":\"%s\",\"token\":\"Ar3H8JuWQAULEgJhTr3tfjWCa-CNNQkKGVUroCy5JpKJ\",\"start\":0,\"size\":50,\"platform\":\"html\",\"version\":\"5.2.30\",\"productName\":\"lzcp\"}", userNo, id, accessToken);
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getGradBean(parameter, time, String.format(Locale.CHINA, "Zepto%s", time))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginBean> login(String phone, String password) {
        String time = String.valueOf(new Date().getTime());

        int i = new Random().nextInt(900) + 100;
        String imei = String.valueOf(i);
        String parameter = String.format(Locale.CHINA,
                "{\"command\":\"login\",\"requestType\":\"login\",\"userName\":\"%s\",\"password\":\"%s\",\"productName\":\"lzcp\",\"channel\":\"1020025\",\"platform\":\"android\",\"imei\":\"869271010208%s\",\"version\":\"5.2.30\"}", phone, password, imei);
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getLoginBean(parameter, time, String.format(Locale.CHINA, "jQuery111107794920853339136_%s", time))
                .compose(new IoMainSchedule<>());
    }


    public Observable<GradBean> requestSign(String no, String token) {
        String time = String.valueOf(new Date().getTime());
        String parameter = String.format(Locale.CHINA,
                "{\"command\":\"gold\",\"requestType\":\"signToGold\",\"userNo\":\"%s\"," +
                        "\"accessToken\":\"%s\"}", no, token);
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getSignBean(parameter, time, String.format(Locale.CHINA, "jQuery111102673366607632488_%s", time))
                .compose(new IoMainSchedule<>());
    }

    public Observable<MoneyBean> requestTotalMoney(String no) {
        String time = String.valueOf(new Date().getTime());
        String parameter = String.format(Locale.CHINA,
                "{\"information\":\"bd_web_api\",\"command\":\"myred\",\"userno\":\"%s\",\"start\":0," +
                        "\"size\":50,\"token\":\"dasda-asda\",\"platform\":\"html\"," +
                        "\"version\":\"5.2.40\",\"productName\":\"lzcp\"}", no);
        return RetrofitManager.init().reset("https://m.leader001.cn/", new JsonRefixInterceptor())
                .reCreate(ApiService.class)
                .getMoneyBean(parameter, time, String.format(Locale.CHINA, "Zepto%s", time))
                .compose(new IoMainSchedule<>());
    }
}

