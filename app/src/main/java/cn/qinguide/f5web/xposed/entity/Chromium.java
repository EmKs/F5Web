package cn.qinguide.f5web.xposed.entity;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/20
 */
public class Chromium {

    private String loadUrl;

    private Class<?> NavigationControllerImpl;

    private Class<?> WebContentsObserverProxy;

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    public Class<?> getNavigationControllerImpl() {
        return NavigationControllerImpl;
    }

    public void setNavigationControllerImpl(Class<?> navigationControllerImpl) {
        NavigationControllerImpl = navigationControllerImpl;
    }

    public Class<?> getWebContentsObserverProxy() {
        return WebContentsObserverProxy;
    }

    public void setWebContentsObserverProxy(Class<?> webContentsObserverProxy) {
        WebContentsObserverProxy = webContentsObserverProxy;
    }

}
