package cn.qinguide.f5web.mvp.model;

import com.ljy.devring.DevRing;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.http.DownloadApiService;
import cn.qinguide.f5web.mvp.model.http.ScriptApiService;
import cn.qinguide.f5web.mvp.model.imodel.IHomeModel;
import cn.qinguide.f5web.xposed.share.Share;
import io.reactivex.Observable;

public class HomeModel implements IHomeModel {


    @Override
    public Observable getScriptData(int page, int size) {
        return DevRing.httpManager().getService(ScriptApiService.class).getScriptList(page, size);
    }

    @Override
    public Observable downloadFile(String url) {
        return DevRing.httpManager().getService(DownloadApiService.class).downloadFile(url);
    }

    //储存实体
    @Override
    public void saveScriptData(ScriptEntity scriptEntity) {
        Share.putObject(scriptEntity.getFileName(), scriptEntity);  //写入参数
    }

}