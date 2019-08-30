package cn.qinguide.f5web.xposed.utils;

import android.util.Log;

import java.io.File;

import cn.qinguide.f5web.xposed.share.Share;
import de.robv.android.xposed.XposedBridge;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/2
 */
public class CacheUtil {

    public void initShare() {
        try {
            File file = new File(KeyUtil.SCRIPT_PATH);
            if (!file.exists())
                Log.i("createRootFile", String.valueOf(file.mkdirs()));
            Share.init("CACHE", 10 * 1024, file.getParent());
        } catch (NoClassDefFoundError|ExceptionInInitializerError e) {
            XposedBridge.log("F5 Web Hook App Failed Message ：" + e.getMessage());
        }

    }

}
