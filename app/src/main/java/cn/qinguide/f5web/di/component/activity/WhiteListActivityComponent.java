package cn.qinguide.f5web.di.component.activity;

import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.di.module.activity.WhiteListActivityModule;
import dagger.Component;

import cn.qinguide.f5web.mvp.view.activity.WhiteListActivity;


@ActivityScope
@Component(modules = WhiteListActivityModule.class)
public interface WhiteListActivityComponent {
    void inject(WhiteListActivity activity);
}