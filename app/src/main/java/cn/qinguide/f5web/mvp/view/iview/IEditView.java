package cn.qinguide.f5web.mvp.view.iview;

import cn.qinguide.f5web.mvp.view.iview.base.IBaseView;


public interface IEditView extends IBaseView {

    void finishActivity();

    void initScript(String script);

    void showToast(int stringId);

    void hideDialog();

    void saveFile();

    void checkFileExists();

}
