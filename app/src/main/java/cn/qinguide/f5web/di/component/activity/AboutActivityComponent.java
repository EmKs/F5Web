package cn.qinguide.f5web.di.component.activity;

import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.di.module.activity.AboutActivityModule;
import dagger.Component;

import cn.qinguide.f5web.mvp.view.activity.AboutActivity;


@ActivityScope
@Component(modules = AboutActivityModule.class)
public interface AboutActivityComponent {
    void inject(AboutActivity activity);
}