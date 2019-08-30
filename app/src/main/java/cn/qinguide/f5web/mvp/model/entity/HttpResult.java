package cn.qinguide.f5web.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/9/28
 * Http请求返回实体
 */
public class HttpResult<T> implements Serializable {


    /**
     * errno : 0
     * data : DataBean
     * errmsg : 执行成功
     */

    private int errno;
    private DataBeanX<T> data;
    private String errmsg;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public DataBeanX<T> getData() {
        return data;
    }

    public void setData(DataBeanX<T> data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public class DataBeanX<T> implements Serializable {
        /**
         * count : 总数
         * numsPerPage : 当前页数量
         * totalPages : 总页数
         * currentPage : 当前页数
         * data : 列表数据
         */

        private int count;
        private int numsPerPage;
        private int totalPages;
        private int currentPage;
        private List<T> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getNumsPerPage() {
            return numsPerPage;
        }

        public void setNumsPerPage(int numsPerPage) {
            this.numsPerPage = numsPerPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

    }
}
