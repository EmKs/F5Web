package cn.qinguide.f5web.di.module.activity;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.R;
import dagger.Module;
import dagger.Provides;

import cn.qinguide.f5web.mvp.presenter.SplashPresenter;
import cn.qinguide.f5web.mvp.view.iview.ISplashView;
import cn.qinguide.f5web.mvp.model.imodel.ISplashModel;
import cn.qinguide.f5web.mvp.model.SplashModel;


@Module
public class SplashActivityModule {

    private ISplashView mIView;
    private Context context;

    public SplashActivityModule(ISplashView iView, Context context) {
        mIView = iView;
        this.context = context;
    }

    @ActivityScope
    @Provides
    ISplashView iSplashView() {
        return mIView;
    }

    @ActivityScope
    @Provides
    ISplashModel iSplashModel() {
        return new SplashModel();
    }

    @ActivityScope
    @Provides
    SplashPresenter provideSplashPresenter(ISplashView iView, ISplashModel iModel) {
        return new SplashPresenter(iView, iModel);
    }

    @Provides
    @ActivityScope
    MaterialDialog MaterialDialog(){
        return new MaterialDialog.Builder(context)
                .title(context.getString(R.string.tips)).positiveText(context.getString(R.string.agree)).build();
    }
}