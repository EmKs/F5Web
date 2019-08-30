package cn.qinguide.f5web.app.constant;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/9/29
 */
public class KeyConstants {

    /**
     * 默认请求参数
     */
    public static int PAGE = 1;   //默认加载页数
    public static int SIZE = 10;  //默认加载数量

    //请求数据时的不同触发时机
    public static final int INIT = 3;//初始化数据
    public static final int REFRESH = 4;//刷新数据
    public static final int LOADMORE = 5;//加载更多数据

    public static String FILE_NAME = "fileName";

}
