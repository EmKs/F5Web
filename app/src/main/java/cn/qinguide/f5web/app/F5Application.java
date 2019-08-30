package cn.qinguide.f5web.app;

import android.app.Application;

import com.ljy.devring.DevRing;

import cn.qinguide.f5web.EventBusIndex;
import cn.qinguide.f5web.R;
import cn.qinguide.f5web.app.constant.PathConstants;

public class F5Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DevRing.init(this);
        DevRing.configureHttp()
                .setBaseUrl(PathConstants.BASE_URL)
                .setConnectTimeout(15);
        DevRing.configureImage()
                .setLoadingResId(R.mipmap.ic_launcher)
                .setErrorResId(R.drawable.ic_error)
                .setIsShowTransition(true);
        DevRing.configureBus().setIndex(new EventBusIndex()).setIsUseIndex(true);
        DevRing.create();
    }
}
