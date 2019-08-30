package cn.qinguide.f5web.di.component.activity;

import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.di.module.activity.MainActivityModule;
import dagger.Component;

import cn.qinguide.f5web.mvp.view.activity.MainActivity;


@ActivityScope
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}