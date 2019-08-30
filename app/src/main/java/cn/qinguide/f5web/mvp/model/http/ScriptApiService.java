package cn.qinguide.f5web.mvp.model.http;


import cn.qinguide.f5web.app.constant.PathConstants;
import cn.qinguide.f5web.mvp.model.entity.HttpResult;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/9/28
 */
public interface ScriptApiService {

    @GET(PathConstants.GET_SCRIPT_LIST_URL)
    Observable<HttpResult<ScriptEntity>> getScriptList(@Query("page") int page, @Query("size") int size);

}
