package cn.qinguide.f5web.mvp.presenter;

import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.body.ProgressInfo;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.http.support.observer.DownloadObserver;
import com.ljy.devring.http.support.throwable.HttpThrowable;
import com.ljy.devring.util.RxLifecycleUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.model.entity.HttpResult;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.utils.UrlUtil;
import cn.qinguide.f5web.mvp.presenter.base.BasePresenter;
import cn.qinguide.f5web.mvp.view.iview.IHomeView;
import cn.qinguide.f5web.mvp.model.imodel.IHomeModel;
import cn.qinguide.f5web.xposed.utils.KeyUtil;

public class HomePresenter extends BasePresenter<IHomeView, IHomeModel> {

    public HomePresenter(IHomeView iView, IHomeModel iModel) {
        super(iView, iModel);
    }

    public void getScriptListData(int page, int size, final int type) {
        mIView.showRefresh();
        DevRing.httpManager().commonRequest(mIModel.getScriptData(page, size), new CommonObserver<HttpResult<ScriptEntity>>() {

            @Override
            public void onResult(HttpResult<ScriptEntity> result) {
                mIView.hintRefresh();
                if (mIView != null)
                    mIView.getScriptSuccess(result.getData().getData(), type);
                if (result.getData().getCurrentPage() == result.getData().getTotalPages())
                    mIView.loadMoreEnd();
            }

            @Override
            public void onError(HttpThrowable httpThrowable) {
                mIView.hintRefresh();
                if (mIView != null)
                    mIView.getScriptFail(httpThrowable.errorType, httpThrowable.message, type);
            }
        }, RxLifecycleUtil.bindUntilEvent(mIView, FragmentEvent.DESTROY));
    }

    public void downloadScript(final ScriptEntity scriptEntity) {
        final File file = new File(KeyUtil.SCRIPT_PATH + UrlUtil.getDownloadFileName(scriptEntity.getDownloadUrl()));
        if (file.exists())
            mIView.showToast(R.string.tips_exists);
        else {
            mIView.showToast(R.string.tips_download_start);
            DevRing.httpManager().downloadRequest(file, mIModel.downloadFile(scriptEntity.getDownloadUrl()), new DownloadObserver() {
                @Override
                public void onResult(boolean isSaveSuccess, String filePath) {
                    mIView.showToast(R.string.tips_download_success);
                    scriptEntity.setFileName(file.getName());
                    mIModel.saveScriptData(scriptEntity);
                }

                @Override
                public void onError(long progressInfoId, HttpThrowable httpThrowable) {
                    mIView.showToast(httpThrowable.message);
                }

                @Override
                public void onProgress(ProgressInfo progressInfo) {
                    //DownloadObserver 传入URL即可监听，不传入则无效
                }
            }, RxLifecycleUtil.bindUntilDestroy(mIView));
        }
    }

}
