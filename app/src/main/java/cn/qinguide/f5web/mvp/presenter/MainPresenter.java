package cn.qinguide.f5web.mvp.presenter;

import cn.qinguide.f5web.mvp.presenter.base.BasePresenter;
import cn.qinguide.f5web.mvp.view.iview.IMainView;
import cn.qinguide.f5web.mvp.model.imodel.IMainModel;

public class MainPresenter extends BasePresenter<IMainView, IMainModel> implements IMainModel.onExitAppListener {

    public MainPresenter(IMainView iView, IMainModel iModel) {
        super(iView, iModel);
    }

    public void pressedBack() {
        mIModel.onPressedBack(this);
    }

    @Override
    public void exit() {
        mIView.exitApp();
    }

    @Override
    public void showToast() {
        mIView.showExitToast();
    }


}
