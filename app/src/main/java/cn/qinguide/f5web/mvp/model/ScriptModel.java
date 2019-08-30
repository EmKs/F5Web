package cn.qinguide.f5web.mvp.model;

import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.imodel.IScriptModel;
import cn.qinguide.f5web.xposed.share.Share;

import cn.qinguide.f5web.xposed.utils.KeyUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScriptModel implements IScriptModel {

    @Override   //Try包裹防止权限被关闭后报错
    public void getLocalFile(final onFindLocalFileListener onFindLocalFileListener) {
        Observable.create(new ObservableOnSubscribe<List<ScriptEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ScriptEntity>> e) throws Exception {
                if (!FileUtil.isSDCardAvailable())
                    onFindLocalFileListener.failed("储存无法使用");
                else {
                    File file = new File(KeyUtil.SCRIPT_PATH);
                    File[] files = file.listFiles();
                    if (files.length <= 0)
                        onFindLocalFileListener.noData();
                    else {
                        List<ScriptEntity> data = new ArrayList<>();
                        for (File scriptFile : files) {
                            String type = scriptFile.getName().substring(scriptFile.getName().lastIndexOf(".") + 1);
                            if (!type.equalsIgnoreCase("js"))
                                continue;
                            ScriptEntity scriptEntity = (ScriptEntity) Share.getObject(scriptFile.getName());
                            if (scriptEntity == null) {
                                scriptEntity = new ScriptEntity();
                                scriptEntity.setDescription("暂无简介");
                                scriptEntity.setName(scriptFile.getName());
                                scriptEntity.setFileName(scriptFile.getName());
                                scriptEntity.setAuthor("未知");
                                scriptEntity.setSupportPage("*");
                                scriptEntity.setEnable(false);
                            }
                            data.add(scriptEntity);
                        }
                        e.onNext(data);
                        e.onComplete();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ScriptEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ScriptEntity> scriptEntities) {
                        onFindLocalFileListener.success(scriptEntities);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFindLocalFileListener.failed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void save(ScriptEntity scriptEntity) {
        chang(scriptEntity.getFileName(), scriptEntity.isEnable());
        Share.putObject(scriptEntity.getFileName(), scriptEntity);
    }

    @Override
    public void delete(String fileName, boolean isEnabled) {
        File file = new File(KeyUtil.SCRIPT_PATH + fileName);
        if (file.exists())
            RingLog.i("删除文件" + file.delete());
        File cacheFile = new File(KeyUtil.ROOT_PATH + fileName + ".0");
        if (cacheFile.exists())
            RingLog.i("删除缓存" + cacheFile.delete());
        chang(fileName, false);
    }

    private void chang(String fileName, boolean isEnabled) {
        String[] settings = (String[]) Share.getObject(KeyUtil.SCRIPT_LIST);
        if (settings == null)
            settings = new String[]{};
        List<String> list = new ArrayList<>(Arrays.asList(settings));
        if (isEnabled) {
            if (!list.contains(fileName))
                list.add(fileName);
        } else {
            if (list.contains(fileName))
                list.remove(fileName);
        }
        Share.putObject(KeyUtil.SCRIPT_LIST, list.toArray(new String[list.size()]));
    }

}