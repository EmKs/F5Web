package cn.qinguide.f5web.mvp.model.imodel;

import cn.qinguide.f5web.mvp.model.imodel.base.IBaseModel;

public interface IMainModel extends IBaseModel {

    void onPressedBack(onExitAppListener onExitAppListener);

    interface onExitAppListener{
        void exit();
        void showToast();
    }

}
