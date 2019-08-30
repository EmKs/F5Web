package cn.qinguide.f5web.mvp.presenter;

import com.badoo.mobile.util.WeakHandler;
import com.ljy.devring.other.permission.PermissionListener;

import cn.qinguide.f5web.mvp.presenter.base.BasePresenter;
import cn.qinguide.f5web.mvp.view.iview.ISplashView;
import cn.qinguide.f5web.mvp.model.imodel.ISplashModel;

public class SplashPresenter extends BasePresenter<ISplashView, ISplashModel> {

    private WeakHandler weakHandler = new WeakHandler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mIModel.mkdirsRootPath();
            mIView.startMainActivity();
        }
    };

    public SplashPresenter(ISplashView iView, ISplashModel iModel) {
        super(iView, iModel);
    }

    public void checkPermission(){
        if (mIModel.isMarshmallow()){
            mIModel.isPermission(new PermissionListener() {
                @Override
                public void onGranted(String permissionName) {
                    weakHandler.postDelayed(runnable, 2000);
                }

                @Override
                public void onDenied(String permissionName) {
                    mIView.permissionDenied(permissionName);
                }

                @Override
                public void onDeniedWithNeverAsk(String permissionName) {
                    mIView.permissionRefusal(permissionName);
                }
            });
        }else {
            weakHandler.postDelayed(runnable, 2000);
        }
    }

}
