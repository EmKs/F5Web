package cn.qinguide.f5web.di.module.activity;

import android.content.Context;
import android.view.LayoutInflater;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.R;
import dagger.Module;
import dagger.Provides;

import cn.qinguide.f5web.mvp.presenter.AboutPresenter;
import cn.qinguide.f5web.mvp.view.iview.IAboutView;
import cn.qinguide.f5web.mvp.model.imodel.IAboutModel;
import cn.qinguide.f5web.mvp.model.AboutModel;


@Module
public class AboutActivityModule {
    private IAboutView mIView;
    private Context context;

    public AboutActivityModule(IAboutView iView, Context context) {
        mIView = iView;
        this.context = context;
    }

    @ActivityScope
    @Provides
    IAboutView iAboutView() {
        return mIView;
    }

    @ActivityScope
    @Provides
    IAboutModel iAboutModel() {
        return new AboutModel();
    }

    @ActivityScope
    @Provides
    AboutPresenter provideAboutPresenter(IAboutView iView, IAboutModel iModel) {
        return new AboutPresenter(iView, iModel);
    }

    @Provides
    @ActivityScope
    MaterialDialog materialDialog() {
        return new MaterialDialog.Builder(context)
                .title(context.getString(R.string.wechat_pay))
                .customView(LayoutInflater.from(context).inflate(R.layout.view_donation, null), false)
                .build();
    }
}