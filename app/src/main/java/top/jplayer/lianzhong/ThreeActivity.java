package top.jplayer.lianzhong;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.mvp.model.SampleModel;
import top.jplayer.baseprolibrary.ui.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Administrator on 2018/2/15.
 * 红包雨
 */

public class ThreeActivity extends SuperBaseActivity {
    @Override
    public void initSuperData(FrameLayout mFlRootView) {
        mFlRootView.addView(View.inflate(this, R.layout.activity_three, null));
        View view = mFlRootView.findViewById(R.id.btnHBY);
        SampleModel model = new SampleModel();
        String[] strs = {"2017082407616512", "2017091307758112", "2017082107581885"};
        view.setOnClickListener(btn -> model.requestStr().filter(wzBean -> {
            if ("0000".equals(wzBean.errorCode)) {

                return true;
            }
            ToastUtils.init().showQuickToast(mBaseActivity, "当前无活动");
            return false;
        }).subscribe(strBean ->
                Observable.fromArray(strs).subscribe(s -> model.requestWZ(strBean.data.token, s).subscribe(gradBean ->
                {
                    if (TextUtils.equals("0000", gradBean.errorCode)) {
                        ToastUtils.init().showSuccessToast(this, "抢到了，关闭该界面吧");
                    }
                })), throwable -> {
        }, () -> {
        }));
    }
}
