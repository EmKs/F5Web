package cn.qinguide.f5web.mvp.view.iview;

import android.content.pm.PackageManager;

import java.util.List;

import cn.qinguide.f5web.mvp.view.iview.base.IBaseView;
import cn.qinguide.f5web.xposed.entity.AppInfo;


public interface IWhiteListView extends IBaseView {

    void setAdapter(List<AppInfo> appInfos);

    void showToast(String msg);

    void setPlan(int plan, String packageName);

    String getSelectApp();

    PackageManager getAppPackageManager();

}
