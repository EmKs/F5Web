package cn.qinguide.f5web.xposed.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private int appPlan = -1;

    private String packageName;

    private String appName;

    private Drawable icon;

    private int icons;

    public int getIcons() {
        return icons;
    }

    public void setIcons(int icons) {
        this.icons = icons;
    }

    public int getAppPlan() {
        return appPlan;
    }

    public void setAppPlan(int appPlan) {
        this.appPlan = appPlan;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
