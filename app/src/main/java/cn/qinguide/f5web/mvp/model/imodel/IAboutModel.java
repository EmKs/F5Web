package cn.qinguide.f5web.mvp.model.imodel;

import android.content.Context;
import android.content.Intent;

import cn.qinguide.f5web.mvp.model.imodel.base.IBaseModel;


public interface IAboutModel extends IBaseModel {

    void aliPay(PayListener payListener);

    void hongbao(PayListener payListener);

    boolean checkInstallAliPay(Context context);

    boolean checkInstallQQ(Context context);

    interface PayListener{
        void onSuccess(Intent t);
        void onFailed(String msg);
    }

}
