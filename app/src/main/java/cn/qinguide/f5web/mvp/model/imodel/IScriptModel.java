package cn.qinguide.f5web.mvp.model.imodel;

import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.imodel.base.IBaseModel;


public interface IScriptModel extends IBaseModel {

    void getLocalFile(onFindLocalFileListener onFindLocalFileListener);

    void save(ScriptEntity scriptEntity);

    void delete(String fileName, boolean isEnabled);

    interface onFindLocalFileListener{
        void success(List<ScriptEntity> data);
        void failed(String msg);
        void noData();
    }

}
