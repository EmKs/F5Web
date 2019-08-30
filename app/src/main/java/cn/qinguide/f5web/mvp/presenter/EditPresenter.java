package cn.qinguide.f5web.mvp.presenter;


import android.text.Editable;
import android.text.TextUtils;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.presenter.base.BasePresenter;
import cn.qinguide.f5web.mvp.view.iview.IEditView;
import cn.qinguide.f5web.mvp.model.imodel.IEditModel;
import cn.qinguide.f5web.xposed.share.Share;

public class EditPresenter extends BasePresenter<IEditView, IEditModel> {

    public EditPresenter(IEditView iView, IEditModel iModel) {
        super(iView, iModel);
    }

    public ScriptEntity getScript(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            mIView.initScript("");
            mIView.hideDialog();
            return null;
        } else {
            mIModel.readFile(fileName, new IEditModel.onReadFileListener() {
                @Override
                public void success(String content) {
                    mIView.initScript(content);
                    mIView.hideDialog();
                }

                @Override
                public void error() {
                    mIView.showToast(R.string.tips_create_not_exist_file);
                    mIView.initScript("");
                    mIView.hideDialog();
                }
            });
        }
        return (ScriptEntity) Share.getObject(fileName);
    }

    public void saveScript(Editable name, Editable supportPage, Editable description, Editable script) {
        String fileName = name.toString();
        if (!fileName.contains(".js"))
            fileName = fileName + ".js";
        mIModel.save(fileName, supportPage.toString(),  description.toString(), script.toString(), new IEditModel.onSaveFileListener() {
            @Override
            public void success(Integer id) {
                mIView.hideDialog();
                mIView.showToast(id);
                mIView.finishActivity();
            }

            @Override
            public void error() {
                mIView.hideDialog();
                mIView.showToast(R.string.save_failed);
            }
        });
    }

}
