package cn.qinguide.f5web.xposed.custom;

import java.util.ArrayList;
import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.xposed.script.InjectScript;
import cn.qinguide.f5web.xposed.utils.CacheUtil;
import cn.qinguide.f5web.xposed.utils.KeyUtil;

public class HookX5WebViewClient {

    private String url;
    private InjectScript injectScript;
    private List<ScriptEntity> scriptEntities = new ArrayList<>();

    public void initScript(){
        injectScript = new InjectScript();
        scriptEntities = injectScript.getEnabledScript();
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void injectScript(Object webView)  {
        if (!isEnabledScript() && KeyUtil.TENCENT_X5_WEBVIEW_CLASS.equals(webView.getClass().toString()))
            return;
        injectScript.loadScript(webView, url, scriptEntities);
    }

    private boolean isEnabledScript() {
        return scriptEntities != null && scriptEntities.size() > 0;
    }

}
