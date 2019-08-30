package cn.qinguide.f5web.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.util.ColorBar;

import javax.inject.Inject;

import cn.qinguide.f5web.di.component.activity.DaggerSplashActivityComponent;
import cn.qinguide.f5web.di.module.activity.SplashActivityModule;
import cn.qinguide.f5web.mvp.view.activity.base.BaseActivity;

import cn.qinguide.f5web.mvp.view.iview.ISplashView;
import cn.qinguide.f5web.mvp.presenter.SplashPresenter;

import cn.qinguide.f5web.R;
import dagger.Lazy;


public class SplashActivity extends BaseActivity<SplashPresenter> implements ISplashView {

    @Inject
    Lazy<MaterialDialog> materialDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }


    @Override
    protected void initView(Bundle bundle) {
        DaggerSplashActivityComponent.builder().splashActivityModule(new SplashActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void initData(Bundle bundle) {
        mPresenter.checkPermission();
    }

    @Override
    protected void initEvent() {
        materialDialog.get().getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                mPresenter.checkPermission();
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                SplashActivity.this.finish();
            }
        }).build();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        SplashActivity.this.finish();
    }

    @Override
    public void permissionDenied(String permissionName) {
        materialDialog
                .get()
                .getBuilder()
                .content(getString(R.string.permission_denied))
                .build()
                .show();
    }

    @Override
    public void permissionRefusal(String permissionName) {
        materialDialog
                .get()
                .getBuilder()
                .content(getString(R.string.permission_refusal))
                .positiveText(null)
                .negativeText(getString(R.string.agree))
                .build().show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            ColorBar.newHideBuilder()
                    .applyNav(true)     // 是否应用到导航栏
                    .build(this)
                    .apply();
        }
    }

}
