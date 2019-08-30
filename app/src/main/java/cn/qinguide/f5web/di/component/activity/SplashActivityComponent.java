package cn.qinguide.f5web.di.component.activity;

import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.di.module.activity.SplashActivityModule;
import dagger.Component;

import cn.qinguide.f5web.mvp.view.activity.SplashActivity;


@ActivityScope
@Component(modules = SplashActivityModule.class)
public interface SplashActivityComponent {
    void inject(SplashActivity activity);
}