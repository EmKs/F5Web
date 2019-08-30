package cn.qinguide.f5web.mvp.model.imodel;

import com.ljy.devring.other.permission.PermissionListener;

import cn.qinguide.f5web.mvp.model.imodel.base.IBaseModel;


public interface ISplashModel extends IBaseModel {

    boolean isMarshmallow();

    void mkdirsRootPath();

    void isPermission(PermissionListener permissionListener);

}
