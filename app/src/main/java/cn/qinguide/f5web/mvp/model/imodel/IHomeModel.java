package cn.qinguide.f5web.mvp.model.imodel;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.imodel.base.IBaseModel;
import io.reactivex.Observable;


public interface IHomeModel extends IBaseModel {

    Observable getScriptData(int page, int size);

    Observable downloadFile(String url);

    void saveScriptData(ScriptEntity scriptEntity);

}
