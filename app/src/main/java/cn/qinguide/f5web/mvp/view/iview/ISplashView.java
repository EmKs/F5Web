package cn.qinguide.f5web.mvp.view.iview;

import cn.qinguide.f5web.mvp.view.iview.base.IBaseView;


public interface ISplashView extends IBaseView {

    void startMainActivity();

    void permissionDenied(String permissionName);

    void permissionRefusal(String permissionName);

}
