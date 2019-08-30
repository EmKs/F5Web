package cn.qinguide.f5web.xposed;

import android.graphics.Bitmap;
import android.webkit.WebViewClient;

import cn.qinguide.f5web.xposed.custom.HookChromium;
import cn.qinguide.f5web.xposed.custom.HookWebViewClient;
import cn.qinguide.f5web.xposed.custom.HookX5WebViewClient;
import cn.qinguide.f5web.xposed.script.InjectScript;
import cn.qinguide.f5web.xposed.share.Share;
import cn.qinguide.f5web.xposed.utils.CacheUtil;
import cn.qinguide.f5web.xposed.utils.KeyUtil;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * Author ：叫我阿喵
 * WeChat ：Merry_X_
 * Email：MHyun@live.com
 */
public class Main implements IXposedHookLoadPackage {

    private HookChromium hookChromium;
    private HookWebViewClient hookWebViewClient;
    private HookX5WebViewClient hookX5WebViewClient;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        //如果是本模块，则Hook显示激活
        if (loadPackageParam.packageName.equals(KeyUtil.APP_PACKAGE)) {
            ActiveXposedCheck(loadPackageParam);
        }
        new CacheUtil().initShare();    //初始化存储类
        switch (loadPackageParam.packageName) {
            case KeyUtil.SYSTEM_WEBVIEW_PACKAGE:
                hookSystemWebView(loadPackageParam);
                break;
            case KeyUtil.MEIZU_WEBVIEW_PACKAGE:
                hookSystemWebView(loadPackageParam);
                break;
            default:
                Integer plan = Share.getInt(loadPackageParam.packageName, -1);
                switch (plan) {
                    case 1:
                        hookChromiumKernel(loadPackageParam);
                        break;
                    case 2:
                        hookTencentX5Kernel(loadPackageParam);
                        break;
                }
                break;
        }
    }

    /**
     * 检查Xposed模块是否激活
     */
    private void ActiveXposedCheck(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod(KeyUtil.APP_ACTIVE_UTIL_CLASS, loadPackageParam.classLoader, KeyUtil.APP_IS_MODULE_ACTIVE_METHOD, XC_MethodReplacement.returnConstant(true));
    }

    /**
     * Hook系统WebView
     */
    private void hookSystemWebView(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod(KeyUtil.SYSTEM_WEBVIEW_CLASS, loadPackageParam.classLoader, KeyUtil.SYSTEM_WEBVIEW_SET_CLIENT_METHOD, WebViewClient.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (Share.getInt(loadPackageParam.processName, -1) != 0) return;
                if (hookWebViewClient == null)
                    hookWebViewClient = new HookWebViewClient((WebViewClient) param.args[0], new InjectScript());
                //如果没有开启脚本，则不替换WebViewClient
                param.args[0] = hookWebViewClient;
            }
        });
    }

    /**
     * Hook Chromium Core
     */
    private void hookChromiumKernel(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (hookChromium == null)
            hookChromium = new HookChromium(loadPackageParam.classLoader, new InjectScript());
        if (!hookChromium.isEnabledScript()) return;

        XposedHelpers.findAndHookMethod(KeyUtil.CHROMIUM_NAVIGATION_CONTROLLER_CLASS, loadPackageParam.classLoader, KeyUtil.CHROMIUM_CREATE_METHOD, long.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                hookChromium.initController(param.getResult(), (Long) param.args[0]);
            }
        });

        XposedHelpers.findAndHookMethod(KeyUtil.CHROMIUM_NAVIGATION_CONTROLLER_CLASS, loadPackageParam.classLoader, "destroy", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                hookChromium.destroy(param.thisObject.toString());
            }
        });

        XposedHelpers.findAndHookMethod(KeyUtil.CHROMIUM_WEB_CONTENTS_OBSERVER_PROXY_CLASS, loadPackageParam.classLoader, KeyUtil.CHROMIUM_DID_START_LOADING_METHOD, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                hookChromium.setController(param.thisObject.toString());
            }
        });

        XposedHelpers.findAndHookMethod(KeyUtil.CHROMIUM_WEB_CONTENTS_OBSERVER_PROXY_CLASS, loadPackageParam.classLoader, KeyUtil.CHROMIUM_DID_FINISH_LOAD_METHOD, long.class, String.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                hookChromium.setLoadUrl(param.args[1].toString());
                hookChromium.injectScript(param.thisObject.toString());
            }
        });
    }

    /**
     * Hook腾讯X5内核
     */
    private void hookTencentX5Kernel(final XC_LoadPackage.LoadPackageParam loadPackageParam) {

        Class<?> webView = XposedHelpers.findClass(KeyUtil.TENCENT_X5_WEBVIEW_CLASS, loadPackageParam.classLoader);
        XposedHelpers.findAndHookMethod(KeyUtil.TENCENT_X5_WEBVIEW_CLIENT_CLASS, loadPackageParam.classLoader, KeyUtil.TENCENT_X5_ON_PAGE_STARTED_METHOD, webView, String.class, Bitmap.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (hookX5WebViewClient == null)
                    hookX5WebViewClient = new HookX5WebViewClient();
                hookX5WebViewClient.setUrl((String) param.args[1]);
                XposedBridge.log("加载开始");
            }
        });

        XposedHelpers.findAndHookMethod(KeyUtil.TENCENT_X5_WEBVIEW_CLIENT_CLASS, loadPackageParam.classLoader, KeyUtil.TENCENT_X5_ON_PAGE_FINISHED_METHOD, webView, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("加载结束");
            }
        });

        XposedHelpers.findAndHookMethod(KeyUtil.TENCENT_X5_WEBVIEW_CHROME_CLIENT_CLASS, loadPackageParam.classLoader, KeyUtil.TENCENT_X5_ON_PROGRESS_CHANGED_METHOD, webView, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if ((Integer) param.args[1] != 100) return;
                hookX5WebViewClient.injectScript(param.args[0]);
                XposedBridge.log("进度条加载" + param.args[0]);
            }
        });

    }

}
