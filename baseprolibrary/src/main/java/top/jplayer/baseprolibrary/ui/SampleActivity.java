package top.jplayer.baseprolibrary.ui;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.mvp.contract.SampleContract;
import top.jplayer.baseprolibrary.mvp.model.bean.SampleBean;
import top.jplayer.baseprolibrary.mvp.presenter.SamplePresenter;
import top.jplayer.baseprolibrary.net.IoMainSchedule;
import top.jplayer.baseprolibrary.ui.adapter.SampleAdapter;
import top.jplayer.baseprolibrary.utils.DateUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.MoneyUtils;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.widgets.MultipleStatusView;

/**
 * Created by Administrator on 2018/1/27.
 * 样例
 */

public class SampleActivity extends SuperBaseActivity implements SampleContract.ISampleView {

    private SamplePresenter presenter;
    private SampleAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private MultipleStatusView multipleStatusView;
    private EditText etPhone;
    private EditText etPassword;
    private TextView tvTime;
    private TextView tvNum;
    private LinearLayout llNames;
    private CompositeDisposable disposable;
    private String mNames;
    private String mUserNos;
    private String mAccessToken;
    private List<String> mUserNosList;

    @Override
    public void initSuperData(FrameLayout mFlRootView) {
        mFlRootView.addView(View.inflate(this, R.layout.activity_sample, null));
        presenter = new SamplePresenter(this);
        refreshLayout = mFlRootView.findViewById(R.id.smartRefreshLayout);
        tvTime = mFlRootView.findViewById(R.id.tvTime);
        tvNum = mFlRootView.findViewById(R.id.tvNum);
        multipleStatusView = mFlRootView.findViewById(R.id.multiplestatusview);
        etPhone = mFlRootView.findViewById(R.id.etPhone);
        etPassword = mFlRootView.findViewById(R.id.etPassword);
        Button btnAdd = mFlRootView.findViewById(R.id.btnAdd);
        Button btnStart = mFlRootView.findViewById(R.id.btnStart);
        Button btnOne = mFlRootView.findViewById(R.id.btnOne);
        Button btnTotal = mFlRootView.findViewById(R.id.btnTotal);
        showLoading();
        getNames();
        btnStart.setOnClickListener(v -> {
            requestSign();
        });
        presenter.requestHBList(mUserNos.split(",")[0], mAccessToken.split(",")[0]);
        presenter.requestHasHBList(mUserNos.split(",")[0], mAccessToken.split(",")[0]);
        RecyclerView recyclerView = mFlRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ArrayList<SampleBean.DataBean.ListBean> sampleBeans = new ArrayList<>();
        adapter = new SampleAdapter(sampleBeans);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener((thisAdapter, view, position) ->
        {
            List<SampleBean.DataBean.ListBean> datas = thisAdapter.getData();
            SampleBean.DataBean.ListBean listBean = datas.get(position);
            getUserNo(listBean.id);
            return false;
        });
        refreshLayout.setOnRefreshListener(refresh -> presenter.requestHBList(mUserNos.split(",")[0], mAccessToken.split(",")[0]));
        btnAdd.setOnClickListener(view ->
                presenter.addAccount(etPhone.getText().toString().trim(),
                        etPassword.getText().toString().trim()));
        btnOne.setOnClickListener(view -> {
            List<String> list = new ArrayList<>();
            list.add("17667936541");
            list.add("18366108542");
            list.add("17085329627");
            for (String s : list) {
                presenter.addAccount(s,
                        "lj011200");

            }
        });
        llNames = mFlRootView.findViewById(R.id.llShowName);
        disposable = new CompositeDisposable();
        Disposable subscribe = Observable.interval(5, 5, TimeUnit.SECONDS)
                .compose(new IoMainSchedule<>())
                .subscribe(aLong -> presenter.requestHBList(mUserNos.split(",")[0], mAccessToken.split(",")[0]));
        Disposable subscribe1 = Observable.interval(5, 5, TimeUnit.SECONDS)
                .compose(new IoMainSchedule<>())
                .subscribe(aLong -> presenter.requestHasHBList(mUserNos.split(",")[0], mAccessToken.split(",")[0]));
        disposable.add(subscribe);
        disposable.add(subscribe1);
        btnTotal.setOnClickListener(v -> {
            requestTotalMoney();
            getMoney(btnTotal);
        });
        getMoney(btnTotal);
    }

    private void getMoney(Button btnTotal) {
        if (mUserNos != null) {
            mUserNosList = Arrays.asList(mUserNos.split(","));
            Observable.timer(10, TimeUnit.SECONDS)
                    .compose(new IoMainSchedule<>()).subscribe(aLong -> {
                if (mUserNosList != null) {
                    int total = 0;
                    for (String no : mUserNosList) {
                        int money = (int) SharePreUtil.getData(this, no, 0);
                        total += money;
                    }
                    String formatIntF = MoneyUtils.formatIntF(total);
                    btnTotal.setText(String.format(Locale.CHINA, "共赚%s", formatIntF));
                }
            });
        }
    }

    private void getNames() {
        mNames = (String) SharePreUtil.getData(this, "name", "");
        mUserNos = (String) SharePreUtil.getData(this, "userNo", "");
        mAccessToken = (String) SharePreUtil.getData(this, "accessToken", "");
        LogUtil.e(mNames);
        LogUtil.e(mUserNos);
        LogUtil.e(mAccessToken);
        if (!TextUtils.equals("", mNames)) {
            assert mNames != null;
            Observable.fromIterable(Arrays.asList(mNames.split(",")))
                    .compose(new IoMainSchedule<>())
                    .subscribe(s -> {
                        TextView textView = new TextView(this);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        textView.setGravity(Gravity.CENTER);
                        textView.setText(String.format(Locale.CHINA, "%s-已登录", s));
                        textView.setTextColor(getResources().getColor(R.color.grey));
                        llNames.addView(textView);
                    });
        }
    }

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
                presenter.requestGrad(id, userNosList.get(i), accessTokensList.get(i));
            }
        }
    }

    private void requestTotalMoney() {
        String userNos = (String) SharePreUtil.getData(this, "userNo", "");
        if (!TextUtils.equals("", userNos)) {
            assert userNos != null;
            mUserNosList = Arrays.asList(userNos.split(","));
            for (int i = 0; i < mUserNosList.size(); i++) {
                presenter.requestTotalMoney(mUserNosList.get(i));
            }
        }
    }

    private void requestSign() {
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
                presenter.requestSign(userNosList.get(i), accessTokensList.get(i));
            }
        }
    }

    public void setHBOne(SampleBean sampleBean) {
        SampleBean.DataBean.ListBean bean = sampleBean.data.list.get(0);
        if (bean.status != 4) {
            tvNum.setText(bean.num + "");
            tvTime.setText(bean.sendTime);
            autoGrad(sampleBean.data);
        }
    }

    @Override
    public void setHBList(SampleBean sampleBean) {
        multipleStatusView.showContent();
        SampleBean.DataBean data = sampleBean.data;
        refreshLayout.finishRefresh();
        adapter.setNewData(data.list);
        autoGrad(data);
    }

    /**
     * 自动抢
     *
     * @param data
     */
    private void autoGrad(SampleBean.DataBean data) {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        String sendTime = data.list.get(0).sendTime;
        long stamp = DateUtils.dateToStamp(sendTime);
        long time = new Date().getTime();
        long delay = (stamp - time) - 7000;
        String id = data.list.get(0).id;
        Disposable disposable = Observable.timer(delay, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> getUserNo(id));
        compositeDisposable.add(disposable);
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.clear();
        }
    }

    @Override
    public void showError() {

        if (multipleStatusView != null) {
            multipleStatusView.showError();
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void showLoading() {
        if (multipleStatusView != null) {
            multipleStatusView.showLoading();
        }
    }

    @Override
    public void showEmpty() {

        if (multipleStatusView != null) {
            multipleStatusView.showEmpty();
        }
        refreshLayout.finishRefresh();

    }


    public void loginSuccess() {
        llNames.removeAllViews();
        getNames();
    }


}
