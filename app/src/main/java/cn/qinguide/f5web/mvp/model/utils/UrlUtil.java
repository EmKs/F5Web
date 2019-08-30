package cn.qinguide.f5web.mvp.model.utils;

import android.util.Log;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/6
 * 地址优化
 */
public class UrlUtil {

    public static String FormatSupportPage(String pages) {

        if (pages.contains("*")) {
            return "所有网站";
        } else {
            String[] list = pages.split(",");
            StringBuilder pagesBuilder = new StringBuilder();
            for (String aList : list) {
                pagesBuilder.append(aList).append("\n");
            }
            pages = pagesBuilder.toString();

            return pages;
        }

    }

    public static String getDownloadFileName(String downloadUrl) {

        return downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);

    }

}
