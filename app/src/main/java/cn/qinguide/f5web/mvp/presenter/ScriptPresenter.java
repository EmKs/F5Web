package cn.qinguide.f5web.mvp.presenter;

import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.presenter.base.BasePresenter;
import cn.qinguide.f5web.mvp.view.iview.IScriptView;
import cn.qinguide.f5web.mvp.model.imodel.IScriptModel;

public class ScriptPresenter extends BasePresenter<IScriptView, IScriptModel> {

    public ScriptPresenter(IScriptView iView, IScriptModel iModel) {
        super(iView, iModel);
    }


    public void initData() {
        mIView.showRefresh();
        mIModel.getLocalFile(new IScriptModel.onFindLocalFileListener() {
            @Override
            public void success(List<ScriptEntity> data) {
                mIView.setAdapter(data);
                mIView.hintRefresh();
            }

            @Override
            public void failed(String msg) {
                mIView.showToast(msg);
                mIView.hintRefresh();
            }

            @Override
            public void noData() {
                mIView.noData();
                mIView.hintRefresh();
            }
        });
    }

    public void saveSettings(ScriptEntity scriptEntity) {
        mIModel.save(scriptEntity);
    }

    public void deleteFile(String fileName, boolean isEnabled){
        mIModel.delete(fileName, isEnabled);
        mIView.refresh();
    }

}
