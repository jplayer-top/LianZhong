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
        String[] strs = {"2017122900092302", "2017122900092309", "2017101000011400"};
        view.setOnClickListener(btn -> {
            io.reactivex.Observable.fromArray(strs).subscribe(s -> model.requestHBY(s).subscribe(gradBean ->
            {
                if (TextUtils.equals("0000", gradBean.errorCode)) {
                    ToastUtils.init().showSuccessToast(this, "抢到了，关闭该界面吧");
                } else if (TextUtils.equals("1002", gradBean.errorCode)) {
                    ToastUtils.init().showSuccessToast(this, "活动未开始");
                }
            }, throwable -> ToastUtils.init().showSuccessToast(this, "当前无活动")));
        });
    }
}
