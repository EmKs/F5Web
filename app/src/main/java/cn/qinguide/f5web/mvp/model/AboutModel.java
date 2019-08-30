package cn.qinguide.f5web.mvp.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import cn.qinguide.f5web.app.constant.PathConstants;
import cn.qinguide.f5web.mvp.model.imodel.IAboutModel;

public class AboutModel implements IAboutModel {

    @Override
    public void aliPay(PayListener payListener) {
        try {
            String aliPayQR = PathConstants.ALIPAY_API + URLEncoder.encode(PathConstants.DONATE_ID_ALIPAY, "utf-8")
                    + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis();
            Intent intent = Intent.parseUri(aliPayQR, Intent.URI_INTENT_SCHEME);
            payListener.onSuccess(intent);
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            e.printStackTrace();
            payListener.onFailed(e.getMessage());
        }
    }

    @Override
    public void hongbao(PayListener payListener) {
        try {
            String hongbao = PathConstants.ALIPAY_API + URLEncoder.encode(PathConstants.HONG_BAO, "utf-8");
            Intent intent = Intent.parseUri(hongbao, Intent.URI_INTENT_SCHEME);
            payListener.onSuccess(intent);
        } catch (URISyntaxException |UnsupportedEncodingException e) {
            e.printStackTrace();
            payListener.onFailed(e.getMessage());
        }
    }

    @Override
    public boolean checkInstallAliPay(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.eg.android.AlipayGphone", 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean checkInstallQQ(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.tencent.mobileqq", 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}