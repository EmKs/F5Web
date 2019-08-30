package cn.qinguide.f5web.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import cn.qinguide.f5web.mvp.presenter.base.BasePresenter;
import cn.qinguide.f5web.mvp.view.iview.IAboutView;
import cn.qinguide.f5web.mvp.model.imodel.IAboutModel;

public class AboutPresenter extends BasePresenter<IAboutView, IAboutModel> {

    public AboutPresenter(IAboutView iView, IAboutModel iModel) {
        super(iView, iModel);
    }

    public void aliPayDonation() {
        mIModel.aliPay(new IAboutModel.PayListener() {
            @Override
            public void onSuccess(Intent intent) {
                mIView.openUri(intent);
            }

            @Override
            public void onFailed(String msg) {
                mIView.showToast(msg);
            }
        });
    }

    public void hongbaoDonation() {
        mIModel.hongbao(new IAboutModel.PayListener() {
            @Override
            public void onSuccess(Intent intent) {
                mIView.openUri(intent);
            }

            @Override
            public void onFailed(String msg) {
                mIView.showToast(msg);
            }
        });
    }

    public void addQQGroup(Context context) {
        if (mIModel.checkInstallQQ(context)) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3DHw4bV6Fzl8fWnon6JdsydhNvYKC2aAze"));
            mIView.startActivity(intent);
        } else mIView.copyQQGroup();
    }

    public boolean hasInstalledAlipayClient() {
        return mIModel.checkInstallAliPay(mIView.getContext());
    }

}
