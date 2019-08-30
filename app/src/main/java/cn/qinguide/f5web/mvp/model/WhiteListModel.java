package cn.qinguide.f5web.mvp.model;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.model.imodel.IWhiteListModel;
import cn.qinguide.f5web.xposed.entity.AppInfo;
import cn.qinguide.f5web.xposed.share.Share;
import cn.qinguide.f5web.xposed.utils.KeyUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WhiteListModel implements IWhiteListModel {

    private List<AppInfo> searchAppInfo = new ArrayList<>();
    private List<AppInfo> selectAppInfo = new ArrayList<>();

    @Override
    public void getData(final PackageManager manager, boolean isFilterSystemApp, final onGetDataListener onGetDataListener) {
        Observable.create(new ObservableOnSubscribe<List<AppInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AppInfo>> e) throws Exception {
                List<AppInfo> appInfoList = new ArrayList<>();
                List<PackageInfo> packageInfos = manager.getInstalledPackages(0);
                for (PackageInfo packageInfo : packageInfos) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setPackageName(packageInfo.packageName);
                    appInfo.setIcon(packageInfo.applicationInfo.loadIcon(manager));
                    int plan = Share.getInt(packageInfo.packageName, -1);
                    appInfo.setAppPlan(plan);
                    appInfo.setAppName(packageInfo.applicationInfo.loadLabel(manager).toString());
                    if (plan != -1) selectAppInfo.add(appInfo);
                    else appInfoList.add(appInfo);
                }
                selectAppInfo.addAll(appInfoList);
                e.onNext(selectAppInfo);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        onGetDataListener.success(appInfos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onGetDataListener.error(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void savePlans(int planId, String packageName) {
        Share.putInt(packageName, planId);
    }

    @Override
    public void removePlan(String packageName) {
        Share.remove(packageName);
    }

    @Override
    public void search(final String name, final OnSearchAppInfoListener onSearchAppInfoListener) {
        if (selectAppInfo.size() == 0) return;
        Observable.create(new ObservableOnSubscribe<List<AppInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AppInfo>> e) throws Exception {
                searchAppInfo.clear();
                for (AppInfo appInfo : selectAppInfo) {
                    if (appInfo.getAppName().toLowerCase().contains(name))
                        searchAppInfo.add(appInfo);
                }
                e.onNext(searchAppInfo);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<AppInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<AppInfo> appInfos) {
                onSearchAppInfoListener.result(appInfos);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}