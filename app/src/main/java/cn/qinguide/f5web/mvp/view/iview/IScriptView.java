package cn.qinguide.f5web.mvp.view.iview;

import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.view.iview.base.IBaseView;


public interface IScriptView extends IBaseView {

    void setAdapter(List<ScriptEntity> data);

    void showToast(String msg);

    void noData();

    void hintRefresh();

    void showRefresh();

    void refresh();
}
