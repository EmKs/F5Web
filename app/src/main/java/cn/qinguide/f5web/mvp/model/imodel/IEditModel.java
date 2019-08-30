package cn.qinguide.f5web.mvp.model.imodel;

import cn.qinguide.f5web.mvp.model.imodel.base.IBaseModel;


public interface IEditModel extends IBaseModel {

    void readFile(String fileName, onReadFileListener onReadFileListener);

    interface onReadFileListener{
        void success(String content);
        void error();
    }

    void save(String fileName, String supportPage, String description, String script, onSaveFileListener onSaveFileListener);

    interface onSaveFileListener{
        void success(Integer id);
        void error();
    }

}
