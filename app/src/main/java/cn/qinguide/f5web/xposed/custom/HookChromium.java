package cn.qinguide.f5web.xposed.custom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.qinguide.f5web.xposed.entity.Chromium;
import cn.qinguide.f5web.xposed.entity.KeyValue;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.xposed.script.InjectScript;

import cn.qinguide.f5web.xposed.utils.KeyUtil;
import de.robv.android.xposed.XposedHelpers;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/20
 */
public class HookChromium {

    private Chromium chromium;
    private long nativeNavigationControllerAndroid;
    private Object controller;
    private boolean isCreate = false;
    private InjectScript injectScript;
    private Map<String, KeyValue> controllers;
    private List<ScriptEntity> scriptEntities;

    public HookChromium(ClassLoader classLoader, InjectScript injectScript) {
        this.injectScript = injectScript;
        chromium = new Chromium();
        chromium.setLoadUrl("");
        chromium.setNavigationControllerImpl(XposedHelpers.findClass(KeyUtil.CHROMIUM_NAVIGATION_CONTROLLER_CLASS, classLoader));
        chromium.setWebContentsObserverProxy(XposedHelpers.findClass(KeyUtil.CHROMIUM_WEB_CONTENTS_OBSERVER_PROXY_CLASS, classLoader));
        controllers = new HashMap<>();
        scriptEntities = injectScript.getEnabledScript();
    }

    public void setLoadUrl(String url) {
        if (chromium.getLoadUrl().equals(url))
            return;
        chromium.setLoadUrl(url);
    }

    public void initController(Object controller, long nativeNavigationControllerAndroid) {
        isCreate = true;
        this.controller = controller;
        this.nativeNavigationControllerAndroid = nativeNavigationControllerAndroid;
    }

    public void setController(String key) {
        if (!isCreate) return;
        if (controllers.containsKey(key)) return;
        KeyValue keyValue = new KeyValue();
        keyValue.setKey(nativeNavigationControllerAndroid);
        keyValue.setValue(controller);
        keyValue.setControllerName(controller.toString());
        controllers.put(key, keyValue);
        isCreate = false;
    }

    public void injectScript(String keyName) {
        if (!controllers.containsKey(keyName))
            return;
        KeyValue keyValue = controllers.get(keyName);
        if (keyValue == null)
            return;
        injectScript.loadScript(keyValue.getKey(), keyValue.getValue(), chromium.getLoadUrl(), scriptEntities);
    }

    public void destroy(String controllerName) {
        if (controllers == null || controllers.size() == 0)
            return;
        Iterator<Map.Entry<String, KeyValue>> iterator = controllers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, KeyValue> entry = iterator.next();
            if (entry.getValue().getControllerName().equals(controllerName)) {
                iterator.remove();
            }
        }
    }

    public boolean isEnabledScript() {
        return scriptEntities != null && scriptEntities.size() > 0;
    }


}
