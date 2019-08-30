package cn.qinguide.f5web.mvp.model;

import cn.qinguide.f5web.mvp.model.imodel.IMainModel;

public class MainModel implements IMainModel {

    private long pressedTime = 0;

    @Override
    public void onPressedBack(onExitAppListener onExitAppListener) {
        if (System.currentTimeMillis() - pressedTime > 2000){
            pressedTime = System.currentTimeMillis();
            onExitAppListener.showToast();
        }else onExitAppListener.exit();
    }

}