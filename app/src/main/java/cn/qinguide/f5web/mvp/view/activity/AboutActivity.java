package cn.qinguide.f5web.mvp.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.util.RingToast;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

import butterknife.BindView;
import cn.qinguide.f5web.app.constant.PathConstants;
import cn.qinguide.f5web.di.component.activity.DaggerAboutActivityComponent;
import cn.qinguide.f5web.di.module.activity.AboutActivityModule;
import cn.qinguide.f5web.mvp.view.activity.base.BaseActivity;

import cn.qinguide.f5web.mvp.view.iview.IAboutView;
import cn.qinguide.f5web.mvp.presenter.AboutPresenter;

import cn.qinguide.f5web.R;
import dagger.Lazy;


public class AboutActivity extends BaseActivity<AboutPresenter> implements IAboutView, CommonTitleBar.OnTitleBarListener, View.OnClickListener {

    @BindView(R.id.titleBar_about)
    CommonTitleBar titleBarAbout;
    @BindView(R.id.card_wechat_donation)
    CardView cardWeChatDonation;
    @BindView(R.id.card_ali_donation)
    CardView cardAliDonation;
    @BindView(R.id.card_hongbao_donation)
    CardView cardHongbaoDonation;
    @BindView(R.id.card_qq_group)
    CardView cardQQGroup;

    @Inject
    Lazy<MaterialDialog> dialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView(Bundle bundle) {
        DaggerAboutActivityComponent.builder().aboutActivityModule(new AboutActivityModule(this, this)).build().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int[] attribute = new int[]{android.R.attr.selectableItemBackground};
            TypedArray typedArray = getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
            cardWeChatDonation.setForeground(typedArray.getDrawable(0));
            cardHongbaoDonation.setForeground(typedArray.getDrawable(0));
            cardAliDonation.setForeground(typedArray.getDrawable(0));
            cardQQGroup.setForeground(typedArray.getDrawable(0));
        }
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initEvent() {
        titleBarAbout.setListener(this);
        cardAliDonation.setOnClickListener(this);
        cardHongbaoDonation.setOnClickListener(this);
        cardWeChatDonation.setOnClickListener(this);
        cardQQGroup.setOnClickListener(this);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case CommonTitleBar.ACTION_LEFT_BUTTON:
                AboutActivity.this.finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_qq_group:
                mPresenter.addQQGroup(this);
                break;
            case R.id.card_ali_donation:
                if (mPresenter.hasInstalledAlipayClient())
                    mPresenter.aliPayDonation();
                else RingToast.show(R.string.install_alipay);
                break;
            case R.id.card_hongbao_donation:
                if (mPresenter.hasInstalledAlipayClient())
                    mPresenter.hongbaoDonation();
                else RingToast.show(R.string.install_alipay);
                break;
            case R.id.card_wechat_donation:
                dialog.get().show();
                break;
        }
    }

    @Override
    public void openUri(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showToast(String msg) {
        RingToast.show(msg);
    }

    @Override
    public void copyQQGroup() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("GroupNumber", "165332276"));
        RingToast.show(R.string.no_install_qq);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
