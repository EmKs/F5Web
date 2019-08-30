package cn.qinguide.f5web.di.component.activity;

import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.di.module.activity.ScriptActivityModule;
import dagger.Component;

import cn.qinguide.f5web.mvp.view.activity.ScriptActivity;


@ActivityScope
@Component(modules = ScriptActivityModule.class)
public interface ScriptActivityComponent {
    void inject(ScriptActivity activity);
}