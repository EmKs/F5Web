package cn.qinguide.f5web.mvp.model.entity;

import java.io.Serializable;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/9/28
 */
public class ScriptEntity implements Serializable {


    /**
     * id : ID
     * name : 名称
     * downloadUrl : 下载地址
     * description : 简介
     * author : 作者
     * iconUrl : 图标
     * supportPage : 支持页面
     */

    private int id;
    private String name;
    private String downloadUrl;
    private String description;
    private String author;
    private String iconUrl;
    private String supportPage;
    private boolean isEnable;
    private String fileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getSupportPage() {
        return supportPage == null ? "*" : supportPage;
    }

    public void setSupportPage(String supportPage) {
        this.supportPage = supportPage;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
