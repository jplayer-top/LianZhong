package top.jplayer.lianzhong;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import top.jplayer.baseprolibrary.mvp.model.SampleModel;
import top.jplayer.baseprolibrary.ui.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Administrator on 2018/2/15.
 * 红包雨
 */

public class SecondActivity extends SuperBaseActivity {
    @Override
    public void initSuperData(FrameLayout mFlRootView) {
        mFlRootView.addView(View.inflate(this, R.layout.activity_second, null));
        View view = mFlRootView.findViewById(R.id.btnHBY);
        SampleModel model = new SampleModel();
        String[] strs = {"2017082407616512", "2017091307758112", "2017082107581885"};
        view.setOnClickListener(btn -> {
            io.reactivex.Observable.fromArray(strs).subscribe(s -> model.requestHBY(s).subscribe(gradBean ->
            {
                if (TextUtils.equals("0000", gradBean.errorCode)) {
                    ToastUtils.init().showSuccessToast(this, "抢到了，关闭该界面吧");
                } else if (TextUtils.equals("2005", gradBean.errorCode)) {
                    ToastUtils.init().showSuccessToast(this, "涉嫌作弊");
                } else {
                    ToastUtils.init().showSuccessToast(this, gradBean.errorCode + "");
                }
            }, throwable -> ToastUtils.init().showSuccessToast(this, "当前无活动")));
        });
    }
}
