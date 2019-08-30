package cn.qinguide.f5web.xposed.share.cache;

/**
 * Created by yjx on 15/5/22.
 * 缓存接口
 */
public interface Cache {

    Object getString(String key);

    byte[] getBytes(String key);

    Object getObject(String key);

    Integer getInt(String key);

    Long getLong(String key);

    Double getDouble(String key);

    Float getFloat(String key);

    Boolean getBoolean(String key);

    void put(String key, Object value);

    void remove(String key);

    void clear();
}
