package cn.qinguide.f5web.mvp.model;

import android.Manifest;
import android.os.Build;

import com.ljy.devring.DevRing;
import com.ljy.devring.other.permission.PermissionListener;



import cn.qinguide.f5web.mvp.model.imodel.ISplashModel;
import cn.qinguide.f5web.mvp.view.activity.SplashActivity;
import cn.qinguide.f5web.xposed.utils.CacheUtil;

public class SplashModel implements ISplashModel {

    @Override
    public boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @Override
    public void mkdirsRootPath() {
        new CacheUtil().initShare();
    }


    @Override
    public void isPermission(PermissionListener permissionListener) {
        DevRing.permissionManager().requestEachCombined(DevRing.activityListManager().findActivity(SplashActivity.class), permissionListener ,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

}