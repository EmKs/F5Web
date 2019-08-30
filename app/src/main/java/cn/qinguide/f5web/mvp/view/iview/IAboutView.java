package cn.qinguide.f5web.mvp.view.iview;

import android.content.Context;
import android.content.Intent;

import cn.qinguide.f5web.mvp.view.iview.base.IBaseView;


public interface IAboutView extends IBaseView {

    void openUri(Intent intent);

    void showToast(String msg);

    void copyQQGroup();

    void startActivity(Intent intent);

    Context getContext();

}
