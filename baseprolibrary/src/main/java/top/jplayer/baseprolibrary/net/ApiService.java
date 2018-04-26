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
}
