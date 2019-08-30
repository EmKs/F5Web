package cn.qinguide.f5web.xposed.entity;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/24
 * 存放控制器键值对
 */
public class KeyValue {

    private long key = 0;

    private Object value;

    private String controllerName;

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
