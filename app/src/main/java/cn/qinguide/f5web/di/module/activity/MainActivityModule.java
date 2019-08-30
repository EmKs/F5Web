package cn.qinguide.f5web.di.module.activity;

import com.ljy.devring.di.scope.ActivityScope;

import javax.inject.Named;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.view.fragment.HomeFragment;
import dagger.Module;
import dagger.Provides;

import cn.qinguide.f5web.mvp.presenter.MainPresenter;
import cn.qinguide.f5web.mvp.view.iview.IMainView;
import cn.qinguide.f5web.mvp.model.imodel.IMainModel;
import cn.qinguide.f5web.mvp.model.MainModel;


@Module
public class MainActivityModule {
    private IMainView mIView;

    public MainActivityModule(IMainView iView) {
        mIView = iView;
    }

    @ActivityScope
    @Provides
    IMainView iMainView() {
        return mIView;
    }

    @ActivityScope
    @Provides
    IMainModel iMainModel() {
        return new MainModel();
    }

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(IMainView iView, IMainModel iModel) {
        return new MainPresenter(iView, iModel);
    }

    @ActivityScope
    @Provides
    @Named("home")
    HomeFragment homeFragment() {
        return new HomeFragment();
    }
}