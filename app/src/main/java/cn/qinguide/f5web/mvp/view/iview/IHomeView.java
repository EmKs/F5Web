package cn.qinguide.f5web.mvp.view.iview;

import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.view.iview.base.IBaseView;


public interface IHomeView extends IBaseView {


    void getScriptSuccess(List<ScriptEntity> list, int type);

    void getScriptFail(int status, String desc, int type);

    void hintRefresh();

    void showRefresh();

    void loadMoreEnd();

    void showToast(int stringId);

    void showToast(String msg);

    void startActivity(Class clazz);

}
