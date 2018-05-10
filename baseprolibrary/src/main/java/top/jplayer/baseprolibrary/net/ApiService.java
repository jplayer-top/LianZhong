package top.jplayer.baseprolibrary.net;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import top.jplayer.baseprolibrary.mvp.model.bean.GradBean;
import top.jplayer.baseprolibrary.mvp.model.bean.LoginBean;
import top.jplayer.baseprolibrary.mvp.model.bean.MoneyBean;
import top.jplayer.baseprolibrary.mvp.model.bean.SampleBean;
import top.jplayer.baseprolibrary.mvp.model.bean.WZBean;

/**
 * Created by Obl on 2018/1/17.
 * top.jplayer.baseprolibrary.net
 */

public interface ApiService {
    @GET("support/bdapi/sharporder/redhallwill?")
    Observable<SampleBean> getSampleBean(@Query("parameter") String parameter,
                                         @Query("_") String cur_,
                                         @Query("callback") String callback);

    @GET("support/bdapi/sharporder/redhalling?")
    Observable<SampleBean> getSampleHasBean(@Query("parameter") String parameter,
                                            @Query("_") String cur_,
                                            @Query("callback") String callback);

    @POST("app/user/login?")
    Observable<LoginBean> getLoginBean(@Query("phone") String phone,
                                       @Query("password") String password);

    @GET("support/bdapi/sharporder/grab?")
    Flowable<GradBean> getGradBean(@Query("parameter") String parameter,
                                   @Query("_") String cur_,
                                   @Query("callback") String callback);

    @GET("qmch/generalRequest?")
    Observable<LoginBean> getLoginBean(@Query("parameter") String parameter,
                                       @Query("_") String cur_,
                                       @Query("callBackMethod") String callback);

    @GET("support/bdapi/activity/redrain/games/submitJoinGames?")
    Observable<GradBean> getHBYBean(@Query("parameter") String parameter, @Query("_") String time, @Query("callback") String format);

    @GET("support/bdapi/activity/hongbao/drawhongbao?")
    Observable<GradBean> getWZBean(@Query("parameter") String parameter, @Query("_") String time, @Query("callback") String format);

    @GET("support/bdapi/activity/hongbao/queryTheme?")
    Observable<WZBean> getStrBean(@Query("parameter") String parameter, @Query("_") String time, @Query("callback") String format);

    @GET("qmch/generalRequest?")
    Observable<GradBean> getSignBean(@Query("parameter") String parameter, @Query("_") String time, @Query("callBackMethod") String format);

    @GET("support/bdapi/sharporder/myred?")
    Observable<MoneyBean> getMoneyBean(@Query("parameter") String parameter, @Query("_") String time, @Query("callback") String format);

    @GET("support/bdapi/sharporder/redhalling?parameter={%22information%22:%22bd_web_api%22,%22command%22:%22redhalling%22,%22userno%22:%222017082107579931%22,%22accessToken%22:%22f99b978a99b3c6e144ada6b2c9a27934%22,%22token%22:%22%22,%22imei%22:%22DC974AEA-50C8-4423-B1FE-4B0EB0C62D1E%22,%22platform%22:%22html%22,%22version%22:%224.4.25%22,%22productName%22:%22ltcp22%22}&_=1525939181745&callback=Zepto1525939171239")
    Observable<MoneyBean> getTestBean();
}
