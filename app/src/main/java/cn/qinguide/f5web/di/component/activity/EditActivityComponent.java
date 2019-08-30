package cn.qinguide.f5web.di.component.activity;

import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.di.module.activity.EditActivityModule;
import dagger.Component;

import cn.qinguide.f5web.mvp.view.activity.EditActivity;


@ActivityScope
@Component(modules = EditActivityModule.class)
public interface EditActivityComponent {
    void inject(EditActivity activity);
}