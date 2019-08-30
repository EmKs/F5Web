package cn.qinguide.f5web.mvp.model.imodel;

import android.content.pm.PackageManager;

import java.util.List;

import cn.qinguide.f5web.mvp.model.imodel.base.IBaseModel;
import cn.qinguide.f5web.xposed.entity.AppInfo;


public interface IWhiteListModel extends IBaseModel {

    void getData(PackageManager manager, boolean isFilterSystemApp, onGetDataListener onGetDataListener);

    void savePlans(int planId, String packageName);

    void removePlan(String packageName);

    void search(String name, OnSearchAppInfoListener onSearchAppInfoListener);

    interface OnSearchAppInfoListener {

        void result(List<AppInfo> appInfos);

    }

    interface onGetDataListener {

        void success(List<AppInfo> appInfoList);

        void error(String msg);

    }

}
