package cn.qinguide.f5web.mvp.model.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/6
 */
public interface DownloadApiService {

    @GET
    Observable<ResponseBody> downloadFile(@Url String downloadUrl);
}
