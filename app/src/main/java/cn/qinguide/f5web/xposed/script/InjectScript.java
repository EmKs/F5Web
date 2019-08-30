package cn.qinguide.f5web.xposed.script;

import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.xposed.share.Share;
import cn.qinguide.f5web.xposed.utils.KeyUtil;

import de.robv.android.xposed.XposedHelpers;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/12
 */
public class InjectScript {

    private List<ScriptEntity> scripts;
    private HashMap<String, String> cacheScript = new HashMap<>();
    private ValueCallback<String> callback = new ValueCallback<String>() {
        @Override
        public void onReceiveValue(String value) {

        }
    };

    /**
     * 获取启用脚本
     *
     * @return 启用脚本列表
     */
    public List<ScriptEntity> getEnabledScript() {
        if (scripts != null)
            return scripts;
        String[] settings = (String[]) Share.getObject(KeyUtil.SCRIPT_LIST);
        if (settings == null)
            settings = new String[]{};
        List<String> list = new ArrayList<>(Arrays.asList(settings));
        if (list.size() <= 0)
            return null;
        scripts = new ArrayList<>();
        for (String name : list) {
            ScriptEntity scriptEntity = (ScriptEntity) Share.getObject(name);
            scripts.add(scriptEntity);
        }
        return scripts;
    }

    /**
     * 读取脚本
     *
     * @param fileName 脚本文件名
     * @return 脚本数据
     */
    private String readScript(String fileName) {

        String script = cacheScript.get(fileName);
        if (!TextUtils.isEmpty(script)) {
            return script;
        }

        File file = new File(KeyUtil.SCRIPT_PATH + fileName);
        if (!file.exists())
            return null;
        else {
            try {
                FileReader reader = new FileReader(file);
                BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
                StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
                String s = "";
                while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                    sb.append(s).append("\n");//将读取的字符串添加换行符后累加存放在缓存中
                }
                bReader.close();
                script = sb.toString();
                cacheScript.put(fileName, script);
                return script;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 调用系统WebView的异步注入方法注入JavaScript
     *
     * @param webView        系统WebView
     * @param url            网页地址
     * @param scriptEntities 脚本列表
     */
    public void loadScript(WebView webView, String url, List<ScriptEntity> scriptEntities) {
        if (scriptEntities.size() <= 0)
            return;
        for (ScriptEntity scriptEntity : scriptEntities) {
            if (scriptEntity.getSupportPage().contains("*")) {
                String js = readScript(scriptEntity.getFileName());
                if (!TextUtils.isEmpty(js))
                    webView.evaluateJavascript(js, callback);
            } else {
                String supportPage = scriptEntity.getSupportPage();
                String[] pages = supportPage.split(",");
                if (pages.length > 0) {
                    for (String page : pages) {
                        if (url.contains(page)) {
                            String js = readScript(scriptEntity.getFileName());
                            if (!TextUtils.isEmpty(js))
                                webView.evaluateJavascript(js, callback);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 调用Chromium中的Native方法，加载JavaScript
     *
     * @param navigationControllerImpl          注入脚本控制器
     * @param nativeNavigationControllerAndroid 控制器ID
     * @param url                               网页地址
     * @param scriptEntities                    脚本列表
     */
    public void loadScript(long nativeNavigationControllerAndroid, Object navigationControllerImpl, String url, List<ScriptEntity> scriptEntities) {
        if (scriptEntities.size() <= 0)
            return;
        for (ScriptEntity scriptEntity : scriptEntities) {
            if (scriptEntity.getSupportPage().contains("*")) {
                String js = readScript(scriptEntity.getFileName());
                if (!TextUtils.isEmpty(js))
                    callMethod(nativeNavigationControllerAndroid, navigationControllerImpl, js);
            } else {
                String supportPage = scriptEntity.getSupportPage();
                String[] pages = supportPage.split(",");
                if (pages.length > 0) {
                    for (String page : pages) {
                        if (url.contains(page)) {
                            String js = readScript(scriptEntity.getFileName());
                            if (!TextUtils.isEmpty(js))
                                callMethod(nativeNavigationControllerAndroid, navigationControllerImpl, js);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param webView        网页视图
     * @param url            加载地址
     * @param scriptEntities 脚本列表
     */
    public void loadScript(Object webView, String url, List<ScriptEntity> scriptEntities) {
        if (scriptEntities.size() <= 0)
            return;
        for (ScriptEntity scriptEntity : scriptEntities) {
            if (TextUtils.isEmpty(scriptEntity.getSupportPage()))
                continue;
            if (scriptEntity.getSupportPage().contains("*")) {
                String js = readScript(scriptEntity.getFileName());
                if (!TextUtils.isEmpty(js))
                    callMethod(webView, js);
            } else {
                String supportPage = scriptEntity.getSupportPage();
                if (TextUtils.isEmpty(scriptEntity.getSupportPage()))
                    continue;
                String[] pages = supportPage.split(",");
                if (pages.length > 0) {
                    for (String page : pages) {
                        if (url.contains(page)) {
                            String js = readScript(scriptEntity.getFileName());
                            if (!TextUtils.isEmpty(js))
                                callMethod(webView, js);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 注入脚本
     *
     * @param mNativeNavigationControllerAndroid 控制器ID
     * @param navigationControllerImpl           注入脚本控制器
     * @param script                             脚本
     */
    private void callMethod(long mNativeNavigationControllerAndroid, Object navigationControllerImpl, String script) {
        XposedHelpers.callMethod(navigationControllerImpl, KeyUtil.CHROMIUM_NATIVE_LOAD_URL_METHOD,
                mNativeNavigationControllerAndroid,
                KeyUtil.JAVA_SCRIPT + script,
                0, 33554433, null, 0, 0, null, null, null, null, null, false, false, false);
    }

    /**
     * 注入脚本
     *
     * @param webView 网页试图
     * @param script  脚本
     */
    private void callMethod(Object webView, String script) {
        XposedHelpers.callMethod(webView, KeyUtil.TENCENT_X5_LOAD_URL_METHOD, KeyUtil.JAVA_SCRIPT + script);
    }

}
