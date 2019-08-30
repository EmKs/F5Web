package cn.qinguide.f5web.xposed.utils;

import android.os.Environment;

public class KeyUtil {

    /**
     * 前缀
     */
    public static final String JAVA_SCRIPT = "javascript:";
    public static final String HTTP = "http";

    /**
     * 储存参数
     */
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/F5Web/";
    public static final String SCRIPT_PATH = ROOT_PATH + "script/";
    public static final String SCRIPT_LIST = "scriptList";

    /**
     * Xposed 参数
     */
    //APP 包名参数
    public static final String APP_PACKAGE = "cn.qinguide.f5web";
    public static final String APP_ACTIVE_UTIL_CLASS = "cn.qinguide.f5web.xposed.utils.ActiveUtil";
    public static final String APP_IS_MODULE_ACTIVE_METHOD = "isModuleActive";
    //系统WebView参数
    public static final String SYSTEM_WEBVIEW_PACKAGE = "com.google.android.webview";
    public static final String SYSTEM_WEBVIEW_CLASS = "android.webkit.WebView";
    public static final String SYSTEM_WEBVIEW_SET_CLIENT_METHOD = "setWebViewClient";
    //Chromium内核参数
    public static final String CHROMIUM_WEB_CONTENTS_OBSERVER_PROXY_CLASS = "org.chromium.content.browser.webcontents.WebContentsObserverProxy";
    public static final String CHROMIUM_NAVIGATION_CONTROLLER_CLASS = "org.chromium.content.browser.framehost.NavigationControllerImpl";
    public static final String CHROMIUM_NATIVE_LOAD_URL_METHOD = "nativeLoadUrl";
    public static final String CHROMIUM_CREATE_METHOD = "create";
    public static final String CHROMIUM_DID_START_LOADING_METHOD = "didStartLoading";
    public static final String CHROMIUM_DID_FINISH_LOAD_METHOD = "didFinishLoad";
    //TencentX5内核参数
    public static final String TENCENT_X5_WEBVIEW_CLASS = "com.tencent.smtt.sdk.WebView";
    public static final String TENCENT_X5_WEBVIEW_CLIENT_CLASS = "com.tencent.smtt.sdk.WebViewClient";
    public static final String TENCENT_X5_WEBVIEW_CHROME_CLIENT_CLASS = "com.tencent.smtt.sdk.WebChromeClient";
    public static final String TENCENT_X5_ON_PAGE_STARTED_METHOD = "onPageStarted";
    public static final String TENCENT_X5_ON_PAGE_FINISHED_METHOD = "onPageFinished";
    public static final String TENCENT_X5_ON_PROGRESS_CHANGED_METHOD =  "onProgressChanged";
    public static final String TENCENT_X5_LOAD_URL_METHOD = "loadUrl";
    //魅族系统WebView
    public static final String MEIZU_WEBVIEW_PACKAGE = "com.android.webview";

}
