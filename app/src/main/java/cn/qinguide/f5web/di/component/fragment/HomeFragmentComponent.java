package cn.qinguide.f5web.di.component.fragment;

import com.ljy.devring.di.scope.FragmentScope;

import cn.qinguide.f5web.di.module.fragment.HomeFragmentModule;
import dagger.Component;

import cn.qinguide.f5web.mvp.view.fragment.HomeFragment;


@FragmentScope
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {
    void inject(HomeFragment fragment);
}