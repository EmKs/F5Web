package cn.qinguide.f5web.mvp.view.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ljy.devring.util.RingToast;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.qinguide.f5web.di.component.activity.DaggerWhiteListActivityComponent;
import cn.qinguide.f5web.di.module.activity.WhiteListActivityModule;
import cn.qinguide.f5web.mvp.view.activity.base.BaseActivity;

import cn.qinguide.f5web.mvp.view.adapter.WhiteListAdapter;
import cn.qinguide.f5web.mvp.view.iview.IWhiteListView;
import cn.qinguide.f5web.mvp.presenter.WhiteListPresenter;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.xposed.entity.AppInfo;
import cn.qinguide.f5web.xposed.share.Share;
import dagger.Lazy;

public class WhiteListActivity extends BaseActivity<WhiteListPresenter> implements IWhiteListView, CommonTitleBar.OnTitleBarListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, TextWatcher {

    @BindView(R.id.titleBar_white_list)
    CommonTitleBar titleBarWhiteList;
    @BindView(R.id.recycler_view_white_list)
    RecyclerView recyclerViewWhiteList;
    @Inject
    Lazy<MaterialDialog.ListCallbackSingleChoice> listCallbackSingleChoice;

    private String packageName;
    private WhiteListAdapter whiteListAdapter = new WhiteListAdapter(R.layout.item_white_list, new ArrayList<AppInfo>());

    @Override
    protected int getContentLayout() {
        return R.layout.activity_white_list;
    }

    @Override
    protected void initView(Bundle bundle) {
        DaggerWhiteListActivityComponent.builder().whiteListActivityModule(new WhiteListActivityModule(this)).build().inject(this);
        recyclerViewWhiteList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWhiteList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        whiteListAdapter.bindToRecyclerView(recyclerViewWhiteList);
        whiteListAdapter.setEmptyView(R.layout.empty_load);
    }

    @Override
    protected void initData(Bundle bundle) {
        recyclerViewWhiteList.setAdapter(whiteListAdapter);
        mPresenter.getAppInfoList();
    }

    @Override
    protected void initEvent() {
        EditText editText = (EditText) titleBarWhiteList.getCenterSearchView().getChildAt(2);
        editText.addTextChangedListener(this);
        titleBarWhiteList.setListener(this);
        whiteListAdapter.setOnItemChildClickListener(this);
        whiteListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case CommonTitleBar.ACTION_LEFT_BUTTON:
                WhiteListActivity.this.finish();
                break;
        }
    }

    @Override
    public void setAdapter(List<AppInfo> appInfos) {
        whiteListAdapter.setNewData(appInfos);
    }

    @Override
    public void showToast(String msg) {
        RingToast.show(msg);
    }

    @Override
    public void setPlan(int plan, String packageName) {
        mPresenter.changePlan(plan, packageName);
    }

    @Override
    public String getSelectApp() {
        return packageName;
    }

    @Override
    public PackageManager getAppPackageManager() {
        return getPackageManager();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.checkbox_isEnabled:
                packageName = ((AppInfo) adapter.getData().get(position)).getPackageName();
                if (((CheckBox) view).isChecked())
                    getMaterialDialog().show();
                else
                    mPresenter.removePlan(packageName);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (((CheckBox) view.findViewById(R.id.checkbox_isEnabled)).isChecked()) {
            packageName = ((AppInfo) adapter.getData().get(position)).getPackageName();
            getMaterialDialog().show();
        } else RingToast.show(R.string.tips_enabled_please);
    }

    private MaterialDialog getMaterialDialog() {
        return new MaterialDialog
                .Builder(this)
                .cancelable(false)
                .title(getString(R.string.tips_select_plans))
                .itemsCallbackSingleChoice(Share.getInt(packageName, -1), listCallbackSingleChoice.get())
                .items(getResources().getStringArray(R.array.plan))
                .build();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mPresenter.searchAppInfo(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
