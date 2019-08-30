package cn.qinguide.f5web.mvp.presenter;

import java.util.List;

import cn.qinguide.f5web.mvp.presenter.base.BasePresenter;
import cn.qinguide.f5web.mvp.view.iview.IWhiteListView;
import cn.qinguide.f5web.mvp.model.imodel.IWhiteListModel;
import cn.qinguide.f5web.xposed.entity.AppInfo;

public class WhiteListPresenter extends BasePresenter<IWhiteListView, IWhiteListModel> {

    public WhiteListPresenter(IWhiteListView iView, IWhiteListModel iModel) {
        super(iView, iModel);
    }

    public void getAppInfoList() {
        mIModel.getData(mIView.getAppPackageManager(), false, new IWhiteListModel.onGetDataListener() {
            @Override
            public void success(List<AppInfo> appInfoList) {
                mIView.setAdapter(appInfoList);
            }

            @Override
            public void error(String msg) {
                mIView.showToast(msg);
            }
        });
    }

    public void changePlan(int planId, String packageName) {
        mIModel.savePlans(planId, packageName);
    }

    public void removePlan(String packageName) {
        mIModel.removePlan(packageName);
    }

    public void searchAppInfo(String name) {
        mIModel.search(name, new IWhiteListModel.OnSearchAppInfoListener() {
            @Override
            public void result(List<AppInfo> appInfos) {
                mIView.setAdapter(appInfos);
            }
        });
    }

}
